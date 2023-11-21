# -*- coding: utf-8 -*-
"""
Created on Tue Nov 21 15:56:29 2023

@author: zodyac
"""

from array_util import add_column
from chart_util import signal_chart
from performance import performance
from rounding_util import rounding
from gemini.gemini import gemini_candles_import

def bottle_signal(data, open_column, high_column, low_column, close_column, buy_column, sell_column):

    data = add_column(data, 5)

    for i in range(len(data)):

       try:


           # Buy Indicator pattern
           if data[i, close_column] > data[i, open_column] and \
              data[i, open_column] == data[i, low_column] and \
              data[i - 1, close_column] > data[i - 1, open_column] and \
              data[i, open_column] < data[i - 1, close_column] and \
              data[i, buy_column] == 0:

                    data[i + 1, buy_column] = 1

           # Exit Strategy
           elif data[i -4, buy_column] == 1:
               data[i + 1, sell_column] = 1

       except IndexError:

            pass

    return data



# Choose an Asset
pair = "xrpusd"

# Time frame
horizon = "5m"

# Importing the asset as an array
my_data = gemini_candles_import(pair, horizon)
#my_data = rounding(my_data, 0)

# Calling the Signal Function
my_data = bottle_signal(my_data, 1, 2, 3, 4, 6, 7)


# Get Performance Metrics
performance(my_data, 1, 6, 7, 8, 9, 10)