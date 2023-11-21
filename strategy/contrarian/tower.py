# -*- coding: utf-8 -*-
"""
Created on Tue Nov 21 11:56:47 2023

@author: zodyac

Tower Pattern
- Multi candle trend
    - Generally 5 candles
    - Signals the end of a gradual trend
- Bullish pattern
    - Starts with a bearish candle
    - Followed by 3 smaller candles
        - Middle of the three must be slightly lower than the other two
    - Last, a normal sized bullish candlestick
- Bearish Pattern
    - Starts with a bulliosh candle
    - Followed by 3 smaller candles
        - Middle of the three must be higher than the other two
    - Lastly, a normal sized bearish candlestick
- Variations
    - Can vary the number of candles in the middle

Body Size
- Use body = 0.0005 for EURUSD, USDCHF, GBPUSD, USDCAD
- Use body = 50 for BTCUSD
- Use body = 10 for ETHUSD
- Use body = 2 for XAUUSD
- Use body = 10 for SP500m, UK100

"""
from data_import import mass_import
from array_util import add_column
from chart_util import signal_chart
from performance import performance
from rounding_util import rounding

def signal(data, open_column, high_column, low_column, close_column, buy_column, sell_column, body):

    data = add_column(data, 5)

    for i in range(len(data)):

       try:

           # Bullish pattern
           if data[i, close_column] > data[i, open_column] and \
              data[i, close_column] - data[i, open_column] > body and \
              data[i - 2, low_column] < data[i - 1, low_column] and \
              data[i - 2, low_column] < data[i - 3, low_column] and \
              data[i - 4, close_column] < data[i - 4, open_column] and \
              data[i - 4, open_column] - data[i, close_column] > body:

                    data[i + 1, buy_column] = 1

           # Bearish pattern
           elif data[i, close_column] < data[i, open_column] and \
                data[i, open_column] - data[i, close_column] > body and \
                data[i - 2, high_column] > data[i - 1, high_column] and \
                data[i - 2, high_column] > data[i - 3, high_column] and \
                data[i - 4, close_column] > data[i - 4, open_column] and \
                data[i - 4, close_column] - data[i, open_column] > body:

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
my_data = rounding(my_data, 5)

# Calling the Signal Function
body = 0.0005

my_data = signal(my_data, 0, 1, 2, 3, 4, 5, body)

# Charting the latest 150 Signals
signal_chart(my_data, 0, 4, 5, window = 200)

# Get Performance Metrics
performance(my_data, 0, 4, 5, 6, 7, 8)