mod data_import;

use data_import::{gemini_data_import, DataImportError};



#[tokio::main]
async fn main() -> Result<(), DataImportError> {
    let url = "https://api.gemini.com/v2/candles/btcusd/1m"; // replace with your actual URL

    match gemini_data_import(url).await {
        Ok(nested_vec) => {
            println!("Parsed nested arrays: {:?}", nested_vec);
            Ok(())
        }
        Err(e) => {
            println!("Error: {:?}", e);
            Err(e)
        }
    }
}

//let url = "https://api.gemini.com/v2/candles/btcusd/1m"; // replace with your actual URL
