package com.library.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.library.service.LibraryService;

public class MainFrame extends JFrame {
    private static final long serialVersionUID = 1L;
    private LibraryService service; // El cuaderno único compartido

    public MainFrame() {
        // Inicializamos el cuaderno único que controla los archivos CSV
        this.service = new LibraryService();

        setTitle("Menú Principal - Biblioteca");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);
        this.getContentPane().setBackground(new Color(245, 247, 250)); // Blanco grisáceo moderno

        // Título de la aplicación
        JLabel lblTitle = new JLabel("Biblioteca del Grupo 2", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setBounds(50, 40, 400, 40);
        add(lblTitle);

        // --- BOTÓN 1: GESTIONAR CATÁLOGO ---
        JButton btnCatalogo = new JButton("Gestionar Catálogo");
        btnCatalogo.setBounds(125, 120, 250, 45);
        btnCatalogo.setBackground(new Color(0, 123, 255)); // Azul
        btnCatalogo.setForeground(Color.WHITE);
        btnCatalogo.setFocusPainted(false);
        add(btnCatalogo);

        btnCatalogo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abre el JDialog independiente del Catálogo pasándole el servicio
                CatalogoDialog dialog = new CatalogoDialog(MainFrame.this, service);
                dialog.setVisible(true);
            }
        });

        // --- BOTÓN 2: GESTIONAR USUARIOS ---
        JButton btnUsuarios = new JButton("Gestionar Usuarios");
        btnUsuarios.setBounds(125, 190, 250, 45);
        btnUsuarios.setBackground(new Color(40, 167, 69)); // Verde
        btnUsuarios.setForeground(Color.WHITE);
        btnUsuarios.setFocusPainted(false);
        add(btnUsuarios);

        btnUsuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abre el JDialog independiente de Usuarios pasándole el servicio
                UsuariosDialog dialog = new UsuariosDialog(MainFrame.this, service);
                dialog.setVisible(true);
            }
        });

        // --- BOTÓN 3: PRÉSTAMOS Y DEVOLUCIONES ---
        JButton btnOperaciones = new JButton("Préstamos y Devoluciones");
        btnOperaciones.setBounds(125, 260, 250, 45);
        btnOperaciones.setBackground(new Color(23, 162, 184)); // Celeste/Turquesa
        btnOperaciones.setForeground(Color.WHITE);
        btnOperaciones.setFocusPainted(false);
        add(btnOperaciones);

        btnOperaciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Abre el JDialog independiente de Operaciones pasándole el servicio
                OperacionesDialog dialog = new OperacionesDialog(MainFrame.this, service);
                dialog.setVisible(true);
            }
        });

        // --- BOTÓN 4: SALIR DEL SISTEMA ---
        JButton btnExit = new JButton("Salir");
        btnExit.setBounds(160, 340, 150, 40); 
        btnExit.setBackground(new Color(220, 53, 69)); // Rojo elegante
        btnExit.setForeground(Color.WHITE);
        btnExit.setFocusPainted(false);
        add(btnExit);
        
        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Cierra la aplicación de manera limpia
            }
        });
    }
}