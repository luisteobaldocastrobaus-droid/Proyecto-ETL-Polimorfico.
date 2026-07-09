package clases.io;

import java.io.IOException;
import java.util.List;
import clases.excepciones.EsquemaInvalidoException;

/**
 * Interfaz que define el comportamiento para la extracción de datos.
 * * @author Luis Teobaldo Castro Baus
 * @author Alex Burbano
 * @version POO 2026-2027 C1
 * @since 08/07/2026
 */
public interface DataSource {
    List<String[]> cargarDatos(String ruta) throws IOException, EsquemaInvalidoException;
}
