/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.controlador;

import com.seguridad.modelo.Rol;
import com.seguridad.modelo.Usuario;
import java.io.IOException;
import java.util.List;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author Alex
 */
public class VerificarSesion {
    
    boolean estado = true;
    
    FacesContext context = FacesContext.getCurrentInstance();
    List<Rol> listaRoles = (List<Rol>) context.getExternalContext().getSessionMap().get("roles");
    ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

    public VerificarSesion() {
        
    }

    public void verificarSesion() {
        try {
            FacesContext contexto2 = FacesContext.getCurrentInstance();
            Usuario us = (Usuario) contexto2.getExternalContext().getSessionMap().get("usuario");

            if (us == null) {
                contexto2.getExternalContext().redirect("../../../");
            }

        } catch (Exception e) {
        }
    }
    
    public void redireccionExternas() throws IOException {
        if (!"Administrador".equals(listaRoles.get(0).getNombre())) {
            externalContext.redirect("../Global/principal.xhtml");
        }
    }

    public String verificarAdmin() {
        if ("Administrador".equals(listaRoles.get(0).getNombre()) ) {
            return "true";
        } else {
            return "false";
        }
    }
}
