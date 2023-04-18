import matplotlib.pyplot as plt
import numpy as np
from scipy.stats import geom


def get_times(path):
    # Leer el archivo de tiempos
    with open(path) as file:
        tiempos_str = file.read()

    # Convertir los tiempos a una lista de números
    tiempos = list(map(float, tiempos_str.split('\n')))
    data = np.array(tiempos)

    return data


if __name__ == "__main__":
    x1 = get_times('../resources/times_48,22_1.txt')
    x2 = get_times('../resources/times_56_1.txt')
    x3 = get_times('../resources/times_42_1.txt')

    # Grafica la densidad
    plt.xlabel('Tiempo')
    plt.ylabel('Densidad de probabilidad')
    #plt.title('Evolución de la densidad de prob.')
    plt.xscale("log")
    plt.yscale("log")
    #plt.hist(x1, bins=[0, 0.02, 0.04, 0.06, 0.1, 0.2, 0.5], range=(0, 0.5), edgecolor='black', density=True, color='#1b7a5e')

    # Obtener las coordenadas de las cimas de las barras
    heights1, edges1 = np.histogram(x1, bins=[i * 0.0001 for i in range(5001)], range=(0, 0.5), density=True)
    x_vals1 = edges1[:-1] + np.diff(edges1) / 2
    y_vals1 = heights1

    # Dibujar la curva que conecta las cimas de las barras
    plt.plot(x_vals1, y_vals1, '-', color='b', label="y = 48.22")

    heights2, edges2 = np.histogram(x2, bins=[i * 0.0001 for i in range(5001)], range=(0, 0.5), density=True)
    x_vals2 = edges2[:-1] + np.diff(edges2) / 2
    y_vals2 = heights2

    # Dibujar la curva que conecta las cimas de las barras
    plt.plot(x_vals2, y_vals2, '-', color='g', label="y = 56")

    heights3, edges3 = np.histogram(x3, bins=[i * 0.0001 for i in range(5001)], range=(0, 0.5), density=True)
    x_vals3 = edges3[:-1] + np.diff(edges3) / 2
    y_vals3 = heights3

    # Dibujar la curva que conecta las cimas de las barras

    plt.plot(x_vals3, y_vals3, '-', color='r', label="y = 42")
    plt.legend()


    plt.savefig('../resources/times.png')
