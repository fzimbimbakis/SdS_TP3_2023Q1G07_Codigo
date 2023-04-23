import numpy as np
import matplotlib.pyplot as plt

# Leer los tiempos de los archivos
with open('../../resources/animation_total_time_float_1.txt') as file:
    float1 = float(file.read())
with open('../../resources/animation_total_time_float_2.txt') as file:
    float2 = float(file.read())
with open('../../resources/animation_total_time_double_1.txt') as file:
    double1 = float(file.read())
with open('../../resources/animation_total_time_double_2.txt') as file:
    double2 = float(file.read())

# Definir las etiquetas y los tiempos
labels = ['float1', 'float2', 'double1', 'double2']
times = [float1, float2, double1, double2]

# Definir los colores para las barras
float_colors = 'blue'
double_colors = 'red'

# Crear el gráfico de barras
fig, ax = plt.subplots()
ax.bar(labels[:2], times[:2], color=float_colors)
ax.bar(labels[2:], times[2:], color=double_colors)

# Definir la leyenda
ax.legend(['Punto flotante', 'Precisión doble'])

# Añadir los títulos de los ejes y el título del gráfico
ax.set_ylabel('Tiempo total (s)')
ax.set_xlabel('ID Simulación')

# Mostrar el gráfico
plt.show()
