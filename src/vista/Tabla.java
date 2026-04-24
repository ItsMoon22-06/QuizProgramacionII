package vista;

import controlador.ClienteController;
import modelo.Cliente;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Clase Tabla - Vista (V en MVC).
 * Muestra el listado de clientes en una JTable con opciones CRUD:
 *   - Crear  → abre CrearCliente
 *   - Editar → abre ActualizarCliente con la fila seleccionada
 *   - Eliminar → elimina el cliente seleccionado previo diálogo de confirmación
 *
 * Usa el patrón callback (Runnable) para refrescar la tabla
 * automáticamente después de cada operación desde las subvistas.
 */
public class Tabla extends JFrame {

    // ─── Componentes ──────────────────────────────────────────────────────────
    private JTable        tabla;
    private JScrollPane   scrollPane;
    private JButton       btnCrear;
    private JButton       btnEditar;
    private JButton       btnEliminar;
    private JLabel        lblTitulo;

    // ─── Estado ───────────────────────────────────────────────────────────────
    private final ClienteController clienteController;
    private final String            idUsuario;

    // ─────────────────────────────────────────────────────────────────────────
    public Tabla(String idUsuario) {
        this.idUsuario          = idUsuario;
        this.clienteController  = new ClienteController();
        initComponents();
        configurarEventos();
        cargarClientes();
    }

    /** Inicializa y organiza los componentes de la interfaz. */
    private void initComponents() {
        setTitle("Gestión de Clientes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(870, 420);
        setLocationRelativeTo(null);
        setResizable(true);

        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBackground(new Color(255, 238, 208));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setContentPane(panelPrincipal);

        // ── Título ────────────────────────────────────────────────────────────
        lblTitulo = new JLabel("👥 Listado de Clientes", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Serif", Font.BOLD, 22));
        lblTitulo.setForeground(new Color(124, 6, 77));
        panelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        // ── Tabla ─────────────────────────────────────────────────────────────
        tabla = new JTable();
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setRowHeight(24);
        tabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 12));
        tabla.getTableHeader().setBackground(new Color(124, 6, 77));
        tabla.getTableHeader().setForeground(Color.WHITE);

        scrollPane = new JScrollPane(tabla);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);

        // ── Panel de botones ──────────────────────────────────────────────────
        JPanel panelBotones = new JPanel(new GridLayout(3, 1, 0, 10));
        panelBotones.setBackground(new Color(255, 238, 208));
        panelBotones.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        btnCrear    = crearBoton("➕ Crear",   new Color(34, 139, 34));
        btnEditar   = crearBoton("✏️ Editar",   new Color(30, 100, 200));
        btnEliminar = crearBoton("🗑️ Eliminar", new Color(180, 30, 30));

        panelBotones.add(btnCrear);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        panelPrincipal.add(panelBotones, BorderLayout.EAST);
    }

    /** Utilitario para construir botones con estilo uniforme. */
    private JButton crearBoton(String texto, Color color) {
        JButton btn = new JButton(texto);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(160, 40));
        return btn;
    }

    /** Conecta los botones con sus acciones correspondientes. */
    private void configurarEventos() {
        // ── Crear ─────────────────────────────────────────────────────────────
        btnCrear.addActionListener(e -> {
            CrearCliente ventanaCrear = new CrearCliente(idUsuario, this::cargarClientes);
            ventanaCrear.setVisible(true);
        });

        // ── Editar ────────────────────────────────────────────────────────────
        btnEditar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this,
                    "Seleccione un cliente para editar.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Recuperar datos de la fila seleccionada
            String cedula   = tabla.getValueAt(fila, 0).toString();
            String nombre   = tabla.getValueAt(fila, 1).toString();
            String apellido = tabla.getValueAt(fila, 2).toString();
            String correo   = tabla.getValueAt(fila, 3).toString();
            String telefono = tabla.getValueAt(fila, 4).toString();

            ActualizarCliente ventanaActualizar = new ActualizarCliente(
                cedula, nombre, apellido, correo, telefono, idUsuario, this::cargarClientes
            );
            ventanaActualizar.setVisible(true);
        });

        // ── Eliminar ──────────────────────────────────────────────────────────
        btnEliminar.addActionListener(e -> {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this,
                    "Seleccione un cliente para eliminar.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            long cedula = Long.parseLong(tabla.getValueAt(fila, 0).toString());
            int  conf   = JOptionPane.showConfirmDialog(this,
                "¿Está seguro de eliminar el cliente con cédula " + cedula + "?",
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (conf == JOptionPane.YES_OPTION) {
                boolean ok = clienteController.eliminarCliente(cedula);
                if (ok) {
                    JOptionPane.showMessageDialog(this, "Cliente eliminado correctamente.");
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Error al eliminar el cliente.", "Error", JOptionPane.ERROR_MESSAGE);
                }
                cargarClientes(); // Refrescar tabla
            }
        });
    }

    /**
     * Carga (o recarga) los datos de la tabla desde el controlador.
     * Se invoca al abrir la ventana y como callback tras crear/actualizar/eliminar.
     */
    public void cargarClientes() {
        DefaultTableModel modelo = new DefaultTableModel(
            new String[]{"Cédula", "Nombre", "Apellido", "Correo", "Teléfono", "ID Usuario"}, 0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // La tabla NO es editable directamente
            }
        };

        List<Cliente> clientes = clienteController.listarClientes();
        for (Cliente c : clientes) {
            modelo.addRow(new Object[]{
                c.getCedula(),
                c.getNombre(),
                c.getApellido(),
                c.getCorreo(),
                c.getTelefono(),
                c.getIdUsuario()
            });
        }

        tabla.setModel(modelo);

        // Ajustar ancho de columnas
        tabla.getColumnModel().getColumn(0).setPreferredWidth(90);
        tabla.getColumnModel().getColumn(1).setPreferredWidth(120);
        tabla.getColumnModel().getColumn(2).setPreferredWidth(120);
        tabla.getColumnModel().getColumn(3).setPreferredWidth(180);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(100);
        tabla.getColumnModel().getColumn(5).setPreferredWidth(90);
    }
}
