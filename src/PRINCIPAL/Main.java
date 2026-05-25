package PRINCIPAL;

import java.util.Scanner;
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
                        System.out.println("\n[Módulo de Libros]");
                        gestionarLibros(teclado, processResult);
                        break;
                    case 2:
                        System.out.println("\n[Módulo de Usuarios]");
                        gestionarUsuarios(teclado, processResult);
                        break;
                    case 3:
                        System.out.println("\n[Módulo de Préstamos en construcción...]");
                        break;
                    case 4:
                        System.out.println("\n[Módulo de Devoluciones]");
                        System.out.println("\n=======================================");
                        System.out.println("       MODULO DE DEVOLUCIONES          ");
                        System.out.println("=======================================");
                        
                        // 1. Pedir los datos al usuario
                        System.out.print("Ingrese el carnet del usuario: ");
                        String carnet = teclado.nextLine();
                        
                        System.out.print("Ingrese el ID del material a devolver: ");
                        String idMaterial = teclado.nextLine();
                        
                        // 2. Llamar al método que procesa la devolución
                        String resultado =processResult.returnMaterial(carnet, idMaterial);
                        
                        // 3. Mostrar el mensaje en español que retorna el método
                        System.out.println("\n👉 " + resultado);
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
    
    //Metodo auxiliar Case1
    
    private static void gestionarLibros(Scanner teclado, LibraryService processResult) {
        int subOpcion = 0;
        do {
            System.out.println("\n=======================================");
            System.out.println("          MODULO DE LIBROS             ");
            System.out.println("=======================================");
            System.out.println("1. Registrar nuevo libro");
            System.out.println("2. Buscar libro (ID o Título)");
            System.out.println("3. Mostrar todos los libros");
            System.out.println("4. Volver al menú principal");
            System.out.print("Seleccione una opción: ");
            
            try {
                subOpcion = teclado.nextInt();
                teclado.nextLine(); // Limpiar el búfer del scanner
                
                switch (subOpcion) {
                    case 1:
                        System.out.println("\n--- REGISTRAR NUEVO LIBRO ---");
                        System.out.print("Ingrese el título del libro: ");
                        String titulo = teclado.nextLine();
                        
                        System.out.print("Ingrese el autor del libro: ");
                        String autor = teclado.nextLine();
                        
                        String resultadoAgregar = processResult.addBook(titulo, autor);
                        System.out.println("\n👉 " + resultadoAgregar);
                        break;
                        
                    case 2:
                        System.out.println("\n--- BUSCAR LIBRO ---");
                        System.out.print("Ingrese el ID o el Título a buscar: ");
                        String criterio = teclado.nextLine();
                        
                        String resultadoBuscar = processResult.searchBook(criterio);
                        System.out.println(resultadoBuscar);
                        break;
                        
                    case 3:
                        String listaLibros = processResult.getAllBooks();
                        System.out.println(listaLibros);
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
