# -*- coding: utf-8 -*-
"""
Created on Thu Nov  2 11:34:30 2023

@author: zodyac

H Pattern
- 3 candlestick configuration
- Bullish
    - First candle is bullish
    - Second candle is an indecision
        - Open = Close
    - Third candle is bullish
        - And close pirce must be higher than the indecisions close
        - And low of the third must be higher than the low of the indecision candle
- Bearish
    - First candle is bearish
    - Next candlie is an indecision
        - Open = close
    - Third candle is bearish
        - And is clost must be lower than the indecision candle close
        - And the high must be lower than the high of the indecision candle
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
              data[i, close_column] > data[i - 1, close_column] and \
              data[i, low_column] > data[i - 1, low_column] and \
              data[i - 1, close_column] == data[i - 1, open_column] and \
              data[i - 2, close_column] > data[i - 2, open_column] and \
              data[i - 2, high_column] < data[i - 1, high_column]:

                    data[i + 1, buy_column] = 1

           # Bearish pattern
           elif data[i, close_column] < data[i, open_column] and \
                data[i, close_column] < data[i - 1, close_column] and \
                data[i, low_column] < data[i - 1, low_column] and \
                data[i - 1, close_column] == data[i - 1, open_column] and \
                data[i - 2, close_column] < data[i - 2, open_column] and \
                data[i - 2, low_column] > data[i - 1, low_column]:

                    data[i + 1, sell_column] = -1

       except IndexError:

            pass

    return data


# Choose an Asset
pair = 0 # EURUSD

# Time frame
horizon = "M1"

# Importing the asset as an array
my_data = mass_import(pair, horizon)
my_data = rounding(my_data, 5)

# Calling the Signal Function
my_data = signal(my_data, 0, 1, 2, 3, 4, 5)

# Charting the latest 150 Signals
signal_chart(my_data, 0, 4, 5, window = 200)

# Get Performance Metrics
performance(my_data, 0, 4, 5, 6, 7, 8)