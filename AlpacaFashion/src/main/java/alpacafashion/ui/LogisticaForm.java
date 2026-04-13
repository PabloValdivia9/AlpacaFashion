package alpacafashion.ui;

import javax.swing.*;
import java.awt.*;

public class LogisticaForm extends JFrame {

    public LogisticaForm() {
        setTitle("AlpacaFashion - Logística");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- PANEL SUPERIOR CON BOTÓN VOLVER ---
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(45, 52, 54));

        JButton btnVolver = new JButton("⬅ Volver al Menú");

        // --- FIX VISUAL PARA EL BOTÓN (ESTILO DASHBOARD) ---
        btnVolver.setOpaque(true);
        btnVolver.setBorderPainted(false);
        btnVolver.setBackground(new Color(99, 110, 114)); // Gris medio
        btnVolver.setForeground(Color.WHITE);
        btnVolver.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnVolver.setFocusPainted(false);
        btnVolver.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // LÓGICA PARA REGRESAR
        btnVolver.addActionListener(e -> {
            new MainMenuForm().setVisible(true);
            this.dispose();
        });

        topPanel.add(btnVolver);
        add(topPanel, BorderLayout.NORTH);

        // --- CONTENIDO DEL MÓDULO ---
        JLabel lblTemp = new JLabel("ÁREA DE TRABAJO: LOGÍSTICA", SwingConstants.CENTER);
        lblTemp.setFont(new Font("Segoe UI", Font.BOLD, 20));
        add(lblTemp, BorderLayout.CENTER);
    }
}