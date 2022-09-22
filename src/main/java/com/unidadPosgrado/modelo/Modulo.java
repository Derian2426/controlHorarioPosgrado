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
public class Modulo {

    private boolean verifica;
    private int idMateria;
    private String nombreMateria;
    private String descripcion;
    private float hora_materia;

    public Modulo() {
    }

    public Modulo(int idMateria, String nombreMateria, String descripcion) {
        this.idMateria = idMateria;
        this.nombreMateria = nombreMateria;
        this.descripcion = descripcion;
        
    }

    public Modulo(int idMateria, String nombreMateria, String descripcion, float hora_materia) {
        this.idMateria = idMateria;
        this.nombreMateria = nombreMateria;
        this.descripcion = descripcion;
        this.hora_materia = hora_materia;
    }

    public int getIdMateria() {
        return idMateria;
    }

    public void setIdMateria(int idMateria) {
        this.idMateria = idMateria;
    }

    public String getNombreMateria() {
        return nombreMateria;
    }

    public void setNombreMateria(String nombreMateria) {
        this.nombreMateria = nombreMateria;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getHora_materia() {
        return hora_materia;
    }

    public void setHora_materia(float hora_materia) {
        this.hora_materia = hora_materia;
    }

    public boolean isVerifica() {
        return verifica;
    }

    public void setVerifica(boolean verifica) {
        this.verifica = verifica;
    }

}
