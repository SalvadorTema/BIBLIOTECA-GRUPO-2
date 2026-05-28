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
    private LibraryService service; // El cuaderno único

    public MainFrame() {
        // Inicializamos el cuaderno que carga los CSV
        this.service = new LibraryService();

        setTitle("Menú Principal - Biblioteca");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        JLabel lblTitle = new JLabel("Biblioteca del Grupo 2", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitle.setBounds(50, 40, 400, 40);
        add(lblTitle);

        // --- BOTÓN 1: ABRE CATÁLOGO ---
        JButton btnCatalogo = new JButton("Gestionar Catálogo");
        btnCatalogo.setBounds(125, 140, 250, 45);
        btnCatalogo.setBackground(new Color(0, 123, 255));
        btnCatalogo.setForeground(Color.WHITE);
        add(btnCatalogo);

        btnCatalogo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Le pasamos la vista actual (MainFrame) y el cuaderno (service)
                CatalogoDialog dialog = new CatalogoDialog(MainFrame.this, service);
                dialog.setVisible(true);
            }
        });

        // --- BOTÓN 2: ABRE USUARIOS ---
        JButton btnUsuarios = new JButton("Gestionar Usuarios");
        btnUsuarios.setBounds(125, 210, 250, 45);
        btnUsuarios.setBackground(new Color(40, 167, 69));
        btnUsuarios.setForeground(Color.WHITE);
        add(btnUsuarios);

        btnUsuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsuariosDialog dialog = new UsuariosDialog(MainFrame.this, service);
                dialog.setVisible(true);
            }
        });

        // --- BOTÓN 3: ABRE OPERACIONES ---
        JButton btnOperaciones = new JButton("Préstamos y Devoluciones");
        btnOperaciones.setBounds(125, 280, 250, 45);
        btnOperaciones.setBackground(new Color(23, 162, 184));
        btnOperaciones.setForeground(Color.WHITE);
        add(btnOperaciones);

        btnOperaciones.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OperacionesDialog dialog = new OperacionesDialog(MainFrame.this, service);
                dialog.setVisible(true);
            }
        });
    }
}