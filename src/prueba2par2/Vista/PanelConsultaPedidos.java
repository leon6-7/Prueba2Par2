/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba2par2.Vista;
import java.awt.*;
import java.text.SimpleDateFormat;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import prueba2par2.Modelo.*;
import prueba2par2.Control.ControladorRestaurante;

/**
 *
 * @author Abril
 */

public class PanelConsultaPedidos extends JPanel {

    private ControladorRestaurante controlador;
    private VistaRestaurante vista;

    private JTable tablaPedidos, tablaDetalles;
    private DefaultTableModel modeloPedidos, modeloDetalles;

    private JComboBox<EstadoPedido> estados;

    public PanelConsultaPedidos(ControladorRestaurante controlador,
            VistaRestaurante vista) {

        this.controlador = controlador;
        this.vista = vista;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        modeloPedidos = new DefaultTableModel(
                new String[]{"ID", "Fecha", "Modalidad",
                    "Estado", "Total"}, 0) {

            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        modeloDetalles = new DefaultTableModel(
                new String[]{"Producto", "Cantidad",
                    "Precio", "Subtotal"}, 0) {

            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tablaPedidos = new JTable(modeloPedidos);
        tablaDetalles = new JTable(modeloDetalles);

        tablaPedidos.setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION);

        JPanel panelTablas = new JPanel(new GridLayout(2, 1, 10, 10));

        JPanel superior = new JPanel(new BorderLayout());
        superior.setBorder(BorderFactory.createTitledBorder("Pedidos"));
        superior.add(new JScrollPane(tablaPedidos));

        JPanel inferior = new JPanel(new BorderLayout());
        inferior.setBorder(BorderFactory.createTitledBorder(
                "Detalle del pedido"));
        inferior.add(new JScrollPane(tablaDetalles));

        panelTablas.add(superior);
        panelTablas.add(inferior);

        JPanel controles = new JPanel(new FlowLayout());

        estados = new JComboBox<>(EstadoPedido.values());

        JButton cambiar = new JButton("Cambiar estado");

        controles.add(new JLabel("Nuevo estado:"));
        controles.add(estados);
        controles.add(cambiar);

        add(panelTablas, BorderLayout.CENTER);
        add(controles, BorderLayout.SOUTH);

        tablaPedidos.getSelectionModel().addListSelectionListener(e -> {

            if (!e.getValueIsAdjusting()) {
                mostrarDetalles();
            }
        });

        cambiar.addActionListener(e -> cambiarEstado());

        refrescarTabla();
    }

    private Pedido obtenerPedido() {

        int fila = tablaPedidos.getSelectedRow();

        if (fila == -1) {
            return null;
        }

        int id = (int) modeloPedidos.getValueAt(fila, 0);

        return controlador.buscarPedido(id);
    }

    private void mostrarDetalles() {

        modeloDetalles.setRowCount(0);

        Pedido pedido = obtenerPedido();

        if (pedido == null) {
            return;
        }

        estados.setSelectedItem(pedido.getEstado());

        for (DetallePedido detalle : pedido.getDetalles()) {

            modeloDetalles.addRow(new Object[]{
                detalle.getProducto().getNombre(),
                detalle.getCantidad(),
                String.format("L. %.2f",
                        detalle.getProducto().getPrecio()),
                String.format("L. %.2f", detalle.getSubtotal())
            });
        }
    }

    private void cambiarEstado() {

        try {
            Pedido pedido = obtenerPedido();

            if (pedido == null) {
                throw new IllegalArgumentException(
                        "Seleccione un pedido.");
            }

            EstadoPedido estado =
                    (EstadoPedido) estados.getSelectedItem();

            controlador.cambiarEstado(pedido.getId(), estado);

            refrescarTabla();
            vista.refrescarTablas();

            vista.mostrarMensaje("Estado actualizado correctamente.");

        } catch (Exception e) {
            vista.mostrarMensaje(e.getMessage());
        }
    }

    public void refrescarTabla() {

        modeloPedidos.setRowCount(0);
        modeloDetalles.setRowCount(0);

        SimpleDateFormat formato =
                new SimpleDateFormat("dd/MM/yyyy HH:mm");

        for (Pedido pedido : controlador.listarPedidos()) {

            modeloPedidos.addRow(new Object[]{
                pedido.getId(),
                formato.format(pedido.getFecha().getTime()),
                pedido.getModalidad(),
                pedido.getEstado(),
                String.format("L. %.2f", pedido.getTotal())
            });
        }
    }
}