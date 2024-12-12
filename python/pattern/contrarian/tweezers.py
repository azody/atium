# -*- coding: utf-8 -*-
"""
Tweezers Pattern
my_data = rounding(my_data, 5)
"""
def tweezzers_bullish_indicator(data, i: int, open_column: int, high_column: int, low_column: int, close_column: int) -> bool :
    """
    Bullish Pattern
        1. First a bearish candle
        2. Then a bullish candlestick
        3. The bullish candlestick shares a low with the bearish candlestick
    """
    try:
        return data[i, close_column] > data[i, open_column] and \
              data[i - 2, low_column] < data[i - 1, low_column] and \
              data[i - 2, low_column] < data[i - 3, low_column] and \
              data[i - 4, close_column] < data[i - 4, open_column]
    except IndexError:
        return False
    
def tweezzers_bearish_indicator(data, i: int, open_column: int, high_column: int, low_column: int, close_column: int) -> bool :
    """
    Bearish Pattern
        1. First a bullish candlestick
        2. Then a bearish candlestick
        3. The bearish candlestick shares a high with the bullish candlestick
    """
    try:
        return data[i, close_column] < data[i, open_column] and \
                data[i - 2, high_column] > data[i - 1, high_column] and \
                data[i - 2, high_column] > data[i - 3, high_column] and \
                data[i - 4, close_column] > data[i - 4, open_column]
    except IndexError:
        return False
    

def signal(data, open_column, high_column, low_column, close_column, buy_column, sell_column):
    """Tweezer Signal"""
    for i in range(len(data)):

       try:

           # Bullish pattern
           # Note: the the example code contained a max_body
           # data[i, close_column] - data[i, open_column] < body and \
           # data[i - 1, close_column] - data[i - 1, open_column] < body and \
           if tweezzers_bullish_indicator(data, i, open_column, high_column, low_column, close_column):
                data[i + 1, buy_column] = 1

           # Bearish pattern
           # data[i, close_column] - data[i, open_column] < body and \
           # data[i - 1, close_column] - data[i - 1, open_column] < body and \
           elif tweezzers_bearish_indicator(data, i, open_column, high_column, low_column, close_column):
                data[i + 1, sell_column] = -1

       except IndexError:
            pass

    return data
