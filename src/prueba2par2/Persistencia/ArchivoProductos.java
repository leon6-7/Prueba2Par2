/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba2par2.Persistencia;
import java.io.*;
import prueba2par2.Modelo.Catalogo;
import prueba2par2.Excepciones.ErrorPersistenciaException;

/**
 *
 * @author Abril
 */
public class ArchivoProductos implements AlmacenBinario<Catalogo> {

    private final File archivo;

    public ArchivoProductos() {
        archivo = new File("datos/productos.dat");
    }

    @Override
    public void guardar(Catalogo catalogo)
            throws ErrorPersistenciaException {

        archivo.getParentFile().mkdirs();

        try (RandomAccessFile raf = new RandomAccessFile(archivo, "rw");
             ByteArrayOutputStream bytes = new ByteArrayOutputStream();
             ObjectOutputStream salida = new ObjectOutputStream(bytes)) {

            salida.writeObject(catalogo);
            salida.flush();

            raf.setLength(0);
            raf.write(bytes.toByteArray());

        } catch (IOException e) {
            throw new ErrorPersistenciaException(
                    "Error al guardar productos: " + e.getMessage());
        }
    }

    @Override
    public Catalogo cargar() throws ErrorPersistenciaException {

        if (!archivo.exists()) {
            return new Catalogo();
        }

        try (RandomAccessFile raf = new RandomAccessFile(archivo, "r")) {

            byte[] bytes = new byte[(int) raf.length()];
            raf.readFully(bytes);

            try (ObjectInputStream entrada = new ObjectInputStream(
                    new ByteArrayInputStream(bytes))) {

                return (Catalogo) entrada.readObject();
            }

        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            throw new ErrorPersistenciaException(
                    "Error al cargar productos: " + e.getMessage());
        }
    }
}
