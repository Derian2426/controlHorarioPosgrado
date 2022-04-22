/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.controlador;

import com.seguridad.dao.UsuarioDAO;
import com.seguridad.modelo.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 *
 * @author Alex
 */
public class AdminMBeans {

    UsuarioDAO userDAO;
    private Usuario usuario;
    private List<Usuario> listaUsuario;
    
    public AdminMBeans() {
        userDAO = new UsuarioDAO();
        usuario = new Usuario();
        listaUsuario = new ArrayList<>();
    }
    @PostConstruct
    public void init() {
        listaUsuario = userDAO.getListaUsuario();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Usuario> getListaUsuario() {
        return listaUsuario;
    }

    public void setListaUsuario(List<Usuario> listaUsuario) {
        this.listaUsuario = listaUsuario;
    }
    
}
