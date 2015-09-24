import matplotlib.pyplot as plt
import numpy as np

# same as LectureNotes2 except will display the complex part
N = 500 #32 give a different DFT size
k = 3 #5 try a differnt periods

n = np.arange(-N/2,N/2)
s = np.exp (1j * 2 * np.pi * k * n/N)

plt.plot(n,np.imag(s))
plt.axis([-N/2,N/2-1, -1, 1])
plt.xlabel('n');
plt.ylabel('amplitude')

plt.show()