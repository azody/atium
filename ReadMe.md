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

# Feature Ideas
1. Make a common function for bear and bull candles
2. I hate the indexing to field symbol
3. Add linter
4. Make signal function bear and bull expressions functions so that they can be easily pieced together to create new ideas
5. Pattern and filters easy to plug and play
    - ex. Double Trouble Pattern with an optional Filter of RSI

# Signal Inventory
1. Trend Following
    - Classic Patterns
        - Marubozu
        - Three Candles
        - Tasuki
        - Three Methods
        - Hikkake
    - Modern
        - Quintuplets
        - Double Trouble
        - Bottle
        - Slingshot
        - H
2. Contrarian
    - Classic Patterns
        - Doji
        - Harami
        - On Neck
        - Tweezers
        - Stick Sandwhich
        - Hammer
        - Star
        - Piercing
        - Engulfing
        - Abandoned Baby
        - Spinning Top
        - Inside Up/Down
        - Tower
    - Modern
        - Doppelganger
        - Blockade
        - Euphoria
        - Barrier
        - Mirror
        - Shrinking

# Indicator Inventory
1. Moving Average
2. Smoothed Moving Average
3. Relative Strength Indicator

# Volume and Volatility Strategies
1. Trend Following
    - Double Trouble with RSI
    - Three Candles with Moving Average
    - Bottle Pattern with Stochastic Oscillator
    - Marubozu with Ks Volatility Bands
    - H Patter with Trend Intensity Index

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

## Charting Analysis
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

## Indicator Analysis
Types
    1. Trend-following Indicators: Used to detet and trade a trending market where the movie is expected to continue
        - Ex. Moving Average
    2. Contraian Indicators: Used to fade the move and best used in sideways markets
        - Ex. Relative Strength Index

Moving Average
- Most famous trend-following indicator
- Easy to use and understand
- Can use to find support, resistance, stop and target levels
- Moving Average is commonly the rolling mean of the close price
- Rule of Thumb
    - Whenever the market is above moving averagfe, bullish momentum is in progress and you are better off looking for long oopurtunities
    - Whenever the market is below moving average, bearish momentum
    - When the market crosses, the market may be entering a new regime (trend)
- Can combine moving averages
    - Whenever a short term moving average crosses over a long term moving average, a golden cross is formed
    - Opposite is a death cross

Relative Strength Indicator
- Most popular and most versatile bounded indicator
- Steps to calculate 14-period RSI
    1. Calculate the chaing in the closing prices from the previous ones
    2. Seperate the positive net change from the negative net changes
    3. Calculate a smoothed moving average on the positive net changes and on the absolute values of the negative net changes
    4. Divide the smoothed positive changes by the smoothed absolute negative changes (This is the RS)
    5. Apply this normalization formula for every time step to get the RSI
- RSI = 100 - (100/ (1 + RS))
- Smoothed Average is a special type of moving average developed by the creator of the RSI
    - Smoother an dmore stable than the simple moving average
- How to use
    - Generally use a 14 RSI by default though it can vary
    - Whenever the RSI is < 30, market is considered to be oversol and a correction to the upside can occur
    - Whenever the RSI is > 70, market is considered to be over bough and a correction to the downside might occur
    - Whenever the RSI surpasses or breaks the 50 level, a new trend might be emerging


## Pattern Recognition
Types
    1. Classic Price Patterns: Technical reversal patterns, extremely subjective and difficult to backtest without subjective conditions
    2. Timing Patternt: Combinatin of pricing and timing, less well known but can be powerful and predictive
    3. Candlestick Patters: OHLC data

Classic Patterns
- Continuation price patterns: Configurations that confirm the aggregate ongoing move
    - i.e. rectangles and triangles
- Reversal Price Patterns: Configurations that fade the aggregate ongoing move
    - i.e. Head and shoulders and double bottoms
    - Double Bottom
        - Chart Features
            - The neckline: line linking the lowest low between two peaks and the geinning/end of the pattern
                - Determines the pull back level
            - The pull-backL new level where sellers will open short positions
                - Theoretical optimal selling point
            - Potential: miodpoint between the top of the pattern and the neckline
        - Rationale: The market has failed to push prices above first peak and is howing weakness

Pitfalls of Technical Analysis
- Wanting to Get Rich Quickly: Psychological pitfall
    - Lack of discipline and poor management
    - Lack of faith may result in constant changing of strategy
- Forcing patterns: Confirmation bias
    - Prevents traders from seeing signals that contradict thesis
- Hindsight Bias: What looks easy to spot in the past is harder in real time
    - Backtesting almost always outperform forward testing
    - Backtest using an unbiased algorithm to counteract
- Assuming past events have the same future outcome
    - History doesnt exactly repeat but it does generally rhyme
- Making Things more complicated than they need to be
    - Everything should be made as simple as possible but no simpler
    - Avoid over fitting

Technical Analysis Best Practices
- Use Different Time Frames
    - Longer time frame is always more important than the shorter time frame
    - Principle of the alignment of planets: the more elements that confirm each other the better
        - Analyze the market in at least three different time frames
- Use more than one strategy or indicator
    - Markets tend to trend sideways
    - Do not use contrarian strateding in strongly trending markets
    - Do not use trend following strategies in ranging markets
    - Difficult to predict
- Don't Underestimate Default Parameters
    - They have the most visibility to other traders
    - Want your configuration change to be seen by other traders

## Exit Techniques
Symmeterical Exit Technique
- Based on the size of the candle before entering
- Distance of the high and the low of the key candlestick and projects it from one of the extremities (high or low)
- Bullish Exit Price = Key Candle High - Key Candle Low + High Price
- Bearish Exit Price = Key Candle High - (Key Candle High - Key Candle Low)

Fixed Holding Period
- Must exit afet a prespecified number of time periods

Variable Holding Pattern
- Must exit a pattern after encountering another pattern

Hybrid Exit Technique
- The symmeterical projection is calculated and is given a certain wiehg tbetween 1% and 99%
- The variable holding period is monitored for every candlestick and is also given a certain weight
    - Weight it the remainder of the first given weight
    - If a counter pattern is discovered, sell the weights percentage of the position
- Fixed holding period is used to fram the whole trade in a maximum duration scenario

## Pattern Invalidation
Fixed Stop-Losses
    - A fixed level that liquidates the position if the market reaches the limit
ATR-based Stop-Losses
    - A level based on the values of the ATR, recommended to weight risk according to volatility





