# -*- coding: utf-8 -*-
"""
Created on Tue Nov 21 11:56:47 2023

@author: zodyac

Tower Pattern
- Multi candle trend
    - Generally 5 candles
    - Signals the end of a gradual trend
- Variations
    - Can vary the number of candles in the middle

Body Size
- Use body = 0.0005 for EURUSD, USDCHF, GBPUSD, USDCAD
- Use body = 50 for BTCUSD
- Use body = 10 for ETHUSD
- Use body = 2 for XAUUSD
- Use body = 10 for SP500m, UK100

"""
def tower_bullish_indicator(data, i: int, open_column: int, high_column: int, low_column: int, close_column: int, body) -> bool :
    """
    Bullish Criteria
        1. Starts with a bearish candle
        2. Followed by 3 smaller candles
        3. Middle of the three must be slightly lower than the other two
        4. Last, a normal sized bullish candlestick
    """
    try:
        return data[i, close_column] > data[i, open_column] and \
              data[i, close_column] - data[i, open_column] > body and \
              data[i - 2, low_column] < data[i - 1, low_column] and \
              data[i - 2, low_column] < data[i - 3, low_column] and \
              data[i - 4, close_column] < data[i - 4, open_column] and \
              data[i - 4, open_column] - data[i, close_column] > body
    except IndexError:
        return False

def tower_bearish_indicator(data, i: int, open_column: int, high_column: int, low_column: int, close_column: int, body) -> bool :
    """
    Bearish Pattern
        1. Starts with a bulliosh candle
        2. Followed by 3 smaller candles
        3. Middle of the three must be higher than the other two
        4. Lastly, a normal sized bearish candlestick
    """
    try:
        return data[i, close_column] < data[i, open_column] and \
                data[i, open_column] - data[i, close_column] > body and \
                data[i - 2, high_column] > data[i - 1, high_column] and \
                data[i - 2, high_column] > data[i - 3, high_column] and \
                data[i - 4, close_column] > data[i - 4, open_column] and \
                data[i - 4, close_column] - data[i, open_column] > body
    except IndexError:
        return False
    
def signal(data, open_column, high_column, low_column, close_column, buy_column, sell_column, body):
    """Signal for Tower Pattern"""
    for i in range(len(data)):

       try:

           # Bullish pattern
           if tower_bullish_indicator(data, i, open_column, high_column, low_column, close_column, body):
                data[i + 1, buy_column] = 1

           # Bearish pattern
           elif tower_bearish_indicator(data, i, open_column, high_column, low_column, close_column, body):
                data[i + 1, sell_column] = -1

       except IndexError:
            pass

    return data