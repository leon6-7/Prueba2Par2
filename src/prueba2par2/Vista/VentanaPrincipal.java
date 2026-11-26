/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba2par2.Vista;
import java.awt.*;
import javax.swing.*;
import prueba2par2.Control.ControladorRestaurante;

/**
 *
 * @author Abril
 */

public class VentanaPrincipal extends JFrame implements VistaRestaurante {

    private ControladorRestaurante controlador;

    private PanelProductos panelProductos;
    private PanelPedidos panelPedidos;
    private PanelConsultaPedidos panelConsulta;
    private PanelAlmacenamiento panelAlmacenamiento;

    public VentanaPrincipal() {

        controlador = new ControladorRestaurante();

        setTitle("Sistema de Gestion de Restaurante");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Gestion de Restaurante");
        titulo.setFont(new Font("Arial", Font.BOLD, 24));
        titulo.setHorizontalAlignment(SwingConstants.CENTER);
        titulo.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));

        panelProductos = new PanelProductos(controlador, this);
        panelPedidos = new PanelPedidos(controlador, this);
        panelConsulta = new PanelConsultaPedidos(controlador, this);
        panelAlmacenamiento = new PanelAlmacenamiento(controlador, this);

        JTabbedPane pestanas = new JTabbedPane();

        pestanas.addTab("Productos", panelProductos);
        pestanas.addTab("Pedidos", panelPedidos);
        pestanas.addTab("Consultar Pedidos", panelConsulta);
        pestanas.addTab("Almacenamiento", panelAlmacenamiento);

        add(titulo, BorderLayout.NORTH);
        add(pestanas, BorderLayout.CENTER);
    }

    @Override
    public void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje);
    }

    @Override
    public void refrescarTablas() {

        panelProductos.refrescarTabla();
        panelPedidos.refrescarProductos();
        panelPedidos.refrescarTabla();
        panelConsulta.refrescarTabla();
    }
}