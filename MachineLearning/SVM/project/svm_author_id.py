#!/usr/bin/python

""" 
    this is the code to accompany the Lesson 2 (SVM) mini-project

    use an SVM to identify emails from the Enron corpus by their authors
    
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
from sklearn.svm import SVC
### initial line using a linear kernel: clf = SVC(kernel="linear")
### changed to test rbf

clf = SVC(kernel="rbf",C=10000.0)

### these two lines added to train on a small subset of the data
###   it should be faster but less accurate
###features_train = features_train[:len(features_train)/100] 
###labels_train = labels_train[:len(labels_train)/100] 

#### now your job is to fit the classifier
#### using the training features/labels, and to
#### make a set of predictions on the test data
clf.fit(features_train, labels_train) 


#### store your predictions in a list named pred
pred = clf.predict(features_test)

### count number that belong to chris
count = 0
for s in pred:
  if s == 1:
    count = count + 1
print "Chris wrote ", count

### determine who did email 0 = sara 1 = chris
###print "#10 is ", pred[10]
###print "#26 is ", pred[26]
###print "#50 is ", pred[50]


from sklearn.metrics import accuracy_score
acc = accuracy_score(pred, labels_test)
print "Accuracy is ",acc
#########################################################


