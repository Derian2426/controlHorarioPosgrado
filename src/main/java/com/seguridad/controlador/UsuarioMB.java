/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.controlador;

import com.global.config.Correo;
import com.global.config.RandomStringGenerator;
import com.seguridad.dao.RolDAO;
import com.seguridad.dao.UsuarioDAO;
import com.seguridad.modelo.Rol;
import com.seguridad.modelo.Usuario;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.mail.MessagingException;
import javax.servlet.http.HttpSession;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Alex
 */
public class UsuarioMB {

    UsuarioDAO userDAO;
    RolDAO rolDAO;
    private Usuario usuario;
    private Rol rol;
    private Usuario usuarioSesion;
    private Usuario usuarioEdit;
    private Usuario usuarioCredenciales;
    private boolean estado;

    String warnMsj = "Advertencia";
    String infMsj = "Exito";

    private final FacesContext facesContext = FacesContext.getCurrentInstance();
    HttpSession httpSession = (HttpSession) facesContext.getExternalContext().getSession(true);

    public UsuarioMB() {
        usuarioCredenciales = new Usuario();
        userDAO = new UsuarioDAO();
        rolDAO = new RolDAO();
        usuario = new Usuario();
        rol = new Rol();
        usuarioSesion = new Usuario();
        usuarioEdit = new Usuario();
        estado = true;
    }

    @PostConstruct
    public void init() {

    }

    public Usuario getUsuarioCredenciales() {
        return usuarioCredenciales;
    }

    public void setUsuarioCredenciales(Usuario usuarioCredenciales) {
        this.usuarioCredenciales = usuarioCredenciales;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuarioSesion() {
        return usuarioSesion;
    }

    public void setUsuarioSesion(Usuario usuarioSesion) {
        this.usuarioSesion = usuarioSesion;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Usuario getUsuarioEdit() {
        return usuarioEdit;
    }

    public void setUsuarioEdit(Usuario usuarioEdit) {
        this.usuarioEdit = usuarioEdit;
    }

    public void registrarUsuario() {
        Pattern pattern = Pattern
                .compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                        + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(usuario.getCorreo());
        try {
            if ("".equals(usuario.getNombre().trim())) {
                showWarn("Debe ingresar un nombre.");
            } else if ("".equals(usuario.getApellido().trim())) {
                showWarn("Debe ingresar un apellido.");
            } else if ("".equals(usuario.getNombreUsuario().trim())) {
                showWarn("Ingrese una nombre de usuario");
            } else if (matcher.find() == false) {
                showWarn("Ingrese un email válido");
            } else if ("".equals(usuario.getPassword())) {
                showWarn("Ingrese una contraseña");
            } else if ("".equals(usuario.getConfpassword())) {
                showWarn("Confirme contraseña");
            } else if (usuario.getPassword().equals(usuario.getConfpassword())) {
                int resultadoRegistro = userDAO.registrarUsuario(usuario);
                if (resultadoRegistro > 0) {
                    showInfo(usuario.getNombre().trim().replace(".", ",") + " Guardado con exito");
                    usuario = new Usuario();
                } else {
                    showWarn("El nombre de usuario ya se encuentra registrado");
                }
            } else {
                showWarn("Contraseñas no coinciden");
            }
        } catch (Exception e) {
        }
    }

    public void cambiaEstado(int idUsuario) {
        try {
            if ("".equals(usuarioEdit.getNomUserSesion())) {
                mensajeDeAdvertencia("El campo nombre de usuario no puede estar vacio.");
            } else if ("".equals(usuarioEdit.getNombreSesion())) {
                mensajeDeAdvertencia("El campo nombre no puede estar vacio.");
            } else if ("".equals(usuarioEdit.getApellidoSesion())) {
                mensajeDeAdvertencia("El campo apellido no puede estar vacio.");
            } else if ("".equals(usuarioEdit.getCorreoSesion())) {
                mensajeDeAdvertencia("El campo correo no puede estar vacio.");
            } else {
                usuarioEdit.setIdUsuario(idUsuario);
                usuarioEdit.setEstado(true);
                if (userDAO.editarUsuario(usuarioEdit) > 0) {
                    usuarioSesion = usuarioEdit;
                    mensajeDeExito("Se actualizaron sus datos.");
                } else {
                    mensajeDeExito("La mayoria de los campos fueron actualizados");
                }
            }
        } catch (Exception e) {
            mensajeDeAdvertencia("Este error: " + e.getMessage());
        }
    }

    public void iniciarSesion() {
        try {
            if ("".equals(usuario.getNomUserSesion())) {
                mensajeDeAdvertencia("Ingrese un usuario");
            } else if ("".equals(usuario.getPassSesion())) {
                mensajeDeAdvertencia("Ingrese una contraseña");
            } else if (!usuario.getNomUserSesion().isEmpty() && !usuario.getPassSesion().isEmpty()) {
                usuarioSesion = userDAO.iniciarSesion(usuario);
                if (usuarioSesion != null) {
                    if (usuarioSesion.getCodigoAux() < 1) {
                        mensajeDeAdvertencia(usuarioSesion.getMensajeAux());
                    } else {
                        mensajeDeExito(usuarioSesion.getMensajeAux());

                        usuario = usuarioSesion;

                        List<Rol> rolesSesion = rolDAO.getRolesByUsers(usuarioSesion.getIdUsuarioSesion());

                        httpSession.setAttribute("username", usuarioSesion);

                        FacesContext.getCurrentInstance().getExternalContext()
                                .getSessionMap().put("usuario", usuarioSesion);
                        FacesContext.getCurrentInstance().getExternalContext()
                                .getSessionMap().put("roles", rolesSesion);
                        usuarioEdit = usuarioSesion;
                        usuarioEdit.setApellido(usuarioSesion.getApellidoSesion());
                        usuarioEdit.setNombre(usuarioSesion.getNombreSesion());
                        usuarioEdit.setCorreo(usuarioSesion.getCorreoSesion());
                        usuarioEdit.setNombreUsuario(usuarioSesion.getNomUserSesion());
                        facesContext.getExternalContext().redirect("faces/Vistas/Global/principal.xhtml");
                        //aqui cargamos los datos del usuario
                    }
                } else {
                    mensajeDeAdvertencia("Error de conexión al intentar iniciar sesión.");
                }
            }
        } catch (Exception e) {
            showWarn(e.getMessage());
        }
    }

    public void verificarCredenciales() {
        if (!"".equals(usuarioCredenciales.getCorreoSesion())) {
            if (userDAO.verificaCorreoUsuario(usuarioCredenciales) == 1) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Exito:", "¡¡Usuario registrado!!"));
                usuarioCredenciales.setEstado(true);
            } else {
                usuarioCredenciales.setEstado(false);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error:", "Usuario no encontrado. Vuelve a intentarlo con otro correo."));
            }
        } else {
            mensajeDeAdvertencia("Introduce el correo electrónico para validar tu cuenta.");
        }

    }

    public void cerrarPassword() {
        usuarioCredenciales = new Usuario();
    }

    public void cambiarPassword() {
        if (usuarioCredenciales.getRol().equals(usuarioCredenciales.getMensajeAux()) && !"".equals(usuarioCredenciales.getRol())) {
            if (!"".equals(usuarioCredenciales.getPassword()) && !"".equals(usuarioCredenciales.getConfpassword())) {
                if (!usuarioCredenciales.getPassword().equals(usuarioCredenciales.getConfpassword())) {
                    mensajeDeAdvertencia("Las contraseñas no coinciden.");
                } else {
                    if (userDAO.cambioPassword(usuarioCredenciales) == 1) {
                        PrimeFaces.current().executeScript("PF('dlgCambioPassword').hide()");
                        mensajeDeExito("Se cambio la contraseña con exito.");
                        usuarioCredenciales = new Usuario();
                    } else {
                        PrimeFaces.current().executeScript("PF('dlgCambioPassword').hide()");
                        mensajeDeAdvertencia("Ocurrio un error, vuelva a intentarlo más tarde.");
                        usuarioCredenciales = new Usuario();
                    }
                }
            } else {
                mensajeDeAdvertencia("Ingrese una contraseña.");
            }
        } else {
            mensajeDeAdvertencia("El codigo no coincide con el que se envio a su correo, vuelva a intentarlo.");
        }

    }

    public void enviarCorreoElectronico() {
        Correo enviarCorreo = new Correo("smtp-mail.outlook.com", 587, "UnidadPosgradoUTEQ@outlook.com", "LaUnidadTIC_SOFTWARE_VCKR");
        try {
            String userName = userDAO.cambioCredencialesUsuario(usuarioCredenciales);
            usuarioCredenciales.setNomUserSesion(userName);
            usuarioCredenciales.setMensajeAux(RandomStringGenerator.generateRandomString());
            String mensaje = "Mensaje desde el Sistema de Control de Horarios\n"
                    + "\n"
                    + "Ud. ha solicitado el cambio de contraseña, sus datos son los siguientes:\n"
                    + "Usuario: " + userName + "\n"
                    + "Codigo para cambiar la contraseña: " + usuarioCredenciales.getMensajeAux() + "\n"
                    + "\n"
                    + "Esta es una dirección de correo automática que no está gestionada por ninguna persona, "
                    + "por lo tanto, no obtendrá respuesta.\n"
                    + "\n"
                    + "Si tiene cualquier duda o inconveniente para acceder, debe contactarse con nosotros "
                    + "a través del correo electrónico victor.chun2017@uteq.edu.ec";
            enviarCorreo.sendEmail(usuarioCredenciales.getCorreoSesion(),
                    "Solicitud de cambio de contraseña.", mensaje);
            mensajeDeExito("Correo enviado exitosamente.");
            PrimeFaces.current().executeScript("PF('dlgpassword').hide()");
            PrimeFaces.current().executeScript("PF('dlgCambioPassword').show()");
        } catch (MessagingException e) {
            System.out.println("Error al enviar el correo: " + e.getMessage());
        }
    }

    public void cerrarSession() throws IOException {
        httpSession.removeAttribute("usuario");
        httpSession.removeAttribute("roles");
        facesContext.getExternalContext().redirect(
                "../../../");
        usuario.setPassSesion("");
        usuario.setNomUserSesion("");
        rol.setNombre("");
        usuario = new Usuario();
        usuarioSesion = new Usuario();
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
