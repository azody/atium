# -*- coding: utf-8 -*-

"""
Created on Wed Nov  1 10:39:09 2023

@author: zodyac

Signal for Double Trouble pattern

Uses exogeneous variables to be validated
- Borrow information from the Average True Range (ATR) to validate the signal on the pattern



Double Trouble Pattern
- Bullish Pattern
    - 2 bullish candles with the first close price lower than the second close price
    - 2nd candlestick must be at least double the size of the previous candlesticks 10 period ATR
    - Psychology
        - Market euphoria
        - Indicator of a short squeeze
- Bearish Pattern
    - 2 bearish candles with the first close price higher than the second close price
    - 2nd candlestick must also be at least double the size of the previous canbdlestick 10-period ATR

"""

from array_util import add_column
from data_import import mass_import
from chart_util import signal_chart
from performance import performance
from rounding_util import rounding
from volatility import average_true_range

def signal(data, open_column, high_column, low_column, close_column, atr_column, buy_column, sell_column):

    data = add_column(data, 5)

    for i in range(len(data)):

        try:

            # Bullish pattern
            if data[i, close_column] > data[i, open_column] and \
                data[i, close_column] > data[i - 1, close_column] and \
                data[i - 1, close_column] > data[i - 1, open_column] and \
                data[i, high_column] - data[i, low_column] > (2 * data[i - 1, atr_column]) and \
                data[i, close_column] - data[i, open_column] > data[i - 1, close_column] - data[i - 1, open_column] and \
                data[i, buy_column] == 0:

                    data[i + 1, buy_column] = 1

            # Bearish pattern
            elif data[i, close_column] < data[i, open_column] and \
                data[i, close_column] < data[i - 1, close_column] and \
                data[i - 1, close_column] < data[i - 1, open_column] and \
                data[i, high_column] - data[i, low_column] > (2 * data[i - 1, atr_column]) and \
                data[i, open_column] - data[i, close_column] > data[i - 1, open_column] - data[i - 1, close_column] and \
                data[i, sell_column] == 0:

                    data[i + 1, sell_column] = -1

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
my_data = average_true_range(my_data, 10, 1, 2, 3, 4)

# Calling the Signal Function
my_data = signal(my_data, 0, 1, 2, 3, 4, 5, 6)

# Charting the latest 150 Signals
signal_chart(my_data, 0, 5, 6, window = 200)

# Get Performance Metrics
performance(my_data, 0, 5, 6, 7, 8, 9)
