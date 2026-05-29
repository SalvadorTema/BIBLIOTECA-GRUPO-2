package com.library.view;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.library.service.LibraryService;
import com.library.model.Book;

public class CatalogoDialog extends JDialog {
    private static final long serialVersionUID = 1L;
    
 
    private JTextField txtId, txtTitle, txtAuthor;
    private JTable table;
    private DefaultTableModel tableModel;

    
    public CatalogoDialog(JFrame parent, final LibraryService service) {
        super(parent, "Catálogo de Materiales", true);
        
        setSize(800, 600);
        setLocationRelativeTo(parent);
        setLayout(null);

        // Campos del Formulario
        JLabel lblId = new JLabel("ID del Libro:"); lblId.setBounds(50, 100, 100, 30); add(lblId);
        txtId = new JTextField(); txtId.setBounds(160, 100, 150, 30); add(txtId);

        JLabel lblTitle = new JLabel("Título:"); lblTitle.setBounds(50, 150, 100, 30); add(lblTitle);
        txtTitle = new JTextField(); txtTitle.setBounds(160, 150, 250, 30); add(txtTitle);

        JLabel lblAuthor = new JLabel("Autor:"); lblAuthor.setBounds(50, 200, 100, 30); add(lblAuthor);
        txtAuthor = new JTextField(); txtAuthor.setBounds(160, 200, 250, 30); add(txtAuthor);

        JButton btnSave = new JButton("Guardar Libro");
        btnSave.setBounds(160, 260, 150, 40);
        btnSave.setBackground(new Color(0, 123, 255));
        btnSave.setForeground(Color.WHITE);
        add(btnSave);

        // Tabla
        String[] columnNames = {"ID", "Título", "Autor"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(450, 100, 300, 400);
        add(scrollPane);

        // Cargar datos existentes del CSV
        for (com.library.model.AbstractMaterial mat : service.getInventory()) {
            String autor = (mat instanceof Book) ? ((Book) mat).getAuthor() : "Revista";
            tableModel.addRow(new Object[]{mat.getId(), mat.getTitle(), autor});
        }

        // Acción de Guardar
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String id = txtId.getText();
                String title = txtTitle.getText();
                String author = txtAuthor.getText();
                
                // 3. Al usar directamente el parámetro 'final' del constructor, el botón responde perfecto
                service.addBook(id, title, author); 
                tableModel.addRow(new Object[]{id, title, author});
                
                txtId.setText(""); txtTitle.setText(""); txtAuthor.setText("");
            }
        });
        
     // Botón Salir para la ventana de Catálogo
        
        JButton btnSalirCatalogo = new JButton("Salir");
        btnSalirCatalogo.setBounds(160, 330, 140, 40); 
        btnSalirCatalogo.setBackground(new Color(220, 53, 69)); 
        btnSalirCatalogo.setForeground(Color.WHITE);
        btnSalirCatalogo.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));
        btnSalirCatalogo.setFocusPainted(false);

        btnSalirCatalogo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); 
            }
        });

        add(btnSalirCatalogo);
    }
}