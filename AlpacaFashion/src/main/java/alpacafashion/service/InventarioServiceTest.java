package alpacafashion.service;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import alpacafashion.data.DatabaseConfig;

public class InventarioServiceTest {

    private InventarioService service;

    @BeforeEach
    void setUp() {
        service = new InventarioService();
        // Inicializamos la base de datos antes de cada prueba para asegurar que exista la tabla
        DatabaseConfig.inicializarBaseDeDatos();
    }

    @Test
    void testAñadirStock() {
        // Probamos con el primer item de nuestra plantilla
        boolean resultado = service.añadirStock("ALP-001", 10);
        assertTrue(resultado, "El stock debería haberse actualizado correctamente.");
    }

    @Test
    void testRegistrarSalidaExitosa() {
        // Intentamos sacar 1 unidad (asumiendo que hay stock)
        boolean resultado = service.registrarSalida("ALP-001", 1);
        assertTrue(resultado, "La salida debería ser exitosa si hay stock suficiente.");
    }

    @Test
    void testRegistrarSalidaSinStockSuficiente() {
        // Intentamos sacar una cantidad exagerada para forzar el fallo de la validación SQL
        boolean resultado = service.registrarSalida("ALP-001", 999999);
        assertFalse(resultado, "No debería permitir la salida si el stock queda en negativo.");
    }

    @Test
    void testEliminarProductoInexistente() {
        // Intentamos eliminar un SKU que no existe
        boolean resultado = service.eliminarProducto("SKU-FALSO-999");
        assertFalse(resultado, "No debería confirmar eliminación de algo que no existe.");
    }
}