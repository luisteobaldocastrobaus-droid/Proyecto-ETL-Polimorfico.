package clases.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementación de Exporter para transformar los registros tabulares en formato estructurado JSON.
 * @author Luis Teobaldo Castro Baus
 * @author Alex Burbano
 * @version POO 2026-2027 C1
 * @since 08/07/2026
 */
public class JsonExporter implements Exporter {

    @Override
    public void exportar(String ruta, List<String[]> datosProcesados) throws IOException {
        if (datosProcesados == null || datosProcesados.isEmpty()) {
            throw new IllegalArgumentException("No hay datos disponibles para exportar a JSON.");
        }

        // Se extraen los encabezados que servirán como claves ("keys") del JSON
        String[] encabezados = datosProcesados.get(0);
        List<Map<String, String>> listaJson = new ArrayList<>();

        // Se recorren las filas mapeando cada celda con su columna correspondiente
        for (int i = 1; i < datosProcesados.size(); i++) {
            String[] fila = datosProcesados.get(i);
            Map<String, String> objetoJson = new LinkedHashMap<>();
            for (int j = 0; j < encabezados.length; j++) {
                // Si la fila llegara a ser más corta, se asume vacío
                String valor = (j < fila.length) ? fila[j] : "";
                objetoJson.put(encabezados[j], valor);
            }
            listaJson.add(objetoJson);
        }

        // Instanciación y configuración de la librería Jackson para que el JSON quede tabulado y legible
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);

        try (FileWriter escritor = new FileWriter(ruta)) {
            mapper.writeValue(escritor, listaJson);
        } catch (IOException e) {
            throw new IOException("Error físico al generar el archivo estructurado JSON: " + e.getMessage());
        }
    }
}