import matplotlib.pyplot as plt
import numpy as np

#Sinusoids (Real)


A = .8 # Amplitude
f0 = 1000 # Frequecy in Hertz
phi = np.pi/2 # phase
fs = 44100 # sample rate in Hertz

# fills in an array from a to b step c (a,b,c)
t = np.arange(-0.002, 0.002, 1.0/fs)

# Calulate the real sinusoid
x = A * np.cos (2 * np.pi * f0 * t + phi)

#then plot and show
plt.plot(t, x)

# axis is horizontal start finish and then vertical start and finish
plt.axis([-0.002, 0.002, -0.8, 0.8])
plt.xlabel('time')
plt.ylabel('amplitude')
plt.show()