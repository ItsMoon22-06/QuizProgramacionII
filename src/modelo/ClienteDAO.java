package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase ClienteDAO - Patrón DAO (Data Access Object).
 * Centraliza todas las operaciones CRUD sobre la tabla "cliente".
 * No contiene lógica de negocio: solo acceso a datos.
 *
 * Operaciones:
 *   CREATE  → insertarCliente()
 *   READ    → listarClientes()
 *   UPDATE  → actualizarCliente()
 *   DELETE  → eliminarCliente()
 */
public class ClienteDAO {

    // Conexión reutilizada desde el Singleton
    private final Connection conexion;

    public ClienteDAO() {
        this.conexion = ConexionBD.getInstancia().getConnection();
        if (this.conexion == null) {
            System.err.println("❌ ClienteDAO: conexión nula. Verifique ojdbc17.jar y que Oracle XE esté activo.");
        }
    }

    // ─── CREATE ──────────────────────────────────────────────────────────────
    /**
     * Inserta un nuevo cliente en la base de datos.
     *
     * @param cliente Objeto Cliente con los datos a insertar
     * @return true si se insertó correctamente, false en caso de error
     */
    public boolean insertarCliente(Cliente cliente) {
        String sql = "INSERT INTO cliente (CEDULA, NOMBRE, APELLIDO, CORREOELECTRONICO, TELEFONO, IDUSUARIO) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setLong(1,   cliente.getCedula());
            ps.setString(2, cliente.getNombre());
            ps.setString(3, cliente.getApellido());
            ps.setString(4, cliente.getCorreo());
            ps.setLong(5,   cliente.getTelefono());
            ps.setString(6, cliente.getIdUsuario());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error al insertar cliente: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // ─── READ ─────────────────────────────────────────────────────────────────
    /**
     * Obtiene la lista completa de clientes registrados.
     *
     * @return Lista de objetos Cliente; lista vacía si no hay registros
     */
    public List<Cliente> listarClientes() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM cliente ORDER BY CEDULA";

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Cliente c = new Cliente(
                    rs.getLong("CEDULA"),
                    rs.getString("NOMBRE"),
                    rs.getString("APELLIDO"),
                    rs.getString("CORREOELECTRONICO"),
                    rs.getLong("TELEFONO"),
                    rs.getString("IDUSUARIO")
                );
                lista.add(c);
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al listar clientes: " + e.getMessage());
            e.printStackTrace();
        }

        return lista;
    }

    // ─── UPDATE ───────────────────────────────────────────────────────────────
    /**
     * Actualiza los datos de un cliente existente identificado por su cédula.
     *
     * @param cliente Objeto Cliente con los nuevos datos (cédula no cambia)
     * @return true si se actualizó al menos un registro, false en caso de error
     */
    public boolean actualizarCliente(Cliente cliente) {
        String sql = "UPDATE cliente SET NOMBRE = ?, APELLIDO = ?, CORREOELECTRONICO = ?, " +
                     "TELEFONO = ?, IDUSUARIO = ? WHERE CEDULA = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, cliente.getNombre());
            ps.setString(2, cliente.getApellido());
            ps.setString(3, cliente.getCorreo());
            ps.setLong(4,   cliente.getTelefono());
            ps.setString(5, cliente.getIdUsuario());
            ps.setLong(6,   cliente.getCedula());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error al actualizar cliente: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────
    /**
     * Elimina un cliente de la base de datos por su número de cédula.
     *
     * @param cedula Número de cédula del cliente a eliminar
     * @return true si se eliminó correctamente, false en caso de error
     */
    public boolean eliminarCliente(long cedula) {
        String sql = "DELETE FROM cliente WHERE CEDULA = ?";

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setLong(1, cedula);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("❌ Error al eliminar cliente: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
