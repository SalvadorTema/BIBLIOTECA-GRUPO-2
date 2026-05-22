package com.library.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import com.library.service.LibraryService;

public class MainFrame extends JFrame {
    
    private LibraryService service;

    public MainFrame() {
        // Inicializar el servicio Carga automáticamente los CSV
        this.service = new LibraryService();
        
        // Configuración básica de la ventana principal
        setTitle("Sistema de Gestión de Biblioteca");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centra la ventana en la pantalla
        setLayout(null); // Diseño manual para acomodar todo limpio
        
        // Título de bienvenida en la ventana
        JLabel lblTitle = new JLabel("Bienvenido a la Biblioteca del Grupo 2", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setBounds(100, 30, 600, 40);
        add(lblTitle);
    }
}