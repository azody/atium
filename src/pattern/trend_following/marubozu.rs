pub fn signal(mut data: Vec<Vec<f64>>, open_column: usize, high_column: usize, low_column: usize, close_column: usize, buy_column: usize, sell_column: usize) -> Vec<Vec<f64>> {

   let mut new_data = data.clone(); // Create a new vector to store modified data
    
   for i in 0..data.len() {
       if let Some(current) = data.get(i) {
           if current[close_column] > current[open_column] &&
               current[high_column] == current[close_column] &&
               current[low_column] == current[open_column] &&
               current[buy_column] == 0.0 {
                   
                   if let Some(next) = new_data.get_mut(i + 1) {
                       next[buy_column] = 1.0;
                   }
           }

           if current[close_column] < current[open_column] &&
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
