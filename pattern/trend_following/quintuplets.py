'''

Signal for Quintuplets Pattern


Max Body Matrix
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
    - Multi Candlestick patter that confimrs underlying trend
    - 5 short candles in a row going the same direction
    - Bullish
        - The last 5 close prices are each greater than their open price
        - And the last 5 close prices are greater than the close prices proceeding them
        - And and each candlestick respects a maximum body size
    - Bearish
        - The last 5 close prices are each lower than their open price
        - And the last 5 close prices are lower than the close prices proceeding them
        - And and each candlestick respects a maximum body size
Psychology
    - Herding and failure to react
    - Usually seen in markets where the price action remains in a low volatility environment but a clear trend can be seen

'''

from data_import import mass_import
from array_util import add_column
from chart_util import signal_chart
from performance import performance
from rounding_util import rounding


def signal(data, open_column, high_column, low_column, close_column, buy_column, sell_column, max_candle_size):

    data = add_column(data, 5)

    for i in range(len(data)):

        try:

            # Bullish pattern
            if data[i, close_column] > data[i, open_column] and \
                data[i, close_column] > data[i - 1, close_column] and \
                data[i, close_column] - data[i, open_column] <= max_candle_size and \
                data[i - 1, close_column] > data[i - 1, open_column] and \
                data[i - 1, close_column] > data[i - 2, close_column] and \
                data[i - 1, close_column] - data[i - 1, open_column] <= max_candle_size and \
                data[i - 2, close_column] > data[i - 2, open_column] and \
                data[i - 2, close_column] > data[i - 3, close_column] and \
                data[i - 2, close_column] - data[i - 2, open_column] <= max_candle_size and \
                data[i - 3, close_column] > data[i - 3, open_column] and \
                data[i - 3, close_column] > data[i - 4, close_column] and \
                data[i - 3, close_column] - data[i - 3, open_column] <= max_candle_size and \
                data[i - 4, close_column] > data[i - 4, open_column] and \
                data[i - 4, close_column] - data[i - 4, open_column] <= max_candle_size and \
                data[i, buy_column] == 0:

                    data[i + 1, 4] = 1

            # Bearish pattern
            elif data[i, close_column] < data[i, open_column] and \
                data[i, close_column] < data[i - 1, close_column] and \
                data[i, open_column] - data[i, close_column] <= max_candle_size and \
                data[i - 1, close_column] < data[i - 1, open_column] and \
                data[i - 1, close_column] < data[i - 2, close_column] and \
                data[i - 1, open_column] - data[i - 1, close_column] <= max_candle_size and \
                data[i - 2, close_column] < data[i - 2, open_column] and \
                data[i - 2, close_column] < data[i - 3, close_column] and \
                data[i - 2, open_column] - data[i - 2, close_column] <= max_candle_size and \
                data[i - 3, close_column] < data[i - 3, open_column] and \
                data[i - 3, close_column] < data[i - 4, close_column] and \
                data[i - 3, open_column] - data[i - 3, close_column] <= max_candle_size and \
                data[i - 4, close_column] < data[i - 4, open_column] and \
                data[i - 4, open_column] - data[i - 4, close_column] <= max_candle_size and \
                data[i, sell_column] == 0:

                   data[i + 1, 5] = -1

        except IndexError:

            pass

    return data


# Choose an Asset
pair = 0 # EURUSD

# Time frame
horizon = "M5"

# Importing the asset as an array
my_data = mass_import(pair, horizon)
my_data = rounding(my_data, 5)

# Calling the Signal Function
my_data = signal(my_data, 0, 1, 2, 3, 4, 5, 0.0005)

# Charting the latest 150 Signals
signal_chart(my_data, 0, 4, 5, window = 200)

# Get Performance Metrics
performance(my_data, 0, 4, 5, 6, 7, 8)
