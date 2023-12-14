package com.proyecto.soc.service;


import com.proyecto.soc.dto.Persona;
import com.proyecto.soc.dto.Reporte;
import com.proyecto.soc.dto.Respuesta;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaService {

    final String NOMBRE_ARCHIVO = "usuarios.bin";


    public void crearUsuario(String email, String password){

        Respuesta respuesta = new Respuesta();
        respuesta.setStatus(true);

        Persona persona = new Persona();
        persona.setEmail(email);
        persona.setPassword(password);

        boolean existencia = existeBinario();
        if(existencia){
            actualizarBinario(persona);
        }else{
            guardadoReporte(persona);
        }
        leerBinario();

    }


    private void guardadoReporte(Persona persona){
        try{
            OutputStream is = new FileOutputStream(NOMBRE_ARCHIVO);
            ObjectOutputStream ois = new ObjectOutputStream(is);
            ois.writeObject(persona);
            ois.close();
            System.out.println("La escritura se ha completado");
        }catch (Exception e){
            System.err.println("Error " + e.getMessage());
        }
    }

    private void leerBinario(){
        try{
            FileInputStream is = new FileInputStream(NOMBRE_ARCHIVO);
            ObjectInputStream ois = new ObjectInputStream(is);

            List<Reporte> reportes = new ArrayList<>();
            Reporte reporte = null;
            while (true) {
                try {
                    Object objetoTest = ois.readObject();
                    reporte = (Reporte) objetoTest;
                    if (reporte != null) {
                        reportes.add(reporte);
                    }
                } catch (EOFException e) {
                    break;
                }
            }

            ois.close();
        }catch (Exception e){
            System.err.println("Error " + e.getMessage());
        }
    }

    public boolean existeBinario() {
        boolean existe = false;
        try {
            FileInputStream archivo;
            ObjectInputStream lectura;
            try{
                archivo = new FileInputStream(NOMBRE_ARCHIVO);
                lectura = new ObjectInputStream(archivo);
            }catch(FileNotFoundException e){
                new FileOutputStream(NOMBRE_ARCHIVO).close();
                archivo = new FileInputStream(NOMBRE_ARCHIVO);
                lectura = new ObjectInputStream(archivo);
            }


            while (true) {
                try {
                    Object objetoTest = lectura.readObject();
                    Reporte usuarioTest = (Reporte) objetoTest;
                    if (usuarioTest != null) {
                        existe = true;
                        break;
                    }

                } catch (EOFException e) {
                    break;
                }
            }
            lectura.close();
        } catch (Exception e) {
            System.out.println("Error " + e);
        }
        return existe;
    }

    public void actualizarBinario(Persona objeto) {
        ArrayList<Object> objetos = new ArrayList<>();
        try {
            String nombreArchivoOriginal = NOMBRE_ARCHIVO;
            String nombreArchivoTemporal = "temporal.bin";

            FileInputStream archivoInput = new FileInputStream(nombreArchivoOriginal);
            ObjectInputStream lectura = new ObjectInputStream(archivoInput);

            while (true) {
                try {
                    Object objetoExistente = lectura.readObject();
                    Reporte reporte = (Reporte) objetoExistente;
                    objetos.add(reporte);
                } catch (EOFException e) {
                    break;
                }
            }
            lectura.close();
            objetos.add(objeto);

            // Escribir la lista actualizada de objetos en el archivo temporal
            FileOutputStream archivoOutput = new FileOutputStream(nombreArchivoTemporal);
            ObjectOutputStream escritura = new ObjectOutputStream(archivoOutput);
            for (Object objetoFor : objetos) {
                escritura.writeObject(objetoFor);
            }
            escritura.close();

            // Renombrar el archivo temporal al archivo original
            File archivoOriginal = new File(nombreArchivoOriginal);
            File archivoTemporal = new File(nombreArchivoTemporal);

            if (archivoOriginal.exists()) {
                archivoOriginal.delete();  // Elimina el archivo original
            }

            archivoTemporal.renameTo(archivoOriginal);  // Renombra el archivo temporal
            System.out.println("Objeto actualizado con éxito");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}
