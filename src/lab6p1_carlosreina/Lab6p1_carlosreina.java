/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package lab6p1_carlosreina;

import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author betob
 */
public class Lab6p1_carlosreina {

    /**
     * @param args the command line arguments
     */
        public static boolean esEnteroPositivo(String texto) {
        if (texto.length() == 0) {
            return false;
        }

        boolean tieneNumero = false;

        for (int i = 0; i < texto.length(); i++) {
            char letra = texto.charAt(i);

            if (letra < '0' || letra > '9') {
                return false;
            }

            if (letra != '0') {
                tieneNumero = true;
            }
        }

        return tieneNumero;
    }

    public static int convertirEntero(String texto) {
        if (texto.length() == 0) {
            return -1;
        }

        int numero = 0;

        for (int i = 0; i < texto.length(); i++) {
            char letra = texto.charAt(i);

            if (letra < '0' || letra > '9') {
                return -1;
            }

            int digito = letra - '0';

            if (numero > (Integer.MAX_VALUE - digito) / 10) {
                return -1;
            }

            numero = numero * 10 + digito;
        }

        return numero;
    }

    public static int pedirPosicion(Scanner leer, boolean[] descubiertas, int anterior) {
        int posicion = -1;

        while (posicion == -1) {
            System.out.print("Ingrese una posicion de 0 a "
                    + (descubiertas.length - 1) + ": ");

            String texto = leer.nextLine().trim();
            int numero = convertirEntero(texto);

            if (numero < 0 || numero >= descubiertas.length) {
                System.out.println("Posicion invalida.");
            } else if (numero == anterior) {
                System.out.println("No puede elegir la misma posicion.");
            } else if (descubiertas[numero] == true) {
                System.out.println("Esa posicion ya fue descubierta.");
            } else {
                posicion = numero;
            }
        }

        return posicion;
    }

    public static int[] generarTableroMemoria() {
        int[] tablero = new int[6];

        for (int i = 0; i < 6; i++) {
            tablero[i] = (i / 2) + 1;
        }

        Random azar = new Random();

        for (int i = 5; i > 0; i--) {
            int j = azar.nextInt(i + 1);

            int temporal = tablero[i];
            tablero[i] = tablero[j];
            tablero[j] = temporal;
        }

        return tablero;
    }

    public static void mostrarTableroMemoria(int[] tablero, boolean[] descubiertas) {
        System.out.println("\nPosiciones: 0  1  2  3  4  5");
        System.out.print("Cartas:     ");

        for (int i = 0; i < tablero.length; i++) {
            if (descubiertas[i] == true) {
                System.out.print("[" + tablero[i] + "]");
            } else {
                System.out.print("[*]");
            }
        }

        System.out.println();
    }

    public static boolean verificarPareja(int[] tablero, boolean[] descubiertas, int pos1, int pos2) {

        if (tablero[pos1] == tablero[pos2]) {
            descubiertas[pos1] = true;
            descubiertas[pos2] = true;
            return true;
        }

        return false;
    }

    public static void jugarMemoria(Scanner leer) {
        int[] tablero = generarTableroMemoria();
        boolean[] descubiertas = new boolean[6];

        int parejas = 0;
        int intentos = 0;

        System.out.println("\nJUEGO DE MEMORIA");
        System.out.println("Encuentre las 3 parejas de numeros.");

        while (parejas < 3) {
            mostrarTableroMemoria(tablero, descubiertas);

            System.out.println("Primera carta:");
            int pos1 = pedirPosicion(leer, descubiertas, -1);

            System.out.println("Segunda carta:");
            int pos2 = pedirPosicion(leer, descubiertas, pos1);

            intentos++;

            System.out.println("Cartas: [" + tablero[pos1]
                    + "] y [" + tablero[pos2] + "]");

            if (verificarPareja(tablero, descubiertas, pos1, pos2) == true) {
                System.out.println("Pareja encontrada.");
                parejas++;
            } else {
                System.out.println("No coinciden. Se ocultan de nuevo.");
            }
        }

        System.out.println("\nGanaste. Encontraste las 3 parejas.");

        mostrarTableroMemoria(tablero, descubiertas);

        System.out.println("Total de intentos: " + intentos);
    }

    public static boolean esPrimo(int numero) {
        if (numero < 2) {
            return false;
        }

        for (int i = 2; i <= numero / i; i++) {
            if (numero % i == 0) {
                return false;
            }
        }

        return true;
    }

    public static int[] generarCarton() {
        int[] carton = new int[10];
        Random azar = new Random();

        boolean hayPrimo = false;

        while (hayPrimo == false) {
            for (int i = 0; i < carton.length; i++) {
                carton[i] = azar.nextInt(50) + 1;

                if (esPrimo(carton[i]) == true) {
                    hayPrimo = true;
                }
            }
        }

        return carton;
    }

    public static boolean verificarPrimos(int[] carton, int... intentos) {
        int cantidadPrimos = 0;

        for (int i = 0; i < carton.length; i++) {
            if (esPrimo(carton[i]) == true) {
                boolean repetido = false;

                for (int j = 0; j < i; j++) {
                    if (carton[j] == carton[i]) {
                        repetido = true;
                    }
                }

                if (repetido == false) {
                    cantidadPrimos++;
                }
            }
        }

        if (intentos.length != cantidadPrimos) {
            return false;
        }

        boolean[] usados = new boolean[carton.length];

        for (int i = 0; i < intentos.length; i++) {
            boolean encontrado = false;

            for (int j = 0; j < carton.length; j++) {
                if (carton[j] == intentos[i]
                        && esPrimo(carton[j]) == true
                        && usados[j] == false) {

                    encontrado = true;

                    for (int k = 0; k < carton.length; k++) {
                        if (carton[k] == intentos[i]) {
                            usados[k] = true;
                        }
                    }
                }
            }

            if (encontrado == false) {
                return false;
            }
        }

        return true;
    }

    public static void jugarLoteria(Scanner leer) {
        int[] carton = generarCarton();

        System.out.println("\nLOTERIA A CIEGAS");
        System.out.println("Encuentre todos los numeros primos del carton.");
        System.out.print("Carton: ");

        for (int i = 0; i < carton.length; i++) {
            System.out.print(carton[i] + " ");
        }

        System.out.println();

        String entrada = "";

        while (entrada.length() == 0) {
            System.out.println("Ingrese los primos separados por espacios:");

            entrada = leer.nextLine().trim();

            if (entrada.length() == 0) {
                System.out.println("La entrada no puede estar vacia.");
            }
        }

        String[] partes = entrada.split("\\s+");

        int[] numeros = new int[partes.length];
        int cantidad = 0;

        for (int i = 0; i < partes.length; i++) {
            if (esEnteroPositivo(partes[i]) == true) {
                int numero = convertirEntero(partes[i]);

                if (numero > 0) {
                    numeros[cantidad] = numero;
                    cantidad++;
                } else {
                    System.out.println("Numero invalido ignorado: " + partes[i]);
                }
            } else {
                System.out.println("Numero invalido ignorado: " + partes[i]);
            }
        }

        int[] intentos = new int[cantidad];

        for (int i = 0; i < cantidad; i++) {
            intentos[i] = numeros[i];
        }

        if (verificarPrimos(carton, intentos) == true) {
            System.out.println("Ganaste. Encontraste todos los primos del carton.");
        } else {
            System.out.println("Perdiste. No ingresaste exactamente los primos del carton.");
        }
    }

    public static char[] generarTableroBuscaminas(int tamano, int cantidadMinas) {
        char[] tablero = new char[tamano];

        Random azar = new Random();

        for (int i = 0; i < tamano; i++) {
            tablero[i] = '_';
        }

        int minasPuestas = 0;

        while (minasPuestas < cantidadMinas) {
            int pos = azar.nextInt(tamano);

            if (tablero[pos] == '_') {
                tablero[pos] = '*';
                minasPuestas++;
            }
        }

        return tablero;
    }

    public static int contarMinasAdyacentes(char[] tablero, int posicion) {
        int minas = 0;

        if (posicion > 0) {
            if (tablero[posicion - 1] == '*') {
                minas++;
            }
        }

        if (posicion < tablero.length - 1) {
            if (tablero[posicion + 1] == '*') {
                minas++;
            }
        }

        return minas;
    }

    public static void imprimirTableroBuscaminas(char[] tablero, boolean[] descubiertas) {
        System.out.println("\nPosiciones: 0  1  2  3  4  5  6  7  8  9");
        System.out.print("Casillas:   ");

        for (int i = 0; i < tablero.length; i++) {
            if (descubiertas[i] == false) {
                System.out.print("[?]");
            } else if (tablero[i] == '*') {
                System.out.print("[*]");
            } else {
                System.out.print("[" + contarMinasAdyacentes(tablero, i) + "]");
            }
        }

        System.out.println();
    }

    public static void jugarBuscaminas(Scanner leer) {
        int tamano = 10;
        int cantidadMinas = 3;

        char[] tablero = generarTableroBuscaminas(tamano, cantidadMinas);
        boolean[] descubiertas = new boolean[tamano];

        int vidas = 3;
        int casillasSeguras = 0;

        System.out.println("\nBUSCAMINAS LINEAL");
        System.out.println("Encuentre las 7 casillas seguras.");
        System.out.println("Tiene 3 vidas.");

        while (vidas > 0 && casillasSeguras < tamano - cantidadMinas) {
            imprimirTableroBuscaminas(tablero, descubiertas);

            System.out.println("Vidas: " + vidas);

            int posicion = pedirPosicion(leer, descubiertas, -1);

            descubiertas[posicion] = true;

            if (tablero[posicion] == '*') {
                vidas--;
                System.out.println("Explosion. Era una mina.");
                System.out.println("Pierde una vida.");
            } else {
                casillasSeguras++;
                System.out.println("Casilla segura.");
                System.out.println("Minas vecinas: "
                        + contarMinasAdyacentes(tablero, posicion));
            }
        }

        if (vidas == 0) {
            System.out.println("\nPerdiste. Te quedaste sin vidas.");
        } else {
            System.out.println("\nGanaste. Descubriste todas las casillas seguras.");
        }

        for (int i = 0; i < descubiertas.length; i++) {
            descubiertas[i] = true;
        }

        imprimirTableroBuscaminas(tablero, descubiertas);
    }

    public static void main(String[] args) {
        Scanner leer = new Scanner(System.in);

        int opcion = 0;

        while (opcion != 4) {
            System.out.println("\nLABORATORIO 2");
            System.out.println("1. Juego de Memoria");
            System.out.println("2. Loteria a Ciegas");
            System.out.println("3. Buscaminas Lineal");
            System.out.println("4. Salir");

            System.out.print("Seleccione una opcion (1-4): ");

            String texto = leer.nextLine().trim();
            opcion = convertirEntero(texto);

            if (opcion == 1) {
                jugarMemoria(leer);
            } else if (opcion == 2) {
                jugarLoteria(leer);
            } else if (opcion == 3) {
                jugarBuscaminas(leer);
            } else if (opcion == 4) {
                System.out.println("Programa finalizado.");
            } else {
                System.out.println("Opcion invalida. Intente de nuevo.");
            }
        }
    }
}

    
    


    
    

