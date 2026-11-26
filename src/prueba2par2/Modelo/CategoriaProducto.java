/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba2par2.Modelo;

/**
 *
 * @author Abril
 */

public enum CategoriaProducto {
    PLATO("Plato"),
    BEBIDA("Bebida"),
    POSTRE("Postre");

    private final String etiqueta;

    CategoriaProducto(String etiqueta) {
        this.etiqueta = etiqueta;
    }

    @Override
    public String toString() {
        return etiqueta;
    }
}
