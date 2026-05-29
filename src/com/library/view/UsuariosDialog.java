package com.library.view;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable; 
import javax.swing.JScrollPane; 
import javax.swing.table.DefaultTableModel; 
import javax.swing.JOptionPane;
import com.library.service.LibraryService;
import com.library.model.User; 
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class UsuariosDialog extends JDialog {
    private static final long serialVersionUID = 1L;
    
    private JTextField txtCarnet, txtName;
    private JTable table; 
    private DefaultTableModel tableModel; 

    public UsuariosDialog(JFrame parent, final LibraryService service) {
        super(parent, "Gestión de Usuarios", true);

       
        setSize(850, 420);
        setLocationRelativeTo(parent);
        setLayout(null);

        // Formulario (Lado Izquierdo)
        JLabel lblCarnet = new JLabel("Carné:"); 
        lblCarnet.setBounds(30, 50, 100, 30); 
        add(lblCarnet);
        
        txtCarnet = new JTextField(); 
        txtCarnet.setBounds(130, 50, 180, 30); 
        add(txtCarnet);

        JLabel lblName = new JLabel("Nombre:"); 
        lblName.setBounds(30, 100, 100, 30); 
        add(lblName);
        
        txtName = new JTextField(); 
        txtName.setBounds(130, 100, 180, 30); 
        add(txtName);

        JButton btnSave = new JButton("Registrar Usuario");
        btnSave.setBounds(130, 180, 160, 40);
        btnSave.setBackground(new Color(40, 167, 69));
        btnSave.setForeground(Color.WHITE);
        add(btnSave);

        // --- COMPONENTE DE TABLA (Lado Derecho) ---
        String[] columnNames = {"Carné", "Nombre", "Préstamos Activos"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(360, 40, 340, 220); // Posicionada a la derecha
        add(scrollPane);

        // Cargar los usuarios que ya existen en el CSV al abrir la ventana
        for (User user : service.getUsers()) {
            tableModel.addRow(new Object[]{user.getCarnet(), user.getName(), user.getActiveLoans().size()});
        }

        // --- Acción del Botón Guardar ---
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String carnet = txtCarnet.getText().trim();
                String nombre = txtName.getText().trim();
                
                if (carnet.isEmpty() || nombre.isEmpty()) {
                    JOptionPane.showMessageDialog(UsuariosDialog.this, "Por favor, llene todos los campos.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                String resultado = service.registerNewUser(carnet, nombre);
                
                if (resultado.startsWith("Error")) {
                    JOptionPane.showMessageDialog(UsuariosDialog.this, resultado, "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Si el servicio lo guarda con éxito, lo agregamos de golpe a la tabla visual
                    tableModel.addRow(new Object[]{carnet, nombre, 0});
                    JOptionPane.showMessageDialog(UsuariosDialog.this, resultado, "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    
                    txtCarnet.setText(""); 
                    txtName.setText("");
                }
            }
        });
     // Botón Salir para la ventana de Usuarios
        JButton btnSalirUsuarios = new JButton("Salir");
     // Cambia los números del setBounds para ponerlo a la derecha del botón verde
        btnSalirUsuarios.setBounds(130, 260, 160, 40);// Ajusta la posición si se encima con otro componente
        btnSalirUsuarios.setBackground(new Color(220, 53, 69)); // Rojo elegante
        btnSalirUsuarios.setForeground(Color.WHITE);
        btnSalirUsuarios.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        btnSalirUsuarios.setFocusPainted(false);

        btnSalirUsuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Cierra todo el sistema limpiamente
            }
        });

        add(btnSalirUsuarios);
    }
}