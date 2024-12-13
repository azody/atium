# -*- coding: utf-8 -*-
"""
Stick Sandwhich Pattern
- 3 candle contrarian configuration composed of candlseticks that alternate in color
- Logic
    - By failing to form new lows in the bullish scenario, the market may have found support
"""
def stick_sandwhich_bullish_indicator(data, i: int, open_column: int, high_column: int, low_column: int, close_column: int):
    """
    Bullish
        1. First a bearish candlestick
        2. Second a bullish candlestich that is smaller than the first candlestick
        3. Last, a bearish candlestich that is larger than the second candlestick
    """
    try:
        return data[i, close_column] < data[i, open_column] and \
              data[i, high_column] > data[i - 1, high_column] and \
              data[i, low_column] < data[i - 1, low_column] and \
              data[i - 1, close_column] > data[i - 1, open_column] and \
              data[i - 2, close_column] < data[i - 2, open_column] and \
              data[i - 2, high_column] > data[i - 1, high_column] and \
              data[i - 2, low_column] < data[i - 1, low_column] and \
              data[i - 2, close_column] < data[i - 3, close_column] and \
              data[i - 3, close_column] < data[i - 3, open_column]
    except IndexError:
        return False

def stick_sandwhich_bearish_indicator(data, i: int, open_column: int, high_column: int, low_column: int, close_column: int):
    """
    Bearish
        1. First a bullish candlestick
        2. Second a bearish candlestick that is smaller than the first candlestick
        3. Last, a bullish candlestick that is greater than the second
    """
    try:
        return data[i, close_column] > data[i, open_column] and \
                data[i, high_column] > data[i - 1, high_column] and \
                data[i, low_column] < data[i - 1, low_column] and \
                data[i - 1, close_column] < data[i - 1, open_column] and \
                data[i - 2, close_column] > data[i - 2, open_column] and \
                data[i - 2, high_column] > data[i - 1, high_column] and \
                data[i - 2, low_column] < data[i - 1, low_column] and \
                data[i - 2, close_column] > data[i - 3, close_column] and \
                data[i - 3, close_column] > data[i - 3, open_column]
    except IndexError:
        return False
    
def signal(data, open_column, high_column, low_column, close_column, buy_column, sell_column):
    """Stick Sandwhich Signal"""
    for i in range(len(data)):
       try:
           # Bullish pattern
           if stick_sandwhich_bullish_indicator(data, i, open_column, high_column, low_column, close_column):
                data[i + 1, buy_column] = 1

           # Bearish pattern
           elif stick_sandwhich_bearish_indicator(data, i, open_column, high_column, low_column, close_column):
                data[i + 1, sell_column] = -1

       except IndexError:

            pass

    return data
