// Import the necessary modules
use reqwest::Error;

// Main asynchronous function
#[tokio::main]
async fn main() -> Result<(), Error> {
    // Define the URL for the GET request
    let url = "https://api.gemini.com/v2/candles/btcusd/1m";
    println!("URL: {url}");

    // Send the GET request and await the response
    let response = reqwest::get(url).await?;

    // Check if the response status is success
    if response.status().is_success() {
        // Parse the response text
        let body = response.text().await?;
        println!("Response Body: {}", body);
    } else {
        println!("Failed to fetch data. Status: {}", response.status());
    }

    Ok(())
}