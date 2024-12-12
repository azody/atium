# -*- coding: utf-8 -*-
"""
Created on Wed Nov  1 10:51:44 2023

@author: zodyac

For informational and exploratory purposes


Volatility
- Directly related to risk, indirectly related to return
- A volatile asset is one that has its returns fluctuate greately around the mean
- Types
    - Historical Volatility
        - Realized volatility over a certain period of time
        - Used as a guage for future volatility
        - ex. Standard Deviation
    - Implied Volatility
        - The measure that when inputted into the Black-Scholes equation, gives the option's market price
            - Black-Scholes is a mathematical formula used to price options
            - An options price is the premium a buyer pays to buy the option
            - It is the expected future actual volatility
            - Time Scale: Options Expiration Date
    - Forward Volatility
        - The volatility over a specific period in the future
    - Actual volatility
        - The amount of volatility at any given time, also known as the Local Volatility
        - Hard to calculate and has no time scale

Variance
    - Dispersion measure calculate as the squared deviations from the mean
    - By squaring the distances, you acoid a negative distance measure

Standard Deviation
    - The average distance away from the mean that ytou expect to find when you analyze every value in a dataset

Average True Range
- Used as a guage of historical volatility
- True Range is the greatest og the three price differences
    - High - Low
    - High - Previous close
    - Previous close - Low
- Smoothed average of the true range over a look back period gives the ATR
- Trends
    - High volatility usually occurs in times of panic
        - Downward Trending
"""
from array_util import add_column, delete_column, delete_row
from indicator import smoothed_moving_average, moving_average, relative_strength_indicator
import math

def variance(data):
    # Number of observations
    n = len(data)
    # Mean of the data
    mean = sum(data) / n
    # Square deviations
    deviations = [(x - mean) ** 2 for x in data]
    # Variance
    variance = sum(deviations) / n
    return variance

# Square root of variance
def stdev(data):
    var = variance(data)
    std_dev = math.sqrt(var)
    return std_dev


def volatility(data, lookback, close, position):

    data = add_column(data, 1)

    for i in range(len(data)):

        try:

            data[i, position] = (data[i - lookback + 1:i + 1, close].std())

        except IndexError:

            pass

    data = delete_row(data, lookback)

    return data


def average_true_range(data, lookback, high_column, low_column, close_column, position):

    data = add_column(data, 1)

    for i in range(len(data)):

        try:
            data[i, position] = max(data[i, high_column] - data[i, low_column], \
                                    abs(data[i, high_column] - data[i-1, close_column]), \
                                    abs(data[i, low_column] - data[i-1, close_column]))
        except ValueError:
            pass

    data[0, position] = 0

    data = smoothed_moving_average(data, 2, lookback, position, position + 1)

    data = delete_column(data, position, 1)

    data = delete_row(data, lookback)
    return data

def relative_strength_index_average_true_range(data, lookback_rsi, lookback_atr, lookback_rsi_atr, high, low, close, position):
    data = relative_strength_indicator(data, lookback_rsi, close, position)

    data = average_true_range(data, lookback_atr, high, low, close, position + 1)

    data = add_column(data, 1)

    data[:, position + 2] = data[:, position] / data[:, position + 1]

    data = relative_strength_indicator(data, lookback_rsi_atr, position + 2, position + 3)

    data = delete_column(data, position, 3)
    return data

def k_volatility_band(data, lookback, multiplier, high, low, close, position):

    data = add_column(data, 6)

    # Calculating the median line
    for i in range(len(data)):

        try:

            data[i, position] = max(data[i - lookback + 1:i + 1, high])
            data[i, position + 1] = min(data[i - lookback + 1:i + 1, low])
            data[i, position + 2] = (data[i, position] + data[i, position + 1]) / 2

        except ValueError:

            pass

    # Cleaning
    data = delete_column(data, position, 2)

    # Calculating maximum volatility
    data = volatility(data, lookback, close, position + 1)

    for i in range(len(data)):

        try:

            data[i, position + 2] = max(data[i - lookback + 1:i + 1, position + 1])

        except ValueError:

            pass

    # Cleaning
    data = delete_column(data, position + 1, 1)

    # Calculating the bands
    data[:, position + 2] = data[:, position] + (multiplier * data[:, position + 1])
    data[:, position + 3] = data[:, position] - (multiplier * data[:, position + 1])

    # Cleaning
    data = delete_column(data, position + 1, 1)

    return data


def bollinger_bands(data, lookback, standard_deviation, close, position):
    data = add_column(data, 2)

    # Calculating means
    data = moving_average(data, lookback, close, position)

    data = volatility(data, lookback, close, position + 1)

    data[:, position + 2] = data[:, position] + (standard_deviation * data[:, position + 1])
    data[:, position + 3] = data[:, position] - (standard_deviation * data[:, position + 1])

    data = delete_row(data, lookback)

    data = delete_column(data, position + 1, 1)

    return data
