# -*- coding: utf-8 -*-
"""
Created on Tue Nov 21 14:02:10 2023

@author: zodyac

Euphoria Pattern
- Very similar to Three Candles pattern
    - Additional condition makes it contrarian
- Bullish Pattern
    - Three succesive bearish candles
        - Each candles body must be greater than the preceeding candles
- Bearish Pattern
    - Three succesive bullish candles
        - Each candles body must be greater than the preceeding candles
- Trades best in a sideways market
- Ideal for short moves

"""
from data_import import mass_import
from array_util import add_column
from chart_util import signal_chart
from performance import performance
from rounding_util import rounding

def signal(data, open_column, close_column, buy_column, sell_column):

    data = add_column(data, 5)

    data = rounding(data, 4) # Put 0 instead of 4 as of pair 4

    for i in range(len(data)):

       try:

           # Bullish pattern
           if data[i, open_column] > data[i, close_column] and \
              data[i - 1, open_column] > data[i - 1, close_column] and \
              data[i - 2, open_column] > data[i - 2, close_column] and \
              data[i, close_column] < data[i - 1, close_column] and \
              data[i - 1, close_column] < data[i - 2, close_column] and \
              (data[i, open_column] - data[i, close_column]) > (data[i - 1, open_column] - data[i - 1, close_column]) and \
              (data[i - 1, open_column] - data[i - 1, close_column]) > (data[i - 2, open_column] - data[i - 2, close_column]):

                    data[i + 1, buy_column] = 1

           # Bearish pattern
           elif data[i, open_column] < data[i, close_column] and \
                data[i - 1, open_column] < data[i - 1, close_column] and \
                data[i - 2, open_column] < data[i - 2, close_column] and \
                data[i, close_column] > data[i - 1, close_column] and \
                data[i - 1, close_column] > data[i - 2, close_column] and \
                (data[i, open_column] - data[i, close_column]) > (data[i - 1, open_column] - data[i - 1, close_column]) and \
                (data[i - 1, open_column] - data[i - 1, close_column]) > (data[i - 2, open_column] - data[i - 2, close_column]):

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
my_data = rounding(my_data, 4)

# Calling the Signal Function
my_data = signal(my_data, 0, 3, 4, 5)

# Charting the latest 150 Signals
signal_chart(my_data, 0, 4, 5, window = 200)

# Get Performance Metrics
performance(my_data, 0, 4, 5, 6, 7, 8)