package clases.analisis;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import clases.modelo.ColumnaNumerica;

/**
 * Procesa cálculos matemáticos y estadísticos sobre las columnas numéricas limpias.
 * @author Luis Teobaldo Castro Baus
 * @version POO 2026-2027 C1
 * @since 08/07/2026
 */
public class CalculadoraEstadistica {

    /**
     * Extrae y filtra los valores válidos de una columna específica ignorando el valor centinela.
     */
    private static List<Double> obtenerValoresValidos(List<Object[]> filas, int indiceColumna) {
        List<Double> valores = new ArrayList<>();
        for (Object[] fila : filas) {
            if (fila.length > indiceColumna && fila[indiceColumna] instanceof Double) {
                double val = (Double) fila[indiceColumna];
                // Se ignoran los valores centinelas en los cálculos estadísticos para no sesgar la muestra
                if (val != ColumnaNumerica.CENTINELA) {
                    valores.add(val);
                }
            }
        }
        return valores;
    }

    public static double calcularMedia(List<Object[]> filas, int indiceColumna) {
        List<Double> valores = obtenerValoresValidos(filas, indiceColumna);
        if (valores.isEmpty()) return 0.0;

        double suma = 0.0;
        for (double val : valores) {
            suma += val;
        }
        return suma / valores.size();
    }

    public static double calcularMediana(List<Object[]> filas, int indiceColumna) {
        List<Double> valores = obtenerValoresValidos(filas, indiceColumna);
        if (valores.isEmpty()) return 0.0;

        Collections.sort(valores);
        int n = valores.size();
        if (n % 2 == 1) {
            return valores.get(n / 2);
        } else {
            return (valores.get((n / 2) - 1) + valores.get(n / 2)) / 2.0;
        }
    }

    public static double calcularVarianza(List<Object[]> filas, int indiceColumna) {
        List<Double> valores = obtenerValoresValidos(filas, indiceColumna);
        if (valores.size() < 2) return 0.0;

        double media = calcularMedia(filas, indiceColumna);
        double sumaCuadrados = 0.0;
        for (double val : valores) {
            sumaCuadrados += Math.pow(val - media, 2);
        }
        // Varianza muestral (n - 1)
        return sumaCuadrados / (valores.size() - 1);
    }

    public static double calcularDesviacionEstandar(List<Object[]> filas, int indiceColumna) {
        return Math.sqrt(calcularVarianza(filas, indiceColumna));
    }
}
