/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba2par2.Control;
import java.util.ArrayList;
import prueba2par2.Modelo.Producto;
import prueba2par2.Excepciones.CodigoDuplicadoException;

/**
 *
 * @author Abril
 */
public interface GestionProductos {

    void registrarProducto(Producto producto)
            throws CodigoDuplicadoException;

    Producto buscarProducto(String codigo);
    ArrayList<Producto> listarProductos();
}
