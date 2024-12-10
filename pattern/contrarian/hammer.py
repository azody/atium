# -*- coding: utf-8 -*-
"""
Hammer Pattern
- One of 4 simliar patterns
    - Shooting Star, hanging Man, Hammer and Inverted Hammer
- Single candle configuration
- Logic
    - After shaping extreme lows, the buyers have managed to close higher than the open price

Max candle height guide
 - Use body = 0.0005 for EURUSD, USDCHF, GBPUSD, USDCAD
 - Use body = 50 for BTCUSD
 - Use body = 10 for ETHUSD
 - Use body = 2 for XAUUSD
 - Use body = 5 for SP500m, UK100

Min Candle Wick height guide
 - Use wick = 0.0002 for EURUSD, USDCHF, GBPUSD, USDCAD
 - Use wick = 10 for BTCUSD
 - Use wick = 2 for ETHUSD
 - Use wick = 0.5 for XAUUSD
 - Use wick = 3 for SP500m, UK100

Rounding
 - Use my_data = rounding(my_data, 4) for EURUSD, USDCHF, GBPUSD, USDCAD
 - Use my_data = rounding(my_data, 0) for BTCUSD, ETHUSD, XAUUSD, SP500m, UK100

"""
def hammer_buy_indicator(data, i: int, open_column: int, high_column: int, low_column: int, close_column: int, buy_column: int, sell_column: int, body, wick) -> bool:
    """
    Bullish Criteria
        1. A bullish candlestick with a long low wick and no high wick
        2. It also must have a relatively small body
    """
    try:
        return data[i, close_column] > data[i, open_column] and \
                abs(data[i - 1, close_column] - data[i - 1, open_column]) < body and \
                min(data[i - 1, close_column], data[i - 1, open_column]) - data[i - 1, low_column] > 2 * wick and \
                data[i - 1, close_column] == data[i - 1, high_column] and \
                data[i - 2, close_column] < data[i - 2, open_column]
    except IndexError:
        return False
    
def hammer_bear_indicator(data, i: int, open_column: int, high_column: int, low_column: int, close_column: int, buy_column: int, sell_column: int, body, wick) -> bool:
    """
    Bearish Criteria
        1. A long high wick and no low wich
        2. It also must have a relatively small body
    """
    try:
        return data[i, close_column] < data[i, open_column] and \
                abs(data[i - 1, close_column] - data[i - 1, open_column]) < body and \
                data[i - 1, high_column] - max(data[i - 1, close_column], data[i - 1, open_column]) > 2 * wick and \
                data[i - 1, close_column] == data[i - 1, low_column] and \
                data[i - 2, close_column] > data[i - 2, open_column]
    except IndexError:
        return False
    
def signal(data, open_column, high_column, low_column, close_column, buy_column, sell_column, body, wick):
    """Signal for the Hammer Pattern"""
    for i in range(len(data)):

       try:
            # Bullish pattern
            if hammer_buy_indicator(data, i, open_column, high_column, low_column, close_column, buy_column, sell_column, body, wick):
                data[i + 1, buy_column] = 1

            # Bearish pattern
            elif  hammer_bear_indicator(data, i, open_column, high_column, low_column, close_column, buy_column, sell_column, body, wick):
                data[i + 1, sell_column] = -1

       except IndexError:
            pass

    return data