# -*- coding: utf-8 -*-
"""
Created on Sun Dec 31 12:25:08 2023

@author: zodyac

Basics
- Normalization
    - Confine all values between 0 and 1
    - Normalized = (Original - Low) / (High - Low)
Stochastic Oscillator
- Stochastic Normalization = (Close - Lowest Low in Lookback) / (Highest High in Lookback - Lowest Low in Lookback)
- Steps
    - 1. Normalize data using stochastic normalization formula
    - 2. Smooth out the results with a three period moving average
    - 3. Calculate the signal line
        - Another 3 period moving average calculated on the values of the second step
- Features
    - Bounded between 0 and 100
    - Oversold zone is below 20 and overbought zone is above 80
    - More Volatile than RSI
- Strategy
    - Interested in the cross between the stochastic oscillator and its indicator line
    - Long signal is generated whenever a bullish bottle pattern appears while the stochastic oscillator is above its signal line
    - Short signal is generated whenever a bearis bottle pattern appears while the stochastic oscillator is below its signal line
"""

