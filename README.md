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

## Estructura del proyecto
```text
ProyectoRedes/
├── src/
│   ├── gui/                # Paquete de Interfaz Gráfica
│   │   ├── appVentana.java      (Ventana Principal)
│   │   ├── cidrPanel.java      (Pestaña CIDR)
│   │   ├── vlsmPanel.java      (Pestaña VLSM - Tabla)
│   │   └── acercaDePanel.java  (Créditos y Enlaces)
│   │
│   └── logica/             # Paquete de Lógica de Negocio
│       ├── IPUtils.java        (Conversiones y Validaciones Regex)
│       ├── Subnet.java         (Objeto de Datos)
│       └── VLSMAlgorito.java  (Motor de Cálculo y Ordenamiento)
├── bin/                    # Archivos compilados (.class)
├── README.md               # Documentación
└── CalculadoraIP.jar       # Ejecutable Final

## Instrucciones de Instalación y Ejecución

### Opción A: Desde Visual Studio Code (Opcion para codigo fuente) 
1.  Clona o descarga este repositorio.
2.  Abre la carpeta `ProyectoRedes` en VS Code.
3.  Navega a la carpeta `src/gui` y abre el archivo `appVentana.java`.
4.  Presiona `F5` o dale clic al botón **Run** (Play) que aparece sobre el método `main`.

### Opción B: Ejecutar desde Terminal (Línea de Comandos)
Si deseas compilar manualmente, abre una terminal en la carpeta raíz del proyecto y ejecuta:

```bash
# 1. Compilar todos los archivos
javac -d bin -sourcepath src src/gui/appVentana.java

# 2. Ejecutar la aplicación
java -cp bin gui.appVentana
```
### Opción C: Ejecutar desde un archivo .jar (En caso de tener el ejecutable) 
Si has creado el ejecutable o tienes el ejecutable. 
1. Haz clic sobre el ejecutable `CalculadoraIP.jar` <br> 
En caso de no funcionar el paso 1
2. Ejecuta el siguiente comando `java -jar CalculadoraIP.jar` <br> 
**El nombre 'CalculadoraIP' puede cambiar si se escoge otro nombre**

## Creacion del ejecutable (.jar) 
Si deseas regenerar el archivo `.jar` desde el código fuente, puedes seguir los siguientes pasos.

**Pasos para compilar y empaquetar manualmente:**

1.  Abre una terminal en la carpeta raíz del proyecto (`ProyectoRedes`).
2.  Crea una carpeta para los archivos compilados (si no existe):
    ```bash
    mkdir bin
    ```
3.  Compila el código fuente indicando la ruta de origen y destino:
    ```bash
    javac -d bin -sourcepath src src/gui/appVentana.java
    ```
4.  Empaqueta el archivo `.jar` definiendo el punto de entrada (`gui.appVentana`):
    ```bash
    jar cfe CalculadoraIP.jar gui.appVentana -C bin .
    ```
    *Explicación flags:* `c` (crear), `f` (archivo salida), `e` (entry-point/clase main), `-C` (cambiar directorio a `bin` para incluir los .class).

5.  Una vez generado, aparecerá el archivo `CalculadoraIP.jar` en la raíz. Ejecútalo con:
    ```bash
    java -jar CalculadoraIP.jar
    ```
6. Puedes cambiar el nombre "CalculadoraIP" al que tu gustes

## Explicacion de la logica del programa 
El sistema está construido bajo el patrón de diseño **MVC (Modelo-Vista-Controlador)** desacoplado, separando la lógica matemática (`src/logica`) de la interfaz de usuario (`src/gui`).

A continuación se detalla el funcionamiento de los algoritmos principales:

### 1. Motor Matemático 
Para realizar cálculos precisos y rápidos, el sistema no trabaja con cadenas de texto (Strings) durante el procesamiento. En su lugar, todas las IPs se convierten a **Enteros Largos de 32 bits (long)**. Esto debido a que el si usaramos **Enteros (int)** solo podriamos hacer uso de **31 bits** debido a que 1 bit de los 32 posibles por int seria usado para el signo del numero.

* **Conversión:** Se utiliza desplazamiento de bits  para empaquetar los 4 octetos en un solo número.
    * *Fórmula:* `(Octeto1 << 24) + (Octeto2 << 16) + (Octeto3 << 8) + Octeto4`.

### 2. Lógica del Módulo CIDR
El cálculo de una subred estática se basa en operaciones lógicas binarias estándar:

1.  **Máscara Binaria:** Se genera desplazando `1s` a la izquierda según el prefijo.
    * `mascara = 0xFFFFFFFFL << (32 - prefijo)`
2.  **Dirección de Red :** Se aplica una operacion logica **AND** entre la IP y la Máscara. Esto "apaga" los bits de host y conserva solo la red.
    * `direccionRed = ipLong & mascara`
3.  **Dirección de Broadcast:** Se aplica una operacion logica **OR** entre la dirección de red y la *inversa* de la máscara (Wildcard) `~mascara`.
    * `direccionBroadcast = direccionRed | (~mascara & 0xFFFFFFFFL)`

### 3. Algoritmo VLSM (Variable Length Subnet Mask)
Su objetivo es asignar bloques de direcciones de manera eficiente para minimizar el desperdicio. El algoritmo implementado sigue una estrategia **"Best-Fit Descendente"**.

#### Paso A: Ordenamiento 
Para evitar la fragmentación de la red , el sistema **ordena obligatoriamente** los requerimientos de mayor a menor cantidad de hosts.
* *Código:* `    Collections.sort(subnets, new Comparator<Subnet>())`
#### Paso B: Cálculo de Bloques
Para cada subred solicitada:
1.  **Cálculo de Bits de Host (n):** Se utiliza logaritmo base 2 para encontrar la potencia de 2 más cercana que pueda alojar. Como usamos `JAVA` y este trabaja con logaritmos naturales. Por lo tanto, usamos la formula de cambio de base de un logaritmo.<br> `hosts_requeridos + 2` (red + broadcast).
    * `Math.ceil(Math.log(hostNecesitados) / Math.log(2)) `
2.  **Asignación de Prefijo:** `32 - n`.
3.  **Asignación de Rango:**
    * La subred actual inicia donde terminó la anterior (`redActual`).
    * Se suma el tamaño del bloque (`2^n`) para determinar la IP de inicio de la *siguiente* subred.
4.  **Detección de Colisiones:** Antes de asignar, se verifica matemáticamente si `redActual + tamañoBloque` excede el límite de la Red Base. Si es así, se lanza una excepción controlada.

### 4. Manejo de Excepciones y Casos Borde
El sistema incluye protecciones para casos especiales:
* **/31 y /32:** Se ajusta el cálculo de "Hosts Disponibles" a 0 para evitar resultados negativos en la interfaz (ya que matemáticamente `2^1 - 2 = 0`).
* **Input Sanitization:** Uso de Expresiones Regulares (Regex) para validar que el usuario no ingrese octetos mayores a 255 o caracteres no numéricos.

##  Guía de Uso del Sistema (Manual de Usuario)

La aplicación cuenta con una interfaz gráfica intuitiva dividida en pestañas (`Tabs`). A continuación se detalla el flujo de trabajo para cada módulo.

### 1. Módulo: Calculadora CIDR 
Utilice este módulo cuando desee analizar una sola red y conocer sus límites y rango útil.

1.  **Navegación:** Seleccione la pestaña **"Calculadora CIDR"**.
2.  **Ingreso de Datos:**
    * **Dirección IP:** Ingrese una IP válida 
    * **Prefijo:** Ingrese el número de bits de red (Ej: `24` para una máscara 255.255.255.0).
3.  **Ejecución:** Haga clic en el botón **"Calcular"**.
4.  **Lectura de Resultados:** El área de texto inferior mostrará:
    * Dirección de Red y Broadcast.
    * Máscara en formato decimal.
    * Rango de IPs utilizables para hosts.
    * Total de direcciones disponibles.

### 2. Módulo: Calculadora VLSM 
Utilice este módulo para dividir una red principal en múltiples subredes de diferentes tamaños, optimizando el espacio.

#### Paso A: Definir la Red Base
En la sección superior **"1. Configuración Red Base"**:
* Ingrese la IP principal que desea dividir .
* Ingrese el prefijo del bloque total.

#### Paso B: Agregar Departamentos
En la sección media izquierda **"2. Requerimientos de Subredes"**:
1.  **Nombre Depto:** Escriba un identificador (Ej: "Ventas", "Sistemas").
2.  **Hosts Necesarios:** Escriba la cantidad de máquinas requeridas (Ej: `50`, `1000`).
3.  **Acción:** Presione el botón **"Agregar (+)"**.
    * *Nota:* Verá que el departamento se añade a la tabla pequeña de la izquierda.
    * *Repita este paso* para todos los departamentos que necesite. No importa el orden en que los ingrese; el sistema los ordenará automáticamente.

#### Paso C: Calcular y Analizar
Una vez cargados todos los departamentos:
1.  Presione el botón verde **"CALCULAR VLSM"**.
2.  Observe la **Tabla de Resultados** en la parte inferior.

**Interpretación de la Tabla:**
* **Ordenamiento:** Notará que las subredes aparecen ordenadas de mayor a menor demanda (Best-Fit).
* **Desperdicio:** La columna final muestra cuántas IPs sobran en cada bloque asignado.
* **Rangos:** Los rangos son contiguos; donde termina una subred, inmediatamente comienza la siguiente.

#### Funciones Adicionales
* **Limpiar Todo:** Borra la lista de departamentos y los resultados para iniciar un nuevo cálculo desde cero.

### 3. Pestaña "Acerca De"
Información institucional y créditos del equipo de desarrollo.
* Incluye botones interactivos para visitar el **Repositorio en GitHub** o abrir la documentación local (**README**) directamente desde la aplicación.

### Manejo de Errores Comunes
El sistema cuenta con validaciones para guiar al usuario:
* **Espacio Insuficiente:** Si la suma de los hosts requeridos supera la capacidad de la Red Base, el sistema mostrará una alerta indicando que no hay espacio disponible.
* **Formatos Inválidos:** Si ingresa texto en campos numéricos o IPs mal formadas (ej: `999.999.999`), el sistema le solicitará corregir la entrada.

### Alcance y Limitaciones 

El presente software fue desarrollado bajo las siguientes condiciones y restricciones:

* **Protocolo:** Diseñado exclusivamente para **IPv4**. No soporta direcciones IPv6.
* **Enfoque Classless:** Implementa **CIDR** (Classless Inter-Domain Routing), ignorando las restricciones de clases antiguas (A, B, C) para mayor flexibilidad.
* **Validación de Entradas:** El sistema incluye sanitización de datos (Regex) para rechazar octetos > 255 o caracteres alfabéticos.
* **Casos Borde (/31 y /32):** Para máscaras de 31 y 32 bits, el sistema reporta **0 hosts disponibles**, apegándose a la fórmula estándar `(2^n) - 2` utilizada en el curso.

