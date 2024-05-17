# -*- coding: utf-8 -*-

"""

"""
from array_util import *
from chart_util import signal_chart_indicator_plot_candles
from data_import import mass_import
from indicator import relative_strength_indicator
from performance import performance
from rounding_util import rounding

# Creating the signal function
def signal(data, open_column, close_column, indicator_column, buy_column, sell_column, lower_barrier, upper_barrier):
    data = add_column(data, 5)

    data = rounding(data, 4)

    for i in range(len(data)):

        try:

            # Bullish setup
            if data[i, close_column] == data[i, open_column] and \
                    data[i, indicator_column] < lower_barrier:

                data[i + 1, buy_column] = 1

                # Bearish setup
            elif data[i, close_column] == data[i, open_column] and \
                    data[i, indicator_column] > upper_barrier:

                data[i + 1, sell_column] = -1

        except IndexError:

            pass

    return data

# Choosing the asset
pair = 1

# Time Frame
horizon = 'H1'
lookback = 3

# Importing the asset as an array
my_data = mass_import(pair, horizon)
upper_barrier = 80
lower_barrier = 20

my_data = relative_strength_indicator(my_data, lookback, 3, 4)

# Calling the signal function
my_data = signal(my_data, 0, 3, 4, 5, 6, lower_barrier, upper_barrier)

# Charting the latest signals
signal_chart_indicator_plot_candles(my_data, 0, 4, 5, 6, barriers = True,  window = 250)

# Performance
my_data = performance(my_data, 0, 5, 6, 7, 8, 9)