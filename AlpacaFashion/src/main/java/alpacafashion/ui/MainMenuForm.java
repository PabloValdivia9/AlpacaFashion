package alpacafashion.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenuForm extends JFrame {

    // Colores consistentes con el diseño original
    private final Color primaryColor = new Color(45, 52, 54);    // Gris oscuro
    private final Color secondaryColor = new Color(236, 240, 241); // Gris claro
    private final Color accentColor = new Color(99, 110, 114);    // Gris medio (Hover)
    private final Color logoutColor = new Color(192, 57, 43);    // Rojo para salir

    public MainMenuForm() {
        setTitle("AlpacaFashion - Sistema de Gestión");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- SIDEBAR (Barra Lateral Izquierda) ---
        JPanel sidebar = new JPanel();
        sidebar.setBackground(primaryColor);
        sidebar.setPreferredSize(new Dimension(250, 700));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(20, 10, 20, 10));

        // Título de la Marca
        JLabel lblBrand = new JLabel("ALPACA FASHION");
        lblBrand.setForeground(Color.WHITE);
        lblBrand.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblBrand.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(lblBrand);
        sidebar.add(Box.createRigidArea(new Dimension(0, 40)));

        // Botones del Menú Lateral
        sidebar.add(crearBotonMenu(" Catálogo", "📦", e -> abrirModulo(new CatalogoForm())));
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(crearBotonMenu(" Cotización", "💰", e -> abrirModulo(new CotizacionForm())));
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(crearBotonMenu(" Inventario", "📊", e -> abrirModulo(new InventarioForm())));
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(crearBotonMenu(" Logística", "🚚", e -> abrirModulo(new LogisticaForm())));

        sidebar.add(Box.createVerticalGlue()); // Espacio flexible

        // --- BOTÓN CERRAR SESIÓN (CORREGIDO) ---
        JButton btnLogout = new JButton("Cerrar Sesión");
        btnLogout.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogout.setMaximumSize(new Dimension(200, 40));

        // Ajustes para ignorar el estilo nativo de Windows y recuperar el color
        btnLogout.setOpaque(true);
        btnLogout.setBorderPainted(false);
        btnLogout.setBackground(logoutColor);
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnLogout.setFocusPainted(false);
        btnLogout.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnLogout.addActionListener(e -> {
            new LoginForm().setVisible(true);
            this.dispose();
        });
        sidebar.add(btnLogout);

        add(sidebar, BorderLayout.WEST);

        // --- PANEL DE BIENVENIDA ---
        JPanel mainContent = new JPanel(new GridBagLayout());
        mainContent.setBackground(Color.WHITE);

        JLabel lblWelcome = new JLabel("<html><div style='text-align: center;'>"
                + "<h1>Panel de Control AlpacaFashion</h1>"
                + "<p style='font-size: 14pt; color: #636e72;'>Seleccione una opción en el menú lateral para gestionar el negocio.</p>"
                + "</div></html>");
        mainContent.add(lblWelcome);

        add(mainContent, BorderLayout.CENTER);
    }

    /**
     * Crea botones para el menú con el estilo corregido
     */
    private JButton crearBotonMenu(String texto, String icono, java.awt.event.ActionListener accion) {
        JButton btn = new JButton(icono + texto);
        btn.setMaximumSize(new Dimension(230, 50));
        btn.setPreferredSize(new Dimension(230, 50));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Estilo visual forzado
        btn.setOpaque(true);
        btn.setBorderPainted(false);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btn.setBackground(primaryColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto Hover
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btn.setBackground(accentColor);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                btn.setBackground(primaryColor);
            }
        });

        btn.addActionListener(accion);
        return btn;
    }

    private void abrirModulo(JFrame modulo) {
        modulo.setVisible(true);
        this.dispose();
    }
}