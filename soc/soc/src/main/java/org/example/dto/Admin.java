package org.example.dto;

import java.util.HashMap;
import java.util.Map;

public class Admin {

    private String email;

    private String password;

    private Map<String, String> listaAdmins;

    public Admin() {
        this.listaAdmins = new HashMap<>();
        this.adminsAutorizados();
    }

    public Admin(String email, String password) {
        this.email = email;
        this.password = password;
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

    public Map<String, String> getListaAdmins() {
        return listaAdmins;
    }

    public void setListaAdmins(Map<String, String> listaAdmins) {
        this.listaAdmins = listaAdmins;
    }

    private void adminsAutorizados(){
        this.listaAdmins.put("cuentaUno@gmail.com", "123456");
        this.listaAdmins.put("cuentaDos@gmail.com", "123456");
    }

}
