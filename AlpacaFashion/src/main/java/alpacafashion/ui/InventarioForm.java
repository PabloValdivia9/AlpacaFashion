package alpacafashion.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class InventarioForm extends JFrame {
    public InventarioForm() {
        setTitle("AlpacaFashion - Gestión de Inventario");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- PANEL SUPERIOR ---
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(45, 52, 54));
        JButton btnVolver = new JButton("⬅ Volver al Menú");
        btnVolver.setBackground(new Color(99, 110, 114));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.addActionListener(e -> { new MainMenuForm().setVisible(true); this.dispose(); });
        topPanel.add(btnVolver);
        add(topPanel, BorderLayout.NORTH);

        // --- CONTENIDO ---
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Filtros (UX)
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.add(new JLabel("Almacén:"));
        filterPanel.add(new JComboBox<>(new String[]{"Principal - Lima", "Cusco", "Arequipa"}));
        filterPanel.add(new JCheckBox("Ver solo stock bajo"));

        // Tabla de Inventario
        String[] col = {"SKU", "Descripción", "Stock Actual", "Estado"};
        Object[][] data = {{"ALP-001", "Chompa Classic", "50", "OK"}, {"ALP-002", "Bufanda Premium", "5", "CRÍTICO"}};
        mainPanel.add(filterPanel, BorderLayout.NORTH);
        mainPanel.add(new JScrollPane(new JTable(new DefaultTableModel(data, col))), BorderLayout.CENTER);

        add(mainPanel, BorderLayout.CENTER);
    }
}