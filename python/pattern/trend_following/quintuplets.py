'''
Quintuplets Pattern


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

Psychology
    - Herding and failure to react
    - Usually seen in markets where the price action remains in a low volatility environment but a clear trend can be seen

'''
def quintuplets_bull_indicator(data, i: int, open_column: int, close_column: int, max_candle_size) -> bool :
    """
    Bull Indicator Criteria
        1. The last 5 close prices are each greater than their open price
        2. The last 5 close prices are greater than the close prices proceeding them
        3. Each candlestick respects a maximum body size
    """
    try:
        return data[i, close_column] > data[i, open_column] and \
              data[i, close_column] > data[i - 1, close_column] and \
              data[i, close_column] - data[i, open_column] < max_candle_size and \
              data[i - 1, close_column] > data[i - 1, open_column] and \
              data[i - 1, close_column] > data[i - 2, close_column] and \
              data[i - 1, close_column] - data[i - 1, open_column] < max_candle_size and \
              data[i - 2, close_column] > data[i - 2, open_column] and \
              data[i - 2, close_column] > data[i - 3, close_column] and \
              data[i - 2, close_column] - data[i - 2, open_column] < max_candle_size and \
              data[i - 3, close_column] > data[i - 3, open_column] and \
              data[i - 3, close_column] > data[i - 4, close_column] and \
              data[i - 3, close_column] - data[i - 3, open_column] < max_candle_size and \
              data[i - 4, close_column] > data[i - 4, open_column] and \
              data[i - 4, close_column] - data[i - 4, open_column] < max_candle_size
    except IndexError:
        return False

def quintuplets_bear_indicator(data, i: int, open_column: int, close_column: int, max_candle_size) -> bool :
    """
    Bear Indicator Criteria
        1. The last 5 close prices are each lower than their open price
        2. The last 5 close prices are lower than the close prices proceeding them
        3. Each candlestick respects a maximum body size
    """
    try:
        return data[i, close_column] < data[i, open_column] and \
                 data[i, close_column] < data[i - 1, close_column] and \
                 data[i, open_column] - data[i, close_column] < max_candle_size and \
                 data[i - 1, close_column] < data[i - 1, open_column] and \
                 data[i - 1, close_column] < data[i - 2, close_column] and \
                 data[i - 1, open_column] - data[i - 1, close_column] < max_candle_size and \
                 data[i - 2, close_column] < data[i - 2, open_column] and \
                 data[i - 2, close_column] < data[i - 3, close_column] and \
                 data[i - 2, open_column] - data[i - 2, close_column] < max_candle_size and \
                 data[i - 3, close_column] < data[i - 3, open_column] and \
                 data[i - 3, close_column] < data[i - 4, close_column] and \
                 data[i - 3, open_column] - data[i - 3, close_column] < max_candle_size and \
                 data[i - 4, close_column] < data[i - 4, open_column] and \
                 data[i - 4, open_column] - data[i - 4, close_column] < max_candle_size
    except IndexError:
        return False

def signal(data, open_column, close_column, buy_column, sell_column, max_candle_size):
    """Generates Bull and Bear Indicatrors for the Quintuplets Pattern"""
    for i in range(len(data)):

        try:

            # Bullish pattern
            if quintuplets_bull_indicator(data, i, open_column, close_column, max_candle_size) and \
                data[i, buy_column] == 0:

                data[i + 1, buy_column] = 1

            # Bearish pattern
            elif quintuplets_bear_indicator(data, i, open_column, close_column, max_candle_size) and \
                data[i, sell_column] == 0:
                
                data[i + 1, sell_column] = 1

        except IndexError:
            pass

    return data
