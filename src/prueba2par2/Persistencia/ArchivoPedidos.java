/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prueba2par2.Persistencia;
import java.io.*;
import java.util.ArrayList;
import prueba2par2.Modelo.Pedido;
import prueba2par2.Excepciones.ErrorPersistenciaException;

/**
 *
 * @author Abril
 */
public class ArchivoPedidos implements AlmacenBinario<ArrayList<Pedido>> {

    private final File archivo;

    public ArchivoPedidos() {
        archivo = new File("datos/pedidos.dat");
    }

    @Override
    public void guardar(ArrayList<Pedido> pedidos)
            throws ErrorPersistenciaException {

        archivo.getParentFile().mkdirs();

        try (RandomAccessFile raf = new RandomAccessFile(archivo, "rw");
             ByteArrayOutputStream bytes = new ByteArrayOutputStream();
             ObjectOutputStream salida = new ObjectOutputStream(bytes)) {

            salida.writeObject(pedidos);
            salida.flush();

            raf.setLength(0);
            raf.write(bytes.toByteArray());

        } catch (IOException e) {
            throw new ErrorPersistenciaException(
                    "Error al guardar pedidos: " + e.getMessage());
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public ArrayList<Pedido> cargar()
            throws ErrorPersistenciaException {

        if (!archivo.exists()) {
            return new ArrayList<>();
        }

        try (RandomAccessFile raf = new RandomAccessFile(archivo, "r")) {

            byte[] bytes = new byte[(int) raf.length()];
            raf.readFully(bytes);

            try (ObjectInputStream entrada = new ObjectInputStream(
                    new ByteArrayInputStream(bytes))) {

                return (ArrayList<Pedido>) entrada.readObject();
            }

        } catch (IOException | ClassNotFoundException | ClassCastException e) {
            throw new ErrorPersistenciaException(
                    "Error al cargar pedidos: " + e.getMessage());
        }
    }
}