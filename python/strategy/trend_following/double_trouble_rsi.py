# -*- coding: utf-8 -*-
"""
Created on Sun Dec 31 11:02:43 2023

@author: zodyac

Double Trouble Pattern with RSI Filter

Basics
- Double Trouble Pattern
    - Trend Following that uses the Average True Range (ATR)
    - ATR is essentially a smoothed average over a lookback period
- RSI - Relative Strength Indicator
    - Momentum Gauge
    - Values over 50 represent bullish momentum (uptrend)
    - Values under 50 represent bearish momentum (downtrend)

Trading Conditions
- Long Signal
    - Generated when there is a bullish Double Trouble Pattern
    - AND 14 period RSI is above 50
- Short Signal
    - Generated when there is a beraish Double Trouble Pattern
    - AND 14 period RSI is below 50

Notes and Observations
- RSI is a derived on traded data
    - Lagging indicator based on what has already happened

"""
from python.data_import import mass_import
from python.array_util import add_column
from python.chart_util import signal_chart_indicator_plot_candles
from python.performance import performance
from python.volatility import average_true_range
from python.indicator import relative_strength_indicator

# Creating the signal function
def signal(data, open_column, high_column, low_column, close_column, atr_column, rsi_column, buy_column, sell_column):

    data = add_column(data, 5)

    for i in range(len(data)):

       try:

           # Bullish pattern
           if data[i, close_column] > data[i, open_column] and \
              data[i, close_column] > data[i - 1, close_column] and \
              data[i - 1, close_column] > data[i - 1, open_column] and \
              data[i, high_column] - data[i, low_column] > (2 * data[i - 1, atr_column]) and \
              data[i, close_column] - data[i, open_column] > data[i - 1, close_column] - data[i - 1, open_column] and \
              data[i, buy_column] == 0 and \
              data[i, rsi_column] > 50:

                    data[i + 1, buy_column] = 1

           # Bearish pattern
           elif data[i, close_column] < data[i, open_column] and \
              data[i, close_column] < data[i - 1, close_column] and \
              data[i - 1, close_column] < data[i - 1, open_column] and \
              data[i, high_column] - data[i, low_column] > (2 * data[i - 1, atr_column]) and \
              data[i, open_column] - data[i, close_column] > data[i - 1, open_column] - data[i - 1, close_column] and \
              data[i, sell_column] == 0 and \
              data[i, rsi_column] < 50:

                    data[i + 1, sell_column] = -1

       except IndexError:

            pass

    return data


# Choosing the asset
pair = 2

# Time Frame
horizon = 'H1'
lookback = 10
lookback_rsi = 14

# Importing the asset as an array
my_data = mass_import(pair, horizon)

# Calculating the ATR
my_data = average_true_range(my_data, lookback, 1, 2, 3, 4)
my_data = relative_strength_indicator(my_data, lookback_rsi, 3, 5)
my_data = signal(my_data, 0, 1, 2, 3, 4, 5, 6, 7)

# Charting the latest 150 Signals
# Charting the latest signals
signal_chart_indicator_plot_candles(my_data,
                                    0,
                                    5,
                                    6,
                                    7,
                                    barriers = False,
                                    window = 250)

# Get Performance Metrics
my_data = performance(my_data, 0, 6, 7, 8, 9, 10)