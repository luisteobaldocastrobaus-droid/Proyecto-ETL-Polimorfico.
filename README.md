# Motor ETL Polimórfico en Java

Este proyecto implementa un pipeline robusto de **Extracción, Transformación y Carga (ETL)** desarrollado bajo el paradigma de la Programación Orientada a Objetos (POO). El sistema está diseñado para automatizar la limpieza, normalización y análisis estadístico de fuentes de datos tabulares, garantizando una alta tolerancia a fallos mediante un diseño arquitectónico limpio y extensible.

## 🚀 Características Principales

* **Arquitectura Polimórfica Pura:** Procesamiento dinámico de colecciones de datos heterogéneas utilizando una estructura basada en la clase abstracta `TipoDato` y especializaciones por columna (`ColumnaTexto`, `ColumnaNumerica`, `ColumnaLogica`), eliminando el uso de condicionales rígidos (`if-else` masivos).
* **Mitigación de Anomalías:** Manejo de excepciones y aislamiento de celdas vacías o errores de casteo mediante la imputación estratégica de valores **Centinela (-999999.0)**.
* **Análisis Estadístico Descriptivo:** Computación en tiempo de ejecución de métricas clave (Media, Mediana, Varianza Muestral y Desviación Estándar) purificadas de ruidos o datos corruptos.
* **Persistencia Multiformato Dual:** Carga final del conjunto de datos limpio de manera simultánea en formatos planos operativos (**CSV**) y estructuras jerárquicas jerarquizadas (**JSON**).

## 📂 Estructura del Proyecto

* `src/main/Principal.java`: Orquestador principal y punto de entrada del pipeline ETL.
* `src/clases/modelo/`: Contenedores del dominio, esquema dinámico y clases polimórficas de tipos de datos.
* `src/clases/analisis/`: Lógica matemática encargada de los cálculos estadísticos descriptivos.
* `src/clases/io/`: Interfaces y adaptadores de lectura/escritura (DataSources y Exporters).
* `src/clases/excepciones/`: Excepciones personalizadas para el control y validación de esquemas tabulares.
* `datos/entrada/`: Directorio de origen para los archivos crudos (`datos_crudos.csv`).
* `datos/salida/`: Almacenamiento automatizado de los productos procesados limpios.

## 🛠️ Tecnologías y Librerías Utilizadas

* **Lenguaje:** Java (POO)
* **Parser CSV:** OpenCSV 5.9 (con dependencia nativa Apache Commons-Lang3)
* **Serializador JSON:** Jackson Databind 2.15

---
Autores: **Luis Teobaldo Castro Baus & Alex Burbano** Curso: Programación Orientada a Objetos (POO) 2026-2027 C1  
Asignación Académica - Universidad de Guayaquil
