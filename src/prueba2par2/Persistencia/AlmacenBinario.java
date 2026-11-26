/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba2par2.Persistencia;
import prueba2par2.Excepciones.ErrorPersistenciaException;

/**
 *
 * @author Abril
 */
public interface AlmacenBinario<T> {

    void guardar(T datos) throws ErrorPersistenciaException;

    T cargar() throws ErrorPersistenciaException;
}
