# -*- coding: utf-8 -*-
"""
Star Pattern
- 3 Candle configuration
    - Less common than others as it relies on a gap
- Logic
    - The market has made a perfectly shaped U Turn after discovering a new high/low
Rounding
 - Use my_data = rounding(my_data, 4) for EURUSD, USDCHF, GBPUSD, USDCAD
 - Use my_data = rounding(my_data, 0) for BTCUSD, ETHUSD, XAUUSD, SP500m, UK100
"""
def star_bullish_indicator(data, i: int, open_column: int, high_column: int, low_column: int, close_column: int):
    """
    Bullish Pattern (Morning Star)
        1. First, a bearish candle
        2. Second, a small bodied candle that gaps below it
        3. Does not matter if second candle bearish or bullish
        4. Third, a bullish candle which gaps higher than the middle candle
    """
    try:
        return data[i, close_column] > data[i, open_column] and \
                max(data[i - 1, close_column], data[i - 1, open_column]) < data[i, open_column] and \
                max(data[i - 1, close_column], data[i - 1, open_column]) < data[i - 2, close_column] and \
                data[i - 2, close_column] < data[i - 2, open_column]
    except IndexError:
        return False
        
def star_bearish_indicator(data, i: int, open_column: int, high_column: int, low_column: int, close_column: int):
    """
    Bearish Pattern (Evening Star)
        1. First, a nullish candle
        2. Second, a small bodied candle that gaps above it
        3. Does not matter if second candle is bearish or bullish
        4. Third, a bearish candle which gaps lower than the middle candle
    """
    try:
        return data[i, close_column] < data[i, open_column] and \
                min(data[i - 1, close_column], data[i - 1, open_column]) > data[i, open_column] and \
                min(data[i - 1, close_column], data[i - 1, open_column]) > data[i - 2, close_column] and \
                data[i - 2, close_column] > data[i - 2, open_column]
    except IndexError:
        return False
    
def signal(data, open_column, high_column, low_column, close_column, buy_column, sell_column):
    """Star Pattern Signal"""
    for i in range(len(data)):

       try:
            # Bullish pattern
            if star_bullish_indicator(data, i, open_column, high_column, low_column, close_column):
                data[i + 1, buy_column] = 1

            # Bearish pattern
            elif star_bearish_indicator(data, i, open_column, high_column, low_column, close_column):
                data[i + 1, sell_column] = -1

       except IndexError:
            pass

    return data