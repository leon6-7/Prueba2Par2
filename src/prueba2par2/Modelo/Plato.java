/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba2par2.Modelo;

/**
 *
 * @author Abril
 */
public class Plato extends Producto {

    private static final long serialVersionUID = 1L;

    private String tipoPlato;   // ej: entrada, fuerte, sopa
    private int porciones;

    public Plato(String codigo, String nombre, double precio, String tipoPlato, int porciones) {
        super(codigo, nombre, precio, CategoriaProducto.PLATO);
        if (tipoPlato == null || tipoPlato.trim().isEmpty())
            throw new IllegalArgumentException("El tipo de plato no puede estar vacío.");
        if (porciones <= 0)
            throw new IllegalArgumentException("Las porciones deben ser mayores a 0.");
        this.tipoPlato = tipoPlato.trim();
        this.porciones = porciones;
    }

    public String getTipoPlato() { return tipoPlato; }
    public int getPorciones() { return porciones; }

    @Override
    public String getDetalleEspecifico() {
        return "Tipo: " + tipoPlato + " | Porciones: " + porciones;
    }
}
