# -*- coding: utf-8 -*-
"""
Created on Tue Nov 21 11:12:19 2023

@author: zodyac

Spinning Top Pattern
- Resembles Doji Pattern but it less strict
- Hints at a rise in Volatility
- Bullish Pattern
    - First a bearish candle
    - Second a small bodied candle with long wick (highs and low)
    - Third a bullish candle
- Bearish Pattern
    - First a bullish candle
    - Second a small bodied candle with long wicks (highs and lows)
    - Third a bearish candle

Max Body Size
- Use body = 0.0003 for EURUSD, USDCHF, GBPUSD, USDCAD
- Use body = 50 for BTCUSD
- Use body = 10 for ETHUSD
- Use body = 2 for XAUUSD
- Use body = 5 for SP500m, UK100

Min Wick Size
- Use wick = 0.0005 for EURUSD, USDCHF, GBPUSD, USDCAD
- Use wick = 20 for BTCUSD
- Use wick = 5 for ETHUSD
- Use wick = 1 for XAUUSD
- Use wick = 3 for SP500m, UK100

"""

from data_import import mass_import
from array_util import add_column
from chart_util import signal_chart
from performance import performance

def signal(data, open_column, high_column, low_column, close_column, buy_column, sell_column, body, wick):

    data = add_column(data, 5)

    for i in range(len(data)):

       try:

            # Bullish pattern
            if data[i, close_column] - data[i, open_column] > body and \
               data[i - 1, high_column] - data[i - 1, close_column] >= wick and \
               data[i - 1, open_column] - data[i - 1, low_column] >= wick and \
               data[i - 1, close_column] - data[i - 1, open_column] < body and \
               data[i - 1, close_column] > data[i - 1, open_column] and \
               data[i - 2, close_column] < data[i - 2, open_column] and \
               data[i - 2, open_column] - data[i - 2, close_column] > body:

                     data[i + 1, buy_column] = 1

            # Bearish pattern
            elif data[i, open_column] - data[i, close_column] > body and \
                 data[i - 1, high_column] - data[i - 1, open_column] >= wick and \
                 data[i - 1, close_column] - data[i - 1, low_column] >= wick and \
                 data[i - 1, open_column] - data[i - 1, close_column] < body and \
                 data[i - 1, close_column] < data[i - 1, open_column] and \
                 data[i - 2, close_column] > data[i - 2, open_column] and \
                 data[i - 2, close_column] - data[i - 2, open_column] > body:

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
body = 0.0003
wick = 0.0005
my_data = signal(my_data, 0, 1, 2, 3, 4, 5, body, wick)

# Charting the latest 150 Signals
signal_chart(my_data, 0, 4, 5, window = 200)

# Get Performance Metrics
performance(my_data, 0, 4, 5, 6, 7, 8)
