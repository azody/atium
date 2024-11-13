# -*- coding: utf-8 -*-
"""
Engulfing Pattern
- Mirror of the Harami Pattern
    - Both are contrarian
"""
def engulfing_bullish_pattern(data, i: int, open_column: int, high_column: int, low_column: int, close_column: int) -> bool:
    """
    Bullish Criteria
        1. First a bearish candle
        2. Then a bullish candle
            - Bullish candle completely engulfs the first candlestick in a strict way
    """
    try:
        return data[i, close_column] > data[i, open_column] and \
              data[i, open_column] < data[i - 1, close_column] and \
              data[i, close_column] > data[i - 1, open_column] and \
              data[i - 1, close_column] < data[i - 1, open_column] and \
              data[i - 2, close_column] < data[i - 2, open_column]
    except IndexError:
         return False
    
def engulfing_bearish_pattern(data, i: int, open_column: int, high_column: int, low_column: int, close_column: int) -> bool:
    """
    Bearish Criteria
        1. First a bullish candle
        2. Then a bearish candle
            - Bearish candle completely engulfs the first candlestick in a strict way
    """
    try:
        return data[i, close_column] < data[i, open_column] and \
                data[i, open_column] > data[i - 1, close_column] and \
                data[i, close_column] < data[i - 1, open_column] and \
                data[i - 1, close_column] > data[i - 1, open_column] and \
                data[i - 2, close_column] > data[i - 2, open_column]
    except IndexError:
         return False
    
def signal(data, open_column, high_column, low_column, close_column, buy_column, sell_column):

    for i in range(len(data)):

       try:

           # Bullish pattern
           if engulfing_bullish_pattern(data, i, open_column, high_column, low_column, close_column):
                data[i + 1, buy_column] = 1

           # Bearish pattern
           elif engulfing_bearish_pattern(data, i, open_column, high_column, low_column, close_column):
                data[i + 1, sell_column] = -1

       except IndexError:
            pass

    return data
