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
    private int idCurso;
    private int idMaestria;
    private String nombre;
    private String nombreParalelo;
    private String descripcion;
    private float tiempoMaestria;
    private Date fechaInicio;
    private Date fechaFin;
    private int idDocente;
    private String nombreDocente;
    private String estado;

    public Maestria() {
    }

    public Maestria(int idCurso, int idMaestria, String nombre, Date fechaInicio, Date fechaFin, String estado) {
        this.idCurso = idCurso;
        this.idMaestria = idMaestria;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }

    public Maestria(int idCurso, int idMaestria, String nombre, String nombreParalelo, String descripcion, Date fechaInicio, int idDocente, String nombreDocente) {
        this.idCurso = idCurso;
        this.idMaestria = idMaestria;
        this.nombre = nombre;
        this.nombreParalelo = nombreParalelo;
        this.descripcion = descripcion;
        this.fechaInicio = fechaInicio;
        this.idDocente = idDocente;
        this.nombreDocente = nombreDocente;
    }

    public Maestria(int idCurso, String nombre, String nombreParalelo, float tiempoMaestria, Date fechaInicio) {
        this.idCurso = idCurso;
        this.nombre = nombre;
        this.nombreParalelo = nombreParalelo;
        this.tiempoMaestria = tiempoMaestria;
        this.fechaInicio = fechaInicio;
    }

    public Maestria(String nombreParalelo, int idMaestria, String nombre, String descripcion, float tiempoMaestria) {
        this.nombreParalelo = nombreParalelo;
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

    public Maestria(int idMaestria, String nombre, String descripcion, Date fechaInicio, Date fechaFin, int idCurso, String nombreParalelo) {
        this.idMaestria = idMaestria;
        this.nombre = nombre;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.idCurso = idCurso;
        this.nombreParalelo = nombreParalelo;
        this.descripcion = descripcion;
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

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public String getNombreParalelo() {
        return nombreParalelo;
    }

    public void setNombreParalelo(String nombreParalelo) {
        this.nombreParalelo = nombreParalelo;
    }

    public int getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(int idDocente) {
        this.idDocente = idDocente;
    }

    public String getNombreDocente() {
        return nombreDocente;
    }

    public void setNombreDocente(String nombreDocente) {
        this.nombreDocente = nombreDocente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

}
