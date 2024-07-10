use crate::candle_utils::*;

/**

Signal for Marubozu pattern

If the close price greater than the open price
    And the high price equals the close price
    And the low price equals the open price
    Then add a bullish indicator

If the close price is lower than the open price
    And the high price equals the open price
    And the low price equals the close price
    Then add a bearish indicator


Characteristics
- Candles do not have any wicks
    - Bullish has the same open as the low and the close as the high price
    - Bearish has has the same open as the high price and the same close as the low price
- Occurs during shorter time frames
    - 1 and 5 minute charts
Reasoning
- Market Psychology
    - Maximum force of demand is when there is only upward movement
- Most powerful representation of bullish or bearish activity
Nuances
- May be difficult in some markets
    - Currencies go out to 5 decimal places
    - Consider rounding in this scenario

 */
#[allow(dead_code)]
pub fn signal(data: Vec<Vec<f64>>, open_column: usize, high_column: usize, low_column: usize, close_column: usize, buy_column: usize, sell_column: usize) -> Vec<Vec<f64>> {

   let mut new_data = data.clone(); // Create a new vector to store modified data
    
   for i in 0..data.len() {
       if let Some(current) = data.get(i) {
           if is_bullish(current, open_column, close_column) &&
               current[high_column] == current[close_column] &&
               current[low_column] == current[open_column] &&
               current[buy_column] == 0.0 {
                   
                   if let Some(next) = new_data.get_mut(i + 1) {
                       next[buy_column] = 1.0;
                   }
           }

           if is_bearish(current, open_column, close_column) &&
               current[high_column] == current[open_column] &&
               current[low_column] == current[close_column] &&
               current[sell_column] == 0.0 {
                   
                   if let Some(next) = new_data.get_mut(i + 1) {
                       next[sell_column] = -1.0;
                   }
           }
       }
   }
   new_data
}