/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba2par2.Vista;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author Abril
 */
public class VentanaPrincipal extends JFrame implements VistaRestaurante {

    private static final long serialVersionUID = 1L;

    public static final String VISTA_INICIO = "inicio";
    public static final String VISTA_PRODUCTOS = "productos";
    public static final String VISTA_PEDIDOS = "pedidos";
    public static final String VISTA_CONSULTA = "consulta";
    public static final String VISTA_ALMACENAMIENTO = "almacenamiento";

    private final CardLayout disposicion = new CardLayout();
    private final JPanel contenedor = new JPanel(disposicion);
    private final Set<String> vistasRegistradas = new HashSet<>();
    private final List<Runnable> oyentesRefresco = new ArrayList<>();

    public VentanaPrincipal() {
        super("Sistema de Gestión de Pedidos - Restaurante");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(950, 620);
        setMinimumSize(new Dimension(800, 500));
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        add(construirEncabezado(), BorderLayout.NORTH);
        add(construirMenuNavegacion(), BorderLayout.WEST);
        add(contenedor, BorderLayout.CENTER);

        agregarPanel(VISTA_INICIO, construirPanelInicio());
        mostrarPanel(VISTA_INICIO);
    }

    private JPanel construirEncabezado() {
        JLabel titulo = new JLabel("Sistema de Gestión de Pedidos del Restaurante", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);

        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setBackground(new Color(120, 40, 30));
        encabezado.setBorder(BorderFactory.createEmptyBorder(14, 10, 14, 10));
        encabezado.add(titulo, BorderLayout.CENTER);
        return encabezado;
    }

    private JPanel construirMenuNavegacion() {
        JPanel menu = new JPanel(new GridLayout(0, 1, 0, 10));
        menu.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        menu.setPreferredSize(new Dimension(210, 0));

        menu.add(crearBotonNavegacion("Inicio", VISTA_INICIO));
        menu.add(crearBotonNavegacion("Gestión de productos", VISTA_PRODUCTOS));
        menu.add(crearBotonNavegacion("Gestión de pedidos", VISTA_PEDIDOS));
        menu.add(crearBotonNavegacion("Consulta de pedidos", VISTA_CONSULTA));
        menu.add(crearBotonNavegacion("Almacenamiento", VISTA_ALMACENAMIENTO));
        return menu;
    }

    private JButton crearBotonNavegacion(String texto, String vista) {
        JButton boton = new JButton(texto);
        boton.setFocusPainted(false);
        boton.addActionListener(e -> mostrarPanel(vista));
        return boton;
    }

    private JPanel construirPanelInicio() {
        JLabel bienvenida = new JLabel(
                "<html><div style='text-align:center'>"
                + "<h2>Bienvenido</h2>"
                + "Use el menú de la izquierda para administrar los productos,<br>"
                + "registrar pedidos, consultar su estado y guardar la información."
                + "</div></html>", SwingConstants.CENTER);
        JPanel inicio = new JPanel(new BorderLayout());
        inicio.add(bienvenida, BorderLayout.CENTER);
        return inicio;
    }


    public void agregarPanel(String nombre, JPanel panel) {
        contenedor.add(panel, nombre);
        vistasRegistradas.add(nombre);
    }

    public void agregarOyenteRefresco(Runnable oyente) {
        oyentesRefresco.add(oyente);
    }

    public void mostrarPanel(String nombre) {
        if (!vistasRegistradas.contains(nombre)) {
            mostrarMensaje("Esta vista aún no está disponible.");
            return;
        }
        refrescarTablas();
        disposicion.show(contenedor, nombre);
    }


    @Override
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    public void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void refrescarTablas() {
        for (Runnable oyente : oyentesRefresco) {
            oyente.run();
        }
    }
}
