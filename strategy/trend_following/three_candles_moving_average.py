# -*- coding: utf-8 -*-
"""
Created on Sun Dec 31 11:41:56 2023

@author: zodyac

Basics
- Three Candles Pattern
    - Big bodied homogenous candles
- Moving Average
    - Rolling mean of the market
    - Useful in determining overall trend
        - Above the moving average is generally bullish
        - Below th emoving Average is generally bearish

Trading Signal
- Long Signal
    - Generated whenever a Three White SOldiers pattern appears
    - AND the market price is above its 100 period moving average
- Short Signal
    - Generated whenever a Three Black Crows pattern apperas
    - AND the market price is below its 100-period moving average

"""
from array_util import add_column
from data_import import mass_import
from chart_util import signal_chart
from performance import performance
from indicator import moving_average

def signal(data, open_column, close_column, ma_column, buy_column, sell_column, body):

    data = add_column(data, 10)

    for i in range(len(data)):

       try:

           # Bullish pattern
           if data[i, close_column] - data[i, open_column] > body and \
              data[i - 1, close_column] - data[i - 1, open_column] > body and \
              data[i - 2, close_column] - data[i - 2, open_column] > body and \
              data[i, close_column] > data[i - 1, close_column] and \
              data[i - 1, close_column] > data[i - 2, close_column] and \
              data[i - 2, close_column] > data[i - 3, close_column] and \
              data[i, close_column] > data[i, ma_column] and \
              data[i, buy_column] == 0:

                    data[i + 1, buy_column] = 1

           # Bearish pattern
           elif data[i, open_column] - data[i, close_column] > body and \
                data[i - 1, open_column] - data[i - 1, close_column] > body and \
                data[i - 2, open_column] - data[i - 2, close_column] > body and \
                data[i, close_column] < data[i - 1, close_column] and \
                data[i - 1, close_column] < data[i - 2, close_column] and \
                data[i - 2, close_column] < data[i - 3, close_column] and \
                data[i, close_column] < data[i, ma_column] and \
                data[i, sell_column] == 0:

                    data[i + 1, sell_column] = -1

       except IndexError:

            pass

    return data


pair = 1

# Time Frame
horizon = 'H1'
lookback = 100

# Importing the asset as an array
my_data = mass_import(pair, horizon)

# Calculating the MA
my_data = moving_average(my_data, lookback, 3, 4)

body = 0.0005
# Calling the signal function
my_data = signal(my_data, 0, 3, 4, 5, 6, body)

# Charting the latest signals
signal_chart(my_data, 0, 5, 6, window = 250)

# Performance
my_data = performance(my_data, 0, 5, 6, 7, 8, 9)

