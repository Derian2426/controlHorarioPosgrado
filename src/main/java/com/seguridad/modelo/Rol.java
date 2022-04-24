/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.modelo;

/**
 *
 * @author Alex
 */
public class Rol {
    
    private boolean verifica;
    private int idRol;
    private String nombre;
    private String detalle;
    private boolean estado;

    public Rol() {
    }

    public Rol(int idRol, String nombre, String detalle, boolean estado) {
        this.idRol = idRol;
        this.nombre = nombre;
        this.detalle = detalle;
        this.estado = estado;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDetalle() {
        return detalle;
    }

    public void setDetalle(String detalle) {
        this.detalle = detalle;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public boolean isVerifica() {
        return verifica;
    }

    public void setVerifica(boolean verifica) {
        this.verifica = verifica;
    }
    
}
