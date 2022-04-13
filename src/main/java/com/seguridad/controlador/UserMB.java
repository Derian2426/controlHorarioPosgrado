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
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Alex
 */
public class UserMB {

    UsuarioDAO userDAO;
    private Usuario usuario;
    RolDAO rolDao;
    String warnMsj = "Advertencia";
    String infMsj = "Exito";
    private final FacesContext facesContext = FacesContext.getCurrentInstance();
    HttpSession httpSession = (HttpSession) facesContext.getExternalContext().getSession(true);

    public UserMB() {
        userDAO = new UsuarioDAO();
        usuario = new Usuario();
        rolDao = new RolDAO();
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

    public void iniciarSesion() throws IOException, SQLException {
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
                        List<Rol> rolesSesion = rolDao.getRolesByUsers(usuarioSesion.getIdUsuario());
                        httpSession.setAttribute("username", usuarioSesion);

                        FacesContext.getCurrentInstance().getExternalContext()
                                .getSessionMap().put("usuario", usuarioSesion);
                        FacesContext.getCurrentInstance().getExternalContext()
                                .getSessionMap().put("roles", rolesSesion);
                        facesContext.getExternalContext().redirect("faces/Vistas/Global/principal.xhtml");
                    }
                } else {
                    mensajeDeAdvertencia("Error de conexión al intentar iniciar sesión.");
                }
            }
        } catch (SQLException e) {
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
