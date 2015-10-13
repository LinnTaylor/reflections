import A3Part1
import A3Part2
import A3Part3
import A3Part4
import A3Part5
from fractions import gcd
import numpy as np
import loadTestCases as ltc

def compareAnswersDB(t, a):
    for i in range(t.size):
        if (t[i] > -120 and a[i] > -120 and abs(t[i]-a[i]) > 1.0e-06):
            print("Error", i,abs(t[i]-a[i]))
            return False
    return True

def compareAnswers(t, a):
    for i in range(t.size):
        if (abs(t[i]-a[i])>1.0e-06):
            print("Error", i,abs(t[i]-a[i]))
            return False
    return True

#tc = ltc.load(1, 1) 
#tc = ltc.load(1, 2) 
#print("TestCase:",tc)
#mx = A3Part1.minimizeEnergySpreadDFT(tc['input']['x'], tc['input']['fs'], tc['input']['f1'], tc['input']['f2'])
#print(mx)
#compareAnswersDB(tc['output'],mx)

tc = ltc.load(2, 1) 
tc = ltc.load(2, 2) 
print("TestCase:",tc)
mx = A3Part2.optimalZeropad(tc['input']['x'], tc['input']['fs'], tc['input']['f'])
print(mx)
print("Compare",compareAnswersDB(tc['output'],mx))

#tc = ltc.load(3, 1) 
#tc = ltc.load(3, 2) 
#print("TestCase:",tc)
#(isRealEven, dftbuffer, X) = A3Part3.testRealEven(tc['input']['x'])
#print("isRealEven:",isRealEven)
#print("dftBuffer:",dftbuffer)
#print("X",X)

#tc = ltc.load(4, 1) 
#tc = ltc.load(4, 2) 
#print("TestCase:",tc)
#(y, yfilt) = A3Part4.suppressFreqDFTmodel(tc['input']['x'], tc['input']['fs'], tc['input']['N'])
#(ny, nyfilt) = tc['output']
#print("Compare y", compareAnswers(ny, y))
#print("Compare yfilt", compareAnswers(nyfilt, yfilt))

#tc = ltc.load(5, 1) 
#print("TestCase:",tc)
#(mX1_80, mX2_80, mX3_80) = A3Part5.zpFFTsizeExpt(tc['input']['x'], tc['input']['fs'])
#print("Expected",tc['output'])
#print("MX1",mX1_80)
#print("MX2",mX2_80)
#print("MX3",mX3_80)