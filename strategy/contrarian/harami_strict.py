# -*- coding: utf-8 -*-
"""
Created on Tue Nov  7 11:15:53 2023

@author: zodyac

Harami Pattern
- Contrarian Pttern where the body of the second candle is englobed by the body of the first candle
- Bullish Pattern
    - First candle is a bearish candle
    - And the second candle is bullish
    - And the second candles high price is lower than the open price of the first candle
    - And the second canles low price is higher than the close price of the previous candle
- Bearish Pattern
    - The first candle is bulklish
    - And the second candle is bearish
    - And the second candles has a lower high price than the close price of the first candle
    - And the second candles low price is higher than the open price of the first candle
- Difficult to find and backtest

"""
from data_import import mass_import
from array_util import add_column
from chart_util import signal_chart
from performance import performance

def signal(data, open_column, high_column, low_column, close_column, buy_column, sell_column):

    data = add_column(data, 5)

    for i in range(len(data)):

       try:

           # Bullish pattern
           if data[i, close_column] > data[i, open_column] and \
              data[i, high_column] < data[i - 1, open_column] and \
              data[i, low_column] > data[i - 1, close_column] and \
              data[i - 1, close_column] < data[i - 1, open_column] and \
              data[i - 2, close_column] < data[i - 2, open_column]:


                    data[i + 1, buy_column] = 1

           # Bearish pattern
           elif data[i, close_column] < data[i, open_column] and \
                data[i, high_column] < data[i - 1, close_column] and \
                data[i, low_column] > data[i - 1, open_column] and \
                data[i - 1, close_column] > data[i - 1, open_column] and \
                data[i - 2, close_column] > data[i - 2, open_column]:

                    data[i + 1, sell_column] = -1

       except IndexError:

            pass

    return data


# Choose an Asset
pair = 0 # EURUSD

# Time frame
horizon = "H1"

# Importing the asset as an array
my_data = mass_import(pair, horizon)

# Calling the Signal Function
my_data = signal(my_data, 0, 1, 2, 3, 4, 5)

# Charting the latest 150 Signals
signal_chart(my_data, 0, 4, 5, window = 200)

# Get Performance Metrics
performance(my_data, 0, 4, 5, 6, 7, 8)
