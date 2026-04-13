package alpacafashion.app;

import alpacafashion.data.DatabaseConfig;
import alpacafashion.ui.LoginForm;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class AppLauncher {
    public static void main(String[] args) {

        // 1. Inicializar la Base de Datos SQL antes de mostrar la interfaz
        try {
            System.out.println("Iniciando base de datos AlpacaFashion...");
            DatabaseConfig.inicializarBaseDeDatos();
        } catch (Exception e) {
            System.err.println("Error crítico al iniciar la base de datos: " + e.getMessage());
            e.printStackTrace();
        }

        // 2. Configurar el LookAndFeel (Opcional, hace que Swing se vea más nativo)
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            // Si falla, usará el estilo clásico de Java por defecto
        }

        // 3. Lanzar la interfaz gráfica en el hilo de despacho de eventos
        SwingUtilities.invokeLater(() -> {
            LoginForm login = new LoginForm();
            login.setVisible(true);
        });
    }
}