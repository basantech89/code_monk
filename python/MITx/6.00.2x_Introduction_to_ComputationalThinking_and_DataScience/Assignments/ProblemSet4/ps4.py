# 6.00.2x Problem Set 4

import numpy
import random
import pylab
from ps3b import *
import matplotlib.pyplot as plt

#
# PROBLEM 1
#
def simulationDelayedTreatment(numTrials):
    """
    Runs simulations and make histograms for Problem 1.

    Runs numTrials simulations to show the relationship between delayed
    treatment and patient outcome using a histogram.

    Histograms of final total virus populations are displayed for delays of 300,
    150, 75, 0 timesteps (followed by an additional 150 timesteps of
    simulation).

    numTrials: number of simulation runs to execute (an integer)
    """
    v1, v2, v3, v4 = [], [], [], []
    for trial in range(numTrials):
        v1.append(simulationDelayedTreatmentWithDrug(300))
        v2.append(simulationDelayedTreatmentWithDrug(150))
        v3.append(simulationDelayedTreatmentWithDrug(75))
        v4.append(simulationDelayedTreatmentWithDrug(0))
    plt.subplot(221)
    plt.title('delay = 300')
    plt.hist(v1, bins = sum(v1)/len(v1))
    plt.subplot(222)
    plt.title('delay = 150')
    plt.hist(v2, bins = sum(v2)/len(v2))
    plt.subplot(223)
    plt.title('delay = 75')
    plt.hist(v3, bins = sum(v3)/len(v3))
    plt.subplot(224)
    plt.title('delay = 0')
    plt.hist(v4, bins = 50)
    plt.show()



def simulationDelayedTreatmentWithDrug(numSteps, numViruses=100, maxPop=1000, maxBirthProb=0.1,
                                        clearProb=0.05, resistances={'guttagonol':False},
                                        mutProb=0.005):
    """
    Helper function to run simulations for Problem 1.

    For each of numTrials trials, instantiates a patient, runs a simulation for
    numsteps timesteps, adds guttagonol, and runs the simulation for an additional
    150 timesteps.

    numViruses: number of ResistantVirus to create for patient (an integer)
    maxPop: maximum virus population for patient (an integer)
    maxBirthProb: Maximum reproduction probability (a float between 0-1)
    clearProb: maximum clearance probability (a float between 0-1)
    resistances: a dictionary of drugs that each ResistantVirus is resistant to
                 (e.g., {'guttagonol': False})
    mutProb: mutation probability for each ResistantVirus particle
             (a float between 0-1).

    """

    virusPop = [0] * (numSteps + 150)
    resistVirusPop = [0] * (numSteps + 150)
    #list comprehension
    viruses = [ResistantVirus(maxBirthProb, clearProb, resistances, mutProb) for i
    in range(numViruses)]
    patient = TreatedPatient(viruses, maxPop)
    #running the simulation for numsteps + 150 timesteps
    for i in range(numSteps + 150):
        if i == numSteps:
            patient.addPrescription('guttagonol')
        virusPop[i] += patient.update()
        resistVirusPop[i] += patient.getResistPop(['guttagonol'])
    return virusPop[-1]


#simulationDelayedTreatment(100)
#
# PROBLEM 2
#
def simulationTwoDrugsDelayedTreatment(numTrials):
    """
    Runs simulations and make histograms for problem 2.

    Runs numTrials simulations to show the relationship between administration
    of multiple drugs and patient outcome.

    Histograms of final total virus populations are displayed for lag times of
    300, 150, 75, 0 timesteps between adding drugs (followed by an additional
    150 timesteps of simulation).

    numTrials: number of simulation runs to execute (an integer)
    """
    v1, v2, v3, v4 = [], [], [], []
    for trial in range(numTrials):
        v1.append(simulationTwoDelayedTreatmentWithDrug(300))
        v2.append(simulationTwoDelayedTreatmentWithDrug(150))
        v3.append(simulationTwoDelayedTreatmentWithDrug(75))
        v4.append(simulationTwoDelayedTreatmentWithDrug(0))
    plt.subplot(221)
    plt.title('delay = 300')
    plt.hist(v1, bins = sum(v1)/len(v1))
    plt.subplot(222)
    plt.title('delay = 150')
    plt.hist(v2, bins = sum(v2)/len(v2))
    plt.subplot(223)
    plt.title('delay = 75')
    plt.hist(v3, bins = sum(v3)/len(v3))
    plt.subplot(224)
    plt.title('delay = 0')
    plt.hist(v4, bins = 50)
    plt.show()


def simulationTwoDelayedTreatmentWithDrug(numSteps, numViruses=100, maxPop=1000, maxBirthProb=0.1,
                                        clearProb=0.05, resistances={'guttagonol':False,
                                        'grimpex':False}, mutProb=0.005):
    """
    Helper function to run simulations for Problem 2.

    For each of numTrials trials, instantiates a patient, runs a simulation for
    150 timesteps, adds guttagonol, and runs the simulation for numsteps
    timesteps, adds grimpex and run the simulation for an additional
    150 timesteps.

    numViruses: number of ResistantVirus to create for patient (an integer)
    maxPop: maximum virus population for patient (an integer)
    maxBirthProb: Maximum reproduction probability (a float between 0-1)
    clearProb: maximum clearance probability (a float between 0-1)
    resistances: a dictionary of drugs that each ResistantVirus is resistant to
                 (e.g., {'guttagonol': False})
    mutProb: mutation probability for each ResistantVirus particle
             (a float between 0-1).

    """

    virusPop = [0] * (numSteps + 300)
    resistVirusPop = [0] * (numSteps + 300)
    #list comprehension
    viruses = [ResistantVirus(maxBirthProb, clearProb, resistances, mutProb) for i
    in range(numViruses)]
    patient = TreatedPatient(viruses, maxPop)
    #running the simulation for numsteps + 150 timesteps
    for i in range(numSteps + 300):
        if i == 150:
            patient.addPrescription('guttagonol')
        if i == numSteps + 150:
            patient.addPrescription('grimpex')
        virusPop[i] += patient.update()
        resistVirusPop[i] += patient.getResistPop(['guttagonol'])
    return virusPop[-1]


#simulationTwoDrugsDelayedTreatment(100)
