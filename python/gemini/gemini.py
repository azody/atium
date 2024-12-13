# -*- coding: utf-8 -*-
"""
Created on Tue Nov 21 15:43:32 2023

@author: zodyac
"""

import requests, json
import pandas as pd # Data analysis Library

'''
Function to return data from Gemini
Asset
    btcusd
    ethusd
    xrpusd
    etc...
Time Frame
    1m: 1 minute
    5m: 5 minutes
    15m: 15 minutes
    30m: 30 minutes
    1hr: 1 hour
    6hr: 6 hours
    1day: 1 day
'''
def gemini_candles_import(asset, time_frame) :

    base_url = "https://api.gemini.com/v2/candles"
    url = base_url + "/" + asset + "/" + time_frame
    print("URL: " + url)
    response = requests.get(url)
    candle_data = response.json()
    data = pd.DataFrame(candle_data)

    return data
