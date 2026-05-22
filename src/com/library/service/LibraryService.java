
package com.library.service;

import com.library.model.AbstractMaterial;
import com.library.model.User;

import java.util.List;

public class LibraryService {
    // En esta parte simula la persistencia en memoria
    private List<AbstractMaterial> inventory;
    private List<User> users;

    public LibraryService() {
    	
            this.inventory = com.library.model.FilePersistence.loadMaterials();
            this.users = com.library.model.FilePersistence.loadUsers();
        
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
        com.library.model.FilePersistence.saveMaterials(this.inventory);
        com.library.model.FilePersistence.saveUsers(this.users);
    }
}