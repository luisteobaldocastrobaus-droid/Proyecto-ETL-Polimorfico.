package clases.io;

import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Implementación de Exporter para escribir los datos limpios en un archivo CSV.
 * @author Luis Teobaldo Castro Baus
 * @author Alex Burbano
 * @version POO 2026-2027 C1
 * @since 08/07/2026
 */
public class CsvExporter implements Exporter {

    @Override
    public void exportar(String ruta, List<String[]> datosProcesados) throws IOException {
        try (CSVWriter escritor = new CSVWriter(new FileWriter(ruta))) {
            escritor.writeAll(datosProcesados);
        } catch (IOException e) {
            throw new IOException("Error físico al escribir el archivo CSV de salida: " + e.getMessage());
        }
    }
}
