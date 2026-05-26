
package com.library.service;

import com.library.model.AbstractMaterial;
import com.library.model.Book;
import com.library.model.Magazine;
import com.library.model.User;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class LibraryService {
    // En esta parte simula la persistencia en memoria
    private List<AbstractMaterial> inventory;
    private List<User> users;

    public LibraryService() {
    	
    	// 1. Cargar primero las listas base desde los archivos
        this.users = com.library.model.FilePersistence.loadUsers();
        
        // Necesitamos leer temporalmente el archivo de materiales
        this.inventory = new ArrayList<>();
        File file = new File("materials.csv");
        if (file.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                br.readLine(); // Saltar encabezado
                String line;
                while ((line = br.readLine()) != null) {
                    String[] data = line.split(";");
                    if (data.length < 5) continue;

                    String type = data[0];
                    String id = data[1];
                    String title = data[2];
                    String extra = data[3];
                    String carnetDueno = data[4];

                    AbstractMaterial material = null;
                    if (type.equals("B")) {
                        material = new Book(id, title, extra);
                    } else if (type.equals("M")) {
                        material = new Magazine(id, title, Integer.parseInt(extra));
                    }

                    if (material != null) {
                        // Si el carnet es diferente de "NONE", reconstruimos la relación bidireccional
                        if (!carnetDueno.equalsIgnoreCase("NONE")) {
                            material.setAvailable(false);
                            User usuarioDueno = findUserByCarnet(carnetDueno);
                            if (usuarioDueno != null) {
                                usuarioDueno.addLoan(material); // Se añade a los préstamos activos del alumno
                            }
                        } else {
                            material.setAvailable(true);
                        }
                        this.inventory.add(material);
                    }
                }
            } catch (Exception e) {
                System.err.println("Error vinculando persistencia: " + e.getMessage());
            }
        }
        
    }

    //Registros
    public void addMaterial(AbstractMaterial material) {
        if (material != null) {
            inventory.add(material);
        }
    }

    public void addUser(User user) {
        if (user != null) {
            users.add(user);
        }
    }

    public List<AbstractMaterial> getInventory() {
        return inventory;
    }

    public List<User> getUsers() {
        return users;
    }

    //Metodo de busqueda

    public AbstractMaterial findMaterialById(String id) {
        for (AbstractMaterial material : inventory) {
            if (material.getId().equalsIgnoreCase(id)) {
                return material;
            }
        }
        return null; // Si no lo encuentra, retorna nulo para que la UI mande alerta
    }

    public User findUserByCarnet(String carnet) {
        for (User user : users) {
            if (user.getCarnet().equalsIgnoreCase(carnet)) {
                return user;
            }
        }
        return null;
    }

    //Validaciones obligatorias)
   
    /**
     * REQUISITO: Método borrowMaterial con lógica de control de negocio.
     * Evalúa las restricciones y devuelve un reporte del estado del proceso.
     */
    public String borrowMaterial(String carnet, String id) {
        User user = findUserByCarnet(carnet);
        AbstractMaterial material = findMaterialById(id);

        // VALIDACIÓN DE EXISTENCIA: Requisito de materiales/usuarios inexistentes
        if (user == null) {
            return "Error: User with carnet " + carnet + " does not exist.";
        }
        if (material == null) {
            return "Error: Material with ID " + id + " does not exist.";
        }

        // VALIDACIÓN 1 ENUNCIADO: Si el material no está disponible, se deniega
        if (!material.isAvailable()) {
            return "Error: The material '" + material.getTitle() + "' is already borrowed.";
        }

        // VALIDACIÓN 2 ENUNCIADO: Si el usuario ya excedió el límite de 3 préstamos activos
        if (user.getActiveLoans().size() >= 3) {
            return "Error: User " + user.getName() + " has reached the limit of 3 active loans.";
        }

        // ACCIÓN: Si pasa los filtros, se efectúa el préstamo cambiando el estado del objeto
        material.setAvailable(false);
        user.addLoan(material);

        return "Success: '" + material.getTitle() + "' loaned to " + user.getName() + ".";
    }

   //Polimorfimo 
    /**
     * REQUISITO: Recorrer la lista y ejecutar el método abstracto en tiempo de ejecución.
     * Demuestra cómo Java diferencia entre Libro 15 días y Revista 3 días.
     */
    public void printLoanRulesReport() {
        System.out.println("=== SYSTEM POLIMORPHISM REPORT ===");
        for (AbstractMaterial material : inventory) {
            
            int days = material.getMaxLoanDays(); 
            System.out.println("Material ID: " + material.getId() + " | Title: " + material.getTitle() + " | Max Loan: " + days + " days.");
        }
    }
    
    public void saveAllData() {
    	// Le pasamos ambos inventarios para que cruce la información de los préstamos
        com.library.model.FilePersistence.saveMaterials(this.inventory, this.users);
        com.library.model.FilePersistence.saveUsers(this.users);
    }
    public String returnMaterial(String carnet, String id) {
        User user = findUserByCarnet(carnet);
        AbstractMaterial material = findMaterialById(id);
        

        // Validación de existencia
        if (user == null) {
            return "Error: El usuario con carnet " + carnet + " no existe.";
        }
        if (material == null) {
            return "Error: El material con ID " + id + " no existe.";
            
        }

        // Verificar si el usuario realmente tiene prestado ese material
        if (!user.getActiveLoans().contains(material)) {
            return "Error: El usuario " + user.getName() + " no tiene este material registrado como préstamo.";
        }

        // ACCIÓN: Cambiar estados
        material.setAvailable(true);
        user.removeLoan(material); 

        // Guardar cambios automáticamente en los archivos
        saveAllData();

        return "Éxito: '" + material.getTitle() + "' ha sido devuelto exitosamente por " + user.getName() + ".";
    }
    
    //Nuevos metodos 
//  MÉTODOS PARA EL CASO 1: LIBROS
    public String addBook(String id, String title, String author ) {
        if (title == null || title.trim().isEmpty()) {
            return "Error: El título del libro no puede estar vacío.";
        }
       
      
        // Crear la instancia de la clase hija Book
        Book nuevoLibro = new Book(id, title, author);
        
        // Utiliza tu método existente para agregarlo a la lista
        addMaterial(nuevoLibro);
        
        // Guardar automáticamente en el archivo persistente
        saveAllData();
        
        return "Libro '" + title + "' registrado con éxito. ID asignado: " + id;
    }
    /**
     * Busca un libro por su ID o por coincidencias en el título.
     */
    public String searchBook(String criterio) {
        if (criterio == null || criterio.trim().isEmpty()) {
            return "Error: El criterio de búsqueda no puede estar vacío.";
        }

        StringBuilder resultados = new StringBuilder();
        boolean encontrado = false;

        for (AbstractMaterial material : inventory) {
            // Validamos que sea un objeto de tipo Book
            if (material instanceof Book) {
                Book libro = (Book) material;
                // Buscar por ID exacto o si el título contiene el criterio (sin importar mayúsculas)
                if (libro.getId().equalsIgnoreCase(criterio) || 
                    libro.getTitle().toLowerCase().contains(criterio.toLowerCase())) {
                    
                    resultados.append("\n📖 ID: ").append(libro.getId())
                              .append("\n   Título: ").append(libro.getTitle())
                              .append("\n   Autor: ").append(libro.getAuthor())
                              .append("\n   Disponible: ").append(libro.isAvailable() ? "✅ SÍ" : "❌ NO (Prestado)")
                              .append("\n   Días Máx. Préstamo: ").append(libro.getMaxLoanDays()).append(" días\n");
                    encontrado = true;
                }
            }
        }
        if (!encontrado) {
            return "No se encontraron libros que coincidan con: '" + criterio + "'";
        }

        return resultados.toString();
    }
    /**
     * Devuelve una cadena con todos los libros registrados en el sistema.
     */
    public String getAllBooks() {
        StringBuilder lista = new StringBuilder();
        int contador = 0;

        for (AbstractMaterial material : inventory) {
            if (material instanceof Book) {
                Book libro = (Book) material;
                contador++;
                lista.append("[").append(libro.getId()).append("] ")
                     .append(libro.getTitle()).append(" - Autor: ").append(libro.getAuthor())
                     .append(" | Estado: ").append(libro.isAvailable() ? "Disponible" : "Prestado")
                     .append("\n");
            }
        }

        if (contador == 0) {
            return "El inventario de libros está vacío.";
        }

        return "\n=== INVENTARIO DE LIBROS TOTALES (" + contador + ") ===\n" + lista.toString();
    }
    
    /**
     * Registra una nueva revista en el inventario y guarda en el archivo CSV.
     */
    public String addMagazine(String id, String title, int issueNumber) {
        // Validación: Evitar IDs duplicados en el sistema
        if (findMaterialById(id) != null) {
            return "Error: Ya existe un material (Libro o Revista) con el ID " + id + ".";
        }

        // Crear la instancia de la revista y añadirla al inventario polimórfico
        Magazine newMagazine = new Magazine(id, title, issueNumber);
        inventory.add(newMagazine);
        
        // Persistir el cambio de inmediato en el disco
        saveAllData();
        
        return "Éxito: Revista '" + title + "' (Edición #" + issueNumber + ") registrada correctamente.";}
    
    /**
     * Retorna una lista exclusiva de las revistas en el inventario.
     */
    public List<Magazine> getAllMagazines() {
        List<Magazine> magazinesOnly = new ArrayList<>();
        for (AbstractMaterial mat : inventory) {
            if (mat instanceof Magazine) {
                magazinesOnly.add((Magazine) mat);
            }
        }
        return magazinesOnly;
    }
//  MÉTODOS PARA EL CASO 2: USUARIOS
    /**
     * Registra un nuevo usuario en el sistema.
     * Valida que el carné no esté duplicado antes de guardarlo.
     */
    public String registerNewUser(String carnet, String name) {
        if (carnet == null || carnet.trim().isEmpty()) {
            return "Error: El carné no puede estar vacío.";
        }
        if (name == null || name.trim().isEmpty()) {
            return "Error: El nombre no puede estar vacío.";
        }

        // Validar si el usuario ya existe para evitar duplicados
        if (findUserByCarnet(carnet) != null) {
            return "Error: Ya existe un usuario registrado con el carné " + carnet + ".";
        }

        // Crear la instancia de User
        User nuevoUsuario = new User(carnet, name);
        
        // Agregar a la lista usando tu método existente
        addUser(nuevoUsuario);
        
        // Persistir los datos en el archivo
        saveAllData();
        
        return "Usuario '" + name + "' registrado exitosamente con carné: " + carnet;
    }

    /**
     * Busca un usuario por carné y devuelve su información detallada,
     * incluyendo la cantidad de libros que tiene prestados actualmente.
     */
    public String getUserReport(String carnet) {
        User user = findUserByCarnet(carnet);
        
        if (user == null) {
            return "No se encontró ningún usuario con el carné: '" + carnet + "'";
        }

        StringBuilder reporte = new StringBuilder();
        reporte.append("\n👤 IDENTIFICACIÓN DE USUARIO 👤")
               .append("\n   Nombre: ").append(user.getName())
               .append("\n   Carné: ").append(user.getCarnet())
               .append("\n   Préstamos Activos: ").append(user.getActiveLoans().size()).append(" / 3");

        if (!user.getActiveLoans().isEmpty()) {
            reporte.append("\n   Libros en posesión:");
            for (AbstractMaterial material : user.getActiveLoans()) {
                reporte.append("\n     👉 [").append(material.getId()).append("] ").append(material.getTitle());
            }
        } else {
            reporte.append("\n   🟢 Sin préstamos pendientes.");
        }
        reporte.append("\n");

        return reporte.toString();
    }

    /**
     * Devuelve la lista completa de usuarios registrados.
     */
    public String getAllUsers() {
        StringBuilder lista = new StringBuilder();
        int contador = 0;

        for (User user : users) {
            contador++;
            lista.append("• Carné: ").append(user.getCarnet())
                 .append(" | Nombre: ").append(user.getName())
                 .append(" | Préstamos: ").append(user.getActiveLoans().size()).append("\n");
        }

        if (contador == 0) {
            return "No hay usuarios registrados en el sistema.";
        }

        return "\n=== PADRÓN DE USUARIOS REGISTRADOS (" + contador + ") ===\n" + lista.toString();
    }
 // =================================================================
    // 📊 MÓDULO DE REPORTES (CASO 5)
    // =================================================================

    /**
     * Genera un reporte polimórfico aplicando las reglas de préstamo 
     * definidas en las clases hijas de AbstractMaterial.
     */
    public String getLoanRulesReport() {
        if (inventory.isEmpty()) {
            return "No hay materiales registrados en el inventario para generar el reporte.";
        }

        StringBuilder reporte = new StringBuilder();
        reporte.append("\n===============================================================\n");
        reporte.append("        REPORTE POLIMÓRFICO: REGLAS DE PRÉSTAMO VIGENTES       \n");
        reporte.append("===============================================================\n");
        reporte.append(String.format("%-6s | %-25s | %-12s | %-10s\n", "ID", "TÍTULO", "TIPO", "MÁX. DÍAS"));
        reporte.append("---------------------------------------------------------------\n");

        for (AbstractMaterial mat : inventory) {
            String tipo = (mat instanceof Book) ? "Libro" : "Revista";
            
            // Aquí ocurre la magia del polimorfismo: mat.getLoanDurationInDays() 
            // llamará al método específico de Book o de Magazine automáticamente.
            int diasMaximos = mat.getLoanDurationInDays(); 

            reporte.append(String.format("%-6s | %-25.25s | %-12s | %-10d días\n", 
                    mat.getId(), mat.getTitle(), tipo, diasMaximos));
        }
        reporte.append("===============================================================\n");
        
        return reporte.toString();
    }
    
}