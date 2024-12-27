# -*- coding: utf-8 -*-
"""
Inside Up/Down Pattern
- Signals the end of an initial move
Max bodyII
- Use body = 0.0005 for EURUSD, USDCHF, GBPUSD, USDCAD
- Use body = 50 for BTCUSD
- Use body = 10 for ETHUSD
- Use body = 2 for XAUUSD
- Use body = 10 for SP500m, UK100

"""
def inside_up_down_bullish_indicator(data, i: int, open_column: int, high_column: int, low_column: int, close_column: int, body):
    """
    Bullish Criteria
        1. First a berish candle
        2. Second a smaller bullish candle
        3. Second candle body must be contained in the bearish candle
        4. Third a bullish candle
        5. Third candle must surpass the open of the first candle
    """
    try:
        return data[i - 2, close_column] < data[i - 2, open_column] and \
              abs(data[i - 2, open_column] - data[i - 2, close_column]) > body and \
              data[i - 1, close_column] < data[i - 2, open_column] and \
              data[i - 1, open_column] > data[i - 2, close_column] and \
              data[i - 1, close_column] > data[i - 1, open_column] and \
              data[i, close_column] > data[i - 2, open_column] and \
              data[i, close_column] > data[i, open_column] and \
              abs(data[i, open_column] - data[i, close_column]) > body
    except IndexError:
        return False

def inside_up_down_bearish_indicator(data, i: int, open_column: int, high_column: int, low_column: int, close_column: int, body):
    """
    Bearish Criteria
        1. First a bullish candle
        2. Second a smaller bearish candle
        3. Second candle body must be contained in the bullish candle
        4. Third a bearish candle
        5. Third candle must surpass the open of the first bullish candlea
    """
    try:
        return data[i - 2, close_column] > data[i - 2, open_column] and \
                abs(data[i - 2, close_column] - data[i - 2, open_column]) > body and \
                data[i - 1, close_column] > data[i - 2, open_column] and \
                data[i - 1, open_column] < data[i - 2, close_column] and \
                data[i - 1, close_column] < data[i - 1, open_column] and \
                data[i, close_column] < data[i - 2, open_column] and \
                data[i, close_column] < data[i, open_column] and \
                abs(data[i, open_column] - data[i, close_column]) > body
    except IndexError:
        return False

def signal(data, open_column, high_column, low_column, close_column, buy_column, sell_column, body):

    for i in range(len(data)):

       try:
           # Bullish pattern
           if inside_up_down_bullish_indicator(data, i, open_column, high_column, low_column, close_column, body):
                data[i + 1, buy_column] = 1

           # Bearish pattern
           elif inside_up_down_bearish_indicator(data, i, open_column, high_column, low_column, close_column, body):
                data[i + 1, sell_column] = -1

       except IndexError:

            pass

    return data