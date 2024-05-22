mod data_import;
mod pattern;
mod array_util;

use array_util::add_column;
use data_import::{gemini_data_import, DataImportError};
use pattern::trend_following::marubozu::signal;


#[tokio::main]
async fn main() -> Result<(), DataImportError> {

    // Data is in format ["time", "open", "high", "low", "close", "volume"]
    let mut data  = gemini_data_import("btcusd", "1m").await?;

    data = add_column(data, 0.0);
    data = add_column(data, 0.0);

    let signal_data = signal(data, 1, 2, 3, 4, 6, 7);
    println !("Signal: {:?}", signal_data[0]);
    Ok(())
}

//let url = "https://api.gemini.com/v2/candles/btcusd/1m"; // replace with your actual URL
