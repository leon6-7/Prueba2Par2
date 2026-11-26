/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba2par2.Vista;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import prueba2par2.Modelo.*;
import prueba2par2.Control.ControladorRestaurante;

/**
 *
 * @author Abril
 */
public class PanelProductos extends JPanel {

    private ControladorRestaurante controlador;
    private VistaRestaurante vista;

    private JTextField codigo, nombre, precio, atributo1, atributo2;
    private JLabel label1, label2;
    private JComboBox<CategoriaProducto> categoria;
    private DefaultTableModel modelo;
    private JTable tabla;

    public PanelProductos(ControladorRestaurante controlador,
            VistaRestaurante vista) {

        this.controlador = controlador;
        this.vista = vista;

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel formulario = new JPanel(new GridBagLayout());

        codigo = new JTextField(15);
        nombre = new JTextField(15);
        precio = new JTextField(15);
        atributo1 = new JTextField(15);
        atributo2 = new JTextField(15);

        categoria = new JComboBox<>(CategoriaProducto.values());

        label1 = new JLabel("Tipo de plato:");
        label2 = new JLabel("Porciones:");

        agregarCampo(formulario, new JLabel("Codigo:"), codigo, 0);
        agregarCampo(formulario, new JLabel("Nombre:"), nombre, 1);
        agregarCampo(formulario, new JLabel("Precio:"), precio, 2);
        agregarCampo(formulario, new JLabel("Categoria:"), categoria, 3);
        agregarCampo(formulario, label1, atributo1, 4);
        agregarCampo(formulario, label2, atributo2, 5);

        JButton registrar = new JButton("Registrar producto");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 5, 5, 5);

        formulario.add(registrar, gbc);

        modelo = new DefaultTableModel(
                new String[]{"Codigo", "Nombre", "Precio",
                    "Categoria", "Informacion"}, 0) {

            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };

        tabla = new JTable(modelo);

        add(formulario, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        categoria.addActionListener(e -> cambiarCampos());
        registrar.addActionListener(e -> registrarProducto());

        refrescarTabla();
    }

    private void agregarCampo(JPanel panel, JLabel label,
            JComponent campo, int fila) {

        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridy = fila;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        panel.add(label, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        panel.add(campo, gbc);
    }

    private void cambiarCampos() {

        CategoriaProducto tipo =
                (CategoriaProducto) categoria.getSelectedItem();

        switch (tipo) {

            case PLATO:
                label1.setText("Tipo de plato:");
                label2.setText("Porciones:");
                break;

            case BEBIDA:
                label1.setText("Mililitros:");
                label2.setText("Alcohol (Si/No):");
                break;

            case POSTRE:
                label1.setText("Sabor:");
                label2.setText("Apto diabeticos (Si/No):");
                break;
        }

        atributo1.setText("");
        atributo2.setText("");
    }

    private void registrarProducto() {

        try {
            String cod = codigo.getText().trim();
            String nom = nombre.getText().trim();
            double pre = Double.parseDouble(precio.getText());

            String valor1 = atributo1.getText().trim();
            String valor2 = atributo2.getText().trim();

            if (cod.isEmpty() || nom.isEmpty()
                    || valor1.isEmpty() || valor2.isEmpty()) {

                throw new IllegalArgumentException(
                        "Complete todos los campos.");
            }

            if (!Double.isFinite(pre) || pre <= 0) {
                throw new IllegalArgumentException("Precio invalido.");
            }

            CategoriaProducto tipo =
                    (CategoriaProducto) categoria.getSelectedItem();

            Producto producto;

            switch (tipo) {

                case PLATO:

                    int porciones = Integer.parseInt(valor2);

                    if (porciones <= 0) {
                        throw new IllegalArgumentException(
                                "Cantidad de porciones invalida.");
                    }

                    producto = new Plato(cod, nom, pre,
                            valor1, porciones);
                    break;

                case BEBIDA:

                    int ml = Integer.parseInt(valor1);

                    if (ml <= 0) {
                        throw new IllegalArgumentException(
                                "Cantidad de mililitros invalida.");
                    }

                    producto = new Bebida(cod, nom, pre,
                            ml, convertirBoolean(valor2));
                    break;

                case POSTRE:

                    producto = new Postre(cod, nom, pre,
                            valor1, convertirBoolean(valor2));
                    break;

                default:
                    throw new IllegalArgumentException(
                            "Categoria invalida.");
            }

            controlador.registrarProducto(producto);

            limpiarCampos();
            refrescarTabla();
            vista.refrescarTablas();
            vista.mostrarMensaje("Producto registrado correctamente.");

        } catch (Exception e) {
            vista.mostrarMensaje(e.getMessage());
        }
    }

    private boolean convertirBoolean(String texto) {

        if (texto.equalsIgnoreCase("si")) {
            return true;
        }

        if (texto.equalsIgnoreCase("no")) {
            return false;
        }

        throw new IllegalArgumentException("Ingrese Si o No.");
    }

    private void limpiarCampos() {

        codigo.setText("");
        nombre.setText("");
        precio.setText("");
        atributo1.setText("");
        atributo2.setText("");
    }

    public void refrescarTabla() {

    modelo.setRowCount(0);

    for (Producto producto : controlador.listarProductos()) {

        modelo.addRow(new Object[]{
            producto.getCodigo(),
            producto.getNombre(),
            String.format("L. %.2f", producto.getPrecio()),
            producto.getCategoria(),
            producto.getDetalleEspecifico()
            });
        }
    }
}
