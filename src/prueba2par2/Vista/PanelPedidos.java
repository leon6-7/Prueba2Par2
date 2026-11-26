/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba2par2.Vista;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import prueba2par2.Modelo.*;
import prueba2par2.Control.*;

/**
 *
 * @author Abril
 */
public class PanelPedidos extends JPanel {

    private ControladorRestaurante controlador;
    private VistaRestaurante vista;

    private ArrayList<DetallePedido> detalles;

    private JComboBox<Producto> productos;
    private JComboBox<ModalidadServicio> modalidad;
    private JSpinner cantidad;

    private DefaultTableModel modelo;
    private JTable tabla;
    private JLabel total;

    public PanelPedidos(ControladorRestaurante controlador,
            VistaRestaurante vista) {

        this.controlador = controlador;
        this.vista = vista;

        detalles = new ArrayList<>();

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel formulario = new JPanel(new GridBagLayout());

        productos = new JComboBox<>();
        modalidad = new JComboBox<>(ModalidadServicio.values());

        cantidad = new JSpinner(
                new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));

        JButton agregar = new JButton("Agregar producto");
        JButton eliminar = new JButton("Eliminar producto");
        JButton confirmar = new JButton("Confirmar pedido");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.gridy = 0;
        formulario.add(new JLabel("Producto:"), gbc);

        gbc.gridx = 1;
        formulario.add(productos, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        formulario.add(new JLabel("Cantidad:"), gbc);

        gbc.gridx = 1;
        formulario.add(cantidad, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        formulario.add(new JLabel("Modalidad:"), gbc);

        gbc.gridx = 1;
        formulario.add(modalidad, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        formulario.add(agregar, gbc);

        gbc.gridx = 1;
        formulario.add(eliminar, gbc);

        modelo = new DefaultTableModel(
                new String[]{"Codigo", "Producto", "Cantidad",
                    "Precio", "Subtotal"}, 0) {

            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tabla = new JTable(modelo);

        total = new JLabel("Total: L. 0.00");
        total.setFont(new Font("Arial", Font.BOLD, 18));

        JPanel inferior = new JPanel(new BorderLayout());

        inferior.add(total, BorderLayout.WEST);
        inferior.add(confirmar, BorderLayout.EAST);

        add(formulario, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(inferior, BorderLayout.SOUTH);

        agregar.addActionListener(e -> agregarProducto());
        eliminar.addActionListener(e -> eliminarProducto());
        confirmar.addActionListener(e -> confirmarPedido());

        refrescarProductos();
    }

    private void agregarProducto() {

        try {
            Producto producto = (Producto) productos.getSelectedItem();

            if (producto == null) {
                throw new IllegalArgumentException(
                        "Seleccione un producto.");
            }

            int cant = (int) cantidad.getValue();

            for (DetallePedido detalle : detalles) {

                if (detalle.getProducto().getCodigo()
                        .equalsIgnoreCase(producto.getCodigo())) {

                    detalle.setCantidad(detalle.getCantidad() + cant);

                    refrescarTabla();
                    return;
                }
            }

            detalles.add(new DetallePedido(producto, cant));

            cantidad.setValue(1);
            refrescarTabla();

        } catch (Exception e) {
            vista.mostrarMensaje(e.getMessage());
        }
    }

    private void eliminarProducto() {

        int fila = tabla.getSelectedRow();

        if (fila == -1) {
            vista.mostrarMensaje("Seleccione un producto de la tabla.");
            return;
        }

        detalles.remove(fila);
        refrescarTabla();
    }

    private void confirmarPedido() {

        try {
            ModalidadServicio servicio =
                    (ModalidadServicio) modalidad.getSelectedItem();

            Pedido pedido = controlador.crearPedido(servicio, detalles);

            detalles.clear();
            cantidad.setValue(1);

            refrescarTabla();
            vista.refrescarTablas();

            vista.mostrarMensaje(
                    "Pedido #" + pedido.getId() + " registrado.");

        } catch (Exception e) {
            vista.mostrarMensaje(e.getMessage());
        }
    }

    public void refrescarProductos() {

        productos.removeAllItems();

        for (Producto producto : controlador.listarProductos()) {
            productos.addItem(producto);
        }
    }

    public void refrescarTabla() {

        modelo.setRowCount(0);

        for (DetallePedido detalle : detalles) {

            Producto producto = detalle.getProducto();

            modelo.addRow(new Object[]{
                producto.getCodigo(),
                producto.getNombre(),
                detalle.getCantidad(),
                String.format("L. %.2f", producto.getPrecio()),
                String.format("L. %.2f", detalle.getSubtotal())
            });
        }

        double importe = CalculadoraImporte.calcularTotal(detalles);

        total.setText(String.format("Total: L. %.2f", importe));
    }
}
