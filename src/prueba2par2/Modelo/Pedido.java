/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba2par2.Modelo;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import prueba2par2.Control.CalculadoraImporte;
import prueba2par2.Excepciones.PedidoVacioException;

/*
 *
 * @author Abril
 */
public class Pedido implements Serializable {

    private int id;
    private Calendar fecha;
    private ArrayList<DetallePedido> detalles;
    private ModalidadServicio modalidad;
    private EstadoPedido estado;

    public Pedido(int id, ModalidadServicio modalidad,
            ArrayList<DetallePedido> detalles)
            throws PedidoVacioException {

        if (detalles == null || detalles.isEmpty()) {
            throw new PedidoVacioException(
                    "El pedido debe contener al menos un producto.");
        }

        this.id = id;
        this.modalidad = modalidad;
        this.detalles = new ArrayList<>(detalles);
        this.fecha = Calendar.getInstance();
        this.estado = EstadoPedido.PENDIENTE;
    }

    public int getId() {
        return id;
    }

    public Calendar getFecha() {
        return (Calendar) fecha.clone();
    }

    public ModalidadServicio getModalidad() {
        return modalidad;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public ArrayList<DetallePedido> getDetalles() {
        return new ArrayList<>(detalles);
    }

    public void agregarDetalle(DetallePedido detalle) {
        detalles.add(detalle);
    }

    public void eliminarDetalle(int indice)
            throws PedidoVacioException {

        if (indice < 0 || indice >= detalles.size()) {
            throw new IllegalArgumentException(
                    "Indice invalido.");
        }

        if (detalles.size() == 1) {
            throw new PedidoVacioException(
                    "El pedido no puede quedar vacio.");
        }

        detalles.remove(indice);
    }

    public double getTotal() {
        return CalculadoraImporte.calcularTotal(detalles);
    }

    @Override
    public String toString() {
        return "Pedido #" + id + " - " + modalidad
                + " - " + estado;
    }
}
