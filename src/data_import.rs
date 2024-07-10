use polars::{datatypes::{Float64Chunked, ListChunked}, error::PolarsError, frame::DataFrame, prelude::NamedFrom, series::Series};
use reqwest::Error as ReqwestError;
use serde_json::Value;
use std::fmt;

// Define a custom error type
#[derive(Debug)]
pub enum DataImportError {
    PolarsError(PolarsError),
    Reqwest(ReqwestError),
    InvalidResponse(String),
    
}

impl fmt::Display for DataImportError {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        match self {
            DataImportError::PolarsError(e) => write!(f, "Request error: {}", e),
            DataImportError::Reqwest(e) => write!(f, "Request error: {}", e),
            DataImportError::InvalidResponse(msg) => write!(f, "Invalid response: {}", msg),
        }
    }
}

impl From<ReqwestError> for DataImportError {
    fn from(error: ReqwestError) -> Self {
        DataImportError::Reqwest(error)
    }
}

impl From<PolarsError> for DataImportError {
    fn from(error: PolarsError) -> Self {
        DataImportError::PolarsError(error)
    }
}

// Define an asynchronous function to fetch and parse the JSON response
pub async fn gemini_data_import(symbol: &str, time_frame: &str) -> Result<DataFrame, DataImportError> {


    let url = format!("https://api.gemini.com/v2/candles/{}/{}", symbol, time_frame); // replace with your actual URL

    // Send the GET request
    let response = reqwest::get(url).await?;

    // Ensure the request was successful
    if response.status().is_success() {
        // Parse the JSON response
        let json_response: Value = response.json().await?;
        
    
        // Convert the JSON response into a Vec<Vec<f64>>
        let array = json_response.as_array().ok_or(DataImportError::InvalidResponse(String::from("Excpected Json")))?;
        let mut vec_of_vecs: Vec<Vec<f64>> = Vec::new();
    
        for item in array {
            let inner_array = item.as_array().ok_or(DataImportError::InvalidResponse(String::from("Excpected Array of Arrays")))?;
            let mut inner_vec: Vec<f64> = Vec::new();
            for inner_item in inner_array {
                let number = inner_item.as_f64().ok_or(DataImportError::InvalidResponse(String::from("Excpected f64")))?;
                inner_vec.push(number);
            }
            vec_of_vecs.push(inner_vec);
        }
    
        // Define column names
        let column_names = vec![
            "timestamp", "open", "high", "low", "close", "volume"
        ];
    
        // Create Series for each column
        let mut series_vec = Vec::new();
        for (i, col_name) in column_names.iter().enumerate() {
            let column_data: Vec<f64> = vec_of_vecs.iter().map(|row| row[i]).collect();
            let series = Series::new(col_name, column_data);
            series_vec.push(series);
        }
    
        // Create DataFrame from the Series
        let df = DataFrame::new(series_vec);


        Ok(df?)

    } else {
        Err(DataImportError::InvalidResponse(format!("Failed to fetch data. Status: {}", response.status())))
    }
}
