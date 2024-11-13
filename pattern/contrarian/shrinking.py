# -*- coding: utf-8 -*-
"""
Shrinking Pattern

"""
def shrinking_bullish_pattern(data, i: int, open_column: int, high_column: int, low_column: int, close_column: int) -> bool:
    """
    Bullish Criteria
        1. First candle must be bearish
        2. Following three candles may have any color but are shrinking in size every time
        3. Final candle must be bullish and must surpass the high of the second candle
    """
    try:
        return data[i - 4, close_column] < data[i - 4, open_column] and \
              data[i, close_column] > data[i, open_column] and \
              data[i, close_column] > data[i - 3, high_column] and \
              abs(data[i - 3, close_column] - data[i - 3, open_column]) < abs(data[i - 4, close_column] - data[i - 4, open_column]) and \
              abs(data[i - 2, close_column] - data[i - 2, open_column]) < abs(data[i - 3, close_column] - data[i - 3, open_column]) and \
              abs(data[i - 1, close_column] - data[i - 1, open_column]) < abs(data[i - 2, close_column] - data[i - 2, open_column]) and \
              data[i - 1, high_column] < data[i - 2, high_column] and \
              data[i - 2, high_column] < data[i - 3, high_column]
    except IndexError:
         return False
    
def shrinking_bearish_pattern(data, i: int, open_column: int, high_column: int, low_column: int, close_column: int) -> bool:
    """
    Bearish Criteria
        1. First candle must be bullish
        2. Following three candles may have any color but are shrinking in size everytime
        3. Final candle must be bearish and must break the low of the second candlestick
    """
    try:
        return data[i - 4, close_column] > data[i - 4, open_column] and \
                data[i, close_column] < data[i, open_column] and \
                data[i, close_column] < data[i - 3, low_column] and \
                abs(data[i - 3, close_column] - data[i - 3, open_column]) < abs(data[i - 4, close_column] - data[i - 4, open_column]) and \
                abs(data[i - 2, close_column] - data[i - 2, open_column]) < abs(data[i - 3, close_column] - data[i - 3, open_column]) and \
                abs(data[i - 1, close_column] - data[i - 1, open_column]) < abs(data[i - 2, close_column] - data[i - 2, open_column]) and \
                data[i - 1, low_column] > data[i - 2, low_column] and \
                data[i - 2, low_column] > data[i - 3, low_column]
    except IndexError:
         return False

def signal(data, open_column, high_column, low_column, close_column, buy_column, sell_column):

    for i in range(len(data)):

       try:
           # Bullish pattern
           if shrinking_bullish_pattern(data, i, open_column, high_column, low_column, close_column):
                data[i + 1, buy_column] = 1

           # Bearish pattern
           elif shrinking_bearish_pattern(data, i, open_column, high_column, low_column, close_column):
                data[i + 1, sell_column] = -1

       except IndexError:

            pass

    return data