#!/usr/bin/python

import matplotlib.pyplot as plt
from prep_terrain_data import makeTerrainData
from class_vis import prettyPicture

features_train, labels_train, features_test, labels_test = makeTerrainData()


### the training data (features_train, labels_train) have both "fast" and "slow" points mixed
### in together--separate them so we can give them different colors in the scatterplot,
### and visually identify them
grade_fast = [features_train[ii][0] for ii in range(0, len(features_train)) if labels_train[ii]==0]
bumpy_fast = [features_train[ii][1] for ii in range(0, len(features_train)) if labels_train[ii]==0]
grade_slow = [features_train[ii][0] for ii in range(0, len(features_train)) if labels_train[ii]==1]
bumpy_slow = [features_train[ii][1] for ii in range(0, len(features_train)) if labels_train[ii]==1]


#### initial visualization
##plt.xlim(0.0, 1.0)
##plt.ylim(0.0, 1.0)
##plt.scatter(bumpy_fast, grade_fast, color = "b", label="fast")
##plt.scatter(grade_slow, bumpy_slow, color = "r", label="slow")
##plt.legend()
##plt.xlabel("bumpiness")
##plt.ylabel("grade")
##plt.show()
#################################################################################


### your code here!  name your classifier object clf if you want the 
### visualization code (prettyPicture) to show you the decision boundary
from sklearn.ensemble import (RandomForestClassifier, AdaBoostClassifier)
from sklearn.tree import DecisionTreeClassifier
from sklearn.neighbors import KNeighborsClassifier
from sklearn.metrics import accuracy_score

clf = KNeighborsClassifier(n_neighbors=8, weights='distance')
clf.fit(features_train, labels_train)
acc = accuracy_score(clf.predict(features_test), labels_test)### you fill this in!
prettyPicture(clf, features_test, labels_test, "KNearestClassifier.png")
print "Accuracy Of KNearestClassifier() is ", acc

clf = RandomForestClassifier(n_estimators=25,criterion='gini',max_depth=3,min_samples_split=20,max_leaf_nodes=25)
clf.fit(features_train, labels_train)
acc = accuracy_score(clf.predict(features_test), labels_test)### you fill this in!
prettyPicture(clf, features_test, labels_test, "RandomForestClassifier.png")
print "Accuracy Of RandomForestClassifier() is ", acc

clf = AdaBoostClassifier(base_estimator=DecisionTreeClassifier(min_samples_split=100),n_estimators=50,learning_rate=1.0)
clf.fit(features_train, labels_train)
acc = accuracy_score(clf.predict(features_test), labels_test)### you fill this in!
prettyPicture(clf, features_test, labels_test, "AdaBoostClassifier.png")
print "Accuracy Of AdaBoostClassifier() is ", acc
