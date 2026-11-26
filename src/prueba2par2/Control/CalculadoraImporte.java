/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba2par2.Control;

import java.util.ArrayList;
import prueba2par2.Modelo.DetallePedido;

/**
 *
 * @author Abril
 */
public class CalculadoraImporte {

    public static double calcularTotal(ArrayList<DetallePedido> detalles) {
        return calcularTotal(detalles, 0);
    }

    private static double calcularTotal(
            ArrayList<DetallePedido> detalles, int indice) {

        if (indice >= detalles.size()) {
            return 0;
        }

        return detalles.get(indice).getSubtotal()
                + calcularTotal(detalles, indice + 1);
    }
}