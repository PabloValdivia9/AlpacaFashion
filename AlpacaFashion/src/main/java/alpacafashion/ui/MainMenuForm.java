package alpacafashion.ui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenuForm extends JFrame {

    // Definición de colores para consistencia
    private final Color primaryColor = new Color(45, 52, 54);    // Gris oscuro (el de tu botón Login)
    private final Color secondaryColor = new Color(236, 240, 241); // Gris muy claro
    private final Color accentColor = new Color(99, 110, 114);    // Gris medio para hover

    public MainMenuForm() {
        setTitle("AlpacaFashion - Sistema de Gestión");
        setSize(1000, 700); // Un poco más grande para el estilo Dashboard
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- SIDEBAR (Barra Lateral Izquierda) ---
        JPanel sidebar = new JPanel();
        sidebar.setBackground(primaryColor);
        sidebar.setPreferredSize(new Dimension(250, 700));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));
        sidebar.setBorder(new EmptyBorder(20, 10, 20, 10));

        // Título o Logo en el sidebar
        JLabel lblBrand = new JLabel("ALPACA FASHION");
        lblBrand.setForeground(Color.WHITE);
        lblBrand.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblBrand.setAlignmentX(Component.CENTER_ALIGNMENT);
        sidebar.add(lblBrand);
        sidebar.add(Box.createRigidArea(new Dimension(0, 40))); // Espaciador

        // Botones del Sidebar
        sidebar.add(crearBotonMenu(" Catálogo", "📦", e -> abrirModulo(new CatalogoForm())));
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(crearBotonMenu(" Cotización", "💰", e -> abrirModulo(new CotizacionForm())));
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(crearBotonMenu(" Inventario", "📊", e -> abrirModulo(new InventarioForm())));
        sidebar.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebar.add(crearBotonMenu(" Logística", "🚚", e -> abrirModulo(new LogisticaForm())));

        sidebar.add(Box.createVerticalGlue()); // Empuja el botón salir hacia abajo

        // Botón Salir
        JButton btnLogout = new JButton("Cerrar Sesión");
        btnLogout.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnLogout.setBackground(new Color(192, 57, 43)); // Rojo suave
        btnLogout.setForeground(Color.WHITE);
        btnLogout.setFocusPainted(false);
        btnLogout.addActionListener(e -> {
            new LoginForm().setVisible(true);
            this.dispose();
        });
        sidebar.add(btnLogout);

        add(sidebar, BorderLayout.WEST);

        // --- PANEL PRINCIPAL (Área de Bienvenida) ---
        JPanel mainContent = new JPanel(new GridBagLayout());
        mainContent.setBackground(Color.WHITE);

        JLabel lblWelcome = new JLabel("<html><div style='text-align: center;'>"
                + "<h1>Bienvenido al Sistema</h1>"
                + "<p style='font-size: 14pt;'>Seleccione un módulo en la barra lateral para comenzar.</p>"
                + "</div></html>");
        lblWelcome.setForeground(primaryColor);
        mainContent.add(lblWelcome);

        add(mainContent, BorderLayout.CENTER);
    }

    /**
     * Crea un botón estilizado para el menú lateral con efecto Hover
     */
    private JButton crearBotonMenu(String texto, String icono, java.awt.event.ActionListener accion) {
        JButton btn = new JButton(icono + texto);
        btn.setMaximumSize(new Dimension(230, 50));
        btn.setPreferredSize(new Dimension(230, 50));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Estilo visual
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        btn.setBackground(primaryColor);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setHorizontalAlignment(SwingConstants.LEFT);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto Hover (cambio de color al pasar el mouse)
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