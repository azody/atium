# -*- coding: utf-8 -*-
"""
Hikkake pattern

Reasoning
- Market Psychology
    - Historical Bear Trap
"""
def hikkake_bull_indicator(data, i: int, open_column: int, high_column: int, low_column:int, close_column: int) -> bool :
    """
    Bull Indicator Criteria
        1. Start with a bullish candlestick followed by a bearish candlestick completely embedded in the first one
        2.  Then 2 candlesticks must appear with a high that does surpass the second candles high
        3. Finally a big bullish candlestick appears with a close that surpasses the high of the second candlestick
    """
    try:
        return data[i, close_column] > data[i - 3, high_column] and \
                data[i, close_column] > data[i - 4, close_column] and \
                data[i - 1, low_column] < data[i, open_column] and \
                data[i - 1, close_column] < data[i, close_column] and \
                data[i - 1, high_column] <= data[i - 3, high_column] and \
                data[i - 2, low_column] < data[i, open_column] and \
                data[i - 2, close_column] < data[i, close_column] and \
                data[i - 2, high_column] <= data[i - 3, high_column] and \
                data[i - 3, high_column] < data[i - 4, high_column] and \
                data[i - 3, low_column] > data[i - 4, low_column] and \
                data[i - 4, close_column] > data[i - 4, open_column]
    except IndexError:
        return False

def hikkake_bear_indicator(data, i: int, open_column: int, high_column: int, low_column:int, close_column: int) -> bool :
    """
    Bearish Indicator Criteria
        1. Start wiht a bearish candlestick followed by a bullish candlestick completely embedded in the first one
        2. Then 2 candlesticks must appear with a low that does surpass the second candles low
        3. Finally a big bearish candlestick appears with a close that surpasses the low of the second candlestick
    """
    try:
        return data[i, close_column] < data[i - 3, low_column] and \
                data[i, close_column] < data[i - 4, close_column] and \
                data[i - 1, high_column] > data[i, open_column] and \
                data[i - 1, close_column] > data[i, close_column] and \
                data[i - 1, low_column] >= data[i - 3, low_column] and \
                data[i - 2, high_column] > data[i, open_column] and \
                data[i - 2, close_column] > data[i, close_column] and \
                data[i - 2, low_column] >= data[i - 3, low_column] and \
                data[i - 3, low_column] > data[i - 4, low_column] and \
                data[i - 3, high_column] < data[i - 4, high_column] and \
                data[i - 4, close_column] < data[i - 4, open_column]
    except IndexError:
        return False
    
def signal(data, open_column, high_column, low_column, close_column, buy_signal, sell_signal) :
    
    for i in range(len(data)) :

        try:
            
            # Bullish Pattern
            if hikkake_bull_indicator(data, i, open_column, high_column, low_column, close_column):
                data[i + 1, buy_signal] = 1
            
            # Bearish Pattern
            elif  hikkake_bear_indicator(data, i, open_column, high_column, low_column, close_column):
                data[i + 1, sell_signal] = -1

        except IndexError:
            pass

    return data

