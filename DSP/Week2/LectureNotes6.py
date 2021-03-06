import numpy as np
import matplotlib.pyplot as plt

#Real Signal
N = 64
#k0 = 7
k0 = 7.5 ## to see something different
x = np.cos(2*np.pi*k0/N*np.arange(N))

X = np.array([])
nv = np.arange(-N/2,N/2) # time indexes
kv = np.arange(-N/2,N/2) #frequency indexes

for k in kv:
    s = np.exp(1j * 2 * np.pi * k/N * nv)
    X = np.append(X, sum(x*np.conjugate(s)))
    
plt.plot(kv,abs(X))
plt.axis([-N/2, N/2-1, 0, N])

plt.show();