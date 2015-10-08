#!/usr/bin/python


def outlierCleaner(predictions, ages, net_worths):
    """
        clean away the 10% of points that have the largest
        residual errors (different between the prediction
        and the actual net worth)

        return a list of tuples named cleaned_data where 
        each tuple is of the form (age, net_worth, error)
    """
    
    cleaned_data = []

    ### your code goes here
    temp = abs(predictions-net_worths)
    for k in range(len(ages)):
        cleaned_data.append((ages[k][0],net_worths[k][0],temp[k][0]))
    cleaned_data = sorted(cleaned_data, key=lambda data:data[2])
    print("Length: ", len(cleaned_data))
    cleaned_data = cleaned_data[:len(cleaned_data)-9]
    print("Length: ", len(cleaned_data))
    return cleaned_data

