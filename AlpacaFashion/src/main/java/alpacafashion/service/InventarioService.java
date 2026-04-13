package alpacafashion.service;

import alpacafashion.data.DatabaseConfig;
import alpacafashion.model.Producto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventarioService {

    // NUEVO: Método para obtener y filtrar productos (Lógica de Negocio)
    public List<Producto> obtenerProductosFiltrados(String texto, String sede) {
        List<Producto> lista = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM inventario WHERE (sku LIKE ? OR descripcion LIKE ?)");

        if (!sede.equals("Todos")) {
            sql.append(" AND almacen = ?");
        }

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql.toString())) {

            String busqueda = "%" + texto + "%";
            pstmt.setString(1, busqueda);
            pstmt.setString(2, busqueda);

            if (!sede.equals("Todos")) {
                pstmt.setString(3, sede);
            }

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                lista.add(new Producto(
                        rs.getString("sku"),
                        rs.getString("descripcion"),
                        rs.getInt("stock"),
                        rs.getString("almacen"),
                        rs.getDouble("precio")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
    }

    // Lógica para ELIMINAR
    public boolean eliminarProducto(String sku) {
        String sql = "DELETE FROM inventario WHERE sku = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, sku);
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lógica para AÑADIR STOCK
    public boolean añadirStock(String sku, int cantidad) {
        String sql = "UPDATE inventario SET stock = stock + ? WHERE sku = ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, cantidad);
            pstmt.setString(2, sku);
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Lógica para REGISTRAR SALIDA (Con validación de stock)
    public boolean registrarSalida(String sku, int cantidad) {
        String sql = "UPDATE inventario SET stock = stock - ? WHERE sku = ? AND stock >= ?";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, cantidad);
            pstmt.setString(2, sku);
            pstmt.setInt(3, cantidad);
            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}