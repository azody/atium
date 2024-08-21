# -*- coding: utf-8 -*-
"""
Three Methods Pattern

Reasoning
- Market Psychology
    - Stabilizes price through correction or consolidation as traders close out of positions following a big bear/bull candle
Nuances
- Rare signal
- Rarely useful when used on its own

"""
def three_methods_bull_indicator(data, i: int, open_column: int, high_column: int, low_column: int, close_column: int) -> bool:
    """
    Bullish Indicator Criteria
        1. First candlestick is a big bullish candle, followed by three smaller bodied bearish candlesticks and one final big bullish candle
        2. Three bearish candles are normally contained in the range of the first bullish cnadle
        3. Final bullish candle has a close higher than the first candles high
    """
    try:
        return data[i, close_column] > data[i, open_column] and \
              data[i, close_column] > data[i - 4, high_column] and \
              data[i, low_column] < data[i - 1, low_column] and \
              data[i - 1, close_column] < data[i - 4, close_column] and \
              data[i - 1, low_column] > data[i - 4, low_column] and \
              data[i - 2, close_column] < data[i - 4, close_column] and \
              data[i - 2, low_column] > data[i - 4, low_column] and \
              data[i - 3, close_column] < data[i - 4, close_column] and \
              data[i - 3, low_column] > data[i - 4, low_column] and \
              data[i - 4, close_column] > data[i - 4, open_column]
    except IndexError:
        return False
    
def three_methods_bear_indicator(data, i: int, open_column: int, high_column: int, low_column: int, close_column: int) -> bool:
    """
    Bearish Indicator Criteria
        1. First candlestick is a big bearish candle, followed by three smaller bodied bullish candlesticks and one final big bearish candle
        2. Three bullish candles are normally contained in the range of the first bearish cnadle
        3. Final bearish candle has a close lower than the first candles low
    """
    try:
        return data[i, close_column] < data[i, open_column] and \
                data[i, close_column] < data[i - 4, low_column] and \
                data[i, high_column] > data[i - 1, high_column] and \
                data[i - 1, close_column] > data[i - 4, close_column] and \
                data[i - 1, high_column] < data[i - 4, high_column] and \
                data[i - 2, close_column] > data[i - 4, close_column] and \
                data[i - 2, high_column] < data[i - 4, high_column] and \
                data[i - 3, close_column] > data[i - 4, close_column] and \
                data[i - 3, high_column] < data[i - 4, high_column] and \
                data[i - 4, close_column] < data[i - 4, open_column]
    except IndexError:
        return False

def signal(data, open_column, high_column, low_column, close_column, buy_column, sell_column) :
    """Generates Bull and Bear Indicator for the Marubozu Signal"""
    
    for i in range(len(data)) :
        try :
            # Bullish Pattern
            if three_methods_bull_indicator(data, i, open_column, high_column, low_column, close_column):
                    data[i + 1, buy_column] = 1

            # Bearish Pattern
            elif three_methods_bear_indicator(data, i, open_column, high_column, low_column, close_column):
                    data[i + 1, sell_column] = 1

        except IndexError:
            pass
    
    return data
