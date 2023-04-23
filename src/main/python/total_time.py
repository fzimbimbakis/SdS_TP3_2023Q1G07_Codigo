import matplotlib.pyplot as plt
import numpy as np

from utils import get_times, remove_outliers, get_standard_error


def total_time_graphs(directory):
    x = [42, 43.56, 45.11, 46.67, 48.22, 49.78, 51.33, 52.89, 54.44, 56]

    y_without_outliers = []
    yerr_without_outliers = []

    for y_white in x:
        times = get_times("../resources/total_time_" + str(y_white) + "_1.txt")
        times_reduced = remove_outliers(times, "Total time graph. Y =" + str(y_white) + " . Outliers removed: ")
        y_without_outliers.append(np.mean(times_reduced))
        yerr_without_outliers.append(get_standard_error(times_reduced))

    plt.semilogy()
    plt.xlabel('Coordenada y de la blanca (cm)')
    plt.ylabel('Promedio de tiempo total (s)')
    plt.errorbar(x, y_without_outliers, yerr=yerr_without_outliers, marker='o')
    plt.savefig(directory + 'total_time_reduced.png')
    plt.clf()


if __name__ == "__main__":
    total_time_graphs('../resources/graphs/')
