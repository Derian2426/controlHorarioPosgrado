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
public class Maestria {

    private int idMaestria;
    private String nombre;
    private String descripcion;
    private float tiempoMaestria;

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

}
