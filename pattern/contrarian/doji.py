# -*- coding: utf-8 -*-
"""
Doji Pattern
- Most well known and intuitive candle pattern
- Rational
    - Doji candle is the quiliibrium between supply and demand before a reversal
- Variants
    - Dragonfly Doji: High price equal to the close and open price
    - Gravestone Doji: Low price is equal to close and open price
    - Flat Doji: Occurs when open, high, low and close are equal
        - Indicates low volume and liquidity
    - Double Doji: Two Dojis in a row
        - Two Dojis are not worth more than one
    - Tri Star Doji: Three Dojis in a row where the middle Doji gaps over the other two
- Results
    - Common pattern
    - Generally not predictive
    - Works best in sideways marketsa
Rounding
    - Use my_data = rounding(my_data, 4) for EURUSD, USDCHF, GBPUSD, USDCAD
    - vsUse my_data = rounding(my_data, 0) for BTCUSD, ETHUSD, XAUUSD, SP500m, UK100

"""
from array_util import add_column

def doji_bull_indicator(data, i, open_column, close_column):
    """
    Bullish Indicator
        1. First a bearish candle
        2. Then a Doji Candle
        3. Finally a bullish candle
    """
    try:
        return data[i, close_column] > data[i, open_column] and \
              data[i, close_column] > data[i - 1, close_column] and \
              data[i - 1, close_column] == data[i - 1, open_column] and \
              data[i - 2, close_column] < data[i - 2, open_column] and \
              data[i - 2, close_column] < data[i - 2, open_column]
    except IndexError:
         return False

def doji_bear_indicator(data, i, open_column, close_column):
    """
    Bearish Indicator
        1. First a bullish candle
        2. Then a Doji Candle
        3. Finally a bearish candle
    """
    try:

       if data[i, close_column] < data[i, open_column] and \
          data[i, close_column] < data[i - 1, close_column] and \
          data[i - 1, close_column] == data[i - 1, open_column] and \
          data[i - 2, close_column] > data[i - 2, open_column] and \
          data[i - 2, close_column] > data[i - 2, open_column]:

                return True

    except IndexError:
         return False

    return False

def signal(data, open_column, close_column, buy_column, sell_column):
    """Generates Bull and Bear Indicators for the Doji Signal"""

    for i in range(len(data)):

       try:

           # Bullish pattern
           if doji_bull_indicator(data, i, open_column, close_column):
               data[i + 1, buy_column] = 1

           # Bearish pattern
           elif doji_bear_indicator(data, i, open_column, close_column):
               data[i + 1, sell_column] = -1

       except IndexError:
            pass

    return data
