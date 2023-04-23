import math

import numpy as np


def get_times(path):
    # Leer el archivo de tiempos
    with open(path) as file:
        tiempos_str = file.read()

    # Convertir los tiempos a una lista de números
    tiempos = list(map(float, tiempos_str.split('\n')))
    data = np.array(tiempos)

    return data

def remove_outliers(times, reference):
    array = []
    for time in times:
        if time <= (1000000000000000000000000000000):
            array.append(time)
        else:
            print(reference + str(time))
    return array

def get_standard_error(times):
    return np.std(times) / math.sqrt(len(times))
