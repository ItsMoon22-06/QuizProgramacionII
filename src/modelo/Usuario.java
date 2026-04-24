package modelo;

/**
 * Clase Usuario - Modelo (M en MVC).
 * Representa la entidad "Usuario" del sistema de autenticación.
 */
public class Usuario {

    private String id;
    private String correo;
    private String contrasena;

    // ─── Constructor vacío ────────────────────────────────────────────────────
    public Usuario() {}

    // ─── Constructor completo ─────────────────────────────────────────────────
    public Usuario(String id, String correo, String contrasena) {
        this.id        = id;
        this.correo    = correo;
        this.contrasena = contrasena;
    }

    // ─── Getters y Setters ────────────────────────────────────────────────────
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    @Override
    public String toString() {
        return "Usuario{id='" + id + "', correo='" + correo + "'}";
    }
}
