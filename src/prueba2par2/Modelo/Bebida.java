/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba2par2.Modelo;

/**
 *
 * @author Abril
 */
public class Bebida extends Producto {

    private static final long serialVersionUID = 1L;

    private int tamanoMl;
    private boolean conAlcohol;

    public Bebida(String codigo, String nombre, double precio, int tamanoMl, boolean conAlcohol) {
        super(codigo, nombre, precio, CategoriaProducto.BEBIDA);
        if (tamanoMl <= 0)
            throw new IllegalArgumentException("El tamaño en ml debe ser mayor a 0.");
        this.tamanoMl = tamanoMl;
        this.conAlcohol = conAlcohol;
    }

    public int getTamanoMl() {
        return tamanoMl;
    }
    public boolean isConAlcohol(){
        return conAlcohol;
   }

    @Override
    public String getDetalleEspecifico() {
        return "Tamaño: " + tamanoMl + " ml | " + (conAlcohol ? "Con alcohol" : "Sin alcohol");
    }
}
