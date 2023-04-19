import matplotlib.pyplot as plt
import numpy as np

from utils import get_times, remove_outliers_top

x = [42, 43.56, 45.11, 46.67, 48.22, 49.78, 51.33, 52.89, 54.44, 56]
list2 = ["42", "43,56", "45,11", "46,67", "48,22", "49,78", "51,33", "52,89", "54,44", "56"]


def mean_time_graphs(directory):
    y = []
    yerr = []

    y_without_outliers = []
    yerr_without_outliers = []

    for i in range(len(x)):
        times = get_times("../resources/times_" + str(x[i]) + "_1.txt")

        y.append(np.mean(times))
        yerr.append(np.std(times))

        times_reduced = remove_outliers_top(times)
        y_without_outliers.append(np.mean(times_reduced))
        yerr_without_outliers.append(np.std(times_reduced))

    plt.xlabel('Coordenada y de la blanca (cm)')
    plt.ylabel('Tiempo medio entre eventos (s)')
    plt.errorbar(x, y, yerr=yerr, marker='o')
    plt.savefig(directory + 'mean_time.png')
    plt.clf()

    plt.xlabel('Coordenada y de la blanca (cm)')
    plt.ylabel('Tiempo medio entre eventos (s)')
    plt.errorbar(x, y_without_outliers, yerr=yerr_without_outliers, marker='o')
    plt.savefig(directory + 'mean_time_reduced.png')
    plt.clf()


if __name__ == "__main__":
    mean_time_graphs('../resources/graphs/')
