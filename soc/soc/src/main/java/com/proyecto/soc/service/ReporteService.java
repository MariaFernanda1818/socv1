package com.proyecto.soc.service;


import com.proyecto.soc.dto.Reporte;
import com.proyecto.soc.dto.Respuesta;
import java.io.*;
import java.text.Normalizer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ReporteService {

    final String NOMBRE_ARCHIVO = "usuariosReportes.bin";

    public Respuesta crearReporte(String titulo, String cuerpo, String correoUsuario){
        Respuesta respuesta =  new Respuesta();
        Reporte reporte = new Reporte();
        reporte.setCodigo(generarCodigoReporte());
        reporte.setCuerpo(cuerpo);
        reporte.setTitulo(titulo);
        reporte.setCorreoUsuario(correoUsuario);
        String textoLimpio = quitarTildesComasPuntos(cuerpo);
        reporte.setTipo(detectarTipo(textoLimpio));
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String fechaFormateada = LocalDateTime.now().format(formato);
        String nombreArchivo = "reportes/Reporte"+ "-" + reporte.getCodigo();
        StringBuffer txt = new StringBuffer(titulo);
        txt
                .append("-")
                .append(fechaFormateada)
                .append("\n")
                .append("Tipo: ").append(reporte.getTipo())
                .append("\n")
                .append(cuerpo)
                .append("\n")
                .append(reporte.getCorreoUsuario());

        try{
            File archivo = new File(nombreArchivo);
            FileWriter writer = new FileWriter(archivo);
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write(txt.toString());
            bw.flush();
            writer.close();
            respuesta.setStatus(true);
            respuesta.setMensaje("Se creo correctamente el reporte");
            respuesta.setData(reporte);
            boolean existencia = existeBinario();
            if(existencia){
                actualizarBinario(reporte);
            }else{
                guardadoReporte(respuesta);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            respuesta.setStatus(false);
            respuesta.setMensaje("Hubo un error");
            System.err.println(ex.getMessage());
        }

        return respuesta;

    }

    private String detectarTipo(String contenido){
        Map<String, List<String>> parametros = new HashMap<>();
        parametros.put("phishing", new ArrayList<String>(Arrays.asList("correo", "suplantacion", "link", "engañoso", "desconocido", "sms", "mensaje texto", "paginas", "falsas", "redireccion", "falsificacion")));
        parametros.put("keylogger", new ArrayList<String>(Arrays.asList("credenciales", "contraseñas", "acceso", "apps", "aplicaciones", "aplicacion", "mensaje texto", "registro", "captura", "espionaje", "vigilado", "vigilada", "pantalla")));
        parametros.put("ransomware", new ArrayList<String>(Arrays.asList("cifrado", "rescate", "pago", "bitcoins", "moneda", "extorsion", "miedo", "amenaza", "desencriptacion", "cifrar", "pagar", "tiempo", "pantalla", "bloqueo")));
        parametros.put("spyware", new ArrayList<String>(Arrays.asList("vigilancia", "monitoreo", "registro", "sospechoso", "privacidad", "software", "apps", "no deseado", "no instale", "lento", "filtracion", "datos", "personales")));
        parametros.put("Adware", new ArrayList<String>(Arrays.asList("anuncios", "molesto", "lento", "aplicacion", "aplicaciones", "aplicacion", "extension", "navegador", "no deja trabajar", "publicidad", "monitoreo", "ven")));
        parametros.put("RAT", new ArrayList<String>(Arrays.asList("control", "externo", "remoto", "capturas", "pantalla", "apps", "instaladas", "extraño", "aplicacion", "aplicaciones", "vigilado", "manipulacion", "movimiento", "terceros")));
        parametros.put("MitM", new ArrayList<String>(Arrays.asList("red", "lenta", "internet", "control", "modificacion", "autenticacion", "fallido", "bloqueos", "lento", "cargas", "lentas", "vigilada", "reedireccion")));

        StringBuilder tipo = new StringBuilder();

        parametros.keySet().forEach((k) -> {

            int cantidad = 0;
            for (String palabra:contenido.split(" ")){
                for(String palabraTipos :parametros.get(k)){
                    if(palabra.equals(palabraTipos)){
                        cantidad++;
                    }
                }
            }
            if(cantidad > 0){
                tipo.append(k).append(",");
            }
        });
        String tipoString = tipo.toString();
        String tipoSalida = tipoString.length() > 1 ? tipoString.substring(0, tipoString.length() - 1) : "";
        return tipoSalida;
    }

    private String quitarTildesComasPuntos(String cuerpo){

        String textoSinTildes = Normalizer.normalize(cuerpo, Normalizer.Form.NFD);
        textoSinTildes = textoSinTildes.replaceAll("[^\\p{ASCII}]", "");

        String textoSinComasPuntos = textoSinTildes.replaceAll("[,\\.]", "");

        return textoSinComasPuntos;

    }

    private String generarCodigoReporte(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().substring(0,5);
    }

    private void guardadoReporte(Respuesta respuesta){
        try{
            Reporte reporte = (Reporte)respuesta.getData();
            OutputStream is = new FileOutputStream(NOMBRE_ARCHIVO);
            ObjectOutputStream ois = new ObjectOutputStream(is);
            ois.writeObject(reporte);
            ois.close();
            System.out.println("La escritura se ha completado");
        }catch (Exception e){
            System.err.println("Error " + e.getMessage());
        }
    }

    public List<Reporte> leerBinario(){
        List<Reporte> reportes = new ArrayList<>();
        try{
            FileInputStream is = new FileInputStream(NOMBRE_ARCHIVO);
            ObjectInputStream ois = new ObjectInputStream(is);

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
        return reportes;
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

    public void actualizarBinario(Reporte objeto) {
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
