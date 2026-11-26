/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba2par2.Modelo;
import java.io.Serializable;
import prueba2par2.Excepciones.CantidadInvalidaException;

/**
 *
 * @author Abril
 */
public class DetallePedido implements Serializable {

    private Producto producto;
    private int cantidad;

    public DetallePedido(Producto producto, int cantidad)
            throws CantidadInvalidaException {

        this.producto = producto;
        setCantidad(cantidad);
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad)
            throws CantidadInvalidaException {

        if (cantidad <= 0) {
            throw new CantidadInvalidaException(
                    "La cantidad debe ser mayor a cero.");
        }

        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return producto.getPrecio() * cantidad;
    }

    @Override
    public String toString() {
        return producto.getNombre() + " x" + cantidad
                + " - L. " + getSubtotal();
    }
}
