package PRINCIPAL;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
    	Scanner teclado = new Scanner(System.in);
        int opcion = 0;

        do {
            System.out.println("\n=======================================");
            System.out.println("   SISTEMA DE BIBLIOTECA - GRUPO 2     ");
            System.out.println("=======================================");
            System.out.println("1. Gestionar Libros");
            System.out.println("2. Gestionar Usuarios");
            System.out.println("3. Realizar Préstamo");
            System.out.println("4. Devolver Libro");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            
            try {
                opcion = teclado.nextInt();
                teclado.nextLine(); // Limpiar el búfer del scanner
                
                switch (opcion) {
                    case 1:
                        System.out.println("\n[Módulo de Libros en construcción...]");
                        // Aquí llamarás a los métodos de gestión de libros
                        break;
                    case 2:
                        System.out.println("\n[Módulo de Usuarios en construcción...]");
                        // Aquí llamarás a los métodos de gestión de usuarios
                        break;
                    case 3:
                        System.out.println("\n[Módulo de Préstamos en construcción...]");
                        break;
                    case 4:
                        System.out.println("\n[Módulo de Devoluciones en construcción...]");
                        break;
                    case 5:
                        System.out.println("\n¡Gracias por usar el sistema! Saliendo...");
                        break;
                    default:
                        System.out.println("\n❌ Opción no válida. Intente de nuevo.");
                }
            } catch (Exception e) {
                System.out.println("\n❌ Error: Por favor, ingrese un número válido.");
                teclado.nextLine(); // Limpiar el búfer en caso de error de entrada
                opcion = 0;
            }
            
        } while (opcion != 5);

        teclado.close();
    }
}
