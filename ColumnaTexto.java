package clases.modelo;

/**
 * Representa una columna de texto con reglas de normalización e imputación por defecto.
 * @author Luis Teobaldo Castro Baus
 * @author Alex Burbano
 * @version POO 2026-2027 C1
 * @since 08/07/2026
 */
public class ColumnaTexto extends TipoDato {

    public ColumnaTexto(String nombreColumna) {
        super(nombreColumna);
    }

    @Override
    public Object limpiarValor(String valorCrudo, RegistroReporte reporte) {
        if (valorCrudo == null || valorCrudo.trim().isEmpty()) {
            reporte.incrementarVacios(nombreColumna);
            return "DESCONOCIDO";
        }
        return valorCrudo.trim();
    }
}
