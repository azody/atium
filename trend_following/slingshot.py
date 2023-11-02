# -*- coding: utf-8 -*-
"""
Created on Thu Nov  2 09:59:11 2023

@author: zodyac

Slingshot Pattern
- Deals witha  breakout system that confirms the new trend
- Four candlestick trend detecntion system that uses pullback method to identify a breakout

Pattern
- Bullish
    - Bullish first candle
    - And followed by a higher one confirming a bullish bias
    - And a third candle not surpassing the high of the second
    - And a final candle that must have a low at or below the high of the first candletick and a close higher than that of the second candlestick
    - The color of the second and third candles does not matter
- Bearish
    - Bearish first candle
    - And followed by a lower ont confirming a bearish bias
    - And a third candle not breaking the low of the second candle
    - And a final candlestick that has a high at or above the low of the first candsle and a close lower than the low of the second candlestick
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

           # Bullish slingshot
           if data[i, close_column] > data[i - 1, high_column] and \
              data[i, close_column] > data[i - 2, high_column] and \
              data[i, low_column] <= data[i - 3, high_column] and \
              data[i, close_column] > data[i, open_column] and \
              data[i - 1, close_column] >= data[i - 3, high_column] and \
              data[i - 2, low_column] >= data[i - 3, low_column] and \
              data[i - 2, close_column] > data[i - 2, open_column] and \
              data[i - 2, close_column] > data[i - 3, high_column] and \
              data[i - 1, high_column] <= data[i - 2, high_column]:

                    data[i + 1, buy_column] = 1

           # Bearish slingshot
           elif data[i, close_column] < data[i - 1, low_column] and \
                data[i, close_column] < data[i - 2, low_column] and \
                data[i, high_column] >= data[i - 3, low_column] and \
                data[i, close_column] < data[i, open_column] and \
                data[i - 1, high_column] <= data[i - 3, high_column] and \
                data[i - 2, close_column] <= data[i - 3, low_column] and \
                data[i - 2, close_column] < data[i - 2, open_column] and \
                data[i - 2, close_column] < data[i - 3, low_column] and \
                data[i - 1, low_column] >= data[i - 2, low_column]:

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

# Calling the Signal Function
my_data = signal(my_data, 0, 1, 2, 3, 4, 5)

# Charting the latest 150 Signals
signal_chart(my_data, 0, 4, 5, window = 200)

# Get Performance Metrics
performance(my_data, 0, 4, 5, 6, 7, 8)