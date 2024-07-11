# -*- coding: utf-8 -*-
"""
Marubozu Pattern

Characteristics
- Candles do not have any wicks
    - Bullish has the same open as the low and the close as the high price
    - Bearish has has the same open as the high price and the same close as the low price
- Occurs during shorter time frames
    - 1 and 5 minute charts
    
Reasoning
- Market Psychology
    - Maximum force of demand is when there is only upward movement
- Most powerful representation of bullish or bearish activity

Nuances
- May be difficult in some markets
    - Currencies go out to 5 decimal places
    - Consider rounding in this scenario

"""
from array_util import add_column

def marubozu_bull_indicator(data, i: int, open_column: int, high_column: int, low_column: int, close_column: int) -> bool:
    """
    Bullish Indicato Criteria
        1. Open < Close - Bullish Candle
        2. High = Close - No Top Wick
        3. Low = Open   - No Bottom Wick
    """
    try:
        return data[i, close_column] > data[i, open_column] and \
            data[i, high_column] == data[i, close_column] and \
            data[i, low_column] == data[i, open_column] 

    except IndexError:
        return False

def marubozu_bear_indicator(data, i: int, open_column: int, high_column: int, low_column: int, close_column: int) -> bool:
    """
    Bearish Indicator Criteria
        1. Close < Open - Bearich Candle
        2. High = Open  - No Top Wick
        3. Low = Close  - No Bottom Wick
    """
    try:
        return data[i, close_column] < data[i, open_column] and \
                data[i, high_column] == data[i, open_column] and \
                data[i, low_column] == data[i, close_column]
                
    except IndexError:
        return False

def signal(data, open_column, high_column, low_column, close_column, buy_column, sell_column) :
    """Generates Bull and Bear Indicators for the Marubozu Signal"""

    data = add_column(data, 5)

    for i in range(len(data)):
        try:

            # Bullish Pattern
            if marubozu_bull_indicator(data, i, open_column, high_column, low_column, close_column) and \
                data[i, buy_column] == 0:

                data[i+1, buy_column] = 1

            # Bullish Pattern
            if marubozu_bear_indicator(data, i, open_column, high_column, low_column, close_column) and \
                data[i, sell_column] == 0:

                data[i+1, sell_column] = -1

        except IndexError :
            pass

    return data