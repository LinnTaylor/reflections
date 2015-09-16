#!/usr/bin/python

""" 
    this is the code to accompany the Lesson 3 (decision tree) mini-project

    use an DT to identify emails from the Enron corpus by their authors
    
    Sara has label 0
    Chris has label 1

"""
    
import sys
from time import time
sys.path.append("../tools/")
from email_preprocess import preprocess


### features_train and features_test are the features for the training
### and testing datasets, respectively
### labels_train and labels_test are the corresponding item labels
features_train, features_test, labels_train, labels_test = preprocess()




#########################################################
### your code goes here ###
from sklearn import tree
clf = tree.DecisionTreeClassifier(min_samples_split=40)
clf = clf.fit(features_train, labels_train)
from sklearn.metrics import accuracy_score


acc = accuracy_score(clf.predict(features_test), labels_test)### you fill this in!
print "Accuracy is ", acc
### numpy table where number of rows is number of data points and number of columns is number of features
###print "Number of data points is ", len(features_train)
print "Number of features is ", len(features_train[0])
#########################################################


