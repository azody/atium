# -*- coding: utf-8 -*-
"""
Created on Wed Nov  1 13:04:37 2023

@author: zodyac


Bottle Pattern
- Bullish Pattern
    - Bullish Candle followed by another bullish candle
    - And no wick on the low side
    - And with a wick on the high side
    - And the second candle must open below the last candles close
        - Gap lower
    - Psychology: No new low indicates bullish pressure
- Bearish Pattern
    - Bearish Candles followed by another bearish candle
    - And with no wick on the high side
    - And with a wick on the low side
    - Ane the second candle must open above the last cadlesticks close
        - Gap higher

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
              data[i, open_column] == data[i, low_column] and \
              data[i - 1, close_column] > data[i - 1, open_column] and \
              data[i, open_column] < data[i - 1, close_column] and \
              data[i, buy_column] == 0:

                    data[i + 1, buy_column] = 1

           # Bearish pattern
           elif data[i, close_column] < data[i, open_column] and \
                data[i, open_column] == data[i, high_column] and \
                data[i - 1, close_column] < data[i - 1, open_column] and \
                data[i, open_column] > data[i - 1, close_column] and \
                data[i, sell_column] == 0:

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