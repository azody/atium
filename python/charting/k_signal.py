# -*- coding: utf-8 -*-
"""
Created on Tue Nov 21 15:07:08 2023

@author: zodyac
"""
from indicator import moving_average
from array_util import add_column

def k_candlesticks(data, open_column, high_column, low_column, close_column, lookback, position):

    data = add_column(data, 4)

    # Averaging the open price
    data = moving_average(data, lookback, open_column, position)

    # Averaging the high price
    data = moving_average(data, lookback, high_column, position + 1)

    # Averaging the low price
    data = moving_average(data, lookback, low_column, position + 2)

    # Averaging the close price
    data = moving_average(data, lookback, close_column, position + 3)

    return data