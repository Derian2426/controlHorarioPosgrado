/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unidadPosgrado.controlador;

import com.unidadPosgrado.dao.DocenteDAO;
import com.unidadPosgrado.modelo.Docente;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Alex
 */
public class DocenteMBeans {

    private Docente docente;
    DocenteDAO docenteDAO;
    private List<Docente> listaDocente;
    
    public DocenteMBeans() {
        docente = new Docente();
        docenteDAO = new DocenteDAO();
        listaDocente = new ArrayList<>();
    }
    
    @PostConstruct
    public void init() {
        listaDocente = docenteDAO.getListaDocente();
    }
    
    public void registrarDocente() {
        try {
            if ("".equals(docente.getNombre_docente().trim())) {
                showWarn("Debe ingresar un nombre.");
            } else if ("".equals(docente.getApellido_docente().trim())) {
                showWarn("Debe ingresar un apellido.");
            } else if ("".equals(docente.getCedula_docente().trim())) {
                showWarn("Debe ingresar un cédula.");
            } else if ("".equals(docente.getTelefono_docente().trim())) {
                showWarn("Debe ingresar una teléfono.");
            } else if ("".equals(docente.getCorreo_docente().trim())) {
                showWarn("Debe ingresar un correo.");
            } else {
                int resultadoRegistro = docenteDAO.registrarDocente(docente);
                if (resultadoRegistro > 0) {
                    showInfo(docente.getNombre_docente().trim().replace(".", ",") + " Guardado con éxito.");
                    docente = new Docente();
                    PrimeFaces.current().executeScript("PF('dlgDocente').hide()");
                    listaDocente = docenteDAO.getListaDocente();
                } else {
                    showWarn("La cédula " + docente.getCedula_docente().trim().replace(".", ",") + " ya se encuentra registrada.");
                }
                docente = new Docente();
            }
        } catch (Exception e) {
        }
    }
    
    public void onRowEdit(RowEditEvent<Docente> event) {
        try {
            if ("".equals(event.getObject().getNombre_docente().trim())) {
                showWarn("No se puede modificar el registro porque el campo esta vacio.");
            } else if ("".equals(event.getObject().getApellido_docente().trim())) {
                showWarn("No se puede modificar el registro porque el campo esta vacio.");
            } else if ("".equals(event.getObject().getCedula_docente().trim())) {
                showWarn("No se puede modificar el registro porque el campo esta vacio.");
            } else if ("".equals(event.getObject().getTelefono_docente().trim())) {
                showWarn("No se puede modificar el registro porque el campo esta vacio.");
            } else if ("".equals(event.getObject().getCorreo_docente().trim())) {
                showWarn("No se puede modificar el registro porque el campo esta vacio.");
            } else {
                Docente editDocente = new Docente(event.getObject().getId_docente(),
                    event.getObject().getNombre_docente(), event.getObject().getApellido_docente(),
                    event.getObject().getCedula_docente(), event.getObject().getTelefono_docente(),
                    event.getObject().getCorreo_docente());
                int resultadoRegistro = docenteDAO.editarDocente(editDocente);
                if (resultadoRegistro > 0) {
                    showInfo("Se actualizo con éxito, " + editDocente.getNombre_docente().trim());
                } else {
                    showWarn(editDocente.getNombre_docente().trim().replace(".", ",") + " no se actualizo.");
                }
            }
            listaDocente = new ArrayList<>();
            docente = new Docente();
            listaDocente = docenteDAO.getListaDocente();
        } catch (Exception e) {
            showWarn(e.getMessage());
        }
    }
    
    public void onRowCancel(RowEditEvent<Docente> event) {
        try {
            showWarn("Edición del nombre " + event.getObject().getNombre_docente()+ " fue cancelado.");
        } catch (Exception e) {
            showWarn(e.getMessage());
        }
    }
    
    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }

    public void showWarn(String message) {
        addMessage(FacesMessage.SEVERITY_WARN, "Advertencia", message);
    }

    public void showInfo(String message) {
        addMessage(FacesMessage.SEVERITY_INFO, "Exito", message);
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public List<Docente> getListaDocente() {
        return listaDocente;
    }

    public void setListaDocente(List<Docente> listaDocente) {
        this.listaDocente = listaDocente;
    }
    
}
