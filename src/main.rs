mod data_import;

use data_import::{gemini_data_import, DataImportError};



#[tokio::main]
async fn main() -> Result<(), DataImportError> {


    let data  = gemini_data_import("btcusd", "1m").await?;

    println!("Parsed nested arrays: {:?}", data);

    Ok(())
}

//let url = "https://api.gemini.com/v2/candles/btcusd/1m"; // replace with your actual URL
