# -*- coding: utf-8 -*-
"""
Created on Tue Jun 27 06:49:16 2023

@author: zodyac

Signal for Marubozu pattern

If the close price greater than the open price
    And the high price equals the close price
    And the low price equals the open price
    Then add a bullish indicator

If the close price is lower than the open price
    And the high price equals the open price
    And the low price equals the close price
    Then add a bearish indicator

"""
from data_import import mass_import
from array_util import add_column
from chart_util import signal_chart
from performance import performance
from rounding_util import rounding


def signal(data, open_column, high_column, low_column, close_column, buy_column, sell_column) :

    data = add_column(data, 5)

    for i in range(len(data)):
        try:

            # Bullish Pattern
            if data[i, close_column] > data[i, open_column] and data[i, high_column] == data[i, close_column] and data[i, low_column] == data[i, open_column] and data[i, buy_column] == 0:

                    data[i+1, buy_column] = 1

            # Bullish Pattern
            if data[i, close_column] < data[i, open_column] and data[i, high_column] == data[i, open_column] and data[i, low_column] == data[i, close_column] and data[i, sell_column] == 0:

                    data[i+1, sell_column] = -1

        except IndexError :
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
