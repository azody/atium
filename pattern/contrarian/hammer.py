# -*- coding: utf-8 -*-
"""
Created on Mon Nov 20 10:28:52 2023

@author: zodyac


Hammer Pattern
- One of 4 simliar patterns
    - Shooting Star, hanging Man, Hammer and Inverted Hammer
- Single candle configuration
- Bullish Pattern
    - A bullish candlestick with a long low wick and no high wick
    - It also must have a relatively small body
- Bearish Pattern
    - A long high wick and no low wich
    - It also must have a relatively small body
- Logic
    - After shaping extreme lows, the buyers have managed to close higher than the open price

Max candle height guide
 - Use body = 0.0005 for EURUSD, USDCHF, GBPUSD, USDCAD
 - Use body = 50 for BTCUSD
 - Use body = 10 for ETHUSD
 - Use body = 2 for XAUUSD
 - Use body = 5 for SP500m, UK100

Min Candle Wick height guide
 - Use wick = 0.0002 for EURUSD, USDCHF, GBPUSD, USDCAD
 - Use wick = 10 for BTCUSD
 - Use wick = 2 for ETHUSD
 - Use wick = 0.5 for XAUUSD
 - Use wick = 3 for SP500m, UK100

Rounding
 - Use my_data = rounding(my_data, 4) for EURUSD, USDCHF, GBPUSD, USDCAD
 - Use my_data = rounding(my_data, 0) for BTCUSD, ETHUSD, XAUUSD, SP500m, UK100

"""
from data_import import mass_import
from array_util import add_column
from chart_util import signal_chart
from performance import performance
from rounding_util import rounding

def signal(data, open_column, high_column, low_column, close_column, buy_column, sell_column, body, wick):

    data = add_column(data, 5)

    for i in range(len(data)):

       try:

            # Bullish pattern
            if data[i, close_column] > data[i, open_column] and \
               abs(data[i - 1, close_column] - data[i - 1, open_column]) < body and \
               min(data[i - 1, close_column], data[i - 1, open_column]) - data[i - 1, low_column] > 2 * wick and \
               data[i - 1, close_column] == data[i - 1, high_column] and \
               data[i - 2, close_column] < data[i - 2, open_column]:

                     data[i + 1, buy_column] = 1

            # Bearish pattern
            elif data[i, close_column] < data[i, open_column] and \
                 abs(data[i - 1, close_column] - data[i - 1, open_column]) < body and \
                 data[i - 1, high_column] - max(data[i - 1, close_column], data[i - 1, open_column]) > 2 * wick and \
                 data[i - 1, close_column] == data[i - 1, low_column] and \
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
my_data = signal(my_data, 0, 1, 2, 3, 4, 5, 0.0005, 0.0002)

# Charting the latest 150 Signals
signal_chart(my_data, 0, 4, 5, window = 200)

# Get Performance Metrics
performance(my_data, 0, 4, 5, 6, 7, 8)
