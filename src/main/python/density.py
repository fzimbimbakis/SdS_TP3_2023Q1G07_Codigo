import matplotlib.pyplot as plt
import numpy as np

from utils import get_times


def density_graph(directory):
    x1 = get_times('../resources/times_48.22_1.txt')
    x2 = get_times('../resources/times_56_1.txt')
    x3 = get_times('../resources/times_42_1.txt')

    # Grafica la densidad
    plt.xlabel('Tiempo (s)')
    plt.ylabel('Densidad de probabilidad')
    plt.xscale("log")
    plt.yscale("log")

    max_value = 0.5
    step = 0.0001
    range_value = int(max_value / step)

    # Obtener las coordenadas de las cimas de las barras
    heights1, edges1 = np.histogram(x1, bins=[i * 0.0001 for i in range(range_value)], range=(0, 0.5), density=True)
    x_vals1 = edges1[:-1] + np.diff(edges1) / 2
    y_vals1 = heights1

    # Dibujar la curva que conecta las cimas de las barras
    plt.plot(x_vals1, y_vals1, '-', color='b', label="y = 48.22")

    heights2, edges2 = np.histogram(x2, bins=[i * 0.0001 for i in range(range_value)], range=(0, 0.5), density=True)
    x_vals2 = edges2[:-1] + np.diff(edges2) / 2
    y_vals2 = heights2

    # Dibujar la curva que conecta las cimas de las barras
    plt.plot(x_vals2, y_vals2, '-', color='g', label="y = 56")

    heights3, edges3 = np.histogram(x3, bins=[i * 0.0001 for i in range(range_value)], range=(0, 0.5), density=True)
    x_vals3 = edges3[:-1] + np.diff(edges3) / 2
    y_vals3 = heights3

    # Dibujar la curva que conecta las cimas de las barras

    plt.plot(x_vals3, y_vals3, '-', color='r', label="y = 42")
    plt.legend()

    plt.savefig(directory + 'times.png')
    plt.clf()


if __name__ == "__main__":
    density_graph('../resources/graphs/')
