# -*- coding: utf-8 -*-
"""
H Pattern
"""
def h_bullish_indicator(data, i: int, open_column: int, high_column: int, low_column: int, close_column: int) -> bool:
    """
    Bullish Indicator Criteria
        1. First candle is bullish
        2. Second candle is an indecision
            - Open = Close
        3. Third candle is bullish
        4. Third close pirce must be higher than the indecisions close
        5. Third low must be higher than the low of the indecision candle
    """
    try:
        return data[i, close_column] > data[i, open_column] and \
                data[i, close_column] > data[i - 1, close_column] and \
                data[i, low_column] > data[i - 1, low_column] and \
                data[i - 1, close_column] == data[i - 1, open_column] and \
                data[i - 2, close_column] > data[i - 2, open_column] and \
                data[i - 2, high_column] < data[i - 1, high_column]          
    except IndexError:
        return False

def h_bearish_indicator(data, i: int, open_column: int, high_column: int, low_column: int, close_column: int) -> bool:
    """
    Bearish Indicator Criteria
        1. First candle is bearish
        2. Next candlie is an indecision
            - Open = close
        3. Third candle is bearish
        4. Third close must be lower than the indecision candle close
        5. Third high must be lower than the high of the indecision candle
    """
    try:
        return data[i, close_column] < data[i, open_column] and \
                data[i, close_column] < data[i - 1, close_column] and \
                data[i, low_column] < data[i - 1, low_column] and \
                data[i - 1, close_column] == data[i - 1, open_column] and \
                data[i - 2, close_column] < data[i - 2, open_column] and \
                data[i - 2, low_column] > data[i - 1, low_column]
    except IndexError:
        return False
    
def signal(data, open_column, high_column, low_column, close_column, buy_column, sell_column):
    """SIgnal for H Pattern"""
    for i in range(len(data)):

       try:

           # Bullish pattern
           if h_bullish_indicator(data, i, open_column, high_column, low_column, close_column):
                    data[i + 1, buy_column] = 1

           # Bearish pattern
           elif h_bearish_indicator(data, i, open_column, high_column, low_column, close_column):
                    data[i + 1, sell_column] = -1

       except IndexError:

            pass

    return data