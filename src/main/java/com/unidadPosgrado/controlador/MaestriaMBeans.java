/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unidadPosgrado.controlador;

import com.unidadPosgrado.dao.MaestriaDAO;
import com.unidadPosgrado.modelo.Maestria;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;

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
    }

    @PostConstruct
    public void init() {
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
            int resultadoRegistro = maestriaDAO.registrarMaestria(maestria);
            if (resultadoRegistro > 0) {
                showInfo(maestria.getNombre().trim().replace(".", ",") + " registrada con éxito.");
                listaMaestria = maestriaDAO.getListaMaestria();
            } else {
                showWarn(maestria.getNombre().trim().replace(".", ",") + " ya se encuentra registrada.");
            }
            maestria = new Maestria();
        } catch (Exception e) {
            showWarn(e.getMessage());
        }
    }

    public void onRowEdit(RowEditEvent<Maestria> event) {
        try {
            Maestria editMaestria = new Maestria(event.getObject().getIdMaestria(),
                    event.getObject().getNombre(), event.getObject().getDescripcion());
            int resultadoRegistro = maestriaDAO.editarMaestria(editMaestria);
            if (resultadoRegistro > 0) {
                showInfo("Se actualizo con éxito, " + editMaestria.getNombre().trim());
            } else {
                showWarn(editMaestria.getNombre().trim().replace(".", ",") + " no se pudo actualizar por que el registro ya se encuentra registrado."
                        + " Solo se actualiza la descripción si el registro a editar es el mismo.");
                listaMaestria = maestriaDAO.getListaMaestria();
            }
        } catch (Exception e) {
            showWarn(e.getMessage());
        }
    }

    public void onRowCancel(RowEditEvent<Maestria> event) {
        try {
            showWarn("Editar la " + event.getObject().getNombre() + " fue cancelada.");
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
