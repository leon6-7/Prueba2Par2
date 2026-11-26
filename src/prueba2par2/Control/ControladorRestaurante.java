/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba2par2.Control;
import java.util.ArrayList;
import prueba2par2.Modelo.*;
import prueba2par2.Excepciones.CodigoDuplicadoException;
import prueba2par2.Excepciones.PedidoVacioException;

/**
 *
 * @author Abril
 */
public class ControladorRestaurante implements GestionProductos, GestionPedidos {

    private Catalogo catalogo;
    private ArrayList<Pedido> pedidos;
    private int siguienteId;

    public ControladorRestaurante() {
        catalogo = new Catalogo();
        pedidos = new ArrayList<>();
        siguienteId = 1;
    }

    @Override
    public void registrarProducto(Producto producto)
            throws CodigoDuplicadoException {

        catalogo.registrarProducto(producto);
    }

    @Override
    public Producto buscarProducto(String codigo) {
        return catalogo.buscarProducto(codigo);
    }

    @Override
    public ArrayList<Producto> listarProductos() {
        return catalogo.listarProductos();
    }

    @Override
    public Pedido crearPedido(ModalidadServicio modalidad,
            ArrayList<DetallePedido> detalles)
            throws PedidoVacioException {

        Pedido pedido = new Pedido(siguienteId, modalidad, detalles);

        pedidos.add(pedido);
        siguienteId++;

        return pedido;
    }

    @Override
    public Pedido buscarPedido(int id) {

        for (Pedido pedido : pedidos) {
            if (pedido.getId() == id) {
                return pedido;
            }
        }

        return null;
    }

    @Override
    public void cambiarEstado(int id, EstadoPedido estado) {

        Pedido pedido = buscarPedido(id);

        if (pedido == null) {
            throw new IllegalArgumentException(
                    "El pedido no existe.");
        }

        if (estado == null) {
            throw new IllegalArgumentException(
                    "Estado invalido.");
        }

        pedido.setEstado(estado);
    }

    @Override
    public ArrayList<Pedido> listarPedidos() {
        return new ArrayList<>(pedidos);
    }

    public Catalogo getCatalogo() {
        return catalogo;
    }

    public void setCatalogo(Catalogo catalogo) {
        this.catalogo = catalogo;
    }

    public void setPedidos(ArrayList<Pedido> pedidos) {

        this.pedidos = new ArrayList<>(pedidos);
        siguienteId = 1;

        for (Pedido pedido : pedidos) {
            if (pedido.getId() >= siguienteId) {
                siguienteId = pedido.getId() + 1;
            }
        }
    }
}
