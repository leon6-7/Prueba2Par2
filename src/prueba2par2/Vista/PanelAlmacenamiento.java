/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba2par2.Vista;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import prueba2par2.Modelo.*;
import prueba2par2.Control.ControladorRestaurante;
import prueba2par2.Persistencia.*;
import prueba2par2.Excepciones.ErrorPersistenciaException;

/**
 *
 * @author Abril
 */

public class PanelAlmacenamiento extends JPanel {

    private ControladorRestaurante controlador;
    private VistaRestaurante vista;

    private ArchivoProductos archivoProductos;
    private ArchivoPedidos archivoPedidos;

    private JTextArea resultado;

    public PanelAlmacenamiento(ControladorRestaurante controlador,
            VistaRestaurante vista) {

        this.controlador = controlador;
        this.vista = vista;

        archivoProductos = new ArchivoProductos();
        archivoPedidos = new ArchivoPedidos();

        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel botones = new JPanel(new FlowLayout());

        JButton guardar = new JButton("Guardar informacion");
        JButton recuperar = new JButton("Recuperar informacion");

        botones.add(guardar);
        botones.add(recuperar);

        resultado = new JTextArea();
        resultado.setEditable(false);
        resultado.setFont(new Font("Monospaced", Font.PLAIN, 14));

        add(botones, BorderLayout.NORTH);
        add(new JScrollPane(resultado), BorderLayout.CENTER);

        guardar.addActionListener(e -> guardarInformacion());
        recuperar.addActionListener(e -> recuperarInformacion());
    }

    private void guardarInformacion() {

        resultado.setText("");

        try {
            archivoProductos.guardar(controlador.getCatalogo());

            resultado.append("Productos guardados correctamente.\n");

            archivoPedidos.guardar(controlador.listarPedidos());

            resultado.append("Pedidos guardados correctamente.\n");

            resultado.append("\nTotal de productos: "
                    + controlador.listarProductos().size());

            resultado.append("\nTotal de pedidos: "
                    + controlador.listarPedidos().size());

            resultado.append("\n\nArchivos guardados en datos/.");

        } catch (ErrorPersistenciaException e) {
            resultado.append("\nError: " + e.getMessage());
        }
    }

    private void recuperarInformacion() {

        resultado.setText("");

        try {
            Catalogo catalogo = archivoProductos.cargar();
            ArrayList<Pedido> pedidos = archivoPedidos.cargar();

            controlador.setCatalogo(catalogo);
            controlador.setPedidos(pedidos);

            resultado.append("Productos recuperados correctamente.\n");
            resultado.append("Pedidos recuperados correctamente.\n");

            resultado.append("\nTotal de productos: "
                    + controlador.listarProductos().size());

            resultado.append("\nTotal de pedidos: "
                    + controlador.listarPedidos().size());

            vista.refrescarTablas();

        } catch (ErrorPersistenciaException e) {
            resultado.append("\nError: " + e.getMessage());
        }
    }
}
