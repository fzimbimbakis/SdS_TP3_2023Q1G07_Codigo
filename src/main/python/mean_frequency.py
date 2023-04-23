import matplotlib.pyplot as plt
import numpy as np

from utils import get_times, remove_outliers, get_standard_error

x = [42, 43.56, 45.11, 46.67, 48.22, 49.78, 51.33, 52.89, 54.44, 56]
list2 = ["42", "43,56", "45,11", "46,67", "48,22", "49,78", "51,33", "52,89", "54,44", "56"]


def mean_frequency_graphs(directory):

    y_without_outliers = []
    yerr_without_outliers = []

    for i in range(10):
        frequencies = get_times("../resources/frequency_" + str(x[i]) + "_1.txt")

        frequencies_reduced = remove_outliers(frequencies, "Mean frequency graph. Y =" + str(x[i]) + " . Outliers removed: ")
        y_without_outliers.append(np.mean(frequencies_reduced))
        yerr_without_outliers.append(get_standard_error(frequencies_reduced))

    plt.xlabel('Coordenada y de la blanca (cm)')
    plt.ylabel('Frecuencia media de eventos')
    plt.errorbar(x, y_without_outliers, yerr=yerr_without_outliers, marker='o')
    plt.savefig(directory + 'mean_frequency_reduced.png')
    plt.clf()


if __name__ == "__main__":
    mean_frequency_graphs('../resources/graphs/')
