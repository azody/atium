# -*- coding: utf-8 -*-
"""
Barrier Pattern
- Round Data
    data = rounding(data, 4) # Put 0 instead of 4 as of pair 4

- Optional extra parameter
    - Bullish: bullish candle must have a close higher than the high price of the middle candle
    - Bearish: bearish candle must have a close price lower than the low price of the middle candlestick

"""
def barrier_bullish_indicator(data, i: int, open_column: int, high_column: int, low_column: int, close_column: int) -> bool:
    """
    Bullish Criteria
        1. First 2 candles are bearish
        2. Third candle is bullish
        3. Lows of all three candles is the same
    """
    try:
        return data[i, close_column] > data[i, open_column] and \
              data[i - 1, close_column] < data[i - 1, open_column] and \
              data[i - 2, close_column] < data[i - 2, open_column] and \
              data[i, low_column] == data[i - 1, low_column] and \
              data[i, low_column] == data[i - 2, low_column]
    except IndexError:
        return False
    
def barrier_bearish_indicator(data, i: int, open_column: int, high_column: int, low_column: int, close_column: int) -> bool:
    """
    Bearish Criteria
        1. First 2 candles are bullish
        2. Third candle is bearish
        3. Highs of all three candles must be the same
    """
    try:
        return data[i, close_column] < data[i, open_column] and \
                data[i - 1, close_column] > data[i - 1, open_column] and \
                data[i - 2, close_column] > data[i - 2, open_column] and \
                data[i, high_column] == data[i - 1, high_column] and \
                data[i, high_column] == data[i - 2, high_column]
    except IndexError:
        return False

def signal(data, open_column, high_column, low_column, close_column, buy_column, sell_column):

    for i in range(len(data)):

       try:
           # Bullish pattern
           if barrier_bullish_indicator(data, i, open_column, high_column, low_column, close_column):
                data[i + 1, buy_column] = 1

           # Bearish pattern
           elif barrier_bearish_indicator(data, i, open_column, high_column, low_column, close_column):
                data[i + 1, sell_column] = -1

       except IndexError:

            pass

    return data