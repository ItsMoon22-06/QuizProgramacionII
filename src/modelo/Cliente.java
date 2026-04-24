package modelo;

/**
 * Clase Cliente - Modelo (M en MVC).
 * Representa la entidad "Cliente" del sistema CRUD.
 * Contiene únicamente atributos, constructor, getters y setters (POJO).
 */
public class Cliente {

    private Long   cedula;
    private String nombre;
    private String apellido;
    private String correo;
    private Long   telefono;
    private String idUsuario;

    // ─── Constructor vacío ────────────────────────────────────────────────────
    public Cliente() {}

    // ─── Constructor completo ─────────────────────────────────────────────────
    public Cliente(Long cedula, String nombre, String apellido,
                   String correo, Long telefono, String idUsuario) {
        this.cedula    = cedula;
        this.nombre    = nombre;
        this.apellido  = apellido;
        this.correo    = correo;
        this.telefono  = telefono;
        this.idUsuario = idUsuario;
    }

    // ─── Getters y Setters ────────────────────────────────────────────────────
    public Long getCedula() { return cedula; }
    public void setCedula(Long cedula) { this.cedula = cedula; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getApellido() { return apellido; }
    public void setApellido(String apellido) { this.apellido = apellido; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public Long getTelefono() { return telefono; }
    public void setTelefono(Long telefono) { this.telefono = telefono; }

    public String getIdUsuario() { return idUsuario; }
    public void setIdUsuario(String idUsuario) { this.idUsuario = idUsuario; }

    @Override
    public String toString() {
        return "Cliente{cedula=" + cedula + ", nombre='" + nombre + "', apellido='" + apellido +
               "', correo='" + correo + "', telefono=" + telefono + ", idUsuario='" + idUsuario + "'}";
    }
}
