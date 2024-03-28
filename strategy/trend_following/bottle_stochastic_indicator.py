# -*- coding: utf-8 -*-
"""
Created on Sun Dec 31 12:25:08 2023

@author: zodyac

Basics
- Normalization
    - Confine all values between 0 and 1
    - Normalized = (Original - Low) / (High - Low)
Stochastic Oscillator
- Stochastic Normalization = (Close - Lowest Low in Lookback) / (Highest High in Lookback - Lowest Low in Lookback)
- Steps
    - 1. Normalize data using stochastic normalization formula
    - 2. Smooth out the results with a three period moving average
    - 3. Calculate the signal line
        - Another 3 period moving average calculated on the values of the second step
- Features
    - Bounded between 0 and 100
    - Oversold zone is below 20 and overbought zone is above 80
    - More Volatile than RSI
- Strategy
    - Interested in the cross between the stochastic oscillator and its indicator line
    - Long signal is generated whenever a bullish bottle pattern appears while the stochastic oscillator is above its signal line
    - Short signal is generated whenever a bearis bottle pattern appears while the stochastic oscillator is below its signal line
"""
from data_import import mass_import
from array_util import add_column
from performance import performance
from indicator import stochastic_oscillator
from array_util import delete_column
from chart_util import signal_chart_indicator_plot_candles

def signal(data, open_column, high_column, low_column, close_column,
            stochastic_column, signal_column, buy_column, sell_column):

    data = add_column(data, 5)

    for i in range(len(data)):

       try:

           # Bullish pattern
           if data[i, close_column] > data[i, open_column] and \
              data[i, open_column] == data[i, low_column] and \
              data[i - 1, close_column] > data[i - 1, open_column] and \
              data[i, open_column] < data[i - 1, close_column] and \
              data[i, stochastic_column] > data[i, signal_column] and \
              data[i, buy_column] == 0:

                    data[i + 1, buy_column] = 1

           # Bearish pattern
           elif data[i, close_column] < data[i, open_column] and \
                data[i, open_column] == data[i, high_column] and \
                data[i - 1, close_column] < data[i - 1, open_column] and \
                data[i, open_column] > data[i - 1, close_column] and \
                data[i, stochastic_column] > data[i, signal_column] and \
                data[i, sell_column] == 0:

                    data[i + 1, sell_column] = -1

       except IndexError:

            pass

    return data


# Choosing the asset
pair = 1

# Time Frame
horizon = 'H1'
lookback = 14

# Importing the asset as an array
my_data = mass_import(pair, horizon)

my_data = stochastic_oscillator(my_data, lookback, 1, 2, 3, 4, slowing = True, smoothing = True, slowing_period = 3, smoothing_period = 3)

my_data = delete_column(my_data, 4, 1)

# Calling the signal function
my_data = signal(my_data, 0, 1, 2, 3, 4, 5, 6, 7)

# Charting the latest signals
signal_chart_indicator_plot_candles(my_data, 0, 4, 6, 7, barriers = False, window = 250)

# Performance
my_data = performance(my_data, 0, 6, 7, 8, 9, 10)