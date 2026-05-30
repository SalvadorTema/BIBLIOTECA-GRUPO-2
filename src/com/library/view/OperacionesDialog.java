package com.library.view;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextArea; // Nuevo para el reporte
import javax.swing.JScrollPane; // Nuevo para el reporte
import com.library.service.LibraryService;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class OperacionesDialog extends JDialog {
    private static final long serialVersionUID = 1L;
    
    private JTextField txtCarnet, txtIdMaterial;

    public OperacionesDialog(JFrame parent, final LibraryService service) {
        super(parent, "Préstamos y Devoluciones", true);

        // Aumentamos un poco el alto (de 350 a 400) para hacer espacio al nuevo botón
        setSize(450, 400);
        setLocationRelativeTo(parent);
        setLayout(null);

        JLabel lblCarnet = new JLabel("Carné Usuario:"); 
        lblCarnet.setBounds(50, 50, 120, 30); 
        add(lblCarnet);
        
        txtCarnet = new JTextField(); 
        txtCarnet.setBounds(180, 50, 180, 30); 
        add(txtCarnet);

        JLabel lblId = new JLabel("ID Material/Libro:"); 
        lblId.setBounds(50, 100, 120, 30); 
        add(lblId);
        
        txtIdMaterial = new JTextField(); 
        txtIdMaterial.setBounds(180, 100, 180, 30); 
        add(txtIdMaterial);

        JButton btnPrestar = new JButton("Prestar");
        btnPrestar.setBounds(80, 180, 120, 40);
        btnPrestar.setBackground(new Color(23, 162, 184));
        btnPrestar.setForeground(Color.WHITE);
        add(btnPrestar);

        JButton btnDevolver = new JButton("Devolver");
        btnDevolver.setBounds(230, 180, 120, 40);
        btnDevolver.setBackground(Color.ORANGE);
        btnDevolver.setForeground(Color.WHITE);
        add(btnDevolver);

        // --- NUEVO BOTÓN: REPORTE POLIMÓRFICO ---
        JButton btnReporte = new JButton("Ver Reporte");
        btnReporte.setBounds(80, 250, 270, 40);
        btnReporte.setBackground(new Color(108, 117, 125)); // Gris oscuro
        btnReporte.setForeground(Color.WHITE);
        add(btnReporte);

        // --- Evento Prestar ---
        btnPrestar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String carnet = txtCarnet.getText().trim();
                String id = txtIdMaterial.getText().trim();

                if (carnet.isEmpty() || id.isEmpty()) {
                    JOptionPane.showMessageDialog(OperacionesDialog.this, "Por favor, llene ambos campos.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String res = service.borrowMaterial(carnet, id);
                
                if (res.startsWith("Error")) {
                    JOptionPane.showMessageDialog(OperacionesDialog.this, res, "Error de Préstamo", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(OperacionesDialog.this, res, "Préstamo Exitoso", JOptionPane.INFORMATION_MESSAGE);
                    service.saveAllData();
                    txtCarnet.setText("");
                    txtIdMaterial.setText("");
                }
            }
        });

        // --- Evento Devolver ---
        btnDevolver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String carnet = txtCarnet.getText().trim();
                String id = txtIdMaterial.getText().trim();

                if (carnet.isEmpty() || id.isEmpty()) {
                    JOptionPane.showMessageDialog(OperacionesDialog.this, "Por favor, llene ambos campos.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                String res = service.returnMaterial(carnet, id);
                
                if (res.startsWith("Error")) {
                    JOptionPane.showMessageDialog(OperacionesDialog.this, res, "Error de Devolución", JOptionPane.ERROR_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(OperacionesDialog.this, res, "Devolución Exitosa", JOptionPane.INFORMATION_MESSAGE);
                    txtCarnet.setText("");
                    txtIdMaterial.setText("");
                }
            }
        });

        // --- Evento del Nuevo Botón Reporte ---
        btnReporte.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtenemos el reporte en texto estructurado desde tu servicio
                String reporteText = service.getLoanRulesReport();
                
                // Creamos un área de texto con fuente monoespaciada para que las columnas salgan rectas
                JTextArea textArea = new JTextArea(reporteText);
                textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
                textArea.setEditable(false);
                
                JScrollPane scrollPane = new JScrollPane(textArea);
                scrollPane.setPreferredSize(new java.awt.Dimension(500, 300));
                
                // Mostramos el reporte completo en un cuadro emergente
                JOptionPane.showMessageDialog(OperacionesDialog.this, scrollPane, "Reporte Polimórfico - Reglas de Negocio", JOptionPane.PLAIN_MESSAGE);
            }
        });
        
     // Botón Salir para la ventana de Préstamos
        JButton btnSalirPrestamos = new JButton("Salir");
        btnSalirPrestamos.setBounds(120, 310, 180, 40); 
        btnSalirPrestamos.setBackground(new Color(220, 53, 69)); 
        btnSalirPrestamos.setForeground(Color.WHITE);
        btnSalirPrestamos.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        btnSalirPrestamos.setFocusPainted(false);

        btnSalirPrestamos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); 
            }
        });

        add(btnSalirPrestamos);
    }
}