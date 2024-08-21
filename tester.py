import numpy as np
import numpy.lib.recfunctions as rfn

from data_import import mass_import
from array_util import add_column
from chart_util import signal_chart
from performance import performance
from rounding_util import rounding
from pattern.trend_following.tasuki import signal

# Choose an Asset
pair = 0 # EURUSD

# Time frame
horizon = "M5"

# Importing the asset as an array and structuring data -- should probably do elsewhere
my_data = mass_import(pair, horizon)

#my_data = rounding(my_data, 5)
my_data = add_column(my_data, 5)

# Calling the Signal Function
#my_data = signal(my_data, 0, 3, 4, 5, 0.0000001)
my_data = signal(my_data, 0, 3, 4, 5)
# Charting the latest 150 Signals
signal_chart(my_data, 0, 4, 5, window = 200)

# Get Performance Metrics
performance(my_data, 0, 4, 5, 6, 7, 8)
