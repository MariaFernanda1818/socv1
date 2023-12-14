package com.proyecto.soc.dto;

import java.util.HashMap;
import java.util.Map;

public class Persona {

    private String email;

    private String password;

    private Map<String, String> listaPersonas;

    public Persona(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public Persona() {
        this.listaPersonas =  new HashMap<>();
        this.personaDefecto();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Map<String, String> getListaPersonas() {
        return listaPersonas;
    }

    public void setListaPersonas(Map<String, String> listaPersonas) {
        this.listaPersonas = listaPersonas;
    }

    private void personaDefecto(){
        this.listaPersonas.put("cuentaUno@gmail.com", "123456");
        this.listaPersonas.put("cuentaDos@gmail.com", "123456");
        this.listaPersonas.put("sofiasalgado1234@gmail.com", "1234");
        this.listaPersonas.put("mariafvn0127@gmail.com", "123456");
    }

}
