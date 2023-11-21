# -*- coding: utf-8 -*-
"""
Created on Mon Nov 20 10:54:56 2023

@author: zodyac

Star Pattern
- 3 Candle configuration
    - Less common than others as it relies on a gap
- Bullish Pattern (Morning Star)
    - First, a bearish candle
    - Second, a small bodied candle that gaps below it
        - Does not matter if beraish or bullish
    - Third, a bullish candle which gaps higher than the middle candle
- Bearish Pattern (Evening Star)
    - First, a nullish candle
    - Second, a small bodied candle that gaps above it
        - Does not matter if beraish or bullish
    - Third, a bearish candle which gaps lower than the middle candle
- Logic
    - The market has made a perfectly shaped U Turn after discovering a new high/low


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
               max(data[i - 1, close_column], data[i - 1, open_column]) < data[i, open_column] and \
               max(data[i - 1, close_column], data[i - 1, open_column]) < data[i - 2, close_column] and \
               data[i - 2, close_column] < data[i - 2, open_column]:

                     data[i + 1, buy_column] = 1

            # Bearish pattern
            elif data[i, close_column] < data[i, open_column] and \
                 min(data[i - 1, close_column], data[i - 1, open_column]) > data[i, open_column] and \
                 min(data[i - 1, close_column], data[i - 1, open_column]) > data[i - 2, close_column] and \
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