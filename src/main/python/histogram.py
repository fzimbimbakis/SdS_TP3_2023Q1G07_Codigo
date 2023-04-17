import numpy as np
import matplotlib.pyplot as plt

####### Creo que no funciona....

# Leer el archivo de tiempos
with open('../resources/times_56_1.txt', 'r') as file:
    tiempos_str = file.read()

with open('../resources/total_time_56_1.txt', 'r') as file:
    total_time_str = file.read()

# Convertir los tiempos a una lista de números
tiempos = list(float(m) for m in tiempos_str.split('\n'))
data = np.array(tiempos)

total_time = float(total_time_str)

# Filtrar los datos que cumplen la condición
data_filtrado = data[data <= 5]

# Obtener el histograma y normalizarlo
frecuencia, bin_edges = np.histogram(data_filtrado, bins=50)
probabilidad = frecuencia / frecuencia.sum()

# Graficar el histograma con bordes negros y barras alineadas en el borde izquierdo
plt.bar(bin_edges[:-1], probabilidad, width=0.1, edgecolor='black', align='edge', color='#1b7a5e')

plt.xlabel('Tiempos')
plt.ylabel('Distribución de probabilidad')
plt.xlim([0, 5])

plt.show()
