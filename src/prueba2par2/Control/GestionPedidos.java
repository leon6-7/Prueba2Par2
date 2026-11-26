/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba2par2.Control;
import java.util.ArrayList;
import prueba2par2.Modelo.DetallePedido;
import prueba2par2.Modelo.EstadoPedido;
import prueba2par2.Modelo.ModalidadServicio;
import prueba2par2.Modelo.Pedido;
import prueba2par2.Excepciones.PedidoVacioException;

/**
 *
 * @author Abril
 */
public interface GestionPedidos {

    Pedido crearPedido(ModalidadServicio modalidad,
            ArrayList<DetallePedido> detalles)
            throws PedidoVacioException;

    Pedido buscarPedido(int id);

    void cambiarEstado(int id, EstadoPedido estado);

    ArrayList<Pedido> listarPedidos();
}
