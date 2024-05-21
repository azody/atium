# -*- coding: utf-8 -*-
"""
Created on Tue Nov 21 13:38:27 2023

@author: zodyac

Doppelganger Pattern
- Bullish Pattern
    - First a bearish candle
    - Followed by two numerically similar candlesticks
        - Same highs and lows
        - Either they have the same close and open prices or the close price must equal the open price
        - Candlesticls canbe bullish or bearish
- Bearish Pattern
    - First a bullish pattern
    - Followed by two numerically similar candlesticks
        - Same highs and lows
        - Either they have the same close and open prices or the close price must equal the open price
        - Candlesticls canbe bullish or bearish
- Optional condition
    - Confirm bullish reversal
        - Next candle surpasses similar highs
    - COnfirm bearish reversal
        - Next candle surpasses similar lows

"""
from data_import import mass_import
from array_util import add_column
from chart_util import signal_chart
from performance import performance
from rounding_util import rounding


def signal(data, open_column, high_column, low_column, close_column, buy_column, sell_column):

    data = add_column(data, 5)

    data = rounding(data, 4) # Put 0 instead of 4 as of pair 4

    for i in range(len(data)):

       try:

           # Bullish pattern
           if data[i - 2, close_column] < data[i - 2, open_column] and \
              data[i - 1, close_column] < data[i - 2, open_column] and \
              data[i, high_column] == data[i - 1, high_column] and \
              data[i, low_column] == data[i - 1, low_column]:

                    data[i + 1, buy_column] = 1

           # Bearish pattern
           elif data[i - 2, close_column] > data[i - 2, open_column] and \
                data[i - 1, close_column] > data[i - 2, open_column] and \
                data[i, high_column] == data[i - 1, high_column] and \
                data[i, low_column] == data[i - 1, low_column]:

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
my_data = rounding(my_data, 4)

# Calling the Signal Function
my_data = signal(my_data, 0, 1, 2, 3, 4, 5)

# Charting the latest 150 Signals
signal_chart(my_data, 0, 4, 5, window = 200)

# Get Performance Metrics
performance(my_data, 0, 4, 5, 6, 7, 8)


