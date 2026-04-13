package alpacafashion.ui;

import alpacafashion.data.DatabaseConfig;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class InventarioForm extends JFrame {
    private JTable tabla;
    private DefaultTableModel modelo;

    public InventarioForm() {
        setTitle("AlpacaFashion - Gestión de Inventario (SQL)");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- PANEL SUPERIOR (NAVEGACIÓN) ---
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(45, 52, 54));

        JButton btnVolver = new JButton("⬅ Volver al Menú");

        // --- FIX VISUAL BOTÓN VOLVER ---
        btnVolver.setOpaque(true);
        btnVolver.setBorderPainted(false);
        btnVolver.setBackground(new Color(99, 110, 114));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnVolver.setFocusPainted(false);
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnVolver.addActionListener(e -> {
            new MainMenuForm().setVisible(true);
            this.dispose();
        });
        topPanel.add(btnVolver);
        add(topPanel, BorderLayout.NORTH);

        // --- CONTENIDO PRINCIPAL ---
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 1. Filtros y Búsqueda
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        filterPanel.add(new JLabel("Almacén:"));
        filterPanel.add(new JComboBox<>(new String[]{"Todos", "Principal - Lima", "Cusco", "Arequipa"}));

        JCheckBox chkBajoStock = new JCheckBox("Ver solo stock bajo (< 10)");
        filterPanel.add(chkBajoStock);

        JButton btnRefrescar = new JButton("🔄 Refrescar Datos");
        btnRefrescar.addActionListener(e -> cargarDatos());
        filterPanel.add(btnRefrescar);

        mainPanel.add(filterPanel, BorderLayout.NORTH);

        // 2. Tabla de Inventario (SQL)
        String[] columnas = {"SKU", "Descripción", "Stock Actual", "Almacén", "Precio"};
        modelo = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tabla = new JTable(modelo);
        tabla.setRowHeight(30); // Un poco más de altura para legibilidad
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        JScrollPane scrollPane = new JScrollPane(tabla);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // 3. Panel de Acciones Lateral
        JPanel sideActions = new JPanel(new GridLayout(4, 1, 0, 10));
        sideActions.setPreferredSize(new Dimension(180, 0));

        // Botones laterales con estilo consistente
        JButton btnAgregar = crearBotonLateral("Añadir Stock");
        JButton btnSalida = crearBotonLateral("Registrar Salida");
        JButton btnEliminar = crearBotonLateral("Eliminar Item");

        sideActions.add(btnAgregar);
        sideActions.add(btnSalida);
        sideActions.add(btnEliminar);

        mainPanel.add(sideActions, BorderLayout.EAST);
        add(mainPanel, BorderLayout.CENTER);

        cargarDatos();
    }

    // Método auxiliar para que los botones laterales también se vean bien
    private JButton crearBotonLateral(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public void cargarDatos() {
        modelo.setRowCount(0);
        String query = "SELECT * FROM inventario";

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Object[] fila = {
                        rs.getString("sku"),
                        rs.getString("descripcion"),
                        rs.getInt("stock"),
                        rs.getString("almacen"),
                        "S/. " + String.format("%.2f", rs.getDouble("precio"))
                };
                modelo.addRow(fila);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al conectar con la base de datos:\n" + e.getMessage(),
                    "Error SQL", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}