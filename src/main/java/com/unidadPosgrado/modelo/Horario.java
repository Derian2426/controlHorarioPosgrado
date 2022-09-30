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
public class Horario {

    private int idPeriodo;
    private int idCurso;
    private int idDocente;
    private String nombreModulo;
    private String nombreDocente;
    private float hora;
    private Date fechaAsignacion;
    private String Paralelo;

    public Horario() {
    }

    public Horario(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public Horario(int idPeriodo, int idCurso, int idDocente, String nombreModulo, String nombreDocente, float hora, String paralelo) {
        this.idPeriodo = idPeriodo;
        this.idCurso = idCurso;
        this.idDocente = idDocente;
        this.nombreModulo = nombreModulo;
        this.nombreDocente = nombreDocente;
        this.hora = hora;
        this.Paralelo = paralelo;
    }

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public int getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(int idDocente) {
        this.idDocente = idDocente;
    }

    public String getNombreModulo() {
        return nombreModulo;
    }

    public void setNombreModulo(String nombreModulo) {
        this.nombreModulo = nombreModulo;
    }

    public String getNombreDocente() {
        return nombreDocente;
    }

    public void setNombreDocente(String nombreDocente) {
        this.nombreDocente = nombreDocente;
    }

    public float getHora() {
        return hora;
    }

    public void setHora(float hora) {
        this.hora = hora;
    }

    public Date getFechaAsignacion() {
        return fechaAsignacion;
    }

    public void setFechaAsignacion(Date fechaAsignacion) {
        this.fechaAsignacion = fechaAsignacion;
    }

    public String getParalelo() {
        return Paralelo;
    }

    public void setParalelo(String Paralelo) {
        this.Paralelo = Paralelo;
    }

}
