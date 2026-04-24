package vista;

import controlador.LoginController;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Clase Login - Vista (V en MVC).
 * Interfaz gráfica de autenticación de usuarios.
 * Construida manualmente con Swing (sin dependencia de AbsoluteLayout de NetBeans).
 *
 * Flujo:
 *   Usuario ingresa correo + contraseña → LoginController.autenticar()
 *   → Si válido: abre Tabla  |  Si inválido: muestra mensaje de error
 */
public class Login extends JFrame {

    // ─── Componentes de la vista ──────────────────────────────────────────────
    private JTextField     txtCorreo;
    private JPasswordField txtContrasena;
    private JButton        btnLogin;
    private JCheckBox      chkMostrar;
    private JLabel         lblTitulo;

    // ─── Controlador asociado ─────────────────────────────────────────────────
    private final LoginController controlador;

    // ─────────────────────────────────────────────────────────────────────────
    public Login() {
        this.controlador = new LoginController();
        initComponents();
        configurarEventos();
    }

    /** Inicializa y organiza los componentes de la interfaz. */
    private void initComponents() {
        setTitle("Login - Sistema CRUD");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(420, 320);
        setLocationRelativeTo(null);
        setResizable(false);

        // Panel principal con fondo crema
        JPanel panelPrincipal = new JPanel(new GridBagLayout());
        panelPrincipal.setBackground(new Color(255, 238, 208));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        setContentPane(panelPrincipal);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 5, 8, 5);

        // ── Título ────────────────────────────────────────────────────────────
        lblTitulo = new JLabel("🔐 Login", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Serif", Font.BOLD | Font.ITALIC, 32));
        lblTitulo.setForeground(new Color(124, 6, 77));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panelPrincipal.add(lblTitulo, gbc);

        // ── Correo ────────────────────────────────────────────────────────────
        gbc.gridwidth = 1; gbc.gridy = 1; gbc.gridx = 0;
        panelPrincipal.add(new JLabel("Correo / Usuario:"), gbc);

        txtCorreo = new JTextField(20);
        txtCorreo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        gbc.gridx = 1;
        panelPrincipal.add(txtCorreo, gbc);

        // ── Contraseña ────────────────────────────────────────────────────────
        gbc.gridy = 2; gbc.gridx = 0;
        panelPrincipal.add(new JLabel("Contraseña:"), gbc);

        txtContrasena = new JPasswordField(20);
        txtContrasena.setFont(new Font("SansSerif", Font.PLAIN, 13));
        gbc.gridx = 1;
        panelPrincipal.add(txtContrasena, gbc);

        // ── Mostrar contraseña ────────────────────────────────────────────────
        chkMostrar = new JCheckBox("Mostrar contraseña");
        chkMostrar.setBackground(new Color(255, 238, 208));
        gbc.gridy = 3; gbc.gridx = 0; gbc.gridwidth = 2;
        panelPrincipal.add(chkMostrar, gbc);

        // ── Botón Login ───────────────────────────────────────────────────────
        btnLogin = new JButton("Ingresar");
        btnLogin.setBackground(new Color(124, 6, 77));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFont(new Font("SansSerif", Font.BOLD, 14));
        btnLogin.setFocusPainted(false);
        gbc.gridy = 4;
        panelPrincipal.add(btnLogin, gbc);
    }

    /** Configura los eventos de los componentes. */
    private void configurarEventos() {
        // Mostrar/ocultar contraseña
        chkMostrar.addActionListener(e -> {
            if (chkMostrar.isSelected()) {
                txtContrasena.setEchoChar((char) 0); // Muestra texto
            } else {
                txtContrasena.setEchoChar('●');       // Oculta texto
            }
        });

        // Acción del botón Login
        btnLogin.addActionListener(e -> realizarLogin());

        // Permitir Enter desde el campo contraseña
        txtContrasena.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) realizarLogin();
            }
        });
    }

    /** Lógica de autenticación: valida campos y llama al controlador. */
    private void realizarLogin() {
        String correo     = txtCorreo.getText().trim();
        String contrasena = new String(txtContrasena.getPassword());

        if (correo.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Por favor complete todos los campos.",
                "Campos vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Delegar autenticación al controlador
        String idUsuario = controlador.autenticar(correo, contrasena);

        if (idUsuario != null) {
            JOptionPane.showMessageDialog(this,
                "¡Bienvenido! Acceso concedido.",
                "Login exitoso", JOptionPane.INFORMATION_MESSAGE);

            // Abrir la vista principal y pasar el ID del usuario
            new Tabla(idUsuario).setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                "Correo o contraseña incorrectos.",
                "Error de autenticación", JOptionPane.ERROR_MESSAGE);
            txtContrasena.setText("");
        }
    }
}
