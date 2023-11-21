# -*- coding: utf-8 -*-
"""
Created on Tue Nov 21 13:49:52 2023

@author: zodyac

Blockade Pattern
- 4 candlestick pattern that is difficult to visually detect
- Bullish Pattern
    - First candlestick must be bearish
    - Followed by three candlesticks with a low equal to or higher than the first candlestick but lower than its close price
    - Fourth candlestick must be bullish and must have a close price higher than the high price of the first candlestick
- Bearish Pattern
    - First candlestick mut be bullish
    - Followed by three candlesticks with a high equal to or lower than the first candlestick but lower than its close price
    - Fourth candlestick must be bearish and have a close price lower than the low price of the first candlestick

"""
from data_import import mass_import
from array_util import add_column
from chart_util import signal_chart
from performance import performance

def signal(data, open_column, high_column, low_column, close_column, buy_column, sell_column):

    data = add_column(data, 5)

    for i in range(len(data)):

       try:

           # Bullish pattern
           if data[i - 3, close_column] < data[i - 3, open_column] and \
              data[i - 2, close_column] < data[i - 3, open_column] and \
              data[i - 2, low_column] >= data[i - 3, low_column] and \
              data[i - 2, low_column] <= data[i - 3, close_column] and \
              data[i - 1, low_column] >= data[i - 3, low_column] and \
              data[i - 1, low_column] <= data[i - 3, close_column] and \
              data[i, low_column] >= data[i - 3, low_column] and \
              data[i, low_column] <= data[i - 3, close_column] and \
              data[i, close_column] > data[i, open_column] and \
              data[i, close_column] > data[i - 3, high_column]:

                    data[i + 1, buy_column] = 1

           # Bearish pattern
           elif data[i - 3, close_column] > data[i - 3, open_column] and \
                data[i - 2, close_column] > data[i - 3, open_column] and \
                data[i - 2, high_column] <= data[i - 3, high_column] and \
                data[i - 2, high_column] >= data[i - 3, close_column] and \
                data[i - 1, high_column] <= data[i - 3, high_column] and \
                data[i - 1, high_column] >= data[i - 3, close_column] and \
                data[i, high_column] <= data[i - 3, high_column] and \
                data[i, high_column] >= data[i - 3, close_column] and \
                data[i, close_column] < data[i, open_column] and \
                data[i, close_column] < data[i - 3, low_column]:

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

# Calling the Signal Function
my_data = signal(my_data, 0, 1, 2, 3, 4, 5)

# Charting the latest 150 Signals
signal_chart(my_data, 0, 4, 5, window = 200)

# Get Performance Metrics
performance(my_data, 0, 4, 5, 6, 7, 8)