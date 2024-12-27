# -*- coding: utf-8 -*-
"""
Abandoned Baby Pattern
 - Extremely rare configuration
 
Rounding
 - Use my_data = rounding(my_data, 4) for EURUSD, USDCHF, GBPUSD, USDCAD
 - Use my_data = rounding(my_data, 0) for BTCUSD, ETHUSD, XAUUSD, SP500m, UK100

"""
def abandoned_baby_bullish_indicator(data, i: int, open_column: int, high_column: int, low_column: int, close_column: int) -> bool:
   """
   Bullish Criteria
      1. First a bearish candle
      2. Second a doji that gaps lower
      3. Third a bullish candlestick
   """
   try:
      return data[i, close_column] > data[i, open_column] and \
               data[i - 1, close_column] == data[i - 1, open_column] and \
               data[i - 1, high_column] < data[i, low_column] and \
               data[i - 1, high_column] < data[i - 2, low_column] and \
               data[i - 2, close_column] < data[i - 2, open_column]
   except IndexError:
      return False
   
def abandoned_baby_bearish_indicator(data, i: int, open_column: int, high_column: int, low_column: int, close_column: int) -> bool:
   """
   Bearish Citeria
      1. First a bullish candle
      2. Second a doji that gaps higher
      3. Third a bearish candlestick
   """
   try:
      return data[i, close_column] < data[i, open_column] and \
                 data[i - 1, close_column] == data[i - 1, open_column] and \
                 data[i - 1, low_column] > data[i, high_column] and \
                 data[i - 1, low_column] > data[i - 2, high_column] and \
                 data[i - 2, close_column] > data[i - 2, open_column]
   except IndexError:
      return False
   
def signal(data, open_column, high_column, low_column, close_column, buy_column, sell_column):

    for i in range(len(data)):

       try:

            # Bullish pattern
            if abandoned_baby_bullish_indicator(data, i, open_column, high_column, low_column, close_column):
                     data[i + 1, buy_column] = 1

            # Bearish pattern
            elif abandoned_baby_bearish_indicator(data, i, open_column, high_column, low_column, close_column):
                     data[i + 1, sell_column] = -1

       except IndexError:

            pass

    return data