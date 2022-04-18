/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unidadPosgrado.controlador;

import com.unidadPosgrado.dao.MaestriaDAO;
import com.unidadPosgrado.modelo.Maestria;
import com.unidadPosgrado.modelo.Periodo;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;

/**
 *
 * @author HP
 */
public class HorarioMBeans {

    /**
     * Creates a new instance of HorarioMBeans
     */
    private ScheduleModel eventModel;
    private Periodo periodo;
    private Maestria integracionMaestria;
    private Maestria maestriaBusqueda;
    private List<Maestria> listaMaestria;
    List<Maestria> busquedaMaestriaAux;
    MaestriaDAO maestriaDAO;
    List<Maestria> busquedaMaestria;

    public HorarioMBeans() {
        maestriaBusqueda = new Maestria();
        maestriaDAO = new MaestriaDAO();
        integracionMaestria = new Maestria();
        busquedaMaestria = new ArrayList<>();
        periodo = new Periodo();
        busquedaMaestriaAux = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        eventModel = new DefaultScheduleModel();
        listaMaestria = maestriaDAO.getListaMaestria();
        busquedaMaestriaAux = listaMaestria;
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public Maestria getIntegracionMaestria() {
        return integracionMaestria;
    }

    public void setIntegracionMaestria(Maestria integracionMaestria) {
        this.integracionMaestria = integracionMaestria;
    }

    public Maestria getMaestriaBusqueda() {
        return maestriaBusqueda;
    }

    public void setMaestriaBusqueda(Maestria maestriaBusqueda) {
        this.maestriaBusqueda = maestriaBusqueda;
    }

    public List<Maestria> getListaMaestria() {
        return listaMaestria;
    }

    public void setListaMaestria(List<Maestria> listaMaestria) {
        this.listaMaestria = listaMaestria;
    }

    public void registrarPeriodo() {
        try {
            if ("".equals(integracionMaestria.getNombre()) || integracionMaestria.getNombre() == null) {
                showWarn("Seleccione una maestría.");
            } else if ("".equals(periodo.getNombrePeriodo())) {
                showWarn("Ingrese una descripción.");
            } else if (periodo.getFechaInicio() == null) {
                showWarn("Seleccione una fecha de inicio.");
            } else if (periodo.getFechaFin() == null) {
                showWarn("Seleccione una fecha de finaalización.");
            } else if (periodo.getFechaFin().before(periodo.getFechaInicio())) {
                showWarn("La fecha no puede ser anterior a la fecha de inicio del Periodo.");
            } else {
                if (maestriaDAO.registrarPeriodo(integracionMaestria, periodo) > 0) {
                    showInfo("Periodo registrado con exito.");
                    integracionMaestria = new Maestria();
                    periodo = new Periodo();
                    maestriaBusqueda = new Maestria();
                } else {
                    showWarn("Error al registrar el periodo, esta fecha ya se ha utilizado para la planificación"
                            + " de esta maestría.");
                }
            }

        } catch (Exception e) {
            showError(e.getMessage() + "Error al registrar el periodo, vuelva a intentarlo.");
        }

    }

    public void cancelarPeriodo() {
        try {
            integracionMaestria = new Maestria();
            periodo = new Periodo();
            maestriaBusqueda = new Maestria();
            showWarn("Se cancelo el registro.");
        } catch (Exception e) {
            showError(e.getMessage());
        }

    }

    public void llenaMaestriaPeriodo(Maestria maestria) {
        integracionMaestria.setIdMaestria(maestria.getIdMaestria());
        integracionMaestria.setNombre(maestria.getNombre());
        integracionMaestria.setDescripcion(maestria.getDescripcion());
    }

    public void buscarMaestria() {
        if (maestriaBusqueda.getNombre() == null || "".equals(maestriaBusqueda.getNombre())) {
            listaMaestria = busquedaMaestriaAux;
        } else {
            listaMaestria = busquedaMaestriaAux;
            for (Maestria busqueda : listaMaestria) {
                if (busqueda.getNombre().toUpperCase().contains(maestriaBusqueda.getNombre().toUpperCase())) {
                    busquedaMaestria.add(busqueda);
                }
            }
            listaMaestria = busquedaMaestria;
            busquedaMaestria = new ArrayList<>();
//            maestriaBusqueda = new Maestria();
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
