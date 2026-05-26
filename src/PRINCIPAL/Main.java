package PRINCIPAL;

import java.util.List;
import java.util.Scanner;

import com.library.model.Magazine;
import com.library.service.LibraryService;

public class Main {

    public static void main(String[] args) {
    	Scanner teclado = new Scanner(System.in);
    	LibraryService processResult = new LibraryService();
        int opcion = 0;

        do {
            System.out.println("\n=======================================");
            System.out.println("   SISTEMA DE BIBLIOTECA - GRUPO 2     ");
            System.out.println("=======================================");
            System.out.println("1. Gestionar Inventario");
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
                    	gestionarInventario(teclado, processResult);
                       
                        break;
                    case 2:
                        System.out.println("\n[Módulo de Usuarios]");
                        gestionarUsuarios(teclado, processResult);
                        break;
                    case 3:
                        System.out.println("\n[Módulo de Préstamos en construcción...]");
                        System.out.println("\n=======================================");
                        System.out.println("       MODULO DE REALIZAR PRÉSTAMO     ");
                        System.out.println("=======================================");
                        
                        // 1. Pedir los datos al usuario
                        System.out.print("Ingrese el carnet del usuario: ");
                        String carnetPrestamo = teclado.nextLine();
                        
                        System.out.print("Ingrese el ID del material a prestar: ");
                        String idMaterialPrestamo = teclado.nextLine();
                        
                        // 2. Llamar al método del servicio que procesa y valida el préstamo
                        String resultadoPrestamo = processResult.borrowMaterial(carnetPrestamo, idMaterialPrestamo);
                        
                        // 3. Mostrar el reporte del estado del proceso
                        System.out.println("\n👉 " + resultadoPrestamo); 
                        break;
                    case 4:
                    	System.out.println("\n=======================================");
                        System.out.println("      MÓDULO DE GESTIONAR DEVOLUCIÓN   ");
                        System.out.println("=======================================");
                        
                        // 1. Solicitar los datos de la devolución
                        System.out.print("Ingrese el carnet del usuario: ");
                        String carnetDevolucion = teclado.nextLine();
                        
                        System.out.print("Ingrese el ID del material a devolver: ");
                        String idMaterialDevolucion = teclado.nextLine();
                        
                        // 2. Invocar al servicio para procesar la devolución y persistir los datos
                        String resultadoDevolucion = processResult.returnMaterial(carnetDevolucion, idMaterialDevolucion);
                        
                        // 3. Imprimir el resultado de la operación
                        System.out.println("\n👉 " + resultadoDevolucion);
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
        
        /**
         * Submenú para administrar tanto Libros como Revistas de forma independiente.
         */
        private static void gestionarInventario(Scanner teclado, LibraryService processResult) {
            int opcionInventario = 0;
            do {
                System.out.println("\n=======================================");
                System.out.println("       GESTIÓN DE INVENTARIO           ");
                System.out.println("=======================================");
                System.out.println("1. Registrar un Libro");
                System.out.println("2. Registrar una Revista");
                System.out.println("3. Listar todos los Libros");
                System.out.println("4. Listar todas las Revistas");
                System.out.println("5. Volver al Menú Principal");
                System.out.print("Seleccione una opción: ");
                
                try {
                    opcionInventario = Integer.parseInt(teclado.nextLine());
                } catch (NumberFormatException e) {
                    opcionInventario = 0;
                }

                switch (opcionInventario) {
                    case 1:
                        System.out.println("\n--- REGISTRAR LIBRO ---");
                        System.out.print("ID: "); String idB = teclado.nextLine();
                        System.out.print("Título: "); String tituloB = teclado.nextLine();
                        System.out.print("Autor: "); String autorB = teclado.nextLine();
                        System.out.println("\n👉 " + processResult.addBook(idB, tituloB, autorB));
                        break;

                    case 2:
                        System.out.println("\n--- REGISTRAR REVISTA ---");
                        System.out.print("ID: "); String idM = teclado.nextLine();
                        System.out.print("Título: "); String tituloM = teclado.nextLine();
                        System.out.print("Número de Edición: ");
                        int edicionM = 0;
                        try {
                            edicionM = Integer.parseInt(teclado.nextLine());
                            System.out.println("\n👉 " + processResult.addMagazine(idM, tituloM, edicionM));
                        } catch (NumberFormatException e) {
                            System.out.println("\n❌ Error: El número de edición debe ser un valor numérico.");
                        }
                        break;

                    case 3:
                        System.out.println("\n--- LISTADO DE LIBROS ---");
                        String listaLibros = processResult.getAllBooks();
                        System.out.println(listaLibros);
                        break;
                    case 4:
                        System.out.println("\n--- LISTADO DE REVISTAS ---");
                        List<Magazine> revistas = processResult.getAllMagazines();
                        if (revistas.isEmpty()) {
                            System.out.println("No hay revistas registradas.");
                        } else {
                            for (Magazine m : revistas) {
                                String estado = m.isAvailable() ? "Disponible" : "Prestado";
                                System.out.println("[" + m.getId() + "] " + m.getTitle() + " - Edición: #" + m.getIssueNumber() + " (" + estado + ")");
                            }
                        }
                        break;

                    case 5:
                        System.out.println("Regresando al menú principal...");
                        break;

                    default:
                        System.out.println("Opción no válida. Intente de nuevo.");
                }
            } while (opcionInventario != 5);
        

    }
    
    //Metodo auxiliar Case2
    private static void gestionarUsuarios(Scanner teclado, LibraryService processResult) {
        int subOpcion = 0;
        do {
            System.out.println("\n=======================================");
            System.out.println("         MODULO DE USUARIOS            ");
            System.out.println("=======================================");
            System.out.println("1. Registrar nuevo usuario (Estudiante)");
            System.out.println("2. Buscar usuario por Carné");
            System.out.println("3. Mostrar todos los usuarios");
            System.out.println("4. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            
            try {
                subOpcion = teclado.nextInt();
                teclado.nextLine(); // Limpiar el búfer del scanner
                
                switch (subOpcion) {
                    case 1:
                        System.out.println("\n--- REGISTRAR NUEVO USUARIO ---");
                        System.out.print("Ingrese el carné del estudiante: ");
                        String carnet = teclado.nextLine();
                        
                        System.out.print("Ingrese el nombre completo: ");
                        String nombre = teclado.nextLine();
                        
                        String resultadoAgregar = processResult.registerNewUser(carnet, nombre);
                        System.out.println("\n👉 " + resultadoAgregar);
                        break;
                        
                    case 2:
                        System.out.println("\n--- BUSCAR USUARIO ---");
                        System.out.print("Ingrese el carné a buscar: ");
                        String carnetBuscar = teclado.nextLine();
                        
                        String reporteUsuario = processResult.getUserReport(carnetBuscar);
                        System.out.println(reporteUsuario);
                        break;
                        
                    case 3:
                        String listaUsuarios = processResult.getAllUsers();
                        System.out.println(listaUsuarios);
                        break;
                        
                    case 4:
                        System.out.println("\nRegresando al menú principal...");
                        break;
                        
                    default:
                        System.out.println("\n❌ Opción no válida en este submódulo.");
                }
            } catch (Exception e) {
                System.out.println("\n❌ Error: Por favor, ingrese un número válido.");
                teclado.nextLine(); // Limpiar búfer
                subOpcion = 0;
            }
        } while (subOpcion != 4);
    }
}
