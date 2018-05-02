package com.example.igor.msqlandroid.Entidades;

/**
 * Created by Igor on 21/04/2018.
 */

public class comentario {

    String fecha;
    String nombres;
    String apellidos;
    String comentario;
    int idanuncio;
    int idpersona;

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getIdanuncio() {
        return idanuncio;
    }

    public void setIdanuncio(int idanuncio) {
        this.idanuncio = idanuncio;
    }

    public int getIdpersona() {
        return idpersona;
    }

    public void setIdpersona(int idpersona) {
        this.idpersona = idpersona;
    }

}
