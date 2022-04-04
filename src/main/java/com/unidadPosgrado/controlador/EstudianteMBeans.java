/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unidadPosgrado.controlador;

import com.unidadPosgrado.dao.EstudianteDAO;
import com.unidadPosgrado.modelo.Estudiante;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Alex
 */
public class EstudianteMBeans {

    private Estudiante estudiante;
    EstudianteDAO estudianteDAO;
    private List<Estudiante> listaEstudiante;
    
    public EstudianteMBeans() {
        estudiante = new Estudiante();
        estudianteDAO = new EstudianteDAO();
        listaEstudiante = new ArrayList<>();
    }
    @PostConstruct
    public void init() {
        listaEstudiante = estudianteDAO.getListaEstudiante();
    }
    
    public void registrarEstudiante() {
        try {
            if("".equals(estudiante.getNombre_estudiante())){
                showWarn("Debe ingresar un nombre.");
            } else if("".equals(estudiante.getApellido_estudiante())){
                showWarn("Debe ingresar un apellido.");
            } else if("".equals(estudiante.getTelefono_estudiante())){
                showWarn("Debe ingresar un teléfono.");
            } else if("".equals(estudiante.getCedula_estudiante())){
                showWarn("Debe ingresar una cédula.");
            } else if("".equals(estudiante.getSexo())){
                showWarn("Debe ingresar su género.");
            } else if("".equals(estudiante.getCorreo_estudiante())){
                showWarn("Debe ingresar un correo.");
            } else {
                int resultadoRegistro = estudianteDAO.registrarEstudiante(estudiante);
                if (resultadoRegistro > 0) {
                    showInfo(estudiante.getNombre_estudiante().trim().replace(".", ".") + " Registrado exitoso.");
                    listaEstudiante = estudianteDAO.getListaEstudiante();
                } else {
                    showWarn(estudiante.getNombre_estudiante().trim().replace(".", ".") + " ya se encuentra en el sistema.");
                }
                estudiante = new Estudiante();
            }
        } catch (Exception e) {
            showWarn(e.getMessage());
        }
    }
    
    public void onRowEdit(RowEditEvent<Estudiante> event) {
        try {
            if("".equals(event.getObject().getNombre_estudiante())){
                showWarn("No se puede modificar el registro porque el campo esta vacio.");
            } else if ("".equals(event.getObject().getApellido_estudiante())) {
                showWarn("No se puede modificar el registro porque el campo esta vacio.");
            } else if ("".equals(event.getObject().getCedula_estudiante())) {
                showWarn("No se puede modificar el registro porque el campo esta vacio.");
            } else if ("".equals(event.getObject().getTelefono_estudiante())) {
                showWarn("No se puede modificar el registro porque el campo esta vacio.");
            } else if ("".equals(event.getObject().getSexo())) {
                showWarn("No se puede modificar el registro porque el campo esta vacio.");
            } else if ("".equals(event.getObject().getCorreo_estudiante())) {
                showWarn("No se puede modificar el registro porque el campo esta vacio.");
            } else{
                Estudiante editEstudiante = new Estudiante(event.getObject().getId_estudiante(),
                event.getObject().getNombre_estudiante(), event.getObject().getApellido_estudiante(),
                event.getObject().getTelefono_estudiante(), event.getObject().getCedula_estudiante(),
                event.getObject().getSexo(), event.getObject().getCorreo_estudiante());
                int resultadoRegistro = estudianteDAO.editarEstudiante(editEstudiante);
                if(resultadoRegistro > 0){
                    showInfo("Se actualizo con éxito, " + editEstudiante.getNombre_estudiante().trim());
                } else {
                    showInfo("Se actualizo el registro con exito.");
                }
            }
            listaEstudiante = new ArrayList<>();
            listaEstudiante = estudianteDAO.getListaEstudiante();
        } catch (Exception e) {
            showWarn(e.getMessage());
        }
    }
    
    public void onRowCancel(RowEditEvent<Estudiante> event) {
        try {
            showWarn("Editar el nombre " + event.getObject().getNombre_estudiante() + " fue cancelado.");
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

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public EstudianteDAO getEstudianteDAO() {
        return estudianteDAO;
    }

    public void setEstudianteDAO(EstudianteDAO estudianteDAO) {
        this.estudianteDAO = estudianteDAO;
    }

    public List<Estudiante> getListaEstudiante() {
        return listaEstudiante;
    }

    public void setListaEstudiante(List<Estudiante> listaEstudiante) {
        this.listaEstudiante = listaEstudiante;
    }
    
}