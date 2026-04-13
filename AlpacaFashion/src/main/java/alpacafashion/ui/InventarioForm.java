package alpacafashion.ui;

import alpacafashion.model.Producto;
import alpacafashion.service.InventarioService;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class InventarioForm extends JFrame {
    private JTable tabla;
    private DefaultTableModel modelo;
    private JTextField txtBuscar;
    private JComboBox<String> cbAlmacen;

    // El servicio maneja TODA la lógica y acceso a datos
    private InventarioService inventarioService = new InventarioService();

    public InventarioForm() {
        setTitle("AlpacaFashion - Gestión de Inventario (Arquitectura Pro)");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- PANEL SUPERIOR (NAVEGACIÓN) ---
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(45, 52, 54));
        JButton btnVolver = new JButton("⬅ Volver al Menú");
        btnVolver.setOpaque(true);
        btnVolver.setBorderPainted(false);
        btnVolver.setBackground(new Color(99, 110, 114));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnVolver.addActionListener(e -> { new MainMenuForm().setVisible(true); this.dispose(); });
        topPanel.add(btnVolver);
        add(topPanel, BorderLayout.NORTH);

        // --- CONTENIDO PRINCIPAL ---
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 1. Panel de Filtros
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        filterPanel.add(new JLabel("🔍 Buscar:"));
        txtBuscar = new JTextField(15);
        txtBuscar.addActionListener(e -> aplicarFiltros());
        filterPanel.add(txtBuscar);

        filterPanel.add(new JLabel("Almacén:"));
        String[] sedes = {"Todos", "Lima - Central", "Cusco - Almacén", "Arequipa - Planta"};
        cbAlmacen = new JComboBox<>(sedes);
        cbAlmacen.addActionListener(e -> aplicarFiltros());
        filterPanel.add(cbAlmacen);

        JButton btnLimpiar = new JButton("🔄 Limpiar");
        btnLimpiar.addActionListener(e -> {
            txtBuscar.setText("");
            cbAlmacen.setSelectedIndex(0);
            aplicarFiltros();
        });
        filterPanel.add(btnLimpiar);
        mainPanel.add(filterPanel, BorderLayout.NORTH);

        // 2. Tabla de Inventario
        String[] columnas = {"SKU", "Descripción", "Stock Actual", "Almacén", "Precio"};
        modelo = new DefaultTableModel(null, columnas) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tabla = new JTable(modelo);
        tabla.setRowHeight(30);
        tabla.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        JScrollPane scrollPane = new JScrollPane(tabla);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // 3. Panel de Acciones Lateral
        JPanel sideActions = new JPanel(new GridLayout(4, 1, 0, 10));
        sideActions.setPreferredSize(new Dimension(180, 0));

        JButton btnAgregar = crearBotonLateral("Añadir Stock");
        JButton btnSalida = crearBotonLateral("Registrar Salida");
        JButton btnEliminar = crearBotonLateral("Eliminar Item");

        // EVENTO AÑADIR
        btnAgregar.addActionListener(e -> {
            String sku = getSelectedSKU();
            if (sku != null) {
                String cantStr = JOptionPane.showInputDialog(this, "Cantidad a ingresar para " + sku + ":");
                if (cantStr != null) {
                    try {
                        int cantidad = Integer.parseInt(cantStr);
                        if (inventarioService.añadirStock(sku, cantidad)) {
                            aplicarFiltros();
                            JOptionPane.showMessageDialog(this, "Stock actualizado.");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Ingrese un número válido.");
                    }
                }
            }
        });

        // EVENTO SALIDA
        btnSalida.addActionListener(e -> {
            String sku = getSelectedSKU();
            if (sku != null) {
                String cantStr = JOptionPane.showInputDialog(this, "Cantidad a retirar de " + sku + ":");
                if (cantStr != null) {
                    try {
                        int cantidad = Integer.parseInt(cantStr);
                        if (inventarioService.registrarSalida(sku, cantidad)) {
                            aplicarFiltros();
                            JOptionPane.showMessageDialog(this, "Salida registrada.");
                        } else {
                            JOptionPane.showMessageDialog(this, "Error: Stock insuficiente o producto inexistente.");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Ingrese un número válido.");
                    }
                }
            }
        });

        // EVENTO ELIMINAR
        btnEliminar.addActionListener(e -> {
            String sku = getSelectedSKU();
            if (sku != null) {
                int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar " + sku + "?");
                if (confirm == JOptionPane.YES_OPTION) {
                    if (inventarioService.eliminarProducto(sku)) {
                        aplicarFiltros();
                        JOptionPane.showMessageDialog(this, "Producto eliminado.");
                    }
                }
            }
        });

        sideActions.add(btnAgregar);
        sideActions.add(btnSalida);
        sideActions.add(btnEliminar);
        mainPanel.add(sideActions, BorderLayout.EAST);

        add(mainPanel, BorderLayout.CENTER);
        aplicarFiltros();
    }

    /**
     * MÉTODO CLAVE: Ahora solo se comunica con el Service
     */
    public void aplicarFiltros() {
        modelo.setRowCount(0);
        String texto = txtBuscar.getText().trim();
        String sede = (String) cbAlmacen.getSelectedItem();

        // Obtenemos la lista de objetos Producto desde el servicio
        List<Producto> productos = inventarioService.obtenerProductosFiltrados(texto, sede);

        // Llenamos la tabla recorriendo la lista
        for (Producto p : productos) {
            modelo.addRow(new Object[]{
                    p.sku(),
                    p.descripcion(),
                    p.stock(),
                    p.almacen(),
                    "S/. " + String.format("%.2f", p.precio())
            });
        }
    }

    private String getSelectedSKU() {
        int fila = tabla.getSelectedRow();
        if (fila != -1) return (String) tabla.getValueAt(fila, 0);
        JOptionPane.showMessageDialog(this, "Seleccione un producto primero.");
        return null;
    }

    private JButton crearBotonLateral(String texto) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }

    public void cargarDatos() { aplicarFiltros(); }
}