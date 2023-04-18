import matplotlib.pyplot as plt
import numpy as np


list1 = [42, 43.56, 45.11, 46.67, 48.22, 49.78, 51.33, 52.89, 54.44, 56]
list2 = ["42", "43,56", "45,11", "46,67", "48,22", "49,78", "51,33", "52,89", "54,44", "56"]

def get_times(path):
    # Leer el archivo de tiempos
    with open(path) as file:
        tiempos_str = file.read()

    # Convertir los tiempos a una lista de números
    tiempos = list(map(float, tiempos_str.split('\n')))
    #tiempos_reduced = []
    #for t in tiempos:
    #    if t <= 100:
    #        tiempos_reduced.append(t)
    #data = np.array(tiempos_reduced)
    data = np.array(tiempos)
    return data


if __name__ == "__main__":
    plt.xlabel('Tiempo')
    plt.ylabel('Tiempo medio entre eventos')
    #plt.yscale("log")

    for i in range(9):
        x = get_times("../resources/times_" + list2[i] + "_1.txt")
        plt.errorbar(list1[i], np.mean(x),yerr=np.std(x), fmt="og")

    plt.savefig('../resources/mean_time.png')