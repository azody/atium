pub fn is_bearish(candle: &Vec<f64>, open_column: usize, close_column: usize) -> bool {
   return candle[close_column] < candle[open_column]
}

pub fn is_bullish(candle: &Vec<f64>, open_column: usize, close_column: usize) -> bool {
   return candle[close_column] > candle[open_column]
}