# -*- coding: utf-8 -*-
"""
Three Candles Pattern

If the close price is lower than the open price
    And the high price equals the open price
    And the low price equals the close price
    Then add a bearish indicator

Min Body Matrix
| Asset   | Body   | Type   |
-----------------------------
| EURUSD  | 0.0005 | USD    |
| USDCHF  | 0.0005 | CHF    |
| GBPUSD  | 0.0005 | USD    |
| USDCAD  | 0.0005 | CAD    |
| BTCUSD  | 50     | USD    |
| ETHUSD  | 10     | USD    |
| Gold    | 2      | USD    |
| SP500   | 10     | Points |
| FTSE100 | 10     | Points |

Characteristics
- Three White Soldiers
    - Bullish version
    - Three big bulilsh candles in a row with each having a close higher than the previous
- Three Black Crows
    - Bearish version
    - Three big bearish candles in a row with each having a close lower than the previous
Reasoning
- Market Psychology
    - Herding: Markets follow trend because others are doing so
    - Humans are attracted to confidence and strength
Nuances
- May be difficult in some markets
    - Currencies go out to 5 decimal places
    - Consider rounding in this scenario

"""
def three_candles_bull_indicator(data, i :int, open_column: int, close_column: int, min_body_size) -> bool :
    """
    Bullish Indicator Criteria
        1. The last three close prices are each greater than the close prices preceeding them
        2. Each candlestick respects a minimum body size
    """
    try:
        return data[i, close_column] - data[i, open_column] > min_body_size and \
            data[i - 1, close_column] - data [i - 1, open_column] > min_body_size and \
            data[i - 2, close_column] - data [i - 2, open_column] > min_body_size and \
            data[i, close_column] > data[i - 1, close_column] and \
            data[i - 1, close_column] > data[i - 2, close_column] and \
            data[i - 2, close_column] > data[i - 3, close_column]
    except IndexError :
        return False
    
def three_candles_bear_indicator(data, i :int, open_column: int, close_column: int, min_body_size) -> bool :
    """
    Bearish Indicator Criteria
        1. The last three close prices are each lower than the close prices preceeding them
        2. Each candlestick respects a minimum body size
    """
    try:
        return data[i, open_column] - data[i, close_column] > min_body_size and \
                data[i - 1, open_column] - data [i - 1, close_column] > min_body_size and \
                data[i - 2, open_column] - data [i - 2, close_column] > min_body_size and \
                data[i, close_column] < data[i - 1, close_column] and \
                data[i - 1, close_column] < data[i - 2, close_column] and \
                data[i - 2, close_column] < data[i - 3, close_column]
    except IndexError:
        return False
        
def signal(data, open_column: int, close_column: int, buy_column: int, sell_column: int, min_body_size) :
    """Generates Bull and Bear Indicators for the Three Candles Signal"""

    for i in range(len(data)) :
        try:
            # Bullish Pattern
            if three_candles_bull_indicator(data, i, open_column, close_column, min_body_size) and \
                data[i, buy_column] == 0:

                data[i+1, buy_column] = 1

            # Bearis Pattern
            if three_candles_bull_indicator(data, i, open_column, close_column, min_body_size) and \
                data[i, sell_column] == 0:

                data[i+1, sell_column] = -1

        except IndexError :
            pass

    return data
