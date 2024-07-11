# -*- coding: utf-8 -*-
"""
Created on Thu Nov  2 13:39:13 2023

@author: zodyac


Doji Pattern
- Most well known and intuitive candle pattern
- Pattern
    - One candlestick configuration where the open price equals the close price
    - Bullish
        - First a bearish candle
        - Then a Doji Candle
        - Finally a bullish candle
    - Bearish
        - First a bullish candle
        - Then a Doji Candle
        - Finally a bearish candle
- Rational
    - Doji candle is the quiliibrium between supply and demand before a reversal
- Variants
    - Dragonfly Doji: High price equal to the close and open price
    - Gravestone Doji: Low price is equal to close and open price
    - Flat Doji: Occurs when open, high, low and close are equal
        - Indicates low volume and liquidity
    - Double Doji: Two Dojis in a row
        - Two Dojis are not worth more than one
    - Tri Star Doji: Three Dojis in a row where the middle Doji gaps over the other two
- Results
    - Common pattern
    - Generally not predictive
    - Works best in sideways marketsa

"""
from data_import import mass_import
from array_util import add_column
from chart_util import signal_chart
from performance import performance
from rounding_util import rounding

def bull_indicator(data, i, open_column, high_column, low_column, close_column):
    try:

        # Bullish pattern
        if data[i, close_column] > data[i, open_column] and \
           data[i, close_column] > data[i - 1, close_column] and \
           data[i - 1, close_column] == data[i - 1, open_column] and \
           data[i - 2, close_column] < data[i - 2, open_column] and \
           data[i - 2, close_column] < data[i - 2, open_column]:

                return True

    except IndexError:
         return False

    return False

def bear_indicator(data, i, open_column, high_column, low_column, close_column):
    try:

       if data[i, close_column] < data[i, open_column] and \
          data[i, close_column] < data[i - 1, close_column] and \
          data[i - 1, close_column] == data[i - 1, open_column] and \
          data[i - 2, close_column] > data[i - 2, open_column] and \
          data[i - 2, close_column] > data[i - 2, open_column]:

                return True

    except IndexError:
         return False

    return False

def signal(data, open_column, high_column, low_column, close_column, buy_column, sell_column):

    data = add_column(data, 5)

    for i in range(len(data)):

       try:

           # Bullish pattern
           if bull_indicator(data, i, open_column, close_column, buy_column, sell_column):
               data[i + 1, buy_column] = 1

           # Bearish pattern
           elif bear_indicator(data, i, open_column, high_column, low_column, close_column):
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
my_data = rounding(my_data, 4)

# Calling the Signal Function
my_data = signal(my_data, 0, 1, 2, 3, 4, 5)

# Charting the latest 150 Signals
signal_chart(my_data, 0, 4, 5, window = 200)

# Get Performance Metrics
performance(my_data, 0, 4, 5, 6, 7, 8)