# -*- coding: utf-8 -*-
"""
Created on Thu Jun  8 15:47:06 2023

@author: zodyac
"""

# Pip - the 4th decimal
# Pipette 5th decimal

# Optimal rounding for currency pairs is generally 4 decimal places
def rounding(data, how_far) :
    data = data.round(decimals = how_far)
    return data