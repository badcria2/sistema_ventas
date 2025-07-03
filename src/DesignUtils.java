
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Clase utilitaria mejorada para diseño y componentes visuales del sistema
 * Tecnoby Store Versión 2.1 - Corregida compatibilidad de iconos y texto
 *
 * @version 2.1
 */
public class DesignUtils {

    // ============================================================================
    // CONSTANTES DE COLORES MEJORADOS - PALETA VIBRANTE
    // ============================================================================
    // Colores principales más vibrantes
    public static final Color PRIMARY_BLUE = new Color(30, 144, 255);      // Azul más brillante
    public static final Color SUCCESS_GREEN = new Color(34, 197, 94);      // Verde más vibrante
    public static final Color WARNING_ORANGE = new Color(255, 165, 0);     // Naranja en lugar de amarillo
    public static final Color DANGER_RED = new Color(239, 68, 68);         // Rojo más intenso
    public static final Color INFO_PURPLE = new Color(147, 51, 234);       // Púrpura más profundo
    public static final Color ACCENT_CYAN = new Color(6, 182, 212);        // Cyan como color de acento

    // Colores secundarios
    public static final Color DARK_NAVY = new Color(15, 23, 42);           // Azul marino muy oscuro
    public static final Color LIGHT_BLUE = new Color(59, 130, 246);        // Azul claro más vibrante
    public static final Color MEDIUM_BLUE = new Color(37, 99, 235);        // Azul medio

    // Colores de fondo y bordes
    public static final Color BACKGROUND_LIGHT = new Color(248, 250, 252);  // Fondo muy claro
    public static final Color BACKGROUND_WHITE = Color.WHITE;
    public static final Color BORDER_LIGHT = new Color(203, 213, 225);      // Borde claro
    public static final Color BORDER_MEDIUM = new Color(148, 163, 184);     // Borde medio

    // Colores de texto mejorados
    public static final Color TEXT_PRIMARY = new Color(15, 23, 42);         // Texto principal muy oscuro
    public static final Color TEXT_SECONDARY = new Color(71, 85, 105);      // Texto secundario
    public static final Color TEXT_MUTED = new Color(148, 163, 184);        // Texto suave
    public static final Color TEXT_WHITE = Color.BLACK;

    // Colores de hover y estados
    public static final Color HOVER_OVERLAY = new Color(0, 0, 0, 10);       // Overlay sutil para hover
    public static final Color SELECTED_BG = new Color(239, 246, 255);       // Fondo de selección

    // ============================================================================
    // CONSTANTES DE FUENTES MEJORADAS
    // ============================================================================
    public static final String FONT_FAMILY = "Segoe UI";
    public static final Font TITLE_FONT = new Font(FONT_FAMILY, Font.BOLD, 26);
    public static final Font SUBTITLE_FONT = new Font(FONT_FAMILY, Font.PLAIN, 15);
    public static final Font HEADER_FONT = new Font(FONT_FAMILY, Font.BOLD, 18);
    public static final Font SUBHEADER_FONT = new Font(FONT_FAMILY, Font.BOLD, 14);
    public static final Font NORMAL_FONT = new Font(FONT_FAMILY, Font.PLAIN, 13);
    public static final Font BUTTON_FONT = new Font(FONT_FAMILY, Font.BOLD, 13);
    public static final Font MENU_TITLE_FONT = new Font(FONT_FAMILY, Font.BOLD, 32);
    public static final Font TABLE_HEADER_FONT = new Font(FONT_FAMILY, Font.BOLD, 13);
    public static final Font SMALL_FONT = new Font(FONT_FAMILY, Font.PLAIN, 11);

    // ============================================================================
    // MÉTODOS PARA PANELES PRINCIPALES MEJORADOS
    // ============================================================================
    /**
     * Crea un panel de encabezado con título y subtítulo - Versión mejorada
     *
     * @param title Título principal
     * @param subtitle Subtítulo descriptivo
     * @return JPanel configurado como encabezado
     */
    public static JPanel createHeaderPanel(String title, String subtitle) {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(DARK_NAVY);
        headerPanel.setPreferredSize(new Dimension(0, 85));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT_WHITE);

        JLabel subtitleLabel = new JLabel(subtitle);
        subtitleLabel.setFont(SUBTITLE_FONT);
        subtitleLabel.setForeground(new Color(203, 213, 225)); // Gris claro más visible

        JPanel textPanel = new JPanel(new BorderLayout());
        textPanel.setOpaque(false);
        textPanel.add(titleLabel, BorderLayout.NORTH);
        textPanel.add(Box.createVerticalStrut(5), BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);
        bottomPanel.add(subtitleLabel, BorderLayout.NORTH);
        textPanel.add(bottomPanel, BorderLayout.SOUTH);

        headerPanel.add(textPanel, BorderLayout.WEST);

        return headerPanel;
    }

    /**
     * Crea un panel de encabezado principal para la ventana de menú - Mejorado
     *
     * @param title Título principal de la aplicación
     * @param subtitle Subtítulo de la aplicación
     * @return JPanel configurado como encabezado principal
     */
    public static JPanel createMainHeaderPanel(String title, String subtitle) {
        JPanel headerPanel = new JPanel(new BorderLayout());

        // Gradiente simulado con múltiples paneles
        headerPanel.setBackground(PRIMARY_BLUE);
        headerPanel.setPreferredSize(new Dimension(0, 100));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(MENU_TITLE_FONT);
        titleLabel.setForeground(TEXT_WHITE);

        JLabel subtitleLabel = new JLabel(subtitle, SwingConstants.CENTER);
        subtitleLabel.setFont(new Font(FONT_FAMILY, Font.PLAIN, 16));
        subtitleLabel.setForeground(new Color(219, 234, 254)); // Color más claro y visible

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(titleLabel, BorderLayout.CENTER);
        centerPanel.add(subtitleLabel, BorderLayout.SOUTH);

        headerPanel.add(centerPanel, BorderLayout.CENTER);

        return headerPanel;
    }

    /**
     * Crea un panel de pie de página mejorado
     *
     * @param text Texto del pie de página
     * @return JPanel configurado como pie de página
     */
    public static JPanel createFooterPanel(String text) {
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(DARK_NAVY);
        footerPanel.setPreferredSize(new Dimension(0, 45));
        footerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel footerLabel = new JLabel(text, SwingConstants.CENTER);
        footerLabel.setForeground(new Color(203, 213, 225));
        footerLabel.setFont(NORMAL_FONT);

        footerPanel.add(footerLabel, BorderLayout.CENTER);

        return footerPanel;
    }

    // ============================================================================
    // MÉTODOS PARA BOTONES MEJORADOS
    // ============================================================================
    /**
     * Crea un botón estilizado con color personalizado - Versión mejorada
     *
     * @param text Texto del botón
     * @param color Color de fondo del botón
     * @return JButton estilizado
     */
    public static JButton createStyledButton(String text, Color color) {
        return createStyledButton(text, color, new Dimension(130, 40));
    }

    /**
     * Crea un botón estilizado con color y dimensiones personalizadas -
     * Mejorado
     *
     * @param text Texto del botón
     * @param color Color de fondo del botón
     * @param size Dimensiones del botón
     * @return JButton estilizado
     */
    public static JButton createStyledButton(String text, Color color, Dimension size) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(TEXT_SECONDARY);
        button.setFont(BUTTON_FONT);
        button.setPreferredSize(size);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Efecto hover mejorado
        button.addMouseListener(new MouseAdapter() {
            Color originalColor = color;
            Color hoverColor = darkenColor(color, 0.15f);

            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(originalColor);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(darkenColor(color, 0.25f));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(hoverColor);
            }
        });

        return button;
    }

    /**
     * Crea un botón de menú principal con título y descripción - Versión
     * mejorada
     *
     * @param title Título del botón
     * @param description Descripción del botón
     * @param color Color del título
     * @return JButton configurado como botón de menú
     */
    public static JButton createMenuButton(ImageIcon icon, String title, String description, Color color) {
        JButton button = new JButton();
        button.setLayout(new BorderLayout(15, 0)); // Espacio entre icono y texto
        button.setPreferredSize(new Dimension(320, 130)); // Un poco más ancho para el icono
        button.setBackground(BACKGROUND_WHITE);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_LIGHT, 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel iconLabel = new JLabel(icon);
        button.add(iconLabel, BorderLayout.WEST);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(HEADER_FONT);
        titleLabel.setForeground(color);

        JLabel descLabel = new JLabel("<html><body style='width: 180px'>" + description + "</body></html>");
        descLabel.setFont(NORMAL_FONT); // Fuente normal para la descripción
        descLabel.setForeground(TEXT_SECONDARY);

        JPanel textPanel = new JPanel(new BorderLayout(0, 8));
        textPanel.setOpaque(false);
        textPanel.add(titleLabel, BorderLayout.NORTH);
        textPanel.add(descLabel, BorderLayout.CENTER);

        button.add(textPanel, BorderLayout.CENTER);

        // Efecto hover mejorado
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(SELECTED_BG);
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(color, 3),
                        BorderFactory.createEmptyBorder(19, 19, 19, 19)
                ));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(BACKGROUND_WHITE);
                button.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_LIGHT, 2),
                        BorderFactory.createEmptyBorder(20, 20, 20, 20)
                ));
            }
        });

        return button;
    }

    // ============================================================================
    // MÉTODOS PARA TABLAS MEJORADOS
    // ============================================================================
    /**
     * Configura el estilo de una tabla - Versión mejorada
     *
     * @param table Tabla a configurar
     * @param headerColor Color del encabezado
     */
    public static void configureTable(JTable table, Color headerColor) {
        table.setRowHeight(30);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setFont(NORMAL_FONT);
        table.setForeground(TEXT_PRIMARY);
// Esto garantiza que el estilo se aplique correctamente en todos los sistemas operativos.
        table.getTableHeader().setDefaultRenderer(new StyledHeaderRenderer(headerColor));
        table.getTableHeader().setPreferredSize(new Dimension(0, 40)); // Un poco más de altura para el encabezado

        // Configuración del encabezado mejorada
        table.getTableHeader().setBackground(headerColor);
        table.getTableHeader().setForeground(TEXT_WHITE);
        table.getTableHeader().setFont(TABLE_HEADER_FONT);
        table.getTableHeader().setPreferredSize(new Dimension(0, 35));
        table.getTableHeader().setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Colores de la tabla
        table.setGridColor(BORDER_LIGHT);
        table.setShowGrid(true);
        table.setIntercellSpacing(new Dimension(1, 1));
        table.setSelectionBackground(SELECTED_BG);
        table.setSelectionForeground(TEXT_PRIMARY);

        // Colores alternados en las filas
        table.setDefaultRenderer(Object.class, new AlternateRowRenderer());
    }

    /**
     * Renderer personalizado para filas alternadas
     */
    private static class AlternateRowRenderer extends javax.swing.table.DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (!isSelected) {
                if (row % 2 == 0) {
                    c.setBackground(BACKGROUND_WHITE);
                } else {
                    c.setBackground(BACKGROUND_LIGHT);
                }
            }

            setFont(NORMAL_FONT);
            setForeground(TEXT_PRIMARY);
            setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

            return c;
        }
    }

    /**
     * Crea un scroll pane estilizado para tablas - Mejorado
     *
     * @param table Tabla a envolver
     * @return JScrollPane configurado
     */
    public static JScrollPane createTableScrollPane(JTable table) {
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_MEDIUM, 1));
        scrollPane.getViewport().setBackground(BACKGROUND_WHITE);
        scrollPane.setBackground(BACKGROUND_WHITE);
        return scrollPane;
    }

    // ============================================================================
    // MÉTODOS PARA PANELES CON BORDES MEJORADOS
    // ============================================================================
    /**
     * Crea un borde con título estilizado - Mejorado
     *
     * @param title Título del borde
     * @return Border configurado
     */
    public static Border createTitledBorder(String title) {
        return BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(BORDER_MEDIUM, 2),
                title,
                0, 0, SUBHEADER_FONT, PRIMARY_BLUE
        );
    }

    /**
     * Crea un panel con borde y título - Mejorado
     *
     * @param title Título del panel
     * @param layout Layout manager del panel
     * @return JPanel configurado
     */
    public static JPanel createTitledPanel(String title, LayoutManager layout) {
        JPanel panel = new JPanel(layout);
        panel.setBorder(createTitledBorder(title));
        panel.setBackground(BACKGROUND_WHITE);
        return panel;
    }

    // ============================================================================
    // MÉTODOS PARA CAMPOS DE FORMULARIO MEJORADOS
    // ============================================================================
    /**
     * Crea un campo de texto estilizado - Mejorado
     *
     * @param columns Número de columnas
     * @return JTextField configurado
     */
    public static JTextField createStyledTextField(int columns) {
        JTextField textField = new JTextField(columns);
        textField.setFont(NORMAL_FONT);
        textField.setForeground(TEXT_PRIMARY);
        textField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_MEDIUM, 2),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        textField.setPreferredSize(new Dimension(textField.getPreferredSize().width, 35));

        // Efecto focus
        textField.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                textField.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(PRIMARY_BLUE, 2),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                textField.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(BORDER_MEDIUM, 2),
                        BorderFactory.createEmptyBorder(8, 12, 8, 12)
                ));
            }
        });

        return textField;
    }

    /**
     * Crea un área de texto estilizada - Mejorada
     *
     * @param rows Número de filas
     * @param columns Número de columnas
     * @return JTextArea configurada
     */
    public static JTextArea createStyledTextArea(int rows, int columns) {
        JTextArea textArea = new JTextArea(rows, columns);
        textArea.setFont(NORMAL_FONT);
        textArea.setForeground(TEXT_PRIMARY);
        textArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_MEDIUM, 2),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        return textArea;
    }

    /**
     * Crea un combo box estilizado - Mejorado
     *
     * @param items Array de elementos
     * @return JComboBox configurado
     */
    public static JComboBox<String> createStyledComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setFont(NORMAL_FONT);
        comboBox.setForeground(TEXT_PRIMARY);
        comboBox.setBackground(BACKGROUND_WHITE);
        comboBox.setPreferredSize(new Dimension(comboBox.getPreferredSize().width, 35));
        return comboBox;
    }

    // ============================================================================
    // MÉTODOS PARA TARJETAS DE ESTADÍSTICAS MEJORADAS
    // ============================================================================
    /**
     * Crea una tarjeta de estadística - Mejorada
     *
     * @param title Título de la estadística
     * @param value Valor de la estadística
     * @param color Color del valor
     * @return JPanel configurado como tarjeta
     */
    public static JPanel createStatCard(String title, JLabel valueLabel, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(BACKGROUND_WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_LIGHT, 2),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setMaximumSize(new Dimension(190, 100)); // Tamaño máximo para consistencia
        card.setPreferredSize(new Dimension(180, 90)); // Tamaño preferido

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(SUBHEADER_FONT);
        titleLabel.setForeground(TEXT_SECONDARY);

        valueLabel.setHorizontalAlignment(SwingConstants.CENTER);
        valueLabel.setFont(new Font(FONT_FAMILY, Font.BOLD, 28)); // Fuente más grande para el valor
        valueLabel.setForeground(color);

        card.add(titleLabel, BorderLayout.NORTH);
        card.add(valueLabel, BorderLayout.CENTER);

        return card;
    }

    // ============================================================================
    // MÉTODOS PARA PANELES DE BÚSQUEDA MEJORADOS
    // ============================================================================
    /**
     * Crea un panel de búsqueda horizontal - Mejorado
     *
     * @param labelText Texto de la etiqueta
     * @param textFieldColumns Columnas del campo de texto
     * @return JPanel configurado para búsqueda
     */
    public static JPanel createSearchPanel(String labelText, int textFieldColumns) {
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        searchPanel.setBackground(BACKGROUND_WHITE);
        searchPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_LIGHT, 1),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        JLabel label = new JLabel(labelText);
        label.setFont(SUBHEADER_FONT);
        label.setForeground(TEXT_PRIMARY);
        searchPanel.add(label);

        JTextField searchField = createStyledTextField(textFieldColumns);
        searchPanel.add(searchField);

        JButton searchButton = createStyledButton("Buscar", PRIMARY_BLUE);
        searchPanel.add(searchButton);

        return searchPanel;
    }

    // ============================================================================
    // MÉTODOS AUXILIARES MEJORADOS
    // ============================================================================
    /**
     * Oscurece un color en un porcentaje dado
     *
     * @param color Color original
     * @param factor Factor de oscurecimiento (0.0 a 1.0)
     * @return Color oscurecido
     */
    public static Color darkenColor(Color color, float factor) {
        return new Color(
                Math.max(0, (int) (color.getRed() * (1 - factor))),
                Math.max(0, (int) (color.getGreen() * (1 - factor))),
                Math.max(0, (int) (color.getBlue() * (1 - factor)))
        );
    }

    /**
     * Aclara un color en un porcentaje dado
     *
     * @param color Color original
     * @param factor Factor de aclarado (0.0 a 1.0)
     * @return Color aclarado
     */
    public static Color lightenColor(Color color, float factor) {
        return new Color(
                Math.min(255, (int) (color.getRed() + (255 - color.getRed()) * factor)),
                Math.min(255, (int) (color.getGreen() + (255 - color.getGreen()) * factor)),
                Math.min(255, (int) (color.getBlue() + (255 - color.getBlue()) * factor))
        );
    }

    /**
     * Configura una ventana con propiedades comunes - Mejorado
     *
     * @param window Ventana a configurar
     * @param title Título de la ventana
     * @param width Ancho de la ventana
     * @param height Alto de la ventana
     * @param centerOnScreen Si debe centrarse en pantalla
     * @param resizable Si la ventana es redimensionable
     */
    public static void configureWindow(Window window, String title, int width, int height,
            boolean centerOnScreen, boolean resizable) {
        if (window instanceof JFrame) {
            ((JFrame) window).setTitle(title);
            ((JFrame) window).setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            ((JFrame) window).setResizable(resizable);
        } else if (window instanceof JDialog) {
            ((JDialog) window).setTitle(title);
            ((JDialog) window).setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            ((JDialog) window).setResizable(resizable);
        }

        window.setSize(width, height);
        if (centerOnScreen) {
            window.setLocationRelativeTo(null);
        }
    }

    /**
     * Añade un campo al formulario usando GridBagLayout - Mejorado
     *
     * @param panel Panel contenedor
     * @param labelText Texto de la etiqueta
     * @param field Campo a añadir
     * @param gbc Constraints del GridBagLayout
     * @param row Fila donde colocar el campo
     */
    public static void addFormField(JPanel panel, String labelText, JComponent field,
            GridBagConstraints gbc, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(8, 0, 8, 15);

        JLabel label = new JLabel(labelText);
        label.setFont(SUBHEADER_FONT);
        label.setForeground(TEXT_PRIMARY);
        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.insets = new Insets(8, 0, 8, 0);
        panel.add(field, gbc);
    }

    /**
     * Crea un separador horizontal estilizado - Mejorado
     *
     * @return JSeparator configurado
     */
    public static JSeparator createStyledSeparator() {
        JSeparator separator = new JSeparator();
        separator.setForeground(BORDER_MEDIUM);
        separator.setBackground(BORDER_MEDIUM);
        return separator;
    }

    /**
     * Crea un botón estilizado con icono y texto, con tamaño flexible.
     *
     * @param text Texto del botón.
     * @param icon Icono para el botón.
     * @param color Color de fondo del botón.
     * @return JButton estilizado.
     */
    public static JButton createIconButton(String text, ImageIcon icon, Color color) {
        // Usamos la base de nuestro botón estilizado
        JButton button = createStyledButton(text, color);

        // <<< CAMBIO CLAVE: Eliminamos el tamaño fijo y ajustamos el contenido >>>
        button.setPreferredSize(null); // Dejar que el botón calcule su propio tamaño
        button.setIcon(icon);
        button.setIconTextGap(10); // Espacio entre el icono y el texto
        button.setHorizontalAlignment(SwingConstants.LEFT); // Alinear contenido a la izquierda
        button.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15)); // Ajustar padding

        return button;
    }

    /**
     * Crea espaciado horizontal
     *
     * @param width Ancho del espaciado
     * @return Component configurado como espaciado
     */
    public static Component createHorizontalStrut(int width) {
        return Box.createHorizontalStrut(width);
    }

    /**
     * Crea espaciado vertical
     *
     * @param height Alto del espaciado
     * @return Component configurado como espaciado
     */
    public static Component createVerticalStrut(int height) {
        return Box.createVerticalStrut(height);
    }

    // Agrega este método a tu clase DesignUtils.java
    /**
     * Crea un botón "fantasma" estilizado, ideal para acciones secundarias.
     *
     * @param text Texto del botón.
     * @return JButton estilizado como acción secundaria.
     */
    public static JButton createGhostButton(String text) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setForeground(TEXT_SECONDARY); // Color de texto secundario
        button.setBackground(BACKGROUND_WHITE); // Fondo transparente
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Sin borde visible inicial
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFocusPainted(false);

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setOpaque(true);
                button.setContentAreaFilled(true);
                button.setBackground(new Color(0, 0, 0, 10)); // Un gris muy sutil al pasar el ratón
                button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setOpaque(false);
                button.setContentAreaFilled(false);
                button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
            }
        });

        return button;
    }
    private static class StyledHeaderRenderer extends javax.swing.table.DefaultTableCellRenderer {
    private Color backgroundColor;

    public StyledHeaderRenderer(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        
        // Se obtiene el componente JLabel base
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        
        // Se aplican los estilos deseados
        label.setBackground(backgroundColor);
        label.setForeground(TEXT_WHITE); // Texto blanco, que contrasta bien con fondos oscuros
        label.setFont(TABLE_HEADER_FONT);
        label.setHorizontalAlignment(SwingConstants.CENTER); // Centrar texto del encabezado
        label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Añadir padding
        
        return label;
    }
}
}
