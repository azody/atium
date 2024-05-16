# -*- coding: utf-8 -*-
"""
Created on Thu May 16 11:17:24 2024

@author: zodyac

Trend Intensity Index TTI
- Measures the strength of a trend
- Calculation
    1. Calculate Moving Average on the Market PPrice
    2. Calcuulate the deviations on the market price from the moving average
    3. Count the values where the market was above the moving average and where it was below it
- Formula
   - TTI = (Number of above MA / (Number of above MA + Number of Below MA)) * 100

"""
import matplotlib.pyplot as plt

from data_import import mass_import
from array_util import add_column
from performance import performance
from indicator import trend_intensity_indicator
from chart_util import signal_chart_indicator_plot_candles
from rounding_util import rounding

def signal(data, open_column, high_column, low_column, close_column, tii_column, buy_column, sell_column):

    data = add_column(data, 5)

    for i in range(len(data)):

       try:

           # Bullish setup
           if data[i, close_column] > data[i, open_column] and \
              data[i, close_column] > data[i - 1, close_column] and \
              data[i, low_column] > data[i - 1, low_column] and \
              data[i - 1, close_column] == data[i - 1, open_column] and \
              data[i - 2, close_column] > data[i - 2, open_column] and \
              data[i - 2, high_column] < data[i - 1, high_column] and \
              data[i, tii_column] > 50:

                    data[i + 1, buy_column] = 1

           # Bearish setup
           elif  data[i, close_column] < data[i, open_column] and \
                data[i, close_column] < data[i - 1, close_column] and \
                data[i, low_column] < data[i - 1, low_column] and \
                data[i - 1, close_column] == data[i - 1, open_column] and \
                data[i - 2, close_column] < data[i - 2, open_column] and \
                data[i - 2, low_column] > data[i - 1, low_column] and \
                data[i, tii_column] < 50:

                    data[i + 1, sell_column] = -1

       except IndexError:

            pass

    return data


# Choosing the asset
pair = 0

# Time Frame
horizon = 'H1'
lookback = 20

# Importing the asset as an array
my_data = mass_import(pair, horizon)

my_data = trend_intensity_indicator(my_data, lookback, 3, 4)

# Rounding
my_data = rounding(my_data, 4)

# Calling the signal function
my_data = signal(my_data, 0, 1, 2, 3, 4, 5, 6)

# Charting the latest signals
signal_chart_indicator_plot_candles(my_data,
                                    0,
                                    4,
                                    5,
                                    6,
                                    barriers = False,
                                    window = 250)
plt.axhline(y = 50, color = 'black', linestyle = 'dashed')

# Performance
my_data = performance(my_data, 0, 5, 6, 7, 8, 9)