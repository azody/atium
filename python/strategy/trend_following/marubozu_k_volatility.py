# -*- coding: utf-8 -*-
"""
Created on Thu Mar 28 15:14:12 2024

@author: zodyac

Volatility Band
    - Framing technique that envelops the market price to deliver dynamic support and resistance levels
    - Bollinger Bands
        - Calculation
            - Upper Bollinger band is the sum of the current 20 period moving average and the current standard deviation multiplied by 2
            - Lower Bollinger band is the differnce of the current 20 period moving averaeg and the current standard eviation multiplied by 2
        - Features
            - Oversold at the lower bollinger band
            - Overbought at the top bollinger band
K's Volatility Bands
    - Calculation
        - Calculate the mean between the highest highs and the lowest lows of hte last 20 periods
        - Calculate the highest standard deviation measure ofhte last 20 periods
        - Upper band is the sum of the mean step and 2x the 2nd step
        - Lower band is the difference of the mean step and 2x the 2nd step
Strategy
    - Long signal generated whenever a bullish Marubozu pattern appears while the market price is below the middle line
    - Short signal generated whenever a bearish Marubozu pattern appears while the market price is above the middle line

Use my_data = rounding(my_data, 4) for EURUSD, USDCHF, GBPUSD, USDCAD
Use my_data = rounding(my_data, 0) for BTCUSD, ETHUSD, XAUUSD, SP500m, UK100

"""
import matplotlib.pyplot as plt
from python.data_import import mass_import
from python.array_util import add_column
from python.performance import performance
from python.chart_util import signal_chart
from python.volatility import k_volatility_band
from python.rounding_util import rounding

def signal(data, open_column, high_column, low_column, close_column,
            middle_band, buy_column, sell_column):

    data = add_column(data, 5)

    for i in range(len(data)):

       try:

           # Bullish pattern
           if data[i, close_column] > data[i, open_column] and \
              data[i, high_column] == data[i, close_column] and \
              data[i, low_column] == data[i, open_column] and \
              data[i, close_column] < data[i, middle_band] and \
              data[i, buy_column] == 0:

                    data[i + 1, buy_column] = 1

           # Bearish pattern
           elif data[i, close_column] < data[i, open_column] and \
                data[i, high_column] == data[i, open_column] and \
                data[i, low_column] == data[i, close_column] and \
                data[i, close_column] > data[i, middle_band] and \
                data[i, sell_column] == 0:

                    data[i + 1, sell_column] = -1

       except IndexError:

            pass

    return data

# Choosing the asset
pair = 0

# Time Frame
horizon = 'H1'
lookback = 20
multiplier = 2

# Importing the asset as an array
my_data = mass_import(pair, horizon)

my_data = k_volatility_band(my_data, lookback, multiplier, 1, 2, 3, 4)

# Rounding
my_data = rounding(my_data, 4)

# Calling the signal function
my_data = signal(my_data, 0, 1, 2, 3, 4, 7, 8)

# Charting the latest signals
signal_chart(my_data, 0, 7, 8, window = 250)
plt.plot(my_data[-250:, 4], color = 'blue', linestyle = 'dashed')
plt.plot(my_data[-250:, 5], color = 'orange')
plt.plot(my_data[-250:, 6], color = 'purple')

# Performance
my_data = performance(my_data, 0, 7, 8, 9, 10, 11)