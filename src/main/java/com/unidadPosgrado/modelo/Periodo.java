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
public class Periodo {

    private int idPeriodo;
    private String nombrePeriodo;
    private String nombreParalelo;
    private Date fechaInicio;
    private Date fechaFin;
    private int cantidadEstudiante;
    private String estado;
    private String estado_planificacion;
    private String descripcion;

    public Periodo() {
    }

    public Periodo(String nombrePeriodo, String nombreParalelo, Date fechaInicio, Date fechaFin, int cantidadEstudiante, String estado, String descripcion) {
        this.nombrePeriodo = nombrePeriodo;
        this.nombreParalelo = nombreParalelo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.cantidadEstudiante = cantidadEstudiante;
        this.estado = estado;
        this.descripcion = descripcion;
    }

    public Periodo(int idPeriodo, String nombrePeriodo, Date fechaInicio, Date fechaFin, String estado) {
        this.idPeriodo = idPeriodo;
        this.nombrePeriodo = nombrePeriodo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }

    public Periodo(int idPeriodo, String nombrePeriodo, Date fechaInicio, Date fechaFin) {
        this.idPeriodo = idPeriodo;
        this.nombrePeriodo = nombrePeriodo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
    }

    public Periodo(int idPeriodo, String nombrePeriodo, String nombreParalelo, Date fechaInicio, Date fechaFin, int cantidadEstudiante) {
        this.idPeriodo = idPeriodo;
        this.nombrePeriodo = nombrePeriodo;
        this.nombreParalelo = nombreParalelo;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.cantidadEstudiante = cantidadEstudiante;
    }

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public String getNombrePeriodo() {
        return nombrePeriodo;
    }

    public void setNombrePeriodo(String nombrePeriodo) {
        this.nombrePeriodo = nombrePeriodo;
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

    public String getNombreParalelo() {
        return nombreParalelo;
    }

    public void setNombreParalelo(String nombreParalelo) {
        this.nombreParalelo = nombreParalelo;
    }

    public int getCantidadEstudiante() {
        return cantidadEstudiante;
    }

    public void setCantidadEstudiante(int cantidadEstudiante) {
        this.cantidadEstudiante = cantidadEstudiante;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado_planificacion() {
        return estado_planificacion;
    }

    public void setEstado_planificacion(String estado_planificacion) {
        this.estado_planificacion = estado_planificacion;
    }

}
