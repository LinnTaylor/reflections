import A3Part1
from fractions import gcd
import numpy as np

def getSignal(fs, f1, f2):
    t = 1 # s (econds)
    phi = 0 # r/s (econds)
    
    return np.cos(2*np.pi*f1*np.arange(0.0, t, 1.0/fs)+phi) + np.cos(2*np.pi*f2*np.arange(0.0, t, 1.0/fs)+phi)

def announceNonZero(x):
    for i in range(x.size):
        if (x[i] > 1):
            print("Bin ",i,"\n")
            
fs = 10000 # Hz
f1 = 80 # Hz
f2 = 200 # Hz

mx = A3Part1.minimizeEnergySpreadDFT(getSignal(fs,f1,f2), fs, f1, f2)
print("Part 1 Test a ",mx)
announceNonZero(mx)

fs = 48000 # Hz
f1 = 300 # Hz
f2 = 800 # Hz
mx = A3Part1.minimizeEnergySpreadDFT(getSignal(fs,f1,f2), fs, f1, f2)
print("Part 1 Test b ",mx)
announceNonZero(mx)
