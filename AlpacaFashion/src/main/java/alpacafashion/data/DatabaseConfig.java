package alpacafashion.data;

import java.sql.*;
import java.util.Random;

public class DatabaseConfig {
    private static final String URL = "jdbc:sqlite:alpacafashion.db";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL);
    }

    public static void inicializarBaseDeDatos() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            // 1. Crear la tabla de inventario si no existe
            String sql = "CREATE TABLE IF NOT EXISTS inventario (" +
                    "sku TEXT PRIMARY KEY, " +
                    "descripcion TEXT, " +
                    "stock INTEGER, " +
                    "almacen TEXT, " +
                    "precio REAL)";
            stmt.execute(sql);

            // 2. Verificar si la tabla ya tiene datos para no duplicarlos cada vez que inicies
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM inventario");
            if (rs.next() && rs.getInt(1) == 0) {
                System.out.println("Base de datos vacía. Generando 100 items de plantilla...");
                poblarDatosIniciales(conn);
            }

        } catch (Exception e) {
            System.err.println("Error al inicializar la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void poblarDatosIniciales(Connection conn) throws SQLException {
        String sqlInsert = "INSERT INTO inventario (sku, descripcion, stock, almacen, precio) VALUES (?, ?, ?, ?, ?)";

        // Listas para generar combinaciones aleatorias
        String[] tipos = {"Chompa", "Poncho", "Bufanda", "Guantes", "Chullo", "Abrigo", "Chaleco", "Medias"};
        String[] materiales = {"Alpaca Baby", "Alpaca Premium", "Mezcla de Alpaca", "Lana de Alpaca Real"};
        String[] diseños = {"Classic", "Ethnic", "Modern", "Minimalist", "Andino Premium"};
        String[] almacenes = {"Lima - Central", "Cusco - Almacén", "Arequipa - Planta"};

        Random random = new Random();

        try (PreparedStatement pstmt = conn.prepareStatement(sqlInsert)) {
            conn.setAutoCommit(false); // Desactivar auto-commit para insertar los 100 items rápido

            for (int i = 1; i <= 100; i++) {
                // Generar datos aleatorios
                String sku = "ALP-" + String.format("%03d", i);
                String descripcion = tipos[random.nextInt(tipos.length)] + " " +
                        diseños[random.nextInt(diseños.length)] + " (" +
                        materiales[random.nextInt(materiales.length)] + ")";
                int stock = random.nextInt(100) + 1; // Stock entre 1 y 100
                String almacen = almacenes[random.nextInt(almacenes.length)];
                double precio = 40 + (250 - 40) * random.nextDouble(); // Precio entre 40 y 250 soles

                // Configurar el PreparedStatement
                pstmt.setString(1, sku);
                pstmt.setString(2, descripcion);
                pstmt.setInt(3, stock);
                pstmt.setString(4, almacen);
                pstmt.setDouble(5, Math.round(precio * 100.0) / 100.0); // Redondear a 2 decimales

                pstmt.addBatch(); // Añadir a la lista de ejecución por lotes
            }

            pstmt.executeBatch(); // Ejecutar las 100 inserciones de un solo golpe
            conn.commit();        // Guardar cambios
            System.out.println("¡Éxito! 100 productos añadidos al inventario.");
        } catch (SQLException e) {
            conn.rollback(); // Si algo falla, deshacer todo
            throw e;
        }
    }
}