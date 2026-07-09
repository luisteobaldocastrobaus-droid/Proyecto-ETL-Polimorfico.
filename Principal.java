package main;

import clases.analisis.CalculadoraEstadistica;
import clases.io.CsvDataSource;
import clases.io.CsvExporter;
import clases.io.DataSource;
import clases.io.Exporter;
import clases.io.JsonExporter;
import clases.modelo.ColumnaLogica;
import clases.modelo.ColumnaNumerica;
import clases.modelo.ColumnaTexto;
import clases.modelo.Dataset;
import clases.modelo.TipoDato;
import java.io.File;
import java.util.List;

/**
 * Clase de entrada principal que ejecuta y coordina el pipeline ETL completo.
 * @author Luis Teobaldo Castro Baus
 * @author Alex Burbano
 * @version POO 2026-2027 C1
 * @since 08/07/2026
 */
public class Principal {
    public static void main(String[] args) {
        // 1. Configuración de rutas de archivos internos del proyecto
        String rutaEntrada = "datos/entrada/datos_crudos.csv";
        String rutaSalidaCsv = "datos/salida/datos_limpios.csv";
        String rutaSalidaJson = "datos/salida/datos_limpios.json";

        // Asegurar la existencia física de los directorios para evitar fallos de escritura
        new File("datos/entrada").mkdirs();
        new File("datos/salida").mkdirs();

        System.out.println("====== INICIANDO PIPELINE ETL POLIMÓRFICO ======");

        try {
            // Verificar preexistencia del archivo de prueba
            File archivoEntrada = new File(rutaEntrada);
            if (!archivoEntrada.exists()) {
                System.out.println("[WARN] No se encontró un archivo...");
                crearCsvPrueba(rutaEntrada);
            }

            // 2. ETAPA DE EXTRACCIÓN (Lectura)
            System.out.println("\n[ETAPA 1] Extrayendo registros desde origen CSV...");
            DataSource lector = new CsvDataSource();
            List<String[]> datosCrudos = lector.cargarDatos(rutaEntrada);

            // 3. ENLAZAR CON EL CONTENEDOR DATASET
            Dataset dataset = new Dataset(datosCrudos);
            System.out.println("-> Encabezados detectados: " + String.join(" | ", dataset.getEncabezados()));

            // 4. CONFIGURAR EL ESQUEMA DE LAS COLUMNAS (Pasar las instancias de subclases)
            // Mapeamos las 6 columnas del archivo generado para evitar desbordamientos
            TipoDato[] esquema = new TipoDato[]{
                    new ColumnaTexto(dataset.getEncabezados()[0]),
                    new ColumnaNumerica(dataset.getEncabezados()[1]),
                    new ColumnaLogica(dataset.getEncabezados()[2]),
                    new ColumnaTexto(dataset.getEncabezados()[3]),
                    new ColumnaTexto(dataset.getEncabezados()[4]),
                    new ColumnaTexto(dataset.getEncabezados()[5])
            };
            dataset.definirEsquema(esquema);

            // 5. ETAPA DE TRANSFORMACIÓN (Limpieza Polimórfica)
            System.out.println("\n[ETAPA 2] Ejecutando limpieza y normalización de datos...");
            dataset.transformar();

            // 6. ETAPA DE ANÁLISIS ESTADÍSTICO (Cálculos de la Etapa 3)
            System.out.println("\n[ETAPA 3] Procesando métricas descriptivas sobre columnas numéricas...");
            // Procesamos la columna de índice 1
            String nombreColumnaNum = dataset.getEncabezados()[1];
            double media = CalculadoraEstadistica.calcularMedia(dataset.getFilasProcesadas(), 1);
            double mediana = CalculadoraEstadistica.calcularMediana(dataset.getFilasProcesadas(), 1);
            double varianza = CalculadoraEstadistica.calcularVarianza(dataset.getFilasProcesadas(), 1);
            double desvEstandar = CalculadoraEstadistica.calcularDesviacionEstandar(dataset.getFilasProcesadas(), 1);

            System.out.printf("   Métricas calculadas para [%s]:\n", nombreColumnaNum);
            System.out.printf("   > Media: %.2f\n", media);
            System.out.printf("   > Mediana: %.2f\n", mediana);
            System.out.printf("   > Varianza Muestral: %.2f\n", varianza);
            System.out.printf("   > Desviación Estándar: %.2f\n", desvEstandar);

            // 7. IMPRESIÓN DEL REPORTE DE CALIDAD (Requerimiento de la Etapa 4)
            System.out.println("\n[ETAPA 4] Resumen de Calidad del Procesamiento:");
            dataset.getReporteCalidad().mostrarReporte();

            // 8. ETAPA DE CARGA / EXPORTACIÓN (Dos formatos - Etapa 5)
            System.out.println("\n[ETAPA 5] Exportando conjuntos limpios consolidados...");

            Exporter exportadorCsv = new CsvExporter();
            exportadorCsv.exportar(rutaSalidaCsv, dataset.getDatosProcesadosParaExportar());
            System.out.println("   -> Generado con éxito archivo CSV en: " + rutaSalidaCsv);

            Exporter exportadorJson = new JsonExporter();
            exportadorJson.exportar(rutaSalidaJson, dataset.getDatosProcesadosParaExportar());
            System.out.println("   -> Generado con éxito archivo JSON estructurado en: " + rutaSalidaJson);

            System.out.println("\n====== PIPELINE FINALIZADO CON ÉXITO [PROCESO COMPLETADO] ======");

        } catch (Exception e) {
            System.err.println("\n[ERROR CRÍTICO EN EL PIPELINE]: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Genera un archivo CSV sintético inicial que cumple con el requisito mínimo de 6 columnas
     * para verificar la robustez de nuestras reglas de imputación y reporte.
     */
    private static void crearCsvPrueba(String ruta) throws java.io.IOException {
        java.util.List<String[]> lineasPrueba = java.util.Arrays.asList(
                new String[]{"ID", "Precio", "Activo", "Columna4", "Columna5", "Columna6"},
                new String[]{"P001", "120.50", "true", "Dato", "Dato", "Dato"},
                new String[]{"P002", "   ", "FALSE", "Dato", "Dato", "Dato"},
                new String[]{"P003", "ERROR_TEXTO", "yes", "Dato", "Dato", "Dato"},
                new String[]{"   ", "95.00", "TRUE", "Dato", "Dato", "Dato"},
                new String[]{"P005", "210.00", "no", "Dato", "Dato", "Dato"}
        );
        Exporter exp = new CsvExporter();
        exp.exportar(ruta, lineasPrueba);
    }
}