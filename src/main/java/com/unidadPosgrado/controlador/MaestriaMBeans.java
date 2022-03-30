/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unidadPosgrado.controlador;

import com.unidadPosgrado.dao.MaestriaDAO;
import com.unidadPosgrado.modelo.Maestria;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

/**
 *
 * @author HP
 */
public class MaestriaMBeans {

    private Maestria maestria;
    MaestriaDAO maestriaDAO;
    private List<Maestria> listaMaestria;

    public MaestriaMBeans() {
        maestria = new Maestria();
        maestriaDAO = new MaestriaDAO();
        listaMaestria = maestriaDAO.getListaMaestria();
    }

    public Maestria getMaestria() {
        return maestria;
    }

    public void setMaestria(Maestria maestria) {
        this.maestria = maestria;
    }

    public List<Maestria> getListaMaestria() {
        return listaMaestria;
    }

    public void setListaMaestria(List<Maestria> listaMaestria) {
        this.listaMaestria = listaMaestria;
    }

    public void registrarMaestria() {
        try {
            int resultadoRegistro=maestriaDAO.registrarMaestria(maestria);
            if(resultadoRegistro>0){
                showInfo(maestria.getNombre().trim().replace(".", ",")+" registrada con éxito.");
            }else{
                showWarn(maestria.getNombre().trim().replace(".", ",")+" ya se encuentra registrada.");
            }
            maestria= new Maestria();
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
    public void vaciarCampos(){
        
    }

}
