# Environment Setup
## Required Installation
1. Anaconda
2. Spyder
3. MetaTrader 5

## Account Creation
Open MetaTrader5 and create a free demo accont

## Download MetaTrader 5 Dependencies
' pip install Metatrader5

' pip install pandas

' pip install matplotlib


# Core Concepts
## 4 key algorithm types
1. Import Algorithm
- Deals with importing and preparing historical OHLC data to be analyzed or backtested
2. Signal ALgorithm
- Responsible for generating buy and sell orders
3. Charting Algorithm
- Chart the signals on the price chart
4. Performance Algorithm
- Calculate and analyze the resuls aquired from the signal algorithm thats a good step

# Algorithmic Mindset and Functions

## Benefits of Algorithmic Approach
1. Speed
- Algorithms run faster than humans with regard to execution and data analysis
2. Discipline
- Algorithms follow a distincxt set of rules and do not have feelings or emotions
3. Percentage of error
- Algorithms are generally error free and do not rely on human attention


# Technical Analysis
Types
    1. Charting Analysis: Apply subjective visualization to charts.
        - Ex, Support and resistance lines, retracements
    2. Indicator Analysis: Mathematical formulas to create objective indicators that are either trend following or contratian
        - Ex. Moving averages and Relative Strength Index (RSI)
    3. Pattern Recognition: Monitor recurring configuration and act on them
        - Ex. Candlestick patterns

3 Principles
    1. History Repeats Itself
    2. The Market Discounts Eveything: All fundamental, technical, and qunatitative information is included in the current price
    3. The Market Moves in Waves as opposed to straight lines

Market Efficiency states that information is already embeddewd in the current price
- Implies price and value are the same thing

Charting Analysis
- Bullish candlesticks have an open lower than the close
- Bearish candlesticks have a close lower than the open

Support and Resistance
- Support Level: a level where the market should bounce
- Resistance Level: a level where the market should retreat
- Asset Direction cam be an uptrend, downtrend, or sideways
    - Ascending and Descending trends are not scientific
    - However trends can become a self fulfillilng prophecy
- Finding Support and Resistance
    - Fibonacci Retracements: Use fibonacci ratios to give out reactionary levels.
        Generally calculated on up or down trends to give where the market will reverse if it touches one of these levels
        Very Subjective but gives many interesting price levels
    - Pivot Points: Use simple mathematical formulas to find levels
    - Moving averages: Dynamic in nature nad follow the price