# -*- coding: utf-8 -*-
"""
Created on Sat Jun 10 21:04:06 2023

@author: zodyac


Hit Ratio = ( Number of Profitable Trades ) / (Number of Total Trades)
- Calculate the ratio on the total number of realized trades excluding the pending trades

Rate of Return = ( New Balance / Initial Balance ) - 1
Gross Rate of Return - return that does not consider fees
Net rate of return - actual return after fees
Not neccesarily great for backtests as it is a function of position size


Profit Factor = Gross Total Profit / Gross Total Loss
How much you are winning for every 1 currency unit of loss


Risk-Reward Ratio = ( Entry Price - Target Price ) / ( Entry Price - Stop Price )
Target - the level at which you close out profitably
Stop - the level at whic hyou close out at a loss to acoid a worse outcome
Generally want to target a risk reward ratio close to 2.00

Break Even Hit Ration = 1 / ( 1 + Risk Reward Ratio)
Minimum hit ratio to break even excluding costs and fees

Theoretical Risk-Reward Ratio : Set before the trade and is a forecast
Realized Risk-Reward Ratio : This is the average profit per trade divided by the average loss per trade to gice an idea of how close you are to the theoretical

Number of Trades : General Rule of Thumb is to have at least 30 trades in order to meet the minimum threshold for reliability

"""
import numpy as np # Data analysis library

def performance( data, open_price, buy_column, sell_column, long_result_col, short_result_col, total_result_col) :

    # Variable Holding Period
    for i in range(len(data)) :
        try :
            if data[i, buy_column] == 1 :
                for a in range(i + 1, i + 1000) :
                    if data[a, buy_column] == 1 or data[a, sell_column] == -1 :
                        data[a, long_result_col] = data[a, open_price] - data[i, open_price]
                        break
                    else :
                        continue
            else :
                continue
        except IndexError :
            pass

    for i in range(len(data)) :
        try :
            if data[i, sell_column] == -1 :
                for a in range(i + 1, i + 1000) :
                    if data[a, buy_column] == 1 or data[a, sell_column] == -1 :
                        data[a, short_result_col] = data[i, open_price] - data[a, open_price]
                        break
                    else :
                        continue
            else :
                continue
        except IndexError :
            pass

    # Aggregating the long and short results into one column
    data[:, total_result_col] = data[:, long_result_col] + data[:, short_result_col]

    # Profit Factor
    total_net_profits = data[data[:, total_result_col] > 0, total_result_col]
    total_net_losses = data[data[:, total_result_col] < 0, total_result_col]
    total_net_losses = abs(total_net_losses)
    profit_factor = round(np.sum(total_net_profits) / np.sum(total_net_losses), 2)

    # Hit Ratio
    hit_ratio = len(total_net_profits) / (len(total_net_losses) + len(total_net_profits))
    hit_ratio = hit_ratio * 100

    # Risk-Reward Ratio
    average_gain = total_net_profits.mean()
    average_loss = total_net_losses.mean()
    realized_risk_reward = average_gain / average_loss

    # Number of trades
    trades = len(total_net_losses) + len(total_net_profits)

    print('Hit Ratio               = ', hit_ratio)
    print('Profit Factor           = ', profit_factor)
    print('Realized Risk Reward    = ', round(realized_risk_reward, 3))
    print('Number of Trades        = ', trades)