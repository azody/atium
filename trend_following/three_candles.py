# -*- coding: utf-8 -*-
"""
Created on Tue Jun 27 14:04:45 2023

@author: zodyac

Signal for Three Candles pattern

If the last three close prices are greater than the ones preceding them
    And each candlestick respects a minimum body size
    Then add a bullish indicator

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
from data_import import mass_import
from array_util import add_column
from chart_util import signal_chart
from performance import performance
from rounding_util import rounding

def signal(data, open_column, close_column, buy_column, sell_column, min_body_size) :

    data = add_column(data, 5)

    for i in range(len(data)) :
        try:
            # Bullish Pattern
            if data[i, close_column] - data[i, open_column] > min_body_size and \
                data[i - 1, close_column] - data [i - 1, open_column] > min_body_size and \
                data[i - 2, close_column] - data [i - 2, open_column] > min_body_size and \
                data[i, close_column] > data[i - 1, close_column] and \
                data[i - 1, close_column] > data[i - 2, close_column] and \
                data[i - 2, close_column] > data[i - 3, close_column] and \
                data[i, buy_column] == 0:

                    data[i+1, buy_column] = 1
            # Bearis Pattern
            if data[i, open_column] - data[i, close_column] > min_body_size and \
                data[i - 1, open_column] - data [i - 1, close_column] > min_body_size and \
                data[i - 2, open_column] - data [i - 2, close_column] > min_body_size and \
                data[i, close_column] < data[i - 1, close_column] and \
                data[i - 1, close_column] < data[i - 2, close_column] and \
                data[i - 2, close_column] < data[i - 3, close_column] and \
                data[i, sell_column] == 0:

                    data[i+1, sell_column] = -1
        except IndexError :
            pass

    return data


# Choose an Asset
pair = 0 # EURUSD

# Time frame
horizon = "H1"

# Importing the asset as an array
my_data = mass_import(pair, horizon)
my_data = rounding(my_data, 5)

# Calling the Signal Function
min_body = 10
my_data = signal(my_data, 0, 3, 4, 5, min_body)

# Charting the latest 150 Signals
signal_chart(my_data, 0, 4, 5, window = 200)

# Get Performance Metrics
performance(my_data, 0, 4, 5, 6, 7, 8)