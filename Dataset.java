package clases.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Contenedor principal del conjunto de datos que gestiona el ciclo de transformación ETL.
 * @author Luis Teobaldo Castro Baus
 * @author Alex Burbano
 * @version POO 2026-2027 C1
 * @since 08/07/2026
 */
public class Dataset {
    private String[] encabezados;
    private List<String[]> filasCrudas;
    private List<Object[]> filasProcesadas;
    private TipoDato[] esquemaColumnas;
    private RegistroReporte reporteCalidad;

    public Dataset(List<String[]> datosCrudos) {
        if (datosCrudos != null && !datosCrudos.isEmpty()) {
            this.encabezados = datosCrudos.get(0);
            this.filasCrudas = new ArrayList<>(datosCrudos.subList(1, datosCrudos.size()));
        } else {
            this.encabezados = new String[0];
            this.filasCrudas = new ArrayList<>();
        }
        this.filasProcesadas = new ArrayList<>();
        this.reporteCalidad = new RegistroReporte();
    }

    /**
     * Define el esquema de tipos correspondiente a cada columna del dataset.
     */
    public void definirEsquema(TipoDato[] esquema) {
        this.esquemaColumnas = esquema;
    }

    /**
     * Ejecuta el proceso de transformación (ETL) aplicando limpieza polimórfica celda por celda.
     */
    public void transformar() {
        if (esquemaColumnas == null) {
            System.out.println("Error: No se ha definido el esquema de columnas para la transformación.");
            return;
        }

        filasProcesadas.clear();

        for (String[] fila : filasCrudas) {
            reporteCalidad.incrementarLeidas();

            // Si la fila viene incompleta o corrupta con respecto al encabezado, se registra y se continúa
            if (fila.length != encabezados.length) {
                for (String encabezado : encabezados) {
                    reporteCalidad.incrementarErrores(encabezado);
                }
                continue;
            }

            Object[] filaLimpia = new Object[fila.length];
            for (int i = 0; i < fila.length; i++) {
                // Invocación polimórfica automática según la subclase de TipoDato instanciada
                filaLimpia[i] = esquemaColumnas[i].limpiarValor(fila[i], reporteCalidad);
            }

            filasProcesadas.add(filaLimpia);
            reporteCalidad.incrementarProcesadas();
        }
    }

    // Métodos para exportar transformando los objetos de vuelta a cadenas de texto
    public List<String[]> getDatosProcesadosParaExportar() {
        List<String[]> datosString = new ArrayList<>();
        datosString.add(encabezados); // Añade la cabecera
        for (Object[] fila : filasProcesadas) {
            String[] filaString = new String[fila.length];
            for (int i = 0; i < fila.length; i++) {
                filaString[i] = (fila[i] == null) ? "" : fila[i].toString();
            }
            datosString.add(filaString);
        }
        return datosString;
    }

    public String[] getEncabezados() { return encabezados; }
    public List<Object[]> getFilasProcesadas() { return filasProcesadas; }
    public RegistroReporte getReporteCalidad() { return reporteCalidad; }
}
