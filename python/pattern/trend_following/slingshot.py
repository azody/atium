# -*- coding: utf-8 -*-
"""
Slingshot Pattern
- Deals witha  breakout system that confirms the new trend
- Four candlestick trend detecntion system that uses pullback method to identify a breakout
"""
def slingshot_bull_indicator(data, i: int, open_column: int, high_column: int, low_column: int, close_column) -> bool:
    """
    Bullish Indicator Criteria
        1. Bullish first candle
        2. Followed by a higher one confirming a bullish bias
        3. Third candle not surpassing the high of the second
        4. Final candle that must have a low at or below the high of the first candletick and a close higher than that of the second candlestick
        5. Color of the second and third candles does not matter
    """
    try:
        return data[i, close_column] > data[i - 1, high_column] and \
                data[i, close_column] > data[i - 2, high_column] and \
                data[i, low_column] <= data[i - 3, high_column] and \
                data[i, close_column] > data[i, open_column] and \
                data[i - 1, close_column] >= data[i - 3, high_column] and \
                data[i - 2, low_column] >= data[i - 3, low_column] and \
                data[i - 2, close_column] > data[i - 2, open_column] and \
                data[i - 2, close_column] > data[i - 3, high_column] and \
                data[i - 1, high_column] <= data[i - 2, high_column]
    except IndexError:
        return False
    
def slingshot_bear_indicator(data, i: int, open_column: int, high_column: int, low_column: int, close_column) -> bool:
    """
    Bearish Indicator Criteria
        1. Bearish first candle
        2. Followed by a lower ont confirming a bearish bias
        3. Third candle not breaking the low of the second candle
        4. Final candlestick that has a high at or above the low of the first candsle and a close lower than the low of the second candlestick
    """
    try:
        return data[i, close_column] < data[i - 1, low_column] and \
                data[i, close_column] < data[i - 2, low_column] and \
                data[i, high_column] >= data[i - 3, low_column] and \
                data[i, close_column] < data[i, open_column] and \
                data[i - 1, high_column] <= data[i - 3, high_column] and \
                data[i - 2, close_column] <= data[i - 3, low_column] and \
                data[i - 2, close_column] < data[i - 2, open_column] and \
                data[i - 2, close_column] < data[i - 3, low_column] and \
                data[i - 1, low_column] >= data[i - 2, low_column]
    except IndexError:
        return False
    
def signal(data, open_column, high_column, low_column, close_column, buy_column, sell_column):

    for i in range(len(data)):

       try:

           # Bullish slingshot
           if slingshot_bull_indicator(data, i, open_column, high_column, low_column, close_column):
                    data[i + 1, buy_column] = 1

           # Bearish slingshot
           elif  slingshot_bear_indicator(data, i, open_column, high_column, low_column, close_column):
                    data[i + 1, sell_column] = -1

       except IndexError:
            pass

    return data