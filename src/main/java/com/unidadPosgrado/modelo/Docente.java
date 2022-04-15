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
public class Docente {
    
    private int id_docente;
    private String nombre_docente;
    private String apellido_docente;
    private String cedula_docente;
    private String telefono_docente;
    private String correo_docente;
    private boolean estado;
    
    public Docente(){
        
    }

    public Docente(int id_docente, String nombre_docente, String apellido_docente, 
            String cedula_docente, String telefono_docente, String correo_docente, boolean estado) {
        this.id_docente = id_docente;
        this.nombre_docente = nombre_docente;
        this.apellido_docente = apellido_docente;
        this.cedula_docente = cedula_docente;
        this.telefono_docente = telefono_docente;
        this.correo_docente = correo_docente;
        this.estado = estado;
    }
    
    public Docente(int id_docente, String nombre_docente, String apellido_docente, 
            String cedula_docente, String telefono_docente, String correo_docente) {
        this.id_docente = id_docente;
        this.nombre_docente = nombre_docente;
        this.apellido_docente = apellido_docente;
        this.cedula_docente = cedula_docente;
        this.telefono_docente = telefono_docente;
        this.correo_docente = correo_docente;
    }

    public int getId_docente() {
        return id_docente;
    }

    public void setId_docente(int id_docente) {
        this.id_docente = id_docente;
    }

    public String getNombre_docente() {
        return nombre_docente;
    }

    public void setNombre_docente(String nombre_docente) {
        this.nombre_docente = nombre_docente;
    }

    public String getApellido_docente() {
        return apellido_docente;
    }

    public void setApellido_docente(String apellido_docente) {
        this.apellido_docente = apellido_docente;
    }

    public String getTelefono_docente() {
        return telefono_docente;
    }

    public void setTelefono_docente(String telefono_docente) {
        this.telefono_docente = telefono_docente;
    }

    public String getCedula_docente() {
        return cedula_docente;
    }

    public void setCedula_docente(String cedula_docente) {
        this.cedula_docente = cedula_docente;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getCorreo_docente() {
        return correo_docente;
    }

    public void setCorreo_docente(String correo_docente) {
        this.correo_docente = correo_docente;
    }
    
}
