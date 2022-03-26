/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.controlador;

import com.seguridad.dao.UsuarioDAO;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import java.io.Serializable;

/**
 *
 * @author HP
 */
@Named(value = "usuarioControlador")
@ViewScoped
public class UsuarioControlador implements Serializable {

    /**
     * Creates a new instance of UsuarioControlador
     */
    UsuarioDAO usuarioDAO;
    public UsuarioControlador() {
        usuarioDAO = new UsuarioDAO();
        usuarioDAO.insertUsuario();
    }
    public void Saludo(){
        System.out.println("Hola ");
    }
}
