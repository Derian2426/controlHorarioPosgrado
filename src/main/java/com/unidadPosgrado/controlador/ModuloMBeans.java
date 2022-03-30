/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unidadPosgrado.controlador;

import com.unidadPosgrado.dao.ModuloDAO;
import com.unidadPosgrado.modelo.Modulo;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author HP
 */
public class ModuloMBeans {

    private Modulo modulo;
    ModuloDAO moduloDAO;
    private List<Modulo> listaModulo;

    public ModuloMBeans() {
        modulo = new Modulo();
        moduloDAO = new ModuloDAO();
    }

    @PostConstruct
    public void init() {
        listaModulo = moduloDAO.getListaModulo();
    }

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public List<Modulo> getListaModulo() {
        return listaModulo;
    }

    public void setListaModulo(List<Modulo> listaModulo) {
        this.listaModulo = listaModulo;
    }

    public void registrarModulo() {
        try {
            int resultadoRegistro = moduloDAO.registrarModulo(modulo);
            if (resultadoRegistro > 0) {
                showInfo(modulo.getNombreMateria().trim().replace(".", ",") + " registrado con éxito.");
                listaModulo = moduloDAO.getListaModulo();
            } else {
                showWarn(modulo.getNombreMateria().trim().replace(".", ",") + " ya se encuentra registrado.");
            }
            modulo = new Modulo();
        } catch (Exception e) {
            showWarn(e.getMessage());
        }
    }
    public void onRowEdit(RowEditEvent<Modulo> event) {
        try {
            Modulo editModulo = new Modulo(event.getObject().getIdMateria(),
                    event.getObject().getNombreMateria(), event.getObject().getDescripcion(),event.getObject().getHora_materia());
            int resultadoRegistro = moduloDAO.editarModulo(editModulo);
            if (resultadoRegistro > 0) {
                showInfo("Se actualizo con éxito, " + editModulo.getNombreMateria().trim());
            } else {
                showWarn(editModulo.getNombreMateria().trim().replace(".", ",") + " no se pudo actualizar por que el registro ya se encuentra registrado."
                        + " Solo se actualiza la descripción si el registro a editar es el mismo.");
                listaModulo = moduloDAO.getListaModulo();
            }
        } catch (Exception e) {
            showWarn(e.getMessage());
        }
    }

    public void onRowCancel(RowEditEvent<Modulo> event) {
        try {
            showWarn("L edición del módulo de " + event.getObject().getNombreMateria()+ " fue cancelada.");
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
