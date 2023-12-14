package com.proyecto.soc.dto;

import java.io.Serializable;
import java.util.UUID;

public class Reporte implements Serializable {

    private String titulo;

    private String cuerpo;

    private String codigo;

    private boolean revisado;

    private String tipo;

    private String correoUsuario;

    public Reporte(String titulo, String cuerpo, boolean revisado) {
        this.titulo = titulo;
        this.cuerpo = cuerpo;
        this.revisado = revisado;
    }

    public Reporte() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }

    public boolean isRevisado() {
        return revisado;
    }

    public void setRevisado(boolean revisado) {
        this.revisado = revisado;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getTipo() {
        return tipo;
    }

    public String getCorreoUsuario() {
        return correoUsuario;
    }

    public void setCorreoUsuario(String correoUsuario) {
        this.correoUsuario = correoUsuario;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Reporte{" +
                "titulo='" + titulo + '\'' +
                ", cuerpo='" + cuerpo + '\'' +
                ", codigo='" + codigo + '\'' +
                ", revisado=" + revisado +
                ", tipo='" + tipo + '\'' +
                ", correoUsuario='" + correoUsuario + '\'' +
                '}';
    }
}
