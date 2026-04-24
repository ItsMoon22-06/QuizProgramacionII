package vista;

import controlador.ClienteController;
import javax.swing.*;
import java.awt.*;

/**
 * Clase CrearCliente - Vista (V en MVC).
 * Formulario para registrar un nuevo cliente en el sistema.
 *
 * Al guardar exitosamente, ejecuta el callback "onClienteGuardado"
 * que refresca la tabla principal sin necesidad de reabrir la ventana.
 */
public class CrearCliente extends JDialog {

    // ─── Componentes ──────────────────────────────────────────────────────────
    private JTextField txtCedula;
    private JTextField txtNombre;
    private JTextField txtApellido;
    private JTextField txtCorreo;
    private JTextField txtTelefono;
    private JButton    btnGuardar;
    private JButton    btnCancelar;

    // ─── Estado ───────────────────────────────────────────────────────────────
    private final String   idUsuario;
    private final Runnable onClienteGuardado; // Callback para refrescar Tabla

    // ─────────────────────────────────────────────────────────────────────────
    public CrearCliente(String idUsuario, Runnable onClienteGuardado) {
        this.idUsuario         = idUsuario;
        this.onClienteGuardado = onClienteGuardado;
        initComponents();
        configurarEventos();
    }

    /** Inicializa y organiza los componentes del formulario. */
    private void initComponents() {
        setTitle("Crear Nuevo Cliente");
        setModal(true);
        setSize(430, 340);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(255, 238, 208));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        setContentPane(panel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill    = GridBagConstraints.HORIZONTAL;
        gbc.insets  = new Insets(6, 5, 6, 5);

        // ── Título ────────────────────────────────────────────────────────────
        JLabel lblTitulo = new JLabel("➕ Registrar Cliente", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Serif", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(124, 6, 77));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(lblTitulo, gbc);

        // ── Campos del formulario ─────────────────────────────────────────────
        gbc.gridwidth = 1;
        txtCedula    = agregarCampo(panel, gbc, "Cédula:",    1);
        txtNombre    = agregarCampo(panel, gbc, "Nombre:",    2);
        txtApellido  = agregarCampo(panel, gbc, "Apellido:",  3);
        txtCorreo    = agregarCampo(panel, gbc, "Correo:",    4);
        txtTelefono  = agregarCampo(panel, gbc, "Teléfono:",  5);

        // ── Botones ───────────────────────────────────────────────────────────
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        panelBotones.setBackground(new Color(255, 238, 208));

        btnGuardar  = new JButton("💾 Guardar");
        btnCancelar = new JButton("✖ Cancelar");
        estilizarBoton(btnGuardar,  new Color(34, 139, 34));
        estilizarBoton(btnCancelar, new Color(150, 50, 50));

        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);

        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        panel.add(panelBotones, gbc);
    }

    /** Utilitario: añade una fila etiqueta + campo al panel con GridBag. */
    private JTextField agregarCampo(JPanel panel, GridBagConstraints gbc, String label, int fila) {
        gbc.gridx = 0; gbc.gridy = fila; gbc.weightx = 0.3;
        panel.add(new JLabel(label), gbc);

        JTextField campo = new JTextField(18);
        campo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        gbc.gridx = 1; gbc.weightx = 0.7;
        panel.add(campo, gbc);
        return campo;
    }

    /** Aplica estilo visual a los botones. */
    private void estilizarBoton(JButton btn, Color color) {
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("SansSerif", Font.BOLD, 13));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(130, 36));
    }

    /** Configura los eventos de los botones. */
    private void configurarEventos() {
        btnCancelar.addActionListener(e -> dispose());

        btnGuardar.addActionListener(e -> guardarCliente());
    }

    /** Valida los campos y llama al controlador para guardar el cliente. */
    private void guardarCliente() {
        String cedulaTxt   = txtCedula.getText().trim();
        String nombreTxt   = txtNombre.getText().trim();
        String apellidoTxt = txtApellido.getText().trim();
        String correoTxt   = txtCorreo.getText().trim();
        String telefonoTxt = txtTelefono.getText().trim();

        // ── Validación: campos vacíos ─────────────────────────────────────────
        if (cedulaTxt.isEmpty() || nombreTxt.isEmpty() || apellidoTxt.isEmpty() ||
            correoTxt.isEmpty() || telefonoTxt.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Todos los campos son obligatorios.",
                "Campos vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // ── Validación: datos numéricos ───────────────────────────────────────
        long cedula, telefono;
        try {
            cedula   = Long.parseLong(cedulaTxt);
            telefono = Long.parseLong(telefonoTxt);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "La cédula y el teléfono deben ser números válidos.",
                "Datos incorrectos", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // ── Llamar al controlador ─────────────────────────────────────────────
        ClienteController controller = new ClienteController();
        boolean ok = controller.agregarCliente(cedula, nombreTxt, apellidoTxt, correoTxt, telefono, idUsuario);

        if (ok) {
            JOptionPane.showMessageDialog(this, "✅ Cliente creado exitosamente.");
            if (onClienteGuardado != null) onClienteGuardado.run(); // Refresca tabla
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                "❌ Error al crear el cliente. Verifique que la cédula no exista.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
