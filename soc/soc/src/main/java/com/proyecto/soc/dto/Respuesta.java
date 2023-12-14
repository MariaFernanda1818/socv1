package com.proyecto.soc.dto;

public class Respuesta {

    private String mensaje;

    private Object data;

    private boolean status;

    public Respuesta(String mensaje, boolean status) {
        this.mensaje = mensaje;
        this.status = status;
    }

    public Respuesta() {
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMensaje() {
        return mensaje;
    }

    public boolean isStatus() {
        return status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Respuesta{" +
                "mensaje='" + mensaje + '\'' +
                ", status=" + status +
                '}';
    }
}
