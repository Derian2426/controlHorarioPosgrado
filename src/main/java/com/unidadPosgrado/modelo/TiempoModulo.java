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
public class TiempoModulo {

    private int idTiempo;
    private Date fechaAsignacion;
    private int tiempoDuracion;
    private String descripcion;

    public TiempoModulo() {
    }

    public TiempoModulo(int idTiempo, Date fechaAsignacion, int tiempoDuracion, String descripcion) {
        this.idTiempo = idTiempo;
        this.fechaAsignacion = fechaAsignacion;
        this.tiempoDuracion = tiempoDuracion;
        this.descripcion = descripcion;
    }

    public TiempoModulo(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public int getIdTiempo() {
        return idTiempo;
    }

    public void setIdTiempo(int idTiempo) {
        this.idTiempo = idTiempo;
    }

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public int getTiempoDuracion() {
        return tiempoDuracion;
    }

    public void setTiempoDuracion(int tiempoDuracion) {
        this.tiempoDuracion = tiempoDuracion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
