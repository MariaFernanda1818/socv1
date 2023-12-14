package com.proyecto.soc.service;


import com.proyecto.soc.dto.Persona;
import com.proyecto.soc.dto.Respuesta;
import java.util.HashMap;
import java.util.Map;
import org.example.dto.Admin;

public class AdminService {

    public Respuesta login(String email, String password){
        Respuesta respuesta =  new Respuesta();
        Admin admin =  new Admin();
        Persona persona = new Persona();
        String cuentaAdmin = admin.getListaAdmins().get(email);
        String cuentaPersona = persona.getListaPersonas().get(email);
        if(cuentaAdmin != null || cuentaPersona != null){
            if(password.equals(cuentaAdmin) || password.equals(cuentaPersona)){
                respuesta.setStatus(true);
                respuesta.setMensaje("Se logeo correctamente");
            }else{
                respuesta.setStatus(false);
                respuesta.setMensaje("La contraseña ingresada es incorrecta");
            }

        }else{
            respuesta.setStatus(false);
            respuesta.setMensaje("La cuenta no existe");
        }

        return  respuesta;
    }

}
