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
public class Inscripcion {

    private Paralelo paralelo;
    private Estudiante estudiante;
    private int idInscripcion;
    private int idCurso;
    private int idEstudiante;
    private Date fecha_inscripcion;
    private String descripcion;
    private float valor;

    public Inscripcion() {
    }

    public Inscripcion(Paralelo paralelo, Estudiante estudiante, Date fecha_inscripcion, String descripcion, float valor) {
        this.paralelo = paralelo;
        this.estudiante = estudiante;
        this.fecha_inscripcion = fecha_inscripcion;
        this.descripcion = descripcion;
        this.valor = valor;
    }

    public Inscripcion(int idCurso, int idEstudiante, Date fecha_inscripcion, String descripcion, float valor) {
        this.idCurso = idCurso;
        this.idEstudiante = idEstudiante;
        this.fecha_inscripcion = fecha_inscripcion;
        this.descripcion = descripcion;
        this.valor = valor;
    }

    public int getIdInscripcion() {
        return idInscripcion;
    }

    public void setIdInscripcion(int idInscripcion) {
        this.idInscripcion = idInscripcion;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public int getIdEstudiante() {
        return idEstudiante;
    }

    public void setIdEstudiante(int idEstudiante) {
        this.idEstudiante = idEstudiante;
    }

    public Date getFecha_inscripcion() {
        return fecha_inscripcion;
    }

    public void setFecha_inscripcion(Date fecha_inscripcion) {
        this.fecha_inscripcion = fecha_inscripcion;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Paralelo getParalelo() {
        return paralelo;
    }

    public void setParalelo(Paralelo paralelo) {
        this.paralelo = paralelo;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

}
