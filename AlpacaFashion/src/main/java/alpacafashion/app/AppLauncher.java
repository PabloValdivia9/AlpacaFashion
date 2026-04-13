package alpacafashion.app;

import alpacafashion.ui.LoginForm;
import javax.swing.SwingUtilities;

public class AppLauncher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Ahora iniciamos con el Login en lugar del Menú
            new LoginForm().setVisible(true);
        });
    }
}