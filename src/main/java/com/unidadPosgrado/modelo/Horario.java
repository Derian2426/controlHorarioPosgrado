/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unidadPosgrado.modelo;

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

    public Horario() {
    }

    public Horario(int idPeriodo, int idCurso, int idDocente, String nombreModulo, String nombreDocente, float hora) {
        this.idPeriodo = idPeriodo;
        this.idCurso = idCurso;
        this.idDocente = idDocente;
        this.nombreModulo = nombreModulo;
        this.nombreDocente = nombreDocente;
        this.hora = hora;
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

}
