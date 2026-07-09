package clases.modelo;

/**
 * Representa una columna numérica con reglas de imputación centinela.
 * @author Luis Teobaldo Castro Baus
 * @author Alex Burbano
 * @version POO 2026-2027 C1
 * @since 08/07/2026
 */
public class ColumnaNumerica extends TipoDato {
    public static final double CENTINELA = -999999.0;

    public ColumnaNumerica(String nombreColumna) {
        super(nombreColumna);
    }

    @Override
    public Object limpiarValor(String valorCrudo, RegistroReporte reporte) {
        if (valorCrudo == null || valorCrudo.trim().isEmpty()) {
            reporte.incrementarVacios(nombreColumna);
            reporte.incrementarImputados(nombreColumna);
            return CENTINELA;
        }
        try {
            return Double.parseDouble(valorCrudo.trim());
        } catch (NumberFormatException e) {
            reporte.incrementarErrores(nombreColumna);
            reporte.incrementarImputados(nombreColumna);
            return CENTINELA;
        }
    }
}
