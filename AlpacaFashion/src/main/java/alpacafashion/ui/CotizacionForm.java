package alpacafashion.ui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class CotizacionForm extends JFrame {
    public CotizacionForm() {
        setTitle("AlpacaFashion - Catálogo");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Si se cierra aquí, se cierra todo el programa
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- PANEL SUPERIOR CON BOTÓN VOLVER ---
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(45, 52, 54));

        JButton btnVolver = new JButton("⬅ Volver al Menú");
        btnVolver.setBackground(new Color(99, 110, 114));
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFocusPainted(false);

        // LÓGICA PARA REGRESAR
        btnVolver.addActionListener(e -> {
            new MainMenuForm().setVisible(true); // Reabre el menú principal
            this.dispose();                      // Cierra la ventana actual de Catálogo
        });

        topPanel.add(btnVolver);
        add(topPanel, BorderLayout.NORTH);

        // --- CONTENIDO DEL MÓDULO ---
        JLabel lblTemp = new JLabel("ÁREA DE TRABAJO: COTIZACIÓN", SwingConstants.CENTER);
        lblTemp.setFont(new Font("Segoe UI", Font.BOLD, 20));
        add(lblTemp, BorderLayout.CENTER);
    }
}