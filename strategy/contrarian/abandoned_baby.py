# -*- coding: utf-8 -*-
"""
Created on Tue Nov 21 09:40:08 2023

@author: zodyac

Abandoned Baby Pattern
 - Extremely rare configuration
 - Bullish Pattern
    - First a bearish candle
    - Second a doji that gaps lower
    - Third a bullish candlestick
 - Bearish Pattern
    - First a bullish candle
    - Second a doji that gaps higher
    - Third a bearish candlestick

Rounding
 - Use my_data = rounding(my_data, 4) for EURUSD, USDCHF, GBPUSD, USDCAD
 - Use my_data = rounding(my_data, 0) for BTCUSD, ETHUSD, XAUUSD, SP500m, UK100

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
               data[i - 1, close_column] == data[i - 1, open_column] and \
               data[i - 1, high_column] < data[i, low_column] and \
               data[i - 1, high_column] < data[i - 2, low_column] and \
               data[i - 2, close_column] < data[i - 2, open_column]:

                     data[i + 1, buy_column] = 1

            # Bearish pattern
            elif data[i, close_column] < data[i, open_column] and \
                 data[i - 1, close_column] == data[i - 1, open_column] and \
                 data[i - 1, low_column] > data[i, high_column] and \
                 data[i - 1, low_column] > data[i - 2, high_column] and \
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

my_data = rounding(my_data, 4)

# Calling the Signal Function
my_data = signal(my_data, 0, 1, 2, 3, 4, 5)

# Charting the latest 150 Signals
signal_chart(my_data, 0, 4, 5, window = 200)

# Get Performance Metrics
performance(my_data, 0, 4, 5, 6, 7, 8)

