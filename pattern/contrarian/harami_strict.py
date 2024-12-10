# -*- coding: utf-8 -*-
"""
Created on Tue Nov  7 11:15:53 2023

@author: zodyac

Harami Pattern
- Contrarian Pttern where the body of the second candle is englobed by the body of the first candle
- Difficult to find and backtest

"""
def harami_strict_bullish_indicator(data, i: int, open_column: int, high_column: int, low_column: int, close_column: int):
    """
    Bullish Criteria
        1. First candle is a bearish candle
        2. And the second candle is bullish
        3. And the second candles high price is lower than the open price of the first candle
        4. And the second canles low price is higher than the close price of the previous candle
    """
    try:
        return data[i, close_column] > data[i, open_column] and \
                data[i, high_column] < data[i - 1, open_column] and \
                data[i, low_column] > data[i - 1, close_column] and \
                data[i - 1, close_column] < data[i - 1, open_column] and \
                data[i - 2, close_column] < data[i - 2, open_column]
    except IndexError:
        return False

def harami_strict_bearish_indicator(data, i: int, open_column: int, high_column: int, low_column: int, close_column: int):
    """
    Bearish Criteria
        1. The first candle is bulklish
        2. And the second candle is bearish
        3. And the second candles has a lower high price than the close price of the first candle
        4. And the second candles low price is higher than the open price of the first candle
    """
    try:
        return data[i, close_column] < data[i, open_column] and \
                data[i, high_column] < data[i - 1, close_column] and \
                data[i, low_column] > data[i - 1, open_column] and \
                data[i - 1, close_column] > data[i - 1, open_column] and \
                data[i - 2, close_column] > data[i - 2, open_column]
    except IndexError:
        return False

def signal(data, open_column, high_column, low_column, close_column, buy_column, sell_column):
    """Signal for Harami Strict Pattern"""
    for i in range(len(data)):

       try:
            # Bullish pattern
            if harami_strict_bullish_indicator(data, i, open_column, high_column, low_column, close_column):
                data[i + 1, buy_column] = 1

            # Bearish pattern
            elif harami_strict_bearish_indicator(data, i, open_column, high_column, low_column, close_column):
                data[i + 1, sell_column] = -1

       except IndexError:
            pass

    return data
