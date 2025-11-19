import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
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

    // Aqui el coutingSort se aplicara para cada digito
    public static void countingSortPorDigito(int[] arr, int exp) {
        int[] salida = new int[arr.length];
        int[] conteo = new int[10]; // sera para los dígitos del 0 al 9

         // En esta parte de contaran las apariciones por dígito
        for (int num : arr) {
            int indice = (num / exp) % 10;
            conteo[indice]++;
        }

        // Y se transformara en un acumulado
        for (int i = 1; i < 10; i++) {
            conteo[i] += conteo[i - 1];
        }

        // Se construira el arreglo de salida
        for (int i = arr.length - 1; i >= 0; i--) {
            int indice = (arr[i] / exp) % 10;
            salida[conteo[indice] - 1] = arr[i];
            conteo[indice]--;
        }

        // Se copiara al arreglo original
        System.arraycopy(salida, 0, arr, 0, arr.length);

    }
    
    // Guardara el resultado en un archivo
    public static void guardarArchivo(String nombreArchivo, int[] datos) {
        try (FileWriter fw = new FileWriter(nombreArchivo)) {
            for (int num : datos) {
                fw.write(num + " ");
            }
        } catch (IOException e) {
            System.out.println("Error al guardar archivo: " + e.getMessage());
        }
    }
}


