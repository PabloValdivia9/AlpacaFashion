package alpacafashion.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

public class LoginForm extends JFrame {

    public LoginForm() {
        setTitle("AlpacaFashion - Iniciar Sesión");
        setSize(450, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Contenedor principal con fondo blanco
        JPanel container = new JPanel(null);
        container.setBackground(Color.WHITE);
        add(container);

        // --- LOGO DE ALTA CALIDAD ---
        JLabel lblLogo = new JLabel();
        lblLogo.setBounds(0, 30, 450, 150);
        lblLogo.setHorizontalAlignment(SwingConstants.CENTER);

        // Cambiado a .png que es el formato con transparencia que tienes
        URL imgUrl = getClass().getResource("/images/logos/logo_alpaca.jpg");

        if (imgUrl != null) {
            try {
                // Leemos la imagen original
                BufferedImage originalImage = ImageIO.read(imgUrl);

                // Tamaño objetivo
                int targetWidth = 150;
                int targetHeight = 150;

                // Creamos un lienzo nuevo con soporte para transparencia (ARGB)
                BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
                Graphics2D g2 = resizedImage.createGraphics();

                // CONFIGURACIÓN DE ALTA CALIDAD (Anti-aliasing)
                g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Dibujamos la imagen escalada
                g2.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
                g2.dispose();

                lblLogo.setIcon(new ImageIcon(resizedImage));

            } catch (IOException e) {
                System.err.println("No se pudo procesar la imagen: " + e.getMessage());
                lblLogo.setText("ALPACA FASHION");
            }
        } else {
            lblLogo.setText("LOGO NO ENCONTRADO");
        }
        container.add(lblLogo);

        // --- FORMULARIO ---
        Font labelFont = new Font("Segoe UI", Font.BOLD, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);
        Color darkColor = new Color(45, 52, 54);

        JLabel lblUser = new JLabel("Usuario");
        lblUser.setFont(labelFont);
        lblUser.setBounds(75, 200, 300, 20);
        container.add(lblUser);

        JTextField txtUser = new JTextField();
        txtUser.setBounds(75, 225, 300, 40);
        txtUser.setFont(fieldFont);
        container.add(txtUser);

        JLabel lblPass = new JLabel("Contraseña");
        lblPass.setFont(labelFont);
        lblPass.setBounds(75, 280, 300, 20);
        container.add(lblPass);

        JPasswordField txtPass = new JPasswordField();
        txtPass.setBounds(75, 305, 300, 40);
        container.add(txtPass);

        // --- BOTÓN ---
        JButton btnLogin = new JButton("INGRESAR");
        btnLogin.setBounds(75, 380, 300, 50);
        btnLogin.setBackground(darkColor);
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnLogin.setFocusPainted(false);
        btnLogin.setBorderPainted(false);
        container.add(btnLogin);

        // --- LÓGICA DE ACCESO ---
        btnLogin.addActionListener(e -> {
            String user = txtUser.getText();
            String pass = new String(txtPass.getPassword());

            // Credenciales temporales para el equipo
            if (user.equals("admin") && pass.equals("1234")) {
                new MainMenuForm().setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Credenciales incorrectas", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}