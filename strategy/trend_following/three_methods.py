# -*- coding: utf-8 -*-
"""
Created on Tue Jul 10 09:41:14 2023

@author: zodyac

Signal for Three Methods pattern

Characteristics
- Rising Three Methods
    - First candlestick is a big bullish candle, followed by three smaller bodied bearish candlesticks and one final big bullish candle
    - Three bearish candles are normally contained in the range of the first bullish cnadle
    - Final bullish candle has a close higher than the first candles high
- Falling Three Methods
    - First candlestick is a big bearish candle, followed by three smaller bodied bullish candlesticks and one final big bearish candle
    - Three bullish candles are normally contained in the range of the first bearish cnadle
    - Final bearish candle has a close lower than the first candles low

Reasoning
- Market Psychology
    - Stabilizes price through correction or consolidation as traders close out of positions following a big bear/bull candle
Nuances
- Rare signal
- Rarely useful when used on its own

"""

from array_util import add_column
from chart_util import signal_chart
from data_import import mass_import
from performance import performance
from rounding_util import rounding


def signal(data, open_column, high_column, low_column, close_column, buy_column, sell_column) :
    
    data = add_column(data, 5)

    for i in range(len(data)) :
        try :
            # Bullish Pattern
            if data[i, close_column] > data[i, open_column] and \
                data[i, close_column] > data[i- 4, high_column] and \
                data[i, low_column] < data[i - 1, low_column] and \
                data[i - 1, close_column] < data[i - 4, close_column] and \
                data[i - 1, low_column] > data[i - 4, low_column] and \
                data[i - 2, close_column] < data[i - 4, close_column] and \
                data[i - 2, low_column] > data[i - 4, low_column] and \
                data[i - 3, close_column] < data[i - 4, close_column] and \
                data[i - 3, low_column] > data[i - 4, low_column] and \
                data[i - 4, close_column] > data [i - 4, open_column] :

                    data[i + 1, buy_column] = 1

            # Bearish Pattern
            if data[i, close_column] < data[i, open_column] and \
                data[i, close_column] < data[i- 4, low_column] and \
                data[i, high_column] > data[i - 1, high_column] and \
                data[i - 1, close_column] > data[i - 4, close_column] and \
                data[i - 1, high_column] < data[i - 4, high_column] and \
                data[i - 2, close_column] > data[i - 4, close_column] and \
                data[i - 2, high_column] < data[i - 4, high_column] and \
                data[i - 3, close_column] > data[i - 4, close_column] and \
                data[i - 3, high_column] < data[i - 4, high_column] and \
                data[i - 4, close_column] < data [i - 4, open_column] :
                 
                    data[i + 1, sell_column] = -1

        except IndexError : 
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
min_body = 10
my_data = signal(my_data, 0, 1, 2, 3, 4, 5)

# Charting the latest 150 Signals
signal_chart(my_data, 0, 4, 5, window = 200)

# Get Performance Metrics
performance(my_data, 0, 4, 5, 6, 7, 8)