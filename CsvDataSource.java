package clases.io;

import com.opencsv.CSVReader;
import clases.excepciones.EsquemaInvalidoException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/**
 * Implementación de DataSource para extraer datos desde un archivo CSV.
 * @author Luis Teobaldo Castro Baus
 * @author Alex Burbano
 * @version POO 2026-2027 C1
 * @since 08/07/2026
 */
public class CsvDataSource implements DataSource {

    @Override
    public List<String[]> cargarDatos(String ruta) throws IOException, EsquemaInvalidoException {
        try (CSVReader lector = new CSVReader(new FileReader(ruta))) {
            List<String[]> todosLosRegistros = lector.readAll();

            // Validación de estructura: Archivo vacío o sin filas de datos [cite: 170]
            if (todosLosRegistros == null || todosLosRegistros.isEmpty() || todosLosRegistros.size() < 2) {
                throw new EsquemaInvalidoException("El archivo estructurado está vacío o carece de registros suficientes.");
            }

            String[] encabezados = todosLosRegistros.get(0);

            // Validación de estructura: Mínimo 6 columnas [cite: 106, 170]
            if (encabezados.length < 6) {
                throw new EsquemaInvalidoException("El archivo no cumple con el esquema básico: se requieren mínimo 6 columnas.");
            }

            return todosLosRegistros;
        } catch (Exception e) {
            if (e instanceof EsquemaInvalidoException) {
                throw (EsquemaInvalidoException) e;
            }
            throw new IOException("Error físico al intentar procesar el archivo CSV: " + e.getMessage());
        }
    }
}
