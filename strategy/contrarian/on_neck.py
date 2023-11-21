# -*- coding: utf-8 -*-
"""
Created on Tue Nov  7 13:01:34 2023

@author: zodyac

On Neck Pattern
- Bullish Pattern
    - First, a bearish candlestick
    - And then a second bullish candlestick
        - The second candlestick opens at a gap lower
        - The second candlestick closes exactly at the close price of the first candlestick
- Bearish Pattern
    - First, a bullish candlestick
    - And then a second bearish candlestick
        - The second candlestick opens at a gap higher
        - THe second candlestick closes exactly at the close price of the first candlestick
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
           if data[i, close_column] > data[i, open_column] and \
              data[i, close_column] == data[i - 1, close_column] and \
              data[i, open_column] < data[i - 1, close_column] and \
              data[i - 1, close_column] < data[i - 1, open_column]:

                    data[i + 1, buy_column] = 1

           # Bearish pattern
           elif data[i, close_column] < data[i, open_column] and \
                data[i, close_column] == data[i - 1, close_column] and \
                data[i, open_column] > data[i - 1, close_column] and \
                data[i - 1, close_column] > data[i - 1, open_column]:

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

