# -*- coding: utf-8 -*-
"""
Created on Thu Jun  8 16:29:20 2023

@author: zodyac
"""
import matplotlib.pyplot as plt

# Window is the lookback period
def ohlc_plot_bars(data, window) :
    
    sample = data[-window:, ]
    
    for i in range(len(sample)) :
        plt.vlines(x = i, ymin = sample[i,2], ymax = sample[i, 1], color = 'black', linewidth = 1)
        
        if sample[i, 3] > sample[i, 0] :
            plt.vlines(x = i, ymin = sample[i ,0], ymax = sample[i, 3], color = 'black', linewidth = 1)
        
        if sample[i, 3] < sample[i, 0] :
            plt.vlines(x = i, ymin = sample[i ,3], ymax = sample[i, 0], color = 'black', linewidth = 1)
            
        if sample[i, 3] == sample[i, 0] :
            plt.vlines(x = i, ymin = sample[i ,3], ymax = sample[i, 0] + 0.0003, color = 'black', linewidth = 1)

    plt.grid()
    
    
def signal_chart(data, position, buy_column, sell_column, window = 500) :
        
    sample = data[-window:, ]
    
    fig, ax = plt.subplots(figsize = (10, 5))
    
    ohlc_plot_bars(data, window)
    
    for i in range(len(sample)):
        if sample[i, buy_column] == 1:
            x = i
            y = sample[i, position]
            
            ax.annotate(' ', xy = (x, y), arrowprops = dict(width = 9, headlength = 11, headwidth = 11, facecolor = 'green', color = 'green'))
            
        elif sample[i, sell_column] == -1:
            x = i
            y = sample[i, position]
            ax.annotate(' ', xy = (x, y),  arrowprops = dict(width = 9, headlength = -11, headwidth = -11, facecolor = 'red', color = 'red'))