import A2Part1
import A2Part2
import A2Part3
import A2Part4
import A2Part5
import numpy

print ("Number 1: ", A2Part1.genSine(1.0, 10.0, 1.0, 50.0, 0.1))
print ("Number 2: ", A2Part2.genComplexSine(1,5))
print ("Number 3: ", A2Part3.DFT(numpy.array([1,2,3,4])))
print ("Number 4: ", A2Part4.IDFT(numpy.array([1,1,1,1])))
print ("Number 4a: ", A2Part4.IDFT(A2Part3.DFT(numpy.array([1,2,3,4]))))
print ("Number 4b: ", A2Part4.IDFT(A2Part3.DFT(A2Part4.IDFT(A2Part3.DFT(numpy.array([1,2,3,4]))))))
print ("Number 5: ", A2Part5.genMagSpec(numpy.array([1,2,3,4])))