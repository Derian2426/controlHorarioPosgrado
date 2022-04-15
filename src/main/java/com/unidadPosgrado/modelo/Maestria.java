/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unidadPosgrado.modelo;

import java.util.Date;

/**
 *
 * @author HP
 */
public class Maestria {

    private boolean verifica;
    private int idMaestria;
    private String nombre;
    private String descripcion;
    private float tiempoMaestria;
    private Date fechaInicio;
    private Date fechaFin;

    public Maestria() {
    }

    public Maestria(int idMaestria, String nombre, String descripcion, float tiempoMaestria) {
        this.idMaestria = idMaestria;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.tiempoMaestria = tiempoMaestria;
    }

    public Maestria(int idMaestria, String nombre, String descripcion) {
        this.idMaestria = idMaestria;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public Maestria(int idMaestria, String nombre, Date fechaInicio, Date fechaFin) {
        this.idMaestria = idMaestria;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public int getIdMaestria() {
        return idMaestria;
    }

    public void setIdMaestria(int idMaestria) {
        this.idMaestria = idMaestria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getTiempoMaestria() {
        return tiempoMaestria;
    }

    public void setTiempoMaestria(float tiempoMaestria) {
        this.tiempoMaestria = tiempoMaestria;
    }

    public boolean isVerifica() {
        return verifica;
    }

    public void setVerifica(boolean verifica) {
        this.verifica = verifica;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

}
