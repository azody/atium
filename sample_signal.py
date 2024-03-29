# -*- coding: utf-8 -*-
"""
Created on Thu Jun  8 15:56:04 2023

@author: zodyac

A long signal is generated on the next open whenever
    1. When the current low is lower than the low price 5 seconds ago
    2. And the current low price is lower than the low price 13 seconds ago
    3. And the current low price higher than the low price 21 seconds ago
    4. The close price of the current of the current bar must be higher than the close price 3 periods ago

A short signal is generate on the next open whenever
    1. The current high price is higher than the high price 5 periods ago
    2. And the current high price is higher than the high price 13 periods ago
    3. And the current high price is lower than the high price 21 periods ago
    4. The close price of the current bar must be lower than the close price 3 periods ago
"""

from array_util import add_column
from data_import import mass_import
from chart_util import signal_chart
from performance import performance
from indicator import moving_average, relative_strength_indicator

# Signal function scans each row and leaves a trace if all conditions are met
# Traces are buy and sell proxy orders
# For OHLC Data
#   index 0 referst to the open price
#   index 1 refers to the high price
#   index 2 refers to the low price
#   index 3 refers to the close price
# For this specific signal
#   index 4 referst to the buy signal column
#   index 5 refers to the sell signal column
def signal(data) :
    # Add 2 columns
    data = add_column(data, 5)

    for i in range(len(data)) :
        try:
            # Long Signal
            # data[i, 4] == 0 is to avoid signals that are a repetitive pattern
            # proxy foir a buy is 1
            if data[i,2] < data[i-5, 2] and data[i,2] < data[i - 13, 2] and data[i - 21, 2] and data[i, 3] > data[i - 1, 3] and data[i, 4] == 0:
                data[i + 1, 4] = 1
            # Sell Signal
            # data[i, 5] == 0 is to avoid signals that are a repetitive pattern
            # Proxy for a sell is -1
            elif data[i, 1] > data[i - 21, 1] and data[i, 3] < data[i - 1, 3] and data[i, 5] == 0:
                data[i + 1, 5] = -1
        except IndexError:
            pass

    return data


# Choose an Asset
pair = 0 #

# Time frame
horizon = "H1"

# Importing the asset as an array
my_data = mass_import(pair, horizon)

# Calling the Signal Function
my_data = signal(my_data)

# Charting the latest 150 Signals
signal_chart(my_data, 0, 4, 5, window = 150)

# Get Performance Metrics
performance(my_data, 0, 4, 5, 6, 7, 8)

# Get Moving Average
my_data = add_column(my_data, 1)
lookback = 30
close_column = 3
moving_average_column = 10
my_data = moving_average(my_data, lookback, close_column, moving_average_column)

# Get Relative Strength Index
my_data = add_column(my_data, 1)
lookback = 14
close_column = 3
position = 11
# Follow up on this pieve later
#my_data = relative_strength_indicator(my_data, lookback, close_column, close_column)

