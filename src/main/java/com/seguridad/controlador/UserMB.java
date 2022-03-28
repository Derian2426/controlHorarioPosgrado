/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.controlador;

import com.seguridad.dao.UsuarioDAO;
import javax.annotation.PostConstruct;

/**
 *
 * @author HP
 */
public class UserMB {

    UsuarioDAO userDAO;
    public UserMB() {
        userDAO = new UsuarioDAO();
    }
    @PostConstruct
    public void init() {
        userDAO.insertUsuario();
    }
    public void saludo(){
        System.out.println("    Helle ");
    }
    
}
