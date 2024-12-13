# -*- coding: utf-8 -*-
"""
Piercing Pattern
"""
def piercing_bullish_indicator(data, i: int, open_column: int,  high_column: int, low_column: int, close_column: int, buy_column: int, sell_column: int):
    """
    Bullish Pattern
        1. First a bearish candle
        2. Second a bullish candle
        3. Second candle opens at a gap lower and closes above the close of the previous candlestick
    """
    try:
        return data[i, close_column] > data[i, open_column] and \
              data[i, close_column] < data[i - 1, open_column] and \
              data[i, close_column] > data[i - 1, close_column] and \
              data[i, open_column] < data[i - 1, close_column] and \
              data[i - 1, close_column] < data[i - 1, open_column] and \
              data[i - 2, close_column] < data[i - 2, open_column]
    except IndexError:
        return False
    
def piercing_bearish_indicator(data, i: int, open_column: int,  high_column: int, low_column: int, close_column: int, buy_column: int, sell_column: int):
    """
    Bearish Pattern
        1. First a bulilsh candle
        2. Second a bearish candle
        3. Second candle opens at a gap lower and closes below the close of the previous candlestick
    """
    try:
        return data[i, close_column] < data[i, open_column] and \
                data[i, close_column] > data[i - 1, open_column] and \
                data[i, close_column] < data[i - 1, close_column] and \
                data[i, open_column] > data[i - 1, close_column] and \
                data[i - 1, close_column] > data[i - 1, open_column] and \
                data[i - 2, close_column] > data[i - 2, open_column]
    except IndexError:
        return False
    
def signal(data, open_column, high_column, low_column, close_column, buy_column, sell_column):

    for i in range(len(data)):

       try:

           # Bullish pattern
           if piercing_bullish_indicator(data, i, open_column, high_column, low_column, close_column, buy_column, sell_column):
                data[i + 1, buy_column] = 1

           # Bearish pattern
           elif piercing_bearish_indicator(data, i, open_column, high_column, low_column, close_column, buy_column, sell_column):
                data[i + 1, sell_column] = -1

       except IndexError:

            pass

    return data