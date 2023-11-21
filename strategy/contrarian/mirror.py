# -*- coding: utf-8 -*-
"""
Created on Tue Nov 21 14:22:05 2023

@author: zodyac

Mirror Pattern
- Bullish Pattern
    - First Candle stick is bearish
    - Next two candlesticks that have the same highs and lows
    - Last candlestick is bullish
        - Must have a high price equal to the high price of the first candlestick
- Bearish Pattern
    - First candle is bullish
    - Next two candlesticks have the same highs and lows
    - Last candlestick is bearish
        - Must have a low price equal to the low price of the first candlestick

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
              data[i, high_column] == data[i - 3, high_column] and \
              data[i, close_column] > data[i - 1, close_column] and \
              data[i, close_column] > data[i - 2, close_column] and \
              data[i, close_column] > data[i - 3, close_column] and \
              data[i - 3, close_column] < data[i - 3, open_column] and \
              data[i - 1, close_column] == data[i - 2, close_column]:


                    data[i + 1, buy_column] = 1

           # Bearish pattern
           elif data[i, close_column] < data[i, open_column] and \
                data[i, low_column] == data[i - 3, low_column] and \
                data[i, close_column] < data[i - 1, close_column] and \
                data[i, close_column] < data[i - 2, close_column] and \
                data[i, close_column] < data[i - 3, close_column] and \
                data[i - 3, close_column] > data[i - 3, open_column] and \
                data[i - 1, close_column] == data[i - 2, close_column]:

                    data[i + 1, sell_column] = -1

       except IndexError:

            pass

    return data

# Choose an Asset
pair = 1 # EURUSD

# Time frame
horizon = "M1"

# Importing the asset as an array
my_data = mass_import(pair, horizon)
my_data = rounding(my_data, 4)

# Calling the Signal Function
my_data = signal(my_data, 0, 1, 2, 3, 4, 5)

# Charting the latest 150 Signals
signal_chart(my_data, 0, 4, 5, window = 200)

# Get Performance Metrics
performance(my_data, 0, 4, 5, 6, 7, 8)
