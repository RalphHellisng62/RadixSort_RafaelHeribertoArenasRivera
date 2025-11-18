import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class RadixSort {
    // Iniciamos construyendo un main

    public static void main(String[] args) {
        // Sera para llamar el archivo de entrada y de salida

        String archivoEntrada = "numeros.txt";
        String archivoSalida = "numeros_ordenados_radix.txt";

        System.out.println("📥 Leyendo archivo: " + archivoEntrada);
        int[] numeros = leerArchivo(archivoEntrada);

        System.out.println("\n numeros originales:");
        imprimirArray(numeros);

        System.out.println("\n Se esta ejecutando RadixSort (se ordena en tiempo real):");
        radixSort(numeros);

        System.out.println("\n numeros ordenados:");
        imprimirArray(numeros);

        guardarArchivo(archivoSalida, numeros);
        System.out.println("\n Archivo ordenado guardado como: " + archivoSalida);

    }

    // Ahora voy a construir la funcion para leer los archivos
    public static int[] leerArchivo(String nombreArchivo) {
        ArrayList<Integer> numeros = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(nombreArchivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] valores = linea.split("[,\\s]+");
                for (String v : valores) {
                    if (!v.trim().isEmpty()) {
                        numeros.add(Integer.parseInt(v.trim()));
                    }
                }
            }
        } catch (IOException | NumberFormatException e) { // Despues agrego un catch en cason de no reconocer el archivo
            System.out.println("Error al leer el archivo: " + e.getMessage());
            System.exit(1);
        }
        return numeros.stream().mapToInt(i -> i).toArray();
    }
    
    // Ahora se hara las funciones para encontrar el numero máximo
    public static int obtenerMaximo(int[] arr) {
        int max = arr[0];
        for (int num : arr) {
            if (num > max) max = num;
        }
        return max;
    }

    // Proceso de RadixSort
    public static void radixSort(int[] arr) {
        int max = obtenerMaximo(arr);

        // El for procesara por cada dígito 
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSortPorDigito(arr, exp);
            System.out.print("Ordenando por dígito (" + exp + "): ");
            imprimirArray(arr); // Y aqui mostrara el proceso en tiempo real
        }
    }
}


