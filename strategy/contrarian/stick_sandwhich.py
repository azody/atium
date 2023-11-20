# -*- coding: utf-8 -*-
"""
Created on Mon Nov 20 10:18:03 2023

@author: zodyac

Stick Sandwhich Pattern
- 3 candle contrarian configuration composed of candlseticks that alternate in color
- Bullish
    - First a bearish candlestick
    - Second a bullish candlestich that is smaller than the first candlestick
    - Last, a bearish candlestich that is larger than the second candlestick
- Bearish
    - First a bullish candlestick
    - Second a bearish candlestick that is smaller than the first candlestick
    - Last, a bullish candlestick that is greater than the second
- Logic
    - By failing to form new lows in the bullish scenario, the market may have found support
"""

from data_import import mass_import
from array_util import add_column
from chart_util import signal_chart
from performance import performance
from rounding_util import rounding

def signal(data, open_column, high_column, low_column, close_column, buy_column, sell_column):

    data = add_column(data, 5)

    for i in range(len(data)):

       try:

           # Bullish pattern
           if data[i, close_column] < data[i, open_column] and \
              data[i, high_column] > data[i - 1, high_column] and \
              data[i, low_column] < data[i - 1, low_column] and \
              data[i - 1, close_column] > data[i - 1, open_column] and \
              data[i - 2, close_column] < data[i - 2, open_column] and \
              data[i - 2, high_column] > data[i - 1, high_column] and \
              data[i - 2, low_column] < data[i - 1, low_column] and \
              data[i - 2, close_column] < data[i - 3, close_column] and \
              data[i - 3, close_column] < data[i - 3, open_column]:

                    data[i + 1, buy_column] = 1

           # Bearish pattern
           elif data[i, close_column] > data[i, open_column] and \
                data[i, high_column] > data[i - 1, high_column] and \
                data[i, low_column] < data[i - 1, low_column] and \
                data[i - 1, close_column] < data[i - 1, open_column] and \
                data[i - 2, close_column] > data[i - 2, open_column] and \
                data[i - 2, high_column] > data[i - 1, high_column] and \
                data[i - 2, low_column] < data[i - 1, low_column] and \
                data[i - 2, close_column] > data[i - 3, close_column] and \
                data[i - 3, close_column] > data[i - 3, open_column]:

                    data[i + 1, sell_column] = -1

       except IndexError:

            pass

    return data


# Choose an Asset
pair = 1 # EURUSD

# Time frame
horizon = "H1"

# Importing the asset as an array
my_data = mass_import(pair, horizon)
my_data = rounding(my_data, 5)

# Calling the Signal Function
my_data = signal(my_data, 0, 1, 2, 3, 4, 5)

# Charting the latest 150 Signals
signal_chart(my_data, 0, 4, 5, window = 200)

# Get Performance Metrics
performance(my_data, 0, 4, 5, 6, 7, 8)
