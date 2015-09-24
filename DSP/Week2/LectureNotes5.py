import numpy as np
import matplotlib.pyplot as plt

# see LectureNotes4

N = 64
k0 = 7
x = np.cos(2*np.pi*k0/N*np.arange(N))

X = np.array([])

for k in range(N):
    s = np.exp(1j * 2 * np.pi * k/N * np.arange(N))
    X = np.append(X, sum(x*np.conjugate(s)))
    
plt.plot(np.arange(N),abs(X))
plt.axis([0, N-1, 0, N]) # because symetrical start at 0

plt.show();