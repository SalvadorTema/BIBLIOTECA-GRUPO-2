package com.library.model;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Persistencia de Datos Archivos CSV
 * Esta clase se encarga de transformar las listas de objetos a texto csv
 * para guardarlas en el disco, y viceversa cuando el programa se vuelve a abrir.
 */
public class FilePersistence {

    private static final String MATERIALS_FILE = "materials.csv";
    private static final String USERS_FILE = "users.csv";
    private static final String DELIMITER = ";";

   
// PERSISTENCIA DE MATERIALES LIBROS Y REVISTAS (CON ASIGNACIÓN DE PRÉSTAMOS)
    
    public static void saveMaterials(List<AbstractMaterial> inventory, List<User> users) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(MATERIALS_FILE))) {
            writer.println("Type" + DELIMITER + "ID" + DELIMITER + "Title" + DELIMITER + "ExtraField" + DELIMITER + "BorrowedByCarnet");
            
            for (AbstractMaterial mat : inventory) {
                // Buscamos si algún usuario tiene este material en su lista de préstamos activos
                String carnetDueno = "NONE";
                for (User u : users) {
                    if (u.getActiveLoans().contains(mat)) {
                        carnetDueno = u.getCarnet();
                        break;
                    }
                }
                
                if (mat instanceof Book) {
                    Book b = (Book) mat;
                    writer.println("B" + DELIMITER + b.getId() + DELIMITER + b.getTitle() + DELIMITER + b.getAuthor() + DELIMITER + carnetDueno);
                } else if (mat instanceof Magazine) {
                    Magazine m = (Magazine) mat;
                    writer.println("M" + DELIMITER + m.getId() + DELIMITER + m.getTitle() + DELIMITER + m.getIssueNumber() + DELIMITER + carnetDueno);
                }
            }
        } catch (IOException e) {
            System.err.println("Error saving materials: " + e.getMessage());
        }
    }

    public static List<AbstractMaterial> loadMaterials() {
        List<AbstractMaterial> inventory = new ArrayList<>();
        File file = new File(MATERIALS_FILE);
        
        if (!file.exists()) return inventory; // Si el archivo no existe, inicia vacío.

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            br.readLine(); // Saltar la línea del encabezado
            
            while ((line = br.readLine()) != null) {
                String[] data = line.split(DELIMITER);
                if (data.length < 5) continue;

                String type = data[0];
                String id = data[1];
                String title = data[2];
                String extra = data[3];
                boolean isAvailable = Boolean.parseBoolean(data[4]);

                if (type.equals("B")) {
                    Book book = new Book(id, title, extra);
                    book.setAvailable(isAvailable);
                    inventory.add(book);
                } else if (type.equals("M")) {
                    int issue = Integer.parseInt(extra);
                    Magazine mag = new Magazine(id, title, issue);
                    mag.setAvailable(isAvailable);
                    inventory.add(mag);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading materials: " + e.getMessage());
        }
        return inventory;
    }

   
    // PERSISTENCIA DE USUARIOS
    
    public static void saveUsers(List<User> users) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(USERS_FILE))) {
            writer.println("Carnet" + DELIMITER + "Name");
            for (User u : users) {
                writer.println(u.getCarnet() + DELIMITER + u.getName());
            }
        } catch (IOException e) {
            System.err.println("Error saving users: " + e.getMessage());
        }
    }

    public static List<User> loadUsers() {
        List<User> users = new ArrayList<>();
        File file = new File(USERS_FILE);

        if (!file.exists()) return users;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            br.readLine(); // Saltar encabezado
            
            while ((line = br.readLine()) != null) {
                String[] data = line.split(DELIMITER);
                if (data.length < 2) continue;
                
                users.add(new User(data[0], data[1]));
            }
        } catch (IOException e) {
            System.err.println("Error loading users: " + e.getMessage());
        }
        return users;
    }
}