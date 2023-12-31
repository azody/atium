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
            plt.vlines(x = i, ymin = sample[i ,0], ymax = sample[i, 3], color = 'green', linewidth = 3)

        if sample[i, 3] < sample[i, 0] :
            plt.vlines(x = i, ymin = sample[i ,3], ymax = sample[i, 0], color = 'red', linewidth = 3)

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

            ax.annotate(' ', xy = (x, y), arrowprops = dict(width = 5, headlength = 7, headwidth = 7, facecolor = 'green'))

        elif sample[i, sell_column] == -1:
            x = i
            y = sample[i, position]
            ax.annotate(' ', xy = (x, y),  arrowprops = dict(width = 5, headlength = -7, headwidth = -7, facecolor = 'red'))

def indicator_plot(data, second_panel, window = 250):

    fig, ax = plt.subplots(2, figsize = (10, 5))

    sample = data[-window:, ]

    for i in range(len(sample)):

        ax[0].vlines(x = i, ymin = sample[i, 2], ymax = sample[i, 1], color = 'black', linewidth = 1)

        if sample[i, 3] > sample[i, 0]:

            ax[0].vlines(x = i, ymin = sample[i, 0], ymax = sample[i, 3], color = 'black', linewidth = 1.5)

        if sample[i, 3] < sample[i, 0]:

            ax[0].vlines(x = i, ymin = sample[i, 3], ymax = sample[i, 0], color = 'black', linewidth = 1.5)

        if sample[i, 3] == sample[i, 0]:

            ax[0].vlines(x = i, ymin = sample[i, 3], ymax = sample[i, 0], color = 'black', linewidth = 1.5)

    ax[0].grid()

    ax[1].plot(sample[:, second_panel], color = 'royalblue', linewidth = 1)
    ax[1].grid()

def signal_chart_indicator_plot(data,
                                    position,
                                    second_panel,
                                    buy_column,
                                    sell_column,
                                    barriers = False,
                                    window = 250):

    fig, ax = plt.subplots(2, figsize = (10, 5))

    sample = data[-window:, ]

    for i in range(len(sample)):

        ax[0].vlines(x = i, ymin = sample[i, 2], ymax = sample[i, 1], color = 'black', linewidth = 1)

        if sample[i, 3] > sample[i, 0]:

            ax[0].vlines(x = i, ymin = sample[i, 0], ymax = sample[i, 3], color = 'black', linewidth = 1)

        if sample[i, 3] < sample[i, 0]:

            ax[0].vlines(x = i, ymin = sample[i, 3], ymax = sample[i, 0], color = 'black', linewidth = 1)

        if sample[i, 3] == sample[i, 0]:

            ax[0].vlines(x = i, ymin = sample[i, 3], ymax = sample[i, 0] + 0.00003, color = 'black', linewidth = 1.00)

    ax[0].grid()

    for i in range(len(sample)):

        if sample[i, buy_column] == 1:

            x = i
            y = sample[i, position]

            ax[0].annotate(' ', xy = (x, y),
                        arrowprops = dict(width = 9, headlength = 11, headwidth = 11, facecolor = 'green', color = 'green'))

        elif sample[i, sell_column] == -1:

            x = i
            y = sample[i, position]

            ax[0].annotate(' ', xy = (x, y),
                        arrowprops = dict(width = 9, headlength = -11, headwidth = -11, facecolor = 'red', color = 'red'))

    ax[1].plot(sample[:, second_panel], color = 'royalblue', linewidth = 1)
    ax[1].grid()

#    if barriers == True:
#
#        plt.axhline(y = lower_barrier, color = 'black', linestyle = 'dashed', linewidth = 1)
#        plt.axhline(y = upper_barrier, color = 'black', linestyle = 'dashed', linewidth = 1)

def signal_chart_indicator_plot_candles(data,
                                    position,
                                    second_panel,
                                    buy_column,
                                    sell_column,
                                    barriers = False,
                                    window = 250):

    fig, ax = plt.subplots(2, figsize = (10, 5))

    sample = data[-window:, ]

    for i in range(len(sample)):

        ax[0].vlines(x = i, ymin = sample[i, 2], ymax = sample[i, 1], color = 'black', linewidth = 1)

        if sample[i, 3] > sample[i, 0]:

            ax[0].vlines(x = i, ymin = sample[i, 0], ymax = sample[i, 3], color = 'mediumseagreen', linewidth = 3)

        if sample[i, 3] < sample[i, 0]:

            ax[0].vlines(x = i, ymin = sample[i, 3], ymax = sample[i, 0], color = 'maroon', linewidth = 3)

        if sample[i, 3] == sample[i, 0]:

            ax[0].vlines(x = i, ymin = sample[i, 3], ymax = sample[i, 0] + 0.00001, color = 'black', linewidth = 1.75)

    ax[0].grid()

    for i in range(len(sample)):

        if sample[i, buy_column] == 1:

            x = i
            y = sample[i, position]

            ax[0].annotate(' ', xy = (x, y),
                        arrowprops = dict(width = 9, headlength = 11, headwidth = 11, facecolor = 'green', color = 'green'))

        elif sample[i, sell_column] == -1:

            x = i
            y = sample[i, position]

            ax[0].annotate(' ', xy = (x, y),
                        arrowprops = dict(width = 9, headlength = -11, headwidth = -11, facecolor = 'red', color = 'red'))

    ax[1].plot(sample[:, second_panel], color = 'royalblue', linewidth = 1)
    ax[1].grid()

#    if barriers == True:
#
#        plt.axhline(y = lower_barrier, color = 'black', linestyle = 'dashed', linewidth = 1)
#        plt.axhline(y = upper_barrier, color = 'black', linestyle = 'dashed', linewidth = 1)