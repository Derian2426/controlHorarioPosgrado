/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.modelo;

/**
 *
 * @author HP
 */
public class Usuario {

    private int idUsuario;
    private String nombre;
    private String apellido;
    private String correo;
    private String nombreUsuario;
    private String password;
    private boolean estado;
    
    String mensajeAux;
    int codigoAux;

    public Usuario() {
    }

    public Usuario(int idUsuario, String nombre, String apellido, String correo, String nombreUsuario, String password, boolean estado) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.estado = estado;
    }
    
    public Usuario(int code, String reslt, int iduser, String name, String firname, String usrnm, String pssword, String mail) {
        this.codigoAux = code;
        this.mensajeAux = reslt;
        this.idUsuario = iduser;
        this.nombre = name;
        this.apellido = firname;
        this.nombreUsuario = usrnm;
        this.password = pssword;
        this.correo = mail;

    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getMensajeAux() {
        return mensajeAux;
    }

    public void setMensajeAux(String mensajeAux) {
        this.mensajeAux = mensajeAux;
    }

    public int getCodigoAux() {
        return codigoAux;
    }

    public void setCodigoAux(int codigoAux) {
        this.codigoAux = codigoAux;
    }
    
}
