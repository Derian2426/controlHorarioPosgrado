/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.controlador;

import com.seguridad.dao.UsuarioDAO;
import com.seguridad.modelo.Usuario;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Alex
 */
public class UsuarioMB {

    UsuarioDAO userDAO;
    private Usuario usuario;
    
    String warnMsj = "Advertencia";
    String infMsj = "Exito";
    
    private final FacesContext facesContext = FacesContext.getCurrentInstance();
    HttpSession httpSession = (HttpSession) facesContext.getExternalContext().getSession(true);
    
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
    public void iniciarSesion() {
        try {
            Usuario usuarioSesion = new Usuario();
            if ("".equals(usuario.getNombreUsuario())) {
                mensajeDeAdvertencia("Ingrese un usuario");
            } else if ("".equals(usuario.getPassword())) {
                mensajeDeAdvertencia("Ingrese una contraseña");
            } else if (!usuario.getNombreUsuario().isEmpty() && !usuario.getPassword().isEmpty()) {
                usuarioSesion = userDAO.iniciarSesion(usuario);
                if (usuarioSesion != null) {
                    if (usuarioSesion.getCodigoAux() < 1) {
                        mensajeDeAdvertencia(usuarioSesion.getMensajeAux());
                    } else {
                        mensajeDeExito(usuarioSesion.getMensajeAux());

                        usuario = usuarioSesion;
                        
                        httpSession.setAttribute("username", usuarioSesion);

                        FacesContext.getCurrentInstance().getExternalContext()
                                .getSessionMap().put("usuario", usuarioSesion);
                        
                        facesContext.getExternalContext().redirect("faces/Vistas/Global/principal.xhtml");
                    }
                } else {
                    mensajeDeAdvertencia("Error de conexión al intentar iniciar sesión.");
                }
            }
        } catch (Exception e) {
            showWarn(e.getMessage());
        }
    }
    
    public void cerrarSession() throws IOException {
        httpSession.removeAttribute("usuario");
        facesContext.getExternalContext().redirect(
                "../../../");
        usuario.setPassword("");
        usuario.setNombreUsuario("");
    }
    
    public void mensajeDeAdvertencia(String msj) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_WARN, warnMsj, msj));
    }

    public void mensajeDeExito(String msj) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_INFO, infMsj, msj));
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
