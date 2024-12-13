# -*- coding: utf-8 -*-
"""
Euphoria Pattern
- Very similar to Three Candles pattern
    - Additional condition makes it contrarian
- Rounding
    - data = rounding(data, 4)
- Trades best in a sideways market
- Ideal for short moves
"""
def euphoria_bullish_pattern(data, i: int, open_column: int, high_column: int, low_column: int, close_column: int) -> bool:
    """
    Bullish Criteria
        1. Three succesive bearish candles
        2. Each candles body must be greater than the preceeding candles
    """
    try:
        return data[i, open_column] > data[i, close_column] and \
              data[i - 1, open_column] > data[i - 1, close_column] and \
              data[i - 2, open_column] > data[i - 2, close_column] and \
              data[i, close_column] < data[i - 1, close_column] and \
              data[i - 1, close_column] < data[i - 2, close_column] and \
              (data[i, open_column] - data[i, close_column]) > (data[i - 1, open_column] - data[i - 1, close_column]) and \
              (data[i - 1, open_column] - data[i - 1, close_column]) > (data[i - 2, open_column] - data[i - 2, close_column])
    except IndexError:
         return False
    
def euphoria_bearish_pattern(data, i: int, open_column: int, high_column: int, low_column: int, close_column: int) -> bool:
    """
    Bearish Criteria
        1. Three succesive bullish candles
        2. Each candles body must be greater than the preceeding candles
    """
    try:
        return data[i, open_column] < data[i, close_column] and \
                data[i - 1, open_column] < data[i - 1, close_column] and \
                data[i - 2, open_column] < data[i - 2, close_column] and \
                data[i, close_column] > data[i - 1, close_column] and \
                data[i - 1, close_column] > data[i - 2, close_column] and \
                (data[i, open_column] - data[i, close_column]) > (data[i - 1, open_column] - data[i - 1, close_column]) and \
                (data[i - 1, open_column] - data[i - 1, close_column]) > (data[i - 2, open_column] - data[i - 2, close_column])
    except IndexError:
         return False


def signal(data, open_column,  high_column, low_column, close_column, buy_column, sell_column):

    for i in range(len(data)):

       try:

           # Bullish pattern
           if euphoria_bullish_pattern(data, i, open_column, high_column, low_column, close_column):
                data[i + 1, buy_column] = 1

           # Bearish pattern
           elif euphoria_bearish_pattern(data, i, open_column, high_column, low_column, close_column):
                data[i + 1, sell_column] = -1

       except IndexError:
            pass

    return data