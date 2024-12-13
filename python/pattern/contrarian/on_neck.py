# -*- coding: utf-8 -*-
"""
On Neck Pattern

"""
def on_neck_bullish_indicator(data, i: int, open_column: int, high_column: int, low_column: int, close_column: int) :
    """
    Bullish Pattern
        1. First, a bearish candlestick
        2. And then a second bullish candlestick
        3. The second candlestick opens at a gap lower
        4. The second candlestick closes exactly at the close price of the first candlestick
    """
    try:
        return data[i, close_column] > data[i, open_column] and \
                data[i, close_column] == data[i - 1, close_column] and \
                data[i, open_column] < data[i - 1, close_column] and \
                data[i - 1, close_column] < data[i - 1, open_column]
    except IndexError:
        return False

def on_neck_bearish_indicator(data, i: int, open_column: int, high_column: int, low_column: int, close_column: int) :
    """
    Bearish Pattern
        1. First, a bullish candlestick
        2. And then a second bearish candlestick
        3. The second candlestick opens at a gap higher
        4. The second candlestick closes exactly at the close price of the first candlestick
    """
    try:
        return data[i, close_column] < data[i, open_column] and \
                data[i, close_column] == data[i - 1, close_column] and \
                data[i, open_column] > data[i - 1, close_column] and \
                data[i - 1, close_column] > data[i - 1, open_column]
    except IndexError:
        return False

def signal(data, open_column, high_column, low_column, close_column, buy_column, sell_column):

    for i in range(len(data)):

       try:
           # Bullish pattern
           if on_neck_bullish_indicator(data, i, open_column, high_column, low_column, close_column):
                data[i + 1, buy_column] = 1

           # Bearish pattern
           elif on_neck_bearish_indicator(data, i, open_column, high_column, low_column, close_column):
                data[i + 1, sell_column] = -1

       except IndexError:
            pass

    return data

