# -*- coding: utf-8 -*-
"""
Created on Tue Jun  6 14:36:35 2023

@author: zodyac
"""

import datetime #  Tools for manipulating dates and times
import pytz # Cross platform time zone calculations
import pandas as pd # Data analysis Library
import MetaTrader5 as mt5 # Metatrader5 api

# Create Universe of time frames
frame_M1 = mt5.TIMEFRAME_M1 # 1-minute time frame
frame_M5 = mt5.TIMEFRAME_M5 # 1-minute time frame
frame_M15 = mt5.TIMEFRAME_M15 # 15-minute time frame
frame_M30 = mt5.TIMEFRAME_M30 # 30-minute time frame
frame_H1 = mt5.TIMEFRAME_H1 # Hourly time frame
frame_H4 = mt5.TIMEFRAME_H4 # 4-hour time frame
frame_D1 = mt5.TIMEFRAME_D1 # Day time frame
frame_W1 = mt5.TIMEFRAME_W1 # Week time frame
frame_MN1 = mt5.TIMEFRAME_MN1 # Month time frame

now = datetime.datetime.now()

# Asset Classes
# Currencies a.k.a. forex - bigget financial market in terms of daily volume
## EURUSD pair refers to price of 1 EUR in terms of USD

# Cryptocurrencies
## BTCUSD refers to p

# Commodities
## Physical Assets like gold, silver, coppoer

# Equity Indices
# SP500 for US and FTSE 100 for UK
assets = ['EURUSD', 'USDCHF', 'GBPUSD', 'USCAD', 'BTCUSD', 'ETHUSD', 'XAUUSD', 'XAGUSD', 'SP500m', 'UK100']


def get_quotes(time_frame, year = 2005, month = 1, day = 1, asset = "EURUSD") :
    if not mt5.initialize() :
        print("initialize() failed, error code = ", mt5.last_error())
        quit()

    timezone = pytz.timezone("America/New_York")
    time_from = datetime.datetime(year, month, day, tzinfo = timezone)
    time_to = datetime.datetime.now(timezone) + datetime.timedelta(days=1)
    rates = mt5.copy_rates_range(asset, time_frame, time_from, time_to)
    rates_frame = pd.DataFrame(rates)
    return rates_frame


def mass_import(asset, time_frame) :
    if time_frame == 'H1' :
        data = get_quotes(frame_H1, 2023, 10, 1, asset = assets[asset])
        data = data.iloc[:, 1:5].values
        data = data.round(decimals = 5)

    if time_frame == 'D1' :
        data = get_quotes(frame_D1, 2023, 7, 1, asset = assets[asset])
        data = data.iloc[:, 1:5].values
        data = data.round(decimals = 5)

    if time_frame == 'M1' :
        data = get_quotes(frame_M1, 2023, 10, 10, asset = assets[asset])
        data = data.iloc[:, 1:5].values
        data = data.round(decimals = 5)
    if time_frame == 'M5' :
        data = get_quotes(frame_M5, 2023, 10, 10, asset = assets[asset])
        data = data.iloc[:, 1:5].values
        data = data.round(decimals = 5)


    return data


# import ETHUSD data
#my_data = mass_import(5, 'M1')

#my_data_np = np.array(my_data)

# import GBPUSD data
#my_data = mass_import(2, 'H1')

#my_data_np = np.array(my_data)

# import SP500m data
my_data = mass_import(8, 'H1')


