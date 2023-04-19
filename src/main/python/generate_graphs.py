import density
import mean_frequency
import mean_time
import total_time

if __name__ == "__main__":
    directory = '../resources/graphs/'
    total_time.total_time_graphs(directory)
    mean_time.mean_time_graphs(directory)
    density.density_graph(directory)
    mean_frequency.mean_frequency_graphs(directory)
