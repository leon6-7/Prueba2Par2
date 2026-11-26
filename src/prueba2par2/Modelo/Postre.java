/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba2par2.Modelo;

/**
 *
 * @author Abril
 */
public class Postre extends Producto {

    private static final long serialVersionUID = 1L;

    private String sabor;
    private boolean aptoDiabeticos;

    public Postre(String codigo, String nombre, double precio, String sabor, boolean aptoDiabeticos) {
        super(codigo, nombre, precio, CategoriaProducto.POSTRE);
        if (sabor == null || sabor.trim().isEmpty())
            throw new IllegalArgumentException("El sabor no puede estar vacío.");
        this.sabor = sabor.trim();
        this.aptoDiabeticos = aptoDiabeticos;
    }

    public String getSabor() { return sabor; }
    public boolean isAptoDiabeticos() { return aptoDiabeticos; }

    @Override
    public String getDetalleEspecifico() {
        return "Sabor: " + sabor + " | " + (aptoDiabeticos ? "Apto para diabéticos" : "No apto para diabéticos");
    }
}
