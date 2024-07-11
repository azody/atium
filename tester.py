from data_import import mass_import
from array_util import add_column
from chart_util import signal_chart
from performance import performance
from rounding_util import rounding
from pattern.trend_following.marubozu import signal

# Choose an Asset
pair = 0 # EURUSD

# Time frame
horizon = "M5"

# Importing the asset as an array
my_data = mass_import(pair, horizon)
my_data = rounding(my_data, 5)

# Calling the Signal Function
my_data = signal(my_data, 0, 1, 2, 3, 4, 5)

# Charting the latest 150 Signals
signal_chart(my_data, 0, 4, 5, window = 200)

# Get Performance Metrics
performance(my_data, 0, 4, 5, 6, 7, 8)
