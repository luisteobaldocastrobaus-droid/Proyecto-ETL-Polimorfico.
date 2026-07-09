package clases.modelo;

/**
 * Clase abstracta que define el comportamiento polimórfico de una columna.
 * @author Luis Teobaldo Castro Baus
 * @author Alex Burbano
 * @version POO 2026-2027 C1
 * @since 08/07/2026
 */
public abstract class TipoDato {
    protected String nombreColumna;

    public TipoDato(String nombreColumna) {
        this.nombreColumna = nombreColumna;
    }

    public String getNombreColumna() {
        return nombreColumna;
    }

    /**
     * Limpia un valor crudo según las reglas específicas de la columna.
     * * @param valorCrudo Texto leído de la celda
     * @param reporte Objeto para registrar incidencias de calidad
     * @return El objeto procesado limpio
     */
    public abstract Object limpiarValor(String valorCrudo, RegistroReporte reporte);
}
