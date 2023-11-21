# -*- coding: utf-8 -*-
"""
Created on Mon Nov 20 13:39:16 2023

@author: zodyac

Engulfing Pattern
- Mirror of the Harami Pattern
    - Both are contrarian
- Bullish Pattern
    - First a bearish candle
    - Then a bullish candle
        - Bullish candle completely engulfs the first candlestick in a strict way
- Bearish Candle
    - First a bullish candle
    - Then a bearish candle
        - Bearish candle completely engulfs the first candlestick in a strict way
"""
from data_import import mass_import
from array_util import add_column
from chart_util import signal_chart
from performance import performance

def signal(data, open_column, close_column, buy_column, sell_column):

    data = add_column(data, 5)

    for i in range(len(data)):

       try:

           # Bullish pattern
           if data[i, close_column] > data[i, open_column] and \
              data[i, open_column] < data[i - 1, close_column] and \
              data[i, close_column] > data[i - 1, open_column] and \
              data[i - 1, close_column] < data[i - 1, open_column] and \
              data[i - 2, close_column] < data[i - 2, open_column]:

                    data[i + 1, buy_column] = 1

           # Bearish pattern
           elif data[i, close_column] < data[i, open_column] and \
                data[i, open_column] > data[i - 1, close_column] and \
                data[i, close_column] < data[i - 1, open_column] and \
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
my_data = signal(my_data, 0, 3, 4, 5)

# Charting the latest 150 Signals
signal_chart(my_data, 0, 4, 5, window = 200)

# Get Performance Metrics
performance(my_data, 0, 4, 5, 6, 7, 8)