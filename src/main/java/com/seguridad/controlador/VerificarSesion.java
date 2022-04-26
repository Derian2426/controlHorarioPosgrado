/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.controlador;

import com.seguridad.modelo.Rol;
import com.seguridad.modelo.Usuario;
import java.util.List;
import javax.faces.context.FacesContext;

/**
 *
 * @author Alex
 */
public class VerificarSesion {
    
    FacesContext context = FacesContext.getCurrentInstance();
    List<Rol> listaRoles = (List<Rol>) context.getExternalContext().getSessionMap().get("roles");
    public VerificarSesion() {
    }
    
    public void verificarSesion(){
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            Usuario us = (Usuario) context.getExternalContext().getSessionMap().get("usuario");
            
            if(us == null){
                context.getExternalContext().redirect("../../../");
            }
        } catch (Exception e) {
            
        }
    }
    public String verificarAdmin(){
        if("Administrador".equals(listaRoles.get(0).getNombre())){
            return "true";
        } else {
            return "false";
        }
    }
}
