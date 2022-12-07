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
    private int idRol;
    private String nombre;
    private String apellido;
    private String correo;
    private String nombreUsuario;
    private String password;
    private String rol;
    private boolean estado;

    private String confpassword;

    //sesion
    private int idUsuarioSesion;
    private String nomUserSesion;
    private String passSesion;
    private String nombreSesion;
    private String apellidoSesion;
    private String correoSesion;

    String mensajeAux;
    int codigoAux;

    public Usuario() {
    }

    public Usuario(int idUsuario, String nombre, String apellido, String correo,
            String nombreUsuario, boolean estado) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.nombreUsuario = nombreUsuario;
        this.estado = estado;
    }

    public Usuario(int idUsuario, String nombre, String apellido, String correo,
            String nombreUsuario, String password, boolean estado) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.estado = estado;
    }

    public Usuario(int code, String reslt, int iduser, String name, String firname,
            String usrnm, String pssword, String mail) {
        this.codigoAux = code;
        this.mensajeAux = reslt;
        this.idUsuarioSesion = iduser;
        this.nombreSesion = name;
        this.apellidoSesion = firname;
        this.nomUserSesion = usrnm;
        this.passSesion = pssword;
        this.correoSesion = mail;
    }

    public Usuario(int idUsuario, int idRol, String nombre, String apellido, String correo, String nombreUsuario, String rol, boolean estado) {
        this.idUsuario = idUsuario;
        this.idRol = idRol;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.nombreUsuario = nombreUsuario;
        this.rol = rol;
        this.estado = estado;
    }

    public int getIdUsuarioSesion() {
        return idUsuarioSesion;
    }

    public void setIdUsuarioSesion(int idUsuarioSesion) {
        this.idUsuarioSesion = idUsuarioSesion;
    }

    public String getNombreSesion() {
        return nombreSesion;
    }

    public void setNombreSesion(String nombreSesion) {
        this.nombreSesion = nombreSesion;
    }

    public String getApellidoSesion() {
        return apellidoSesion;
    }

    public void setApellidoSesion(String apellidoSesion) {
        this.apellidoSesion = apellidoSesion;
    }

    public String getCorreoSesion() {
        return correoSesion;
    }

    public void setCorreoSesion(String correoSesion) {
        this.correoSesion = correoSesion;
    }

    public String getNomUserSesion() {
        return nomUserSesion;
    }

    public void setNomUserSesion(String nomUserSesion) {
        this.nomUserSesion = nomUserSesion;
    }

    public String getPassSesion() {
        return passSesion;
    }

    public void setPassSesion(String passSesion) {
        this.passSesion = passSesion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public String getConfpassword() {
        return confpassword;
    }

    public void setConfpassword(String confpassword) {
        this.confpassword = confpassword;
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

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

}
