# -*- coding: utf-8 -*-
"""
Bottle Pattern
"""
def bottle_bull_indicator(data, i: int, open_column: int, low_column: int, close_column: int) -> bool:
    """
    Bullish Criteria for Bottle Pattern
        1. Bullish Candle followed by another bullish candle
        2. No wick on the low side
        3. Wick on the high side
        4. Second candle must open below the last candles close
    """
    try:
        return data[i, close_column] > data[i, open_column] and \
            data[i, open_column] == data[i, low_column] and \
            data[i - 1, close_column] > data[i - 1, open_column] and \
            data[i, open_column] < data[i - 1, close_column]
    except IndexError:
        return False

def bottle_bear_indicator(data, i: int, open_column: int, high_column: int, close_column: int) -> bool:
    """
    Bearish Criteria for Bottle Pattern
        1. Bearish Candles followed by another bearish candle
        2. No wick on the high side
        3. Wick on the low side
        4. Second candle must open above the last cadlesticks close
    """
    try:
        return data[i, close_column] < data[i, open_column] and \
            data[i, open_column] == data[i, high_column] and \
            data[i - 1, close_column] < data[i - 1, open_column] and \
            data[i, open_column] > data[i - 1, close_column]
    except IndexError:
        return False

def signal(data, open_column, high_column, low_column, close_column, buy_column, sell_column):
    """Generates Bulla and Bear Indcators for the Bottle Pattern"""
    for i in range(len(data)):

        try:
            # Bullish pattern
            if bottle_bull_indicator(data, i, open_column, low_column, close_column) and \
                data[i, buy_column] == 0:

                data[i + 1, buy_column] = 1

            # Bearish pattern
            elif bottle_bear_indicator(data, i, open_column, high_column, low_column) and \
                data[i, sell_column] == 0:

                data[i + 1, sell_column] = -1

        except IndexError:
            pass

    return data
