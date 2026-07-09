package clases.modelo;

import java.util.HashMap;
import java.util.Map;

/**
 * Registra las métricas de calidad de los datos procesados.
 * @author Luis Teobaldo Castro Baus
 * @author Alex Burbano
 * @version POO 2026-2027 C1
 * @since 08/07/2026
 */
public class RegistroReporte {
    private int totalFilasLeidas = 0;
    private int totalFilasProcesadas = 0;

    // Mapas para contar incidencias por nombre de columna
    private final Map<String, Integer> vaciosPorColumna = new HashMap<>();
    private final Map<String, Integer> imputadosPorColumna = new HashMap<>();
    private final Map<String, Integer> erroresPorColumna = new HashMap<>();

    public void incrementarLeidas() { totalFilasLeidas++; }
    public void incrementarProcesadas() { totalFilasProcesadas++; }

    public void incrementarVacios(String columna) {
        vaciosPorColumna.put(columna, vaciosPorColumna.getOrDefault(columna, 0) + 1);
    }

    public void incrementarImputados(String columna) {
        imputadosPorColumna.put(columna, imputadosPorColumna.getOrDefault(columna, 0) + 1);
    }

    public void incrementarErrores(String columna) {
        erroresPorColumna.put(columna, erroresPorColumna.getOrDefault(columna, 0) + 1);
    }

    // Getters necesarios para armar el reporte final por consola
    public int getTotalFilasLeidas() { return totalFilasLeidas; }
    public int getTotalFilasProcesadas() { return totalFilasProcesadas; }
    public Map<String, Integer> getVaciosPorColumna() { return vaciosPorColumna; }
    public Map<String, Integer> getImputadosPorColumna() { return imputadosPorColumna; }
    public Map<String, Integer> getErroresPorColumna() { return erroresPorColumna; }
    /**
     * Imprime de forma ordenada el reporte de calidad solicitado en la Etapa 4.
     */
    public void mostrarReporte() {
        System.out.println("   > Total de Filas Leídas en origen: " + totalFilasLeidas);
        System.out.println("   > Total de Filas Procesadas con éxito: " + totalFilasProcesadas);
        System.out.println("   > Celdas vacías detectadas por columna: " + vaciosPorColumna);
        System.out.println("   > Valores imputados/reemplazados por columna: " + imputadosPorColumna);
        System.out.println("   > Errores de casteo/formato detectados por columna: " + erroresPorColumna);
    }
}