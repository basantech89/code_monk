import pylab
f = open("C:/users/luv/downloads/julyTemps.txt", "r+")
highTemp , lowTemp, diffenTemps = [], [], []
for line in f:
    line = line.rstrip()
    fields = line.split(" ")
    if fields[0] == "Boston" or fields[0] == "Day" or len(fields) != 3:
        continue
    highTemp.append(fields[1])
    lowTemp.append(fields[2])
    diffenTemps.append(int(highTemp[-1]) - int(lowTemp[-1]))   
print diffenTemps        
pylab.figure(1)
pylab.plot(range(1, 32), diffenTemps, "r", linewidth = 15)
pylab.title("Day by Day Ranges in Temperature in Boston in July 2012", fontsize = 15)
pylab.xlabel("Days", fontsize = 15)
pylab.ylabel("Temperature Ranges", fontsize = 15)
pylab.show()
#if you get tired of chaging these things in code, it's possible to change default values which
#stored in a .rc file, rc is derived from .rc file extension used for runtime configuration
#files in unix operating systems.