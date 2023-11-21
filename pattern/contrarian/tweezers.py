# -*- coding: utf-8 -*-
"""
Created on Tue Nov  7 13:34:11 2023

@author: zodyac

Tweezers Pattern
- Bullish Pattern
    - First a bearish candle
    - Then a bullish candlestick
        - The bullish candlestick shares a low with the bearish candlestick
- Bearish Pattern
    - First a bullish candlestick
    - Then a bearish candlestick
        - The bearish candlestick shares a high with the bullish candlestick

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
           # Note: the the example code contained a max_body
           # data[i, close_column] - data[i, open_column] < body and \
           # data[i - 1, close_column] - data[i - 1, open_column] < body and \
           if data[i, close_column] > data[i, open_column] and \
              data[i, low_column] == data[i - 1, low_column] and \
              data[i - 1, close_column] < data[i - 1, open_column] and \
              data[i - 2, close_column] < data[i - 2, open_column]:

                    data[i + 1, buy_column] = 1

           # Bearish pattern
           # data[i, close_column] - data[i, open_column] < body and \
           # data[i - 1, close_column] - data[i - 1, open_column] < body and \
           elif data[i, close_column] < data[i, open_column] and \
                data[i, high_column] == data[i - 1, high_column] and \
                data[i - 1, close_column] > data[i - 1, open_column] and \
                data[i - 2, close_column] > data[i - 2, open_column]:

                    data[i + 1, sell_column] = -1

       except IndexError:

            pass

    return data


# Choose an Asset
pair = 0 # EURUSD

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