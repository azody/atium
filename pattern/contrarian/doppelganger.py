# -*- coding: utf-8 -*-
"""
Doppelganger Pattern
- Round Data
    -  data = rounding(data, 4) # Put 0 instead of 4 as of pair 4
- Optional condition
    - Confirm bullish reversal
        - Next candle surpasses similar highs
    - COnfirm bearish reversal
        - Next candle surpasses similar lows

"""
def doppleganger_bullish_indicator(data, i: int, open_column: int, high_column: int, low_column: int, close_column: int) -> bool:
    """
    Bullish Criteria
        1. First a bearish candle
        2. Followed by two numerically similar candlesticks
            - Same highs and lows
            - Either they have the same close and open prices or the close price must equal the open price
            - Candlesticls canbe bullish or bearish
    """
    try:
        return data[i - 2, close_column] < data[i - 2, open_column] and \
              data[i - 1, close_column] < data[i - 2, open_column] and \
              data[i, high_column] == data[i - 1, high_column] and \
              data[i, low_column] == data[i - 1, low_column]
    except IndexError:
        return False

def doppleganger_bearish_indicator(data, i: int, open_column: int, high_column: int, low_column: int, close_column: int) -> bool:
    """
    Bearish Pattern
        1. First a bullish pattern
        2. Followed by two numerically similar candlesticks
            - Same highs and lows
            - Either they have the same close and open prices or the close price must equal the open price
            - Candlesticls canbe bullish or bearish
    """
    try:
        return data[i - 2, close_column] > data[i - 2, open_column] and \
                data[i - 1, close_column] > data[i - 2, open_column] and \
                data[i, high_column] == data[i - 1, high_column] and \
                data[i, low_column] == data[i - 1, low_column]
    except IndexError:
        return False

    
def signal(data, open_column, high_column, low_column, close_column, buy_column, sell_column):
    """Signal Generation for Doppleganger Pattern"""
    for i in range(len(data)):

        try:

           # Bullish pattern
            if doppleganger_bullish_indicator(data, i, open_column, high_column, low_column, close_column):
                data[i + 1, buy_column] = 1

            # Bearish pattern
            elif doppleganger_bearish_indicator(data, i, open_column, high_column, low_column, close_column):
                data[i + 1, sell_column] = -1

        except IndexError:
            pass

    return data
