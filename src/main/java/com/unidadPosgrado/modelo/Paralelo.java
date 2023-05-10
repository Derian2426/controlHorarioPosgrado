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
public class Paralelo {
    private int idCurso;
    private Maestria maestria;
    private Periodo periodo;
    private String nombreParalelo;
    private int cantEstudiantes;

    public Paralelo() {
    }

    public Paralelo(int idCurso, Maestria maestria, Periodo periodo, String nombreParalelo, int cantEstudiantes) {
        this.idCurso = idCurso;
        this.maestria = maestria;
        this.periodo = periodo;
        this.nombreParalelo = nombreParalelo;
        this.cantEstudiantes = cantEstudiantes;
    }

    public int getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(int idCurso) {
        this.idCurso = idCurso;
    }

    public Maestria getMaestria() {
        return maestria;
    }

    public void setMaestria(Maestria maestria) {
        this.maestria = maestria;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public String getNombreParalelo() {
        return nombreParalelo;
    }

    public void setNombreParalelo(String nombreParalelo) {
        this.nombreParalelo = nombreParalelo;
    }

    public int getCantEstudiantes() {
        return cantEstudiantes;
    }

    public void setCantEstudiantes(int cantEstudiantes) {
        this.cantEstudiantes = cantEstudiantes;
    }
    
    
}
