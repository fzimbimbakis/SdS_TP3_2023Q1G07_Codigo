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
    x1 = get_times('../resources/times_48.22_1.txt')

    # Grafica la densidad
    plt.xlabel('Tiempo')
    plt.ylabel('Densidad de probabilidad')
    plt.title('Evolución de la densidad de prob.')
    plt.hist(x1, bins=[0, 0.02, 0.04, 0.06, 0.1, 0.2, 0.5], range=(0, 0.5), edgecolor='black', density=True, color='#1b7a5e')

    # Obtener las coordenadas de las cimas de las barras
    heights, edges = np.histogram(x1, bins=[i * 0.01 for i in range(52)], range=(0, 0.5), density=True)
    x_vals = edges[:-1] + np.diff(edges) / 2
    y_vals = heights

    # Dibujar la curva que conecta las cimas de las barras
    plt.plot(x_vals, y_vals, '-', color='#610707')

    plt.show()
