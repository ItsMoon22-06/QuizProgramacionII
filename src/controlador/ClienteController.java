package controlador;

import modelo.Cliente;
import modelo.ClienteDAO;
import java.util.List;

/**
 * Clase ClienteController - Controlador (C en MVC).
 * Actúa como intermediario entre las vistas y el ClienteDAO.
 * Contiene la lógica de negocio del módulo de Clientes:
 *   - Validaciones de datos
 *   - Creación del objeto Cliente
 *   - Delegación al DAO para operaciones de persistencia
 *
 * Las vistas NO deben acceder directamente al DAO.
 */
public class ClienteController {

    private final ClienteDAO clienteDAO;

    public ClienteController() {
        this.clienteDAO = new ClienteDAO();
    }

    // ─── CREATE ──────────────────────────────────────────────────────────────
    /**
     * Crea y persiste un nuevo cliente.
     *
     * @param cedula    Número de cédula único del cliente
     * @param nombre    Nombre del cliente
     * @param apellido  Apellido del cliente
     * @param correo    Correo electrónico del cliente
     * @param telefono  Teléfono del cliente
     * @param idUsuario ID del usuario que registra al cliente
     * @return true si se creó correctamente, false si hubo un error
     */
    public boolean agregarCliente(long cedula, String nombre, String apellido,
                                   String correo, long telefono, String idUsuario) {
        // Validación de campos
        if (nombre.isBlank() || apellido.isBlank() || correo.isBlank()) {
            System.err.println("⚠️  Campos obligatorios vacíos.");
            return false;
        }

        Cliente cliente = new Cliente(cedula, nombre, apellido, correo, telefono, idUsuario);
        boolean resultado = clienteDAO.insertarCliente(cliente);

        if (resultado) {
            System.out.println("✅ Cliente agregado con éxito.");
        } else {
            System.err.println("❌ Error al agregar el cliente.");
        }
        return resultado;
    }

    // ─── READ ─────────────────────────────────────────────────────────────────
    /**
     * Retorna la lista de todos los clientes registrados.
     *
     * @return Lista de objetos Cliente (puede estar vacía)
     */
    public List<Cliente> listarClientes() {
        List<Cliente> clientes = clienteDAO.listarClientes();
        if (clientes.isEmpty()) {
            System.out.println("ℹ️  No hay clientes registrados.");
        }
        return clientes;
    }

    // ─── UPDATE ───────────────────────────────────────────────────────────────
    /**
     * Actualiza los datos de un cliente existente.
     *
     * @param cedula    Cédula del cliente a actualizar (clave primaria, no cambia)
     * @param nombre    Nuevo nombre
     * @param apellido  Nuevo apellido
     * @param correo    Nuevo correo
     * @param telefono  Nuevo teléfono
     * @param idUsuario ID del usuario que realiza la actualización
     * @return true si la actualización fue exitosa, false en caso de error
     */
    public boolean actualizarCliente(long cedula, String nombre, String apellido,
                                      String correo, long telefono, String idUsuario) {
        // Validación de campos
        if (nombre.isBlank() || apellido.isBlank() || correo.isBlank()) {
            System.err.println("⚠️  Campos obligatorios vacíos.");
            return false;
        }

        Cliente cliente = new Cliente(cedula, nombre, apellido, correo, telefono, idUsuario);
        boolean resultado = clienteDAO.actualizarCliente(cliente);

        if (resultado) {
            System.out.println("✅ Cliente actualizado con éxito.");
        } else {
            System.err.println("❌ Error al actualizar el cliente.");
        }
        return resultado;
    }

    // ─── DELETE ───────────────────────────────────────────────────────────────
    /**
     * Elimina un cliente de la base de datos por su cédula.
     *
     * @param cedula Cédula del cliente a eliminar
     * @return true si se eliminó correctamente, false en caso de error
     */
    public boolean eliminarCliente(long cedula) {
        boolean resultado = clienteDAO.eliminarCliente(cedula);

        if (resultado) {
            System.out.println("✅ Cliente eliminado con éxito.");
        } else {
            System.err.println("❌ Error al eliminar el cliente.");
        }
        return resultado;
    }
}
