# -*- coding: utf-8 -*-
"""
Created on Tue Jul 10 10:18:33 2023

@author: zodyac

Signal for Hikkake pattern

Characteristics
- Bullish Pattern
    - Start wiht a bullish candlestick followed by a bearish candlestick completely embedded in the first one
    - Then 2 candlesticks must appear with a high that does surpass the second candles high
    - Finally a big bullish candlestick appears with a close that surpasses the high of the second candlestick
- Bearish Pattern
    - Start wiht a bearish candlestick followed by a bullish candlestick completely embedded in the first one
    - Then 2 candlesticks must appear with a low that does surpass the second candles low
    - Finally a big bearish candlestick appears with a close that surpasses the low of the second candlestick
Reasoning
- Market Psychology
    - Historical Bear Trap
"""

from data_import import mass_import
from array_util import add_column
from chart_util import signal_chart
from performance import performance
from rounding_util import rounding

def signal(data, open_column, high_column, low_column, close_column, buy_signal, sell_signal) :
    
    data = add_column(data, 5)

    for i in range(len(data)) :

        try:
            
            # Bullish Pattern
            if data[i, close_column] > data[i - 3, high_column] and \
                data[i, close_column] > data[i - 4, close_column] and \
                data[i - 1, low_column] < data[i, open_column] and \
                data[i - 1, close_column] < data[i, close_column] and \
                data[i - 1, high_column] <= data[i - 3, high_column] and \
                data[i - 2, low_column] < data[i, open_column] and \
                data[i - 2, close_column] < data[i, close_column] and \
                data[i - 2, high_column] <= data[i - 3, high_column] and \
                data[i - 3, high_column] < data[i - 4, high_column] and \
                data[i - 3, low_column] > data[i - 4, low_column] and \
                data[i - 4, close_column] > data[i - 4, open_column] :

                    data[i + 1, buy_signal] = 1

            elif data[i, close_column] < data[i - 3, low_column] and \
                data[i, close_column] < data[i - 4, close_column] and \
                data[i - 1, high_column] > data[i, open_column] and \
                data[i - 1, close_column] > data[i, close_column] and \
                data[i - 1, low_column] >= data[i - 3, low_column] and \
                data[i - 2, high_column] > data[i, open_column] and \
                data[i - 2, close_column] > data[i, close_column] and \
                data[i - 2, low_column] >= data[i - 3, low_column] and \
                data[i - 3, low_column] > data[i - 4, low_column] and \
                data[i - 3, high_column] < data[i - 4, high_column] and \
                data[i - 4, close_column] < data[i - 4, open_column] :

                    data[i + 1, sell_signal] = -1

        except IndexError:
            pass

    return data

# Choose an Asset
pair = 0 # EURUSD

# Time frame
horizon = "M5"

# Importing the asset as an array
my_data = mass_import(pair, horizon)
my_data = rounding(my_data, 5)

# Calling the Signal Function
my_data = signal(my_data, 0, 1, 2, 3, 4, 5)

# Charting the latest 150 Signals
signal_chart(my_data, 0, 4, 5, window = 200)

# Get Performance Metrics
performance(my_data, 0, 4, 5, 6, 7, 8)
