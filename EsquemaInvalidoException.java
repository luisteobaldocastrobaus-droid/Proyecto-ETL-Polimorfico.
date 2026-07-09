package clases.excepciones;

/**
 * Excepción para controlar errores de estructura en el esquema del conjunto de datos.
 * * @author Luis Teobaldo Castro Baus
 * @author Alex Burbano
 * @version POO 2026-2027 C1
 * @since 08/07/2026
 */
public class EsquemaInvalidoException extends Exception {
    public EsquemaInvalidoException(String mensaje) {
        super(mensaje);
    }
}
