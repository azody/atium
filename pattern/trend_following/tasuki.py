# -*- coding: utf-8 -*-
"""
Signal for Marubozu pattern

Characteristics
- Gap is formed giving a continuation signal
    - Gaps are a discontinuation or a hole between to successive close prices, mainly caused by loq liquidity
- Types of Gaps
    - Common gaps: Occur in a sideways market
        - likely to be filled because of markets mean-reversion dynamic
    - Breakaway gaps: Generally resemble a common gap
        - Gap occurs above a graphical resistance or below a graphical support
    - Runaway gaps: Generally occur within the trend but confirm it more
        - Continuation Pattern
    - Exhaustion gaps: generally occur at the end of a trend and close to a support or resistance level
        - Reversal pattern
    - Impossible to know the type of gap ahead of time
- Bullish characteristic
    - Three candlesticks where first is bullish, second is bullish and the third is bearish but does not close below the close of the first
- Bearish Characteristic
    - Three candlesticks where first is bearish, second is bearish and the third is bullish but does not close above the close of the first
Reasoning
- Market Psychology
    - Not enough pressure to reach the previous threshold
Nuances
- Rarely occurs
- Intuitive but not neccesarily accurate

"""
def tasuki_bull_indicator(data, i:int, open_column: int, close_column: int) -> bool:
    """
    Bullish Indicator
        1. The close price from two periods ago is greater than the open price from two periods ago
        2. The open price from one period ago is greater than the close two period ago
        3. The close price from one period ago is greater than the close two periods ago
        4. The close price from one period ago is greater than the open price from one period ago
        5. The current close price is greater than the close price two periods ago
    """
    try:
        return  data[i, close_column] < data[i, open_column] and \
                data[i, close_column] < data[i - 1, open_column] and \
                data[i, close_column] > data[i - 2, close_column] and \
                data[i - 1, close_column] > data[i - 1, open_column] and \
                data[i - 1, open_column] > data[i - 2, close_column] and \
                data[i - 2, close_column] > data[i - 2, open_column]
    except IndexError:
        return False

def tasuki_bear_indicator(data, i:int, open_column: int, close_column: int) -> bool:
    """
    Bearish Indicator
        1. The close price from two periods ago is lower than the open price from two periods ago
        2. The open price from one period ago is lower than the sloce two periods ago
        3. The close price from one period is greater than the open price from one period ago
        4. The current close price is greater than the close price two periods ago
    """
    try:
        return  data[i, close_column] > data[i, open_column] and \
                data[i, close_column] > data[i - 1, open_column] and \
                data[i, close_column] < data[i - 2, close_column] and \
                data[i - 1, close_column] < data[i - 1, open_column] and \
                data[i - 1, open_column] < data[i - 2, close_column] and \
                data[i - 2, close_column] < data[i - 2, open_column]

    except IndexError:
        return False

def signal(data, open_column, close_column, buy_column, sell_column) :
    """Generates Bull and Bear Indicator for Tasuki Pattern"""

    for i in range(len(data)) :
        try:
            # Bullish pattern
            if tasuki_bull_indicator(data, i, open_column, close_column) and \
                data[i, buy_column] == 0:
                
                data[i, buy_column] = 1

            # Bearish pattern
            elif tasuki_bull_indicator(data, i, open_column, close_column) and \
                data[i, sell_column] == 0:
                
                data[i + 1, sell_column] = -1

        except IndexError:
            pass

    return data
