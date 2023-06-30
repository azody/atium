# -*- coding: utf-8 -*-
"""
Created on Wed Jun 28 16:50:18 2023

@author: zodyac

Signal for Marubozu pattern

If the close price from two periods ago is greater than the open price from two periods ago
    And the open price from one period ago is greater than the close two period ago
    And the close price from one period ago is greater than the close two periods ago
    And the close price from one period ago is greater than the open price from one period ago
    And the current close price is greater than the close price two periods ago
    Then add a bullish indicator

If the close price from two periods ago is lower than the open price from two periods ago
    And the open price from one period ago is lower than the sloce two periods ago
    And the close price from one period is greater than the open price from one period ago
    And the current close price is greater than the close price two periods ago
    Then add a bearish indicator

Characteristics
- Gap is formed giving a continuation signal
    - Gaps are a discontinuation or a hole between to successive close prices, mainly caused by loq liquidity
- Types of Gaps
    - Common gaps: Occur in a sideways market
        - likely to be filled because of markets mean-reversion dynamic
    - Breakaway gaps: Generally resemble a common gap
        - Gap occurs above a graphical resistance or below a graphical support
    - Runaway gaps: Generally occur within the trend but confirm it more
        - Continuation Pattern
    - Exhaustion gaps: generally occur at the end of a trend and close to a support or resistance level
        - Reversal pattern
    - Impossible to know the type of gap ahead of time
- Bullish characteristic
    - Three candlesticks where first is bullish, second is bullish and the third is bearish but does not close below the close of the first
- Bearish Characteristic
    - Three candlesticks where first is bearish, second is bearish and the third is bullish but does not close above the close of the first
Reasoning
- Market Psychology
    - Not enough pressure to reach the previous threshold
Nuances
- Rarely occurs
- Intuitive but not neccesarily accurate

"""
from data_import import mass_import
from array_util import add_column
from chart_util import signal_chart
from performance import performance

def signal(data, open_column, close_column, buy_column, sell_column) :

    data = add_column(data, 5)

    for i in range(len(data)) :
        try:
            # Bullish pattern
            if data[i, close_column] < data[i, open_column] and \
                data[i, close_column] < data[i - 1, open_column] and \
                data[i, close_column] > data[i - 2, close_column] and \
                data[i - 1, close_column] > data[i - 1, open_column] and \
                data[i - 1, open_column] > data[i - 2, close_column] and \
                data[i - 2, close_column] > data[i - 2, open_column] :

                    data[i + 1, buy_column] = 1

                # Bearish pattern
            elif data[i, close_column] > data[i, open_column] and \
                data[i, close_column] > data[i - 1, open_column] and \
                data[i, close_column] < data[i - 2, close_column] and \
                data[i - 1, close_column] < data[i - 1, open_column] and \
                data[i - 1, open_column] < data[i - 2, close_column] and \
                data[i - 2, close_column] < data[i - 2, open_column] :

                    data[i + 1, sell_column] = -1

        except IndexError:
            pass

    return data

# Choose an Asset
pair = 0 # EURUSD

# Time frame
horizon = "M5"

# Importing the asset as an array
my_data = mass_import(pair, horizon)

# Calling the Signal Function
my_data = signal(my_data, 0, 3, 4, 5)

# Charting the latest 150 Signals
signal_chart(my_data, 0, 4, 5, window = 200)

# Get Performance Metrics
performance(my_data, 0, 4, 5, 6, 7, 8)