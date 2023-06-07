# -*- coding: utf-8 -*-
"""
Created on Tue Jun  6 16:30:04 2023

@author: zodyac
"""


# Adds a column to an arrau
# Used for adding proxies for indicators
# data is the data set to be appended
# times is how many additional columns forever
def add_column(data, times) :
    #For each loop a new array is created containing zero values
    for i in range(1, times + 1) :
        # np.zeroes is a prebuild function that zeroes out the column
        new = np.zeroes((len(data), 1), dtype = float)
        # adds the new zero array to the current data set
        # axis is the binary choice where 0=rows and 1=columns
        data = np.append(data, new, axis = 1)
        
    return data


# Deletes a column from a dataset
# Used when there are intermediary steps in an indicator calculation that is no longer needed
# index is starting point of deletion, python indexing starts at 0
# times is how many columns to delete starting at the index
def delete_column(data, index, times) :
    for i in range(1, times + 1) :
        data = np.delete(data, index, axis = 1)
    
    return data

# Adds row to an array
# Commonly used due to lag or manual addition of values
def add_row(data, times) :
    for i in range(1, times+1):
        # np.shpe() built in function gives out the number of rows, columns in the array
        columns = np.shape(data)[1]
        # np.zeroes is a prebuild function that zeroes out the row
        new = np.zeroes((1, columns), dtype = float)
        # adds the new zero array to the current data set
        # axis is the binary choice where 0=rows and 1=column
        data = np.append(data, new, axis = 0)
    return data

# Deletes a row from an Array
# Some indicators require a minimum amount of data that will be needed
# This can be used to remove dummy values that wre previously inserted
def delete_row(data, number) :
    data = data[number:,]
    return data

# Array Cheat Sheet
# Refering to entire array
## my_data
# Refering to first 100 rows inside the array
## my_data[:100, ]
# Refering to first 100 rows of the first 2 columns
## my_data[:100, 0:2]
# Refering to all the rows of the 7th column
## my_data[:, 6]
# Refering to last 500 rows inside the array
## my_data[-500:, ]
# Refering to first row of the first column
## my_data[0, 0]
# Refering to  the last row of the last column
## my_data[-1, -1]
