# -*- coding: utf-8 -*-
"""
Created on Tue Nov 21 14:48:25 2023

@author: zodyac

Heikin-Ashi System
- Transformed Open Price = ((open price i-1) + (close price i-1)) / 2
- Transformed High Price = max(High Price i, Transformed Open Price, Transformed Close Price)
- Transformed Low Price = min(Low PRice i-1, Transformed Open Price, Transformed Close Price)
- Transformed Close Price = (Open Price i + High Price i + Low Price i + Close Price i ) / 4

Notes
- Color variations will be less frequent as it is a smoothed average
- Limitations
    - Not real prices
    - Colors alternate in flat markets
    - A change in color can be lagging
- Always gather performance on real data
"""
from array_util import add_column

def heikin_ashi(data, open_column, high_column, low_column, close_column, position):

    data = add_column(data, 4)

    # Heikin-Ashi Open
    try:
        for i in range(len(data)):
            data[i, position] = (data[i - 1, open_column] + data[i - 1, close_column]) / 2
    except:
        pass

    # Heikin-Ashi Close
    for i in range(len(data)):
        data[i, position + 3] = (data[i, open_column] + data[i, high_column] + data[i, low_column] + data[i, close_column]) / 4

    # Heikin-Ashi High
    for i in range(len(data)):
        data[i, position + 1] = max(data[i, position], data[i, position + 3], data[i, high_column])

    # Heikin-Ashi Low
    for i in range(len(data)):
        data[i, position + 2] = min(data[i, position], data[i, position + 3], data[i, low_column])

    return data