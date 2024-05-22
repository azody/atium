use reqwest::Error as ReqwestError;
use serde_json::Value;
use std::fmt;

// Define a custom error type
#[derive(Debug)]
pub enum DataImportError {
    Reqwest(ReqwestError),
    InvalidResponse(String),
}

impl fmt::Display for DataImportError {
    fn fmt(&self, f: &mut fmt::Formatter<'_>) -> fmt::Result {
        match self {
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

// Define an asynchronous function to fetch and parse the JSON response
pub async fn gemini_data_import(symbol: &str, time_frame: &str) -> Result<Vec<Vec<f64>>, DataImportError> {


    let url = format!("https://api.gemini.com/v2/candles/{}/{}", symbol, time_frame); // replace with your actual URL

    // Send the GET request
    let response = reqwest::get(url).await?;

    // Ensure the request was successful
    if response.status().is_success() {
        // Parse the response body as JSON
        let body: Value = response.json().await?;

        // Check if the body is an array of arrays and map it to Vec<Vec<f64>>
        if let Some(arrays) = body.as_array() {
            let nested_vec: Vec<Vec<f64>> = arrays.iter()
                .filter_map(|array| {
                    if let Some(inner_array) = array.as_array() {
                        Some(inner_array.iter()
                            .filter_map(|v| v.as_f64())
                            .collect())
                    } else {
                        None
                    }
                })
                .collect();

            Ok(nested_vec)
        } else {
            Err(DataImportError::InvalidResponse("Response was not an array of arrays".to_string()))
        }
    } else {
        Err(DataImportError::InvalidResponse(format!("Failed to fetch data. Status: {}", response.status())))
    }
}
