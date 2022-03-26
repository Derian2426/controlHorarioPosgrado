/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.dao;

import com.global.config.Conexion;
import com.seguridad.modelo.Usuario;

/**
 *
 * @author HP
 */
public class UsuarioDAO {

    Conexion conexion;
    private Usuario usuario;
    String sentencia;

    public UsuarioDAO() {
        conexion = new Conexion();
        usuario = new Usuario();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int insertUsuario() {
        sentencia = "INSERT INTO public.usuario(nombre, apellido, correo, nombre_usuario, password, estado)\n"
                + "	VALUES ('Victor', 'Chun', 'victorelianchun14@gmail.com', 'Eliereme', 'HolaMundo', true);";
        try {
            return conexion.insertar(sentencia);
        } catch (Exception e) {
            return -1;
        }
    }

}
