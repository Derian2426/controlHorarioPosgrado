/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.controlador;

import com.seguridad.modelo.Usuario;
import javax.faces.context.FacesContext;

/**
 *
 * @author Alex
 */
public class VerificarSeguridad {

    /**
     * Creates a new instance of VerificarSeguridad
     */
    public VerificarSeguridad() {
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
    
}
