from array_util import add_column
from chart_util import signal_chart_indicator_plot_candles
from data_import import mass_import
from volatility import relative_strength_index_average_true_range
from performance import performance


# Creating the signal function
def signal(data, open_column, close_column, indicator_column, buy_column, sell_column):
    data = add_column(data, 5)

    for i in range(len(data)):

        try:

            # Bullish setup
            if data[i, close_column] > data[i, open_column] and \
                    data[i, close_column] < data[i - 1, open_column] and \
                    data[i, close_column] > data[i - 1, close_column] and \
                    data[i, open_column] < data[i - 1, close_column] and \
                    data[i - 1, close_column] < data[i - 1, open_column] and \
                    data[i - 2, close_column] < data[i - 2, open_column] and \
                    data[i, indicator_column] < lower_barrier:

                data[i + 1, buy_column] = 1

                # Bearish setup
            elif data[i, close_column] < data[i, open_column] and \
                    data[i, close_column] > data[i - 1, open_column] and \
                    data[i, close_column] < data[i - 1, close_column] and \
                    data[i, open_column] > data[i - 1, close_column] and \
                    data[i - 1, close_column] > data[i - 1, open_column] and \
                    data[i - 2, close_column] > data[i - 2, open_column] and \
                    data[i, indicator_column] > upper_barrier:

                data[i + 1, sell_column] = -1

        except IndexError:

            pass

    return data

# Choosing the asset
pair = 0

# Time Frame
horizon = 'H1'
lookback_rsi = 5
lookback_atr = 5
lookback_rsi_atr = 5

# Importing the asset as an array
my_data = mass_import(pair, horizon)
upper_barrier = 80
lower_barrier = 20

my_data = relative_strength_index_average_true_range(my_data, lookback_rsi, lookback_atr, lookback_rsi_atr, 1, 2, 3, 4)



# Calling the signal function
my_data = signal(my_data, 0, 3, 4, 5, 6)

# Charting the latest signals
signal_chart_indicator_plot_candles(my_data,
                                    0,
                                    4,
                                    5,
                                    6,
                                    barriers=True,
                                    window=250)

# Performance
my_data = performance(my_data, 0, 5, 6, 7, 8, 9)