package com.library.view;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import com.library.service.LibraryService;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import com.library.model.Book;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JScrollPane;
import java.awt.Color;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	
    private LibraryService service;
    private JTextField txtId;
    private JTextField txtTitle;
    private JTextField txtAuthor;
    private JButton btnSave;
    private JTable table;
    private DefaultTableModel tableModel;
    
    public MainFrame() {
    	
        // Inicializar el servicio Carga automáticamente los CSV
        this.service = new LibraryService();
        
        // Ventana principal
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
    
 // 1. Formulario: ID del Libro
    JLabel lblId = new JLabel("ID del Libro:");
    lblId.setBounds(50, 100, 100, 30);
    add(lblId);
    
    txtId = new JTextField();
    txtId.setBounds(160, 100, 150, 30);
    add(txtId);
    
    // 2. Formulario: Título del Libro
    JLabel lblTitleBook = new JLabel("Título:");
    lblTitleBook.setBounds(50, 150, 100, 30);
    add(lblTitleBook);
    
    txtTitle = new JTextField();
    txtTitle.setBounds(160, 150, 250, 30);
    add(txtTitle);
    
    // 3. Formulario: Autor del Libro
    JLabel lblAuthorBook = new JLabel("Autor:");
    lblAuthorBook.setBounds(50, 200, 100, 30);
    add(lblAuthorBook);
    
    txtAuthor = new JTextField();
    txtAuthor.setBounds(160, 200, 250, 30);
    add(txtAuthor);
    
    // 4. Botón para Guardar
    btnSave = new JButton("Guardar Libro");
    btnSave.setBounds(160, 260, 150, 40);
    btnSave.setBackground(new Color(0, 123, 255));
    btnSave.setForeground(Color.WHITE);
    btnSave.setFocusPainted(false);
    add(btnSave);
    
    // 5. Acción del Botón Guardar en el CSV
    
    btnSave.addActionListener(new ActionListener() {
        @Override
        
        public void actionPerformed(ActionEvent e) {
            String id = txtId.getText();
            String title = txtTitle.getText();
            String author = txtAuthor.getText();
            
            Book nuevoLibro = new Book(id, title, author);
            
            service.addMaterial(nuevoLibro);
            service.saveAllData();
            
            System.out.println("¡Libro guardado con éxito!: " + title);
            tableModel.addRow(new Object[]{id, title, author});
            txtId.setText("");
            txtTitle.setText("");
            txtAuthor.setText("");
        }
    });
    
 // CONFIGURACIÓN DE LA TABLA LADO DERECHO
    
    // 1. Definimos  los nombres de las columnas
    String[] columnNames = {"ID", "Título", "Autor"};
    
    // 2. Creamos  el modelo de datos empezando con 0 filas
    tableModel = new DefaultTableModel(columnNames, 0);
    table = new JTable(tableModel);
    table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
    
    // 3. sele coloca a la tabla en un panel con barras de desplazamiento Scroll
    JScrollPane scrollPane = new JScrollPane(table);
    // Posición a la derecha: X=450, Y=100, Ancho=300, Alto=400
    scrollPane.setBounds(450, 100, 300, 400);
    add(scrollPane);
    
 // 4. Carga los materiales que ya existan en el CSV al abrir la ventana
    for (com.library.model.AbstractMaterial mat : service.getInventory()) {
        String autor = "";
        
        // Si el material es un Libro Book, recuperamos su autor
        if (mat instanceof com.library.model.Book) {
            autor = ((com.library.model.Book) mat).getAuthor();
        } else if (mat instanceof com.library.model.Magazine) {
            autor = "Revista";
        }
     // Ajusta el tamaño automático después de llenar la tabla con el CSV
        ajustarColumnasAutomatico(table);
        
        // Agregamos la fila usando los métodos de la clase abstracta
        tableModel.addRow(new Object[]{mat.getId(), mat.getTitle(), autor});
    }
     }
    public void ajustarColumnasAutomatico(javax.swing.JTable tabla) {
        for (int columna = 0; columna < tabla.getColumnCount(); columna++) {
            javax.swing.table.TableColumn tableColumn = tabla.getColumnModel().getColumn(columna);
            int anchoMaximo = 50; // Tamaño mínimo por defecto para que no quede invisible

            // Revisa el ancho del título de la columna
            javax.swing.table.TableCellRenderer headerRenderer = tabla.getTableHeader().getDefaultRenderer();
            java.awt.Component compHeader = headerRenderer.getTableCellRendererComponent(tabla, tableColumn.getHeaderValue(), false, false, 0, columna);
            anchoMaximo = Math.max(compHeader.getPreferredSize().width + 15, anchoMaximo);

            // Revisa el ancho de cada celda con datos
            for (int fila = 0; fila < tabla.getRowCount(); fila++) {
                javax.swing.table.TableCellRenderer cellRenderer = tabla.getCellRenderer(fila, columna);
                java.awt.Component comp = tabla.prepareRenderer(cellRenderer, fila, columna);
                anchoMaximo = Math.max(comp.getPreferredSize().width + 15, anchoMaximo);
            }
            
            // Aplica el ancho calculado a la columna
            tableColumn.setPreferredWidth(anchoMaximo);
        }
    }
}