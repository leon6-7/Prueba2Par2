/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba2par2.Modelo;
import java.io.Serializable;
import java.util.ArrayList;
import prueba2par2.Excepciones.CodigoDuplicadoException;

/**
 *
 * @author Abril
 */
public class Catalogo implements Serializable {

    private ArrayList<Producto> productos;

    public Catalogo() {
        productos = new ArrayList<>();
    }

    public void registrarProducto(Producto producto)
            throws CodigoDuplicadoException {

        if (buscarProducto(producto.getCodigo()) != null) {
            throw new CodigoDuplicadoException(
                    "Ya existe un producto con ese codigo.");
        }

        productos.add(producto);
    }

    public Producto buscarProducto(String codigo) {

        for (Producto producto : productos) {
            if (producto.getCodigo().equalsIgnoreCase(codigo)) {
                return producto;
            }
        }

        return null;
    }

    public ArrayList<Producto> listarProductos() {
        return new ArrayList<>(productos);
    }
}
