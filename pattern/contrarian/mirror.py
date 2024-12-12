# -*- coding: utf-8 -*-
"""
Mirror Pattern

"""
def mirror_bullish_pattern(data, i: int, open_column: int, high_column: int, low_column: int, close_column: int) -> bool:
    """
    Bullish Criteria
        1. First Candle stick is bearish
        2. Next two candlesticks that have the same highs and lows
        3. Last candlestick is bullish
            - Must have a high price equal to the high price of the first candlestick
    """
    try:
        return data[i, close_column] > data[i, open_column] and \
              data[i, high_column] == data[i - 3, high_column] and \
              data[i, close_column] > data[i - 1, close_column] and \
              data[i, close_column] > data[i - 2, close_column] and \
              data[i, close_column] > data[i - 3, close_column] and \
              data[i - 3, close_column] < data[i - 3, open_column] and \
              data[i - 1, close_column] == data[i - 2, close_column]
    except IndexError:
         return False
    
def mirror_bearish_pattern(data, i: int, open_column: int, high_column: int, low_column: int, close_column: int) -> bool:
    """
    Bearish Criteria
        1. First candle is bullish
        2. Next two candlesticks have the same highs and lows
        3. Last candlestick is bearish
            - Must have a low price equal to the low price of the first candlestick
    """
    try:
        return data[i, close_column] < data[i, open_column] and \
                data[i, low_column] == data[i - 3, low_column] and \
                data[i, close_column] < data[i - 1, close_column] and \
                data[i, close_column] < data[i - 2, close_column] and \
                data[i, close_column] < data[i - 3, close_column] and \
                data[i - 3, close_column] > data[i - 3, open_column] and \
                data[i - 1, close_column] == data[i - 2, close_column]
    except IndexError:
         return False

def signal(data, open_column, high_column, low_column, close_column, buy_column, sell_column):

    for i in range(len(data)):

       try:

           # Bullish pattern
           if mirror_bullish_pattern(data, i, open_column, high_column, low_column, close_column):
                data[i + 1, buy_column] = 1

           # Bearish pattern
           elif mirror_bearish_pattern(data, i, open_column, high_column, low_column, close_column):
                data[i + 1, sell_column] = -1

       except IndexError:
            pass

    return data
