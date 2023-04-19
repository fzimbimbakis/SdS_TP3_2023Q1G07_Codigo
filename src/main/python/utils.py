import numpy as np


def get_times(path):
    # Leer el archivo de tiempos
    with open(path) as file:
        tiempos_str = file.read()

    # Convertir los tiempos a una lista de números
    tiempos = list(map(float, tiempos_str.split('\n')))
    data = np.array(tiempos)

    return data


def remove_outliers_top(times):
    top_limit, bottom_limit = get_outliers_limit(times)
    return times[times <= top_limit]


def remove_outliers(times):
    top_limit, bottom_limit = get_outliers_limit(times)
    times = times[times <= top_limit]
    return times[times >= bottom_limit]


def get_outliers_limit(times):
    times = np.sort(times)
    q1 = np.percentile(times, 25)
    q3 = np.percentile(times, 75)
    iqr = q3 - q1
    top_limit = q3 + iqr * 3
    bottom_limit = q3 - iqr * 3

    return top_limit, bottom_limit
