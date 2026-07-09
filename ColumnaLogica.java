package clases.modelo;

/**
 * Representa una columna booleana que admite estados verdaderos, falsos o nulos (vacíos).
 * @author Luis Teobaldo Castro Baus
 * @author Alex Burbano
 * @version POO 2026-2027 C1
 * @since 08/07/2026
 */
public class ColumnaLogica extends TipoDato {

    public ColumnaLogica(String nombreColumna) {
        super(nombreColumna);
    }

    @Override
    public Object limpiarValor(String valorCrudo, RegistroReporte reporte) {
        if (valorCrudo == null || valorCrudo.trim().isEmpty()) {
            reporte.incrementarVacios(nombreColumna);
            return null;
        }

        String valor = valorCrudo.trim().toLowerCase();
        if (valor.equals("true") || valor.equals("1") || valor.equals("si") || valor.equals("sí")) {
            return true;
        } else if (valor.equals("false") || valor.equals("0") || valor.equals("no")) {
            return false;
        } else {
            reporte.incrementarErrores(nombreColumna);
            return null; // Si no es interpretable se maneja como vacío [cite: 122]
        }
    }
}
