import numpy as np
from sklearn import linear_model
def studentReg(ages_train, net_worths_train):
    ### import the sklearn regression module, create, and train your regression
    ### name your regression reg
    
    ### your code goes here!
    reg = linear_model.LinearRegression()

    # Train the model using the training sets
    reg.fit(ages_train, net_worths_train)

    # The coefficients
    print('Coefficients: \n', reg.coef_)
    # The mean square error
    print("Residual sum of squares: %.2f", % np.mean((reg.predict(ages_train) - net_worths_train) ** 2))
    # Explained variance score: 1 is perfect prediction
    print('Variance score: %.2f' % reg.score(ages_train, net_worths_train))

    
    
    return reg