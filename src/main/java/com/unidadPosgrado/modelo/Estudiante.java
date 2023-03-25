/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unidadPosgrado.modelo;

/**
 *
 * @author Alex
 */
public class Estudiante {

    private boolean verifica;
    private int id_estudiante;
    private String nombre_estudiante;
    private String apellido_estudiante;
    private String telefono_estudiante;
    private String cedula_estudiante;
    private String sexo;
    private String correo_estudiante;

    public Estudiante() {
    }

    public Estudiante(String nombre_estudiante, String apellido_estudiante, String telefono_estudiante, String cedula_estudiante, String sexo, String correo_estudiante) {
        this.nombre_estudiante = nombre_estudiante;
        this.apellido_estudiante = apellido_estudiante;
        this.telefono_estudiante = telefono_estudiante;
        this.cedula_estudiante = cedula_estudiante;
        this.sexo = sexo;
        this.correo_estudiante = correo_estudiante;
    }

    public Estudiante(int id_estudiante, String nombre_estudiante,
            String apellido_estudiante, String telefono_estudiante,
            String cedula_estudiante, String sexo, String correo_estudiante) {
        this.id_estudiante = id_estudiante;
        this.nombre_estudiante = nombre_estudiante;
        this.apellido_estudiante = apellido_estudiante;
        this.telefono_estudiante = telefono_estudiante;
        this.cedula_estudiante = cedula_estudiante;
        this.sexo = sexo;
        this.correo_estudiante = correo_estudiante;
    }

    public int getId_estudiante() {
        return id_estudiante;
    }

    public void setId_estudiante(int id_estudiante) {
        this.id_estudiante = id_estudiante;
    }

    public String getNombre_estudiante() {
        return nombre_estudiante;
    }

    public void setNombre_estudiante(String nombre_estudiante) {
        this.nombre_estudiante = nombre_estudiante;
    }

    public String getApellido_estudiante() {
        return apellido_estudiante;
    }

    public void setApellido_estudiante(String apellido_estudiante) {
        this.apellido_estudiante = apellido_estudiante;
    }

    public String getTelefono_estudiante() {
        return telefono_estudiante;
    }

    public void setTelefono_estudiante(String telefono_estudiante) {
        this.telefono_estudiante = telefono_estudiante;
    }

    public String getCedula_estudiante() {
        return cedula_estudiante;
    }

    public void setCedula_estudiante(String cedula_estudiante) {
        this.cedula_estudiante = cedula_estudiante;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getCorreo_estudiante() {
        return correo_estudiante;
    }

    public void setCorreo_estudiante(String correo_estudiante) {
        this.correo_estudiante = correo_estudiante;
    }

    public boolean isVerifica() {
        return verifica;
    }

    public void setVerifica(boolean verifica) {
        this.verifica = verifica;
    }

}
