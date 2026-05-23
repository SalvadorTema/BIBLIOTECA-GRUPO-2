package com.library.view;

import javax.swing.SwingUtilities;

public class LibraryApp {
    public static void main(String[] args) {
    	
        // Ejecuta la interfaz gráfica 
    	
        SwingUtilities.invokeLater(() -> {
            MainFrame ventana = new MainFrame();
            ventana.setVisible(true);
        });
    }
}
//