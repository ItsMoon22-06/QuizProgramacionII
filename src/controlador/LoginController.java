package controlador;

import modelo.UsuarioDAO;

/**
 * Clase LoginController - Controlador (C en MVC).
 * Actúa como intermediario entre la vista Login y el DAO de Usuario.
 * Recibe la petición de autenticación y delega la validación al UsuarioDAO.
 */
public class LoginController {

    private final UsuarioDAO usuarioDAO;

    public LoginController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    /**
     * Autentica al usuario verificando sus credenciales contra la base de datos.
     *
     * @param correo     Correo electrónico del usuario
     * @param contrasena Contraseña del usuario
     * @return El IDUSUARIO si la autenticación es exitosa, null si falla
     */
    public String autenticar(String correo, String contrasena) {
        // Validación básica antes de ir a la BD
        if (correo == null || correo.isBlank() || contrasena == null || contrasena.isBlank()) {
            return null;
        }
        return usuarioDAO.validarUsuario(correo, contrasena);
    }
}
