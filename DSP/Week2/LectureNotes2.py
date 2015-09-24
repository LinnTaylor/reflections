import matplotlib.pyplot as plt
import numpy as np

#Sinusoid Complex (the real part)

N = 500 # array length (size of DFT)
k = 3 # frequency

n = np.arange(-N/2,N/2) # time index integer values
s = np.exp (1j * 2 * np.pi * k * n/N) # the complex sine wave

# now display n against the real part
plt.plot(n,np.real(s))
plt.axis([-N/2,N/2-1, -1, 1]) # -1 because 0 is one of samples and 1,1 because normalized amplitudes
plt.xlabel('n');
plt.ylabel('amplitude')

plt.show()