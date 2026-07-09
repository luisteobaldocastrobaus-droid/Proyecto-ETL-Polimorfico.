package clases.io;

import java.io.IOException;
import java.util.List;

/**
 * Interfaz polimórfica para exportar conjuntos de datos en múltiples formatos.
 * @author Luis Teobaldo Castro Baus
 * @author Alex Burbano
 * @version POO 2026-2027 C1
 * @since 08/07/2026
 */
public interface Exporter {
    void exportar(String ruta, List<String[]> datosProcesados) throws IOException;
}
