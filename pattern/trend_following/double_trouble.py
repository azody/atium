# -*- coding: utf-8 -*-

"""
Double Trouble Pattern

Uses exogeneous variables to be validated
- Borrow information from the Average True Range (ATR) to validate the signal on the pattern

Psychology
    - Market euphoria
    - Indicator of a short squeeze
"""
def double_trouble_bull_indicator(data, i: int, open_column: int, high_column: int, low_column: int, close_column: int, atr_column: int ) -> bool :
    """
    Bull Criteria
        1. 2 bullish candles with the first close price lower than the second close price
        2. 2nd candlestick must be at least double the size of the previous candlesticks 10 period ATR
    """
    try:
        return data[i, close_column] > data[i, open_column] and \
              data[i, close_column] > data[i - 1, close_column] and \
              data[i - 1, close_column] > data[i - 1, open_column] and \
              data[i, high_column] - data[i, low_column] > (2 * data[i - 1, atr_column]) and \
              data[i, close_column] - data[i, open_column] > data[i - 1, close_column] - data[i - 1, open_column]
    except IndexError:
        return False

def double_trouble_bear_indicator(data, i: int, open_column: int, high_column: int, low_column: int, close_column: int, atr_column: int ) -> bool :
    """
    Bear Criteria
        1. 2 bearish candles with the first close price higher than the second close price
        2. 2nd candlestick must also be at least double the size of the previous canbdlestick 10-period ATR
    """
    try:
        return data[i, close_column] < data[i, open_column] and \
              data[i, close_column] < data[i - 1, close_column] and \
              data[i - 1, close_column] < data[i - 1, open_column] and \
              data[i, high_column] - data[i, low_column] > (2 * data[i - 1, atr_column]) and \
              data[i, open_column] - data[i, close_column] > data[i - 1, open_column] - data[i - 1, close_column]
    except IndexError:
        return False
    
def signal(data, open_column, high_column, low_column, close_column, atr_column, buy_column, sell_column):
    """Generates Bull and Bear Indicators for the Double Trouble Pattern"""

    for i in range(len(data)):

        try:
            # Bullish pattern
            if double_trouble_bull_indicator(data, i, open_column, high_column, low_column, close_column, atr_column) and \
                data[i, buy_column] == 0:

                    data[i + 1, buy_column] = 1

            # Bearish pattern
            elif double_trouble_bear_indicator(data, i, open_column, high_column, low_column, close_column, atr_column) and \
                data[i, sell_column] == 0:

                    data[i + 1, sell_column] = -1

        except IndexError:
            pass

    return data

# my_data = average_true_range(my_data, 10, 1, 2, 3, 4)
