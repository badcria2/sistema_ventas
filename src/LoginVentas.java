import Entity.RegistroUsuarios;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

public class LoginVentas extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private JLabel lblEstado;
    private RegistroUsuarios registro;

    public LoginVentas() {
        // Cargar los usuarios ANTES de construir la interfaz
        this.registro = RegistroUsuarios.cargar("usuarios.dat");
        initComponents();
        // Cargar un icono (debes tener una imagen 'logo.png' en una carpeta 'resources')
        try {
            // Asegúrate de que la ruta al recurso sea correcta
            Image icon = new ImageIcon(getClass().getResource("/resources/logo.png")).getImage();
            this.setIconImage(icon);
        } catch (Exception e) {
            System.out.println("ADVERTENCIA: No se encontró 'logo.png'. Usando el icono por defecto.");
        }
    }

    private void initComponents() {
        // Ventana sin decoración para un look moderno y con esquinas redondeadas
        setUndecorated(true);
        setSize(420, 580);
        setLocationRelativeTo(null);
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Panel principal que contendrá todo, incluyendo la barra de título personalizada
        JPanel panelPrincipal = new JPanel(new BorderLayout());
        panelPrincipal.setBackground(DesignUtils.PRIMARY_BLUE); // Color base por si algo falla
        
        // El contenido visual irá en un panel con fondo degradado
        JPanel panelContenido = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, DesignUtils.ACCENT_CYAN, 0, getHeight(), DesignUtils.PRIMARY_BLUE);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        
        // Centrar el formulario blanco en el panel de contenido
        panelContenido.add(createFormPanel());
        
        // Añadir nuestra barra de título y el contenido al panel principal
        panelPrincipal.add(createTitleBar(), BorderLayout.NORTH);
        panelPrincipal.add(panelContenido, BorderLayout.CENTER);

        // Hacer que toda la ventana sea arrastrable
        FrameDragListener frameDragListener = new FrameDragListener(this);
        panelPrincipal.addMouseListener(frameDragListener);
        panelPrincipal.addMouseMotionListener(frameDragListener);

        add(panelPrincipal);
    }
    
    // Crea una barra de título personalizada con un botón de cierre
    private JPanel createTitleBar() {
        JPanel titleBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        titleBar.setOpaque(false); // Transparente para ver el degradado
        titleBar.setBorder(new EmptyBorder(5, 0, 5, 5));

        JButton btnClose = new JButton("×");
        styleTitleBarButton(btnClose, DesignUtils.DANGER_RED);
        btnClose.addActionListener(e -> System.exit(0));

        titleBar.add(btnClose);
        return titleBar;
    }

    // Da estilo a los botones de la barra de título (minimizar, cerrar, etc.)
    private void styleTitleBarButton(JButton button, Color hoverColor) {
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setForeground(Color.BLACK);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorder(new EmptyBorder(0,10,0,10));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setOpaque(true);
                button.setBackground(hoverColor);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                button.setOpaque(false);
            }
        });
    }

    // Crea el panel blanco central del formulario
    private JPanel createFormPanel() {
        JPanel panelBlanco = new JPanel(new GridBagLayout());
        panelBlanco.setBackground(DesignUtils.BACKGROUND_WHITE);
        panelBlanco.setBorder(new EmptyBorder(30, 40, 40, 40));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 5, 10, 5);

        // Título principal del formulario
        JLabel lblTitulo = new JLabel("Tecnoby Store");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitulo.setForeground(DesignUtils.DARK_NAVY);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0; gbc.gridy = 0; gbc.insets = new Insets(0, 0, 2, 0);
        panelBlanco.add(lblTitulo, gbc);

        // Subtítulo
        JLabel lblSubtitulo = new JLabel("Bienvenido de nuevo");
        lblSubtitulo.setFont(DesignUtils.SUBTITLE_FONT);
        lblSubtitulo.setForeground(DesignUtils.TEXT_SECONDARY);
        lblSubtitulo.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridy = 1; gbc.insets = new Insets(0, 0, 30, 0);
        panelBlanco.add(lblSubtitulo, gbc);

        // Campos de texto con iconos
        gbc.gridy = 2; gbc.insets = new Insets(10, 0, 10, 0);
        txtUsuario = new JTextField(); // se crea aquí para que sea accesible
        panelBlanco.add(createFieldWithIcon("/resources/user_icon.png", "Usuario", txtUsuario), gbc);
        
        gbc.gridy = 3;
        txtPassword = new JPasswordField();
        panelBlanco.add(createFieldWithIcon("/resources/lock_icon.png", "Contraseña", txtPassword), gbc);

        // Botón de Iniciar Sesión (acción primaria)
        gbc.gridy = 4; gbc.insets = new Insets(30, 0, 10, 0);
        JButton btnLogin = DesignUtils.createStyledButton("INICIAR SESIÓN", DesignUtils.SUCCESS_GREEN);
        btnLogin.setForeground(Color.BLACK);
        btnLogin.setPreferredSize(new Dimension(200, 45)); // Botón más grande
        btnLogin.addActionListener(e -> realizarLogin());
        panelBlanco.add(btnLogin, gbc);
        
        // Botón de Cancelar (acción secundaria)
        gbc.gridy = 5; gbc.insets = new Insets(5, 0, 15, 0);
        JButton btnCancelar = DesignUtils.createGhostButton("Cancelar");
        btnCancelar.addActionListener(e -> System.exit(0));
        panelBlanco.add(btnCancelar, gbc);

        // Etiqueta para mostrar mensajes de estado (errores o éxito)
        lblEstado = new JLabel(" ", SwingConstants.CENTER);
        lblEstado.setFont(DesignUtils.NORMAL_FONT);
        gbc.gridy = 6; gbc.insets = new Insets(10, 0, 0, 0);
        panelBlanco.add(lblEstado, gbc);
        
        // Eventos para que la tecla "Enter" funcione en los campos
        txtUsuario.addActionListener(e -> txtPassword.requestFocus());
        txtPassword.addActionListener(e -> realizarLogin());

        return panelBlanco;
    }
    
    // Método auxiliar para crear un campo de texto con un icono a la izquierda
    private JPanel createFieldWithIcon(String iconPath, String placeholder, JTextField field) {
        JPanel panel = new JPanel(new BorderLayout(10, 0));
        panel.setOpaque(false);
        
        JLabel iconLabel = new JLabel();
        try {
            iconLabel.setIcon(new ImageIcon(getClass().getResource(iconPath)));
        } catch (Exception e) {
            System.out.println("ADVERTENCIA: No se encontró el icono: " + iconPath);
            iconLabel.setText("•"); // Fallback a un punto si no se encuentra el icono
        }
        panel.add(iconLabel, BorderLayout.WEST);

        // Estilos del campo de texto
        field.setFont(DesignUtils.NORMAL_FONT);
        field.setForeground(DesignUtils.TEXT_MUTED);
        field.setText(placeholder);
        field.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, DesignUtils.BORDER_LIGHT));
        field.setOpaque(false);
        
        boolean isPassword = field instanceof JPasswordField;
        if (isPassword) {
            ((JPasswordField) field).setEchoChar((char) 0); // Mostrar placeholder
        }

        // Efecto visual al ganar o perder el foco
        field.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                field.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, DesignUtils.PRIMARY_BLUE));
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(DesignUtils.TEXT_PRIMARY);
                    if (isPassword) ((JPasswordField) field).setEchoChar('•');
                }
            }
            @Override
            public void focusLost(FocusEvent e) {
                field.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, DesignUtils.BORDER_LIGHT));
                if (field.getText().isEmpty()) {
                    field.setText(placeholder);
                    field.setForeground(DesignUtils.TEXT_MUTED);
                    if (isPassword) ((JPasswordField) field).setEchoChar((char) 0);
                }
            }
        });
        
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }
    
    // Lógica para procesar el intento de inicio de sesión
    private void realizarLogin() {
        String usuario = txtUsuario.getText();
        String password = String.valueOf(txtPassword.getPassword());

        if (usuario.equals("Usuario") || usuario.trim().isEmpty()) {
            mostrarError("Por favor, ingrese el nombre de usuario.");
            txtUsuario.requestFocus();
            return;
        }
        if (password.equals("Contraseña") || password.trim().isEmpty()) {
            mostrarError("Por favor, ingrese la contraseña.");
            txtPassword.requestFocus();
            return;
        }

        if (registro.validarCredenciales(usuario, password)) {
            mostrarExito("¡Bienvenido!");
            // Abre la ventana principal después de 1 segundo para que se vea el mensaje
            Timer timer = new Timer(1000, e -> abrirVentanaPrincipal());
            timer.setRepeats(false);
            timer.start();
        } else {
            mostrarError("Usuario o contraseña incorrectos.");
        }
    }

    private void abrirVentanaPrincipal() {
        this.dispose(); // Cierra el login
        // Asegúrate de que tu ventana principal se llame así
        new VentanaPrincipal().setVisible(true); 
    }

    private void mostrarError(String mensaje) {
        lblEstado.setText("<html><div style='text-align: center;'>" + mensaje + "</div></html>");
        lblEstado.setForeground(DesignUtils.DANGER_RED);
    }

    private void mostrarExito(String mensaje) {
        lblEstado.setText("<html><div style='text-align: center;'>" + mensaje + "</div></html>");
        lblEstado.setForeground(DesignUtils.SUCCESS_GREEN);
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> new LoginVentas().setVisible(true));
    }

    // Clase interna estática para hacer la ventana arrastrable
    public static class FrameDragListener extends MouseAdapter {
        private final JFrame frame;
        private Point mouseDownCompCoords = null;

        public FrameDragListener(JFrame frame) {
            this.frame = frame;
        }

        public void mouseReleased(MouseEvent e) {
            mouseDownCompCoords = null;
        }

        public void mousePressed(MouseEvent e) {
            mouseDownCompCoords = e.getPoint();
        }

        public void mouseDragged(MouseEvent e) {
            Point currCoords = e.getLocationOnScreen();
            frame.setLocation(currCoords.x - mouseDownCompCoords.x, currCoords.y - mouseDownCompCoords.y);
        }
    }
}