/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba2par2.Modelo;
import java.io.Serializable;

/**
 *
 * @author Abril
 */
public abstract class Producto implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String codigo;
    private String nombre;
    private double precio;
    private final CategoriaProducto categoria;

    protected Producto(String codigo, String nombre, double precio, CategoriaProducto categoria) {
        if (codigo == null || codigo.trim().isEmpty())
            throw new IllegalArgumentException("El código no puede estar vacío.");
        if (nombre == null || nombre.trim().isEmpty())
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        if (precio <= 0)
            throw new IllegalArgumentException("El precio debe ser mayor a 0.");

        this.codigo = codigo.trim();
        this.nombre = nombre.trim();
        this.precio = precio;
        this.categoria = categoria;
    }

    /** Cada subclase describe sus atributos propios (se muestra en la tabla). */
    public abstract String getDetalleEspecifico();

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public double getPrecio() { return precio; }
    public CategoriaProducto getCategoria() { return categoria; }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty())
            throw new IllegalArgumentException("El nombre no puede estar vacío.");
        this.nombre = nombre.trim();
    }

    public void setPrecio(double precio) {
        if (precio <= 0)
            throw new IllegalArgumentException("El precio debe ser mayor a 0.");
        this.precio = precio;
    }

    @Override
    public String toString() {
        return codigo + " - " + nombre + " ($" + String.format("%.2f", precio) + ")";
    }
}
