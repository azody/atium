# -*- coding: utf-8 -*-
"""
Created on Thu Mar 28 15:14:12 2024

@author: zodyac

Volatility Band
    - Framing technique that envelops the market price to deliver dynamic support and resistance levels
    - Bollinger Bands
        - Calculation
            - Upper Bollinger band is the sum of the current 20 period moving average and the current standard deviation multiplied by 2
            - Lower Bollinger band is the differnce of the current 20 period moving averaeg and the current standard eviation multiplied by 2
        - Features
            - Oversold at the lower bollinger band
            - Overbought at the top bollinger band
K's Volatility Bands
    - Calculation
        - Calculate the mean between the highest highs and the lowest lows of hte last 20 periods
        - Calculate the highest standard deviation measure ofhte last 20 periods
        - Upper band is the sum of the mean step and 2x the 2nd step
        - Lower band is the difference of the mean step and 2x the 2nd step

"""

