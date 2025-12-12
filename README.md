# Proyecto calculadora de redes ip CIDR/VLSM
Proyecto desarrollado para la unidad de aprendizaje **Redes de Computadoras** en la **Escuela Superior de Cómputo (ESCOM - IPN)**.
Dentro del Plan de estudios 2020 
## Integrantes del Equipo
* **Arevalo Villanueva Eduardo** - 2023630062
* **Díaz Presas Angel Aarón** - 2024630362
* **Sandoval Espinoza Moises** - 2024630268

**Profesor:** Alcaraz Torres Juan Jesus 

**Grupo:** 5CM1
## Descripción
Esta aplicación es una herramienta gráfica desarrollada en **Java** que permite realizar cálculos de direccionamiento IP mediante dos módulos principales:
1.  **Calculadora CIDR:** Realiza el análisis de una red individual dado una IP y su prefijo. Calcula dirección de red, broadcast, rango de hosts y capacidad.
2.  **Calculadora VLSM :** Implementa el algoritmo de subdivisión optimizada. Permite ingresar una red base y múltiples departamentos con diferentes requisitos de hosts, ordenándolos y asignándolos sin desperdicio innecesario.

## Requisitos del Sistema
Para ejecutar este proyecto necesitas contar con las siguientes aplicaciones :
* **Java Development Kit (JDK):** Versión 11 o superior (Recomendado JDK 17 o 21).
* **Sistema Operativo:** Windows, Linux o macOS.
* **IDE Sugerido:** Visual Studio Code (con Extension Pack for Java).

## Instrucciones de Instalación y Ejecución

### Opción A: Desde Visual Studio Code (Opcion para codigo fuente) 
1.  Clona o descarga este repositorio.
2.  Abre la carpeta `ProyectoRedes` en VS Code.
3.  Navega a la carpeta `src/gui` y abre el archivo `AppWindow.java`.
4.  Presiona `F5` o dale clic al botón **Run** (Play) que aparece sobre el método `main`.

### Opción B: Ejecutar desde Terminal (Línea de Comandos)
Si deseas compilar manualmente, abre una terminal en la carpeta raíz del proyecto y ejecuta:

```bash
# 1. Compilar todos los archivos
javac -d bin -sourcepath src src/gui/AppWindow.java

# 2. Ejecutar la aplicación
java -cp bin gui.AppWindow
