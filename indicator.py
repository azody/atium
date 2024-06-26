# -*- coding: utf-8 -*-
"""
Created on Mon Jun 19 15:11:07 2023

@author: zodyac
"""
from array_util import add_column, delete_row, delete_column
import numpy as np


def moving_average(data, lookback, close, position) :

    # Add a column to store moving average
    data = add_column(data, 1)

    for i in range(len(data)) :
        try:
            data[i, position] = (data[i - lookback + 1: i + 1, close].mean())
        except IndexError :
            pass

    data = delete_row(data, lookback)
    return data

def smoothed_moving_average(data, alpha, lookback, close, position) :

    lookback = (2 * lookback) - 1

    alpha = alpha / (lookback + 1.0)

    beta = 1 - alpha

    data = moving_average(data, lookback, close, position)

    data[lookback + 1, position] = (data[lookback + 1, close] * alpha) + (data[lookback, position] * beta)

    for i in range(lookback + 2, len(data)) :
        try :
            data[i, position] = (data[i, close] * alpha) + (data[i-1, position] * beta)
        except IndexError :
            pass

    return data

def relative_strength_indicator(data, lookback, close, position) :

    data = add_column(data, 5)

    for i in range(len(data)):

        data[i, position] = data[i, close] - data[i - 1, close]

    for i in range(len(data)):

        if data[i, position] > 0:

            data[i, position + 1] = data[i, position]

        elif data[i, position] < 0:

            data[i, position + 2] = abs(data[i, position])

    data = smoothed_moving_average(data, 2, lookback, position + 1, position + 3)
    data = smoothed_moving_average(data, 2, lookback, position + 2, position + 4)

    data[:, position + 5] = data[:, position + 3] / data[:, position + 4]

    data[:, position + 6] = (100 - (100 / (1 + data[:, position + 5])))

    data = delete_column(data, position, 6)
    data = delete_row(data, lookback)

    return data






def stochastic_oscillator(data, lookback, high, low, close, position, slowing = False, smoothing = False, slowing_period = 1, smoothing_period = 1):

    data = add_column(data, 1)

    for i in range(len(data)):

        try:

            data[i, position] = (data[i, close] - min(data[i - lookback + 1:i + 1, low])) / (max(data[i - lookback + 1:i + 1, high]) - min(data[i - lookback + 1:i + 1, low]))

        except ValueError:

            pass

    data[:, position] = data[:, position] * 100

    if slowing == True and smoothing == False:

        data = moving_average(data, slowing_period, position, position + 1)

    if smoothing == True and slowing == False:

        data = moving_average(data, smoothing_period, position, position + 1)

    if smoothing == True and slowing == True:

        data = moving_average(data, slowing_period, position,   position + 1)

        data = moving_average(data, smoothing_period, position + 1, position + 2)

    data = delete_row(data, lookback)

    return data

def trend_intensity_indicator(data, lookback, close_column, position):

    data = add_column(data, 5)

    # Calculating the Moving Average
    data = moving_average(data, lookback, close_column, position)

    # Deviations
    for i in range(len(data)):

        if data[i, close_column] > data[i, position]:
            data[i, position + 1] = data[i, close_column] - data[i, position]

        if data[i, close_column] < data[i, position]:
            data[i, position + 2] = data[i, position] - data[i, close_column]

    # Trend Intensity Index
    for i in range(len(data)):

        data[i, position + 3] = np.count_nonzero(data[i - lookback + 1:i + 1, position + 1])

    for i in range(len(data)):

        data[i, position + 4] = np.count_nonzero(data[i - lookback + 1:i + 1, position + 2])

    for i in range(len(data)):

        data[i, position + 5] = ((data[i, position + 3]) / (data[i, position + 3] + data[i, position + 4])) * 100

    data = delete_column(data, position, 5)

    return data


def k_envelopes(data, lookback, high, low, position):
    # Calculating the upper moving average
    data = moving_average(data, lookback, high, position)

    # Calculating the lower moving average
    data = moving_average(data, lookback, low, position + 1)

    return data