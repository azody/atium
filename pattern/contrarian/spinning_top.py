# -*- coding: utf-8 -*-
"""
Spinning Top Pattern
- Resembles Doji Pattern but it less strict
- Hints at a rise in Volatility

Max Body Size
- Use body = 0.0003 for EURUSD, USDCHF, GBPUSD, USDCAD
- Use body = 50 for BTCUSD
- Use body = 10 for ETHUSD
- Use body = 2 for XAUUSD
- Use body = 5 for SP500m, UK100

Min Wick Size
- Use wick = 0.0005 for EURUSD, USDCHF, GBPUSD, USDCAD
- Use wick = 20 for BTCUSD
- Use wick = 5 for ETHUSD
- Use wick = 1 for XAUUSD
- Use wick = 3 for SP500m, UK100

"""
def spinning_top_bullish_indicator(data, i: int, open_column: int, high_column: int, low_column: int, close_column: int, body, wick):
    """
    Bullish Pattern
        1. First a bearish candle
        2. Second a small bodied candle with long wick (highs and low)
        3. Third a bullish candle
    """
    try:
        return data[i, close_column] - data[i, open_column] > body and \
               data[i - 1, high_column] - data[i - 1, close_column] >= wick and \
               data[i - 1, open_column] - data[i - 1, low_column] >= wick and \
               data[i - 1, close_column] - data[i - 1, open_column] < body and \
               data[i - 1, close_column] > data[i - 1, open_column] and \
               data[i - 2, close_column] < data[i - 2, open_column] and \
               data[i - 2, open_column] - data[i - 2, close_column] > body
    except IndexError:
        return False

def spinning_top_bearish_indicator(data, i: int, open_column: int, high_column: int, low_column: int, close_column: int, body, wick):
    """
    Bearish Pattern
        1. First a bullish candle
        2. Second a small bodied candle with long wicks (highs and lows)
        3. Third a bearish candle
    """
    try:
        return data[i, open_column] - data[i, close_column] > body and \
                 data[i - 1, high_column] - data[i - 1, open_column] >= wick and \
                 data[i - 1, close_column] - data[i - 1, low_column] >= wick and \
                 data[i - 1, open_column] - data[i - 1, close_column] < body and \
                 data[i - 1, close_column] < data[i - 1, open_column] and \
                 data[i - 2, close_column] > data[i - 2, open_column] and \
                 data[i - 2, close_column] - data[i - 2, open_column] > body
    except IndexError:
        return False


def signal(data, open_column, high_column, low_column, close_column, buy_column, sell_column, body, wick):
    """Spinning Top Signal"""
    for i in range(len(data)):

       try:

            # Bullish pattern
            if spinning_top_bullish_indicator(data, i, open_column, high_column, low_column, close_column, body, wick):
                data[i + 1, buy_column] = 1

            # Bearish pattern
            elif spinning_top_bullish_indicator(data, i, open_column, high_column, low_column, close_column, body, wick):
                data[i + 1, sell_column] = -1

       except IndexError:

            pass

    return data

