/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.controlador;

import com.seguridad.dao.RolDAO;
import com.seguridad.dao.UsuarioDAO;
import com.seguridad.modelo.Rol;
import com.seguridad.modelo.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Alex
 */
public class AdminMBeans implements Serializable{

    UsuarioDAO userDAO;
    private Usuario usuario;
    private Usuario integracionUsuario;
    private List<Usuario> listaUsuario;
    private List<Rol> listaRol;

    public AdminMBeans() {
        userDAO = new UsuarioDAO();
        usuario = new Usuario();
        integracionUsuario = new Usuario();
        listaUsuario = new ArrayList<>();
        listaRol = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        listaUsuario = userDAO.getListaUsuario();
    }

    public void vaciarCamposIntegracionRol() {
        integracionUsuario = new Usuario();
        usuario = new Usuario();
        listaRol = new ArrayList<>();
        showWarn("El registro se Cancelo.");
    }

    public void onRowEdit(RowEditEvent<Usuario> event) {
        try {
            if ("".equals(event.getObject().getNombre().trim())) {
                showWarn("No se puede modificar el registro porque el campo esta vacio.");
            } else if ("".equals(event.getObject().getApellido().trim())) {
                showWarn("No se puede modificar el registro porque el campo esta vacio.");
            } else if ("".equals(event.getObject().getCorreo().trim())) {
                showWarn("No se puede modificar el registro porque el campo esta vacio.");
            } else if ("".equals(event.getObject().getNombreUsuario().trim())) {
                showWarn("No se puede modificar el registro porque el campo esta vacio.");
            } else if ("".equals(event.getObject().isEstado())) {
                showWarn("No se puede modificar el registro porque el campo esta vacio.");
            } else {
                Usuario editUsuario = new Usuario(event.getObject().getIdUsuario(), event.getObject().getIdRol(),
                        event.getObject().getNombre(), event.getObject().getApellido(),
                        event.getObject().getCorreo(), event.getObject().getNombreUsuario(), event.getObject().getRol(),
                        event.getObject().isEstado());
                int resultadoRegistro = userDAO.editarUsuario(editUsuario);
                if (resultadoRegistro > 0) {
                    showInfo("Se actualizo con éxito, " + editUsuario.getNombre().trim());
                } else {
                    showWarn(editUsuario.getNombre().trim().replace(".", ",") + " se actualizaron los otros campos.");
                }
            }
            listaUsuario = new ArrayList<>();
            listaUsuario = userDAO.getListaUsuario();
        } catch (Exception e) {
            showWarn(e.getMessage());
        }
    }

    public void onRowCancel(RowEditEvent<Usuario> event) {
        try {
            showWarn("Edición del nombre " + event.getObject().getNombre() + " fue cancelado.");
        } catch (Exception e) {
            showWarn(e.getMessage());
        }
    }

    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }

    public void showInfo(String message) {
        addMessage(FacesMessage.SEVERITY_INFO, "Exito", message);
    }

    public void showWarn(String message) {
        addMessage(FacesMessage.SEVERITY_WARN, "Advertencia", message);
    }

    public void showError(String message) {
        addMessage(FacesMessage.SEVERITY_WARN, "Error", message);
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

    public List<Rol> getListaRol() {
        return listaRol;
    }

    public void setListaRol(List<Rol> listaRol) {
        this.listaRol = listaRol;
    }

    public Usuario getIntegracionUsuario() {
        return integracionUsuario;
    }

    public void setIntegracionUsuario(Usuario integracionUsuario) {
        this.integracionUsuario = integracionUsuario;
    }

}
