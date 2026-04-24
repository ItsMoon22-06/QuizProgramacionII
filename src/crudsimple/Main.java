package crudsimple;

import vista.Login;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/**
 * Clase Main - Punto de entrada del sistema CRUD MVC+DAO.
 *
 * Estructura del proyecto:
 * ┌──────────────────────────────────────────────────────────┐
 * │                   PATRÓN MVC + DAO                       │
 * ├───────────┬──────────────────────┬───────────────────────┤
 * │  MODELO   │     CONTROLADOR      │        VISTA          │
 * ├───────────┼──────────────────────┼───────────────────────┤
 * │ Cliente   │ ClienteController    │ Login                 │
 * │ ClienteDAO│ LoginController      │ Tabla                 │
 * │ Usuario   │                      │ CrearCliente          │
 * │ UsuarioDAO│                      │ ActualizarCliente     │
 * │ ConexionBD│                      │                       │
 * └───────────┴──────────────────────┴───────────────────────┘
 *
 * Base de datos: Oracle XE  (ojdbc17.jar requerido en classpath)
 * Tablas necesarias:
 *   - usuario  (IDUSUARIO, correousuario, contrasena)
 *   - cliente  (CEDULA, NOMBRE, APELLIDO, CORREOELECTRONICO, TELEFONO, IDUSUARIO)
 */
public class Main {

    public static void main(String[] args) {
        // Aplicar Look & Feel del sistema operativo
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("⚠️  No se pudo aplicar el Look & Feel del sistema: " + e.getMessage());
        }

        // Lanzar la interfaz gráfica en el hilo de eventos de Swing (EDT)
        SwingUtilities.invokeLater(() -> {
            Login login = new Login();
            login.setVisible(true);
        });
    }
}
