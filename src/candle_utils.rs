pub fn isBearish(candle: &Vec<f64>, open_column: usize, close_column: usize) -> bool {
   return candle[close_column] < candle[open_column]
}

pub fn isBullish(candle: &Vec<f64>, open_column: usize, close_column: usize) -> bool {
   return candle[close_column] > candle[open_column]
}


