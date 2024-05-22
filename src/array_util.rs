pub fn add_column(mut data: Vec<Vec<f64>>, default_value: f64) -> Vec<Vec<f64>> {
   for row in data.iter_mut() {
       row.push(default_value);
   }
   data
}