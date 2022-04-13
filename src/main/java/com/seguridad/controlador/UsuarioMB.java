/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.controlador;

import com.seguridad.dao.UsuarioDAO;
import com.seguridad.modelo.Usuario;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Alex
 */
public class UsuarioMB {

    UsuarioDAO userDAO;
    private Usuario usuario;

    public UsuarioMB() {
        userDAO = new UsuarioDAO();
        usuario = new Usuario();
    }

    @PostConstruct
    public void init() {

    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public static String getRemoteAddress(HttpServletRequest request) {
        String ipAddress = request.getHeader("X-FORWARDED-FOR");
        if (ipAddress != null) {
            // cares only about the first IP if there is a list
            ipAddress = ipAddress.replaceFirst(",.*", "");
        } else {
            ipAddress = request.getRemoteAddr();
        }
        return ipAddress;
    }

    public void iniciarSesion() {
        try {
            if ("".equals(usuario.getNombre())) {
                showWarn("Debe ingresar un nombre.");
                System.out.println("HOLA");
                System.out.println("" + getRemoteAddress((HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext()));
            } else if ("".equals(usuario.getPassword())) {
                showWarn("Debe ingresar una contraseña.");
                System.out.println("HOLA");
            }
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

}
