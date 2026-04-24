package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Clase UsuarioDAO - Patrón DAO (Data Access Object).
 * Gestiona el acceso a los datos de la tabla "usuario" en la base de datos.
 * Separa la lógica de persistencia del resto de la aplicación.
 */
public class UsuarioDAO {

    /**
     * Valida las credenciales del usuario contra la base de datos.
     *
     * @param correo     Correo (usuario) ingresado en el Login
     * @param contrasena Contraseña ingresada en el Login
     * @return El IDUSUARIO si las credenciales son correctas, null si no coinciden
     */
    public String validarUsuario(String correo, String contrasena) {
        String sql = "SELECT IDUSUARIO FROM usuario WHERE correousuario = ? AND contrasena = ?";

        // Verificar que la conexión esté disponible antes de continuar
        Connection conn = ConexionBD.getInstancia().getConnection();
        if (conn == null) {
            System.err.println("❌ No hay conexión disponible. Verifique que ojdbc17.jar esté en el classpath y que Oracle esté corriendo.");
            return null;
        }

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, correo);
            stmt.setString(2, contrasena);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("IDUSUARIO");
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al validar usuario: " + e.getMessage());
            e.printStackTrace();
        }

        return null;
    }
}
