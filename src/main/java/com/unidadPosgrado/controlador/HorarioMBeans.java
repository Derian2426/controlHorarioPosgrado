/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unidadPosgrado.controlador;

import com.unidadPosgrado.dao.HorarioDAO;
import com.unidadPosgrado.dao.MaestriaDAO;
import com.unidadPosgrado.modelo.Docente;
import com.unidadPosgrado.modelo.Maestria;
import com.unidadPosgrado.modelo.Modulo;
import com.unidadPosgrado.modelo.Periodo;
import com.unidadPosgrado.modelo.TiempoModulo;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleEvent;
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
    HorarioDAO horarioDAO;
    List<Maestria> busquedaMaestria;
    private boolean estado;
    private boolean estadoAsignacion;
    private ScheduleEvent<?> event = new DefaultScheduleEvent<>();
    private Docente docente;
    private List<Docente> listaDocente;
    private Modulo modulo;
    private List<Modulo> listaModulo;
    private TiempoModulo tiempoModulo;
    List<TiempoModulo> listaTiempoModulo;
    List<TiempoModulo> listaVerificacionTiempo;

    public HorarioMBeans() {
        maestriaBusqueda = new Maestria();
        maestriaDAO = new MaestriaDAO();
        integracionMaestria = new Maestria();
        busquedaMaestria = new ArrayList<>();
        periodo = new Periodo();
        busquedaMaestriaAux = new ArrayList<>();
        horarioDAO = new HorarioDAO();
        listaDocente = new ArrayList<>();
        docente = new Docente();
        modulo = new Modulo();
        listaTiempoModulo = new ArrayList<>();
        tiempoModulo = new TiempoModulo();
        listaVerificacionTiempo = new ArrayList<>();
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

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public ScheduleEvent<?> getEvent() {
        return event;
    }

    public void setEvent(ScheduleEvent<?> event) {
        this.event = event;
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

    public TiempoModulo getTiempoModulo() {
        return tiempoModulo;
    }

    public void setTiempoModulo(TiempoModulo tiempoModulo) {
        this.tiempoModulo = tiempoModulo;
    }

    public boolean isEstadoAsignacion() {
        return estadoAsignacion;
    }

    public void setEstadoAsignacion(boolean estadoAsignacion) {
        this.estadoAsignacion = estadoAsignacion;
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
            } else if (listaTiempoModulo.size() < 1) {
                showWarn("Seleccione al menos una fecha para la asignación del docente.");
            } else if (modulo.getIdMateria() < 1) {
                showWarn("Seleccione uno de los módulos.");
            } else if (docente.getId_docente() < 1) {
                showWarn("Seleccione a uno de los docentes.");
            } else if ("".equals(tiempoModulo.getDescripcion())) {
                showWarn("Ingrese una descripción.");
            } else {
                if (maestriaDAO.registrarPeriodo(integracionMaestria, periodo) > 0 && horarioDAO.registrarHorarioAsignacion(modulo, docente, integracionMaestria, tiempoModulo, listaTiempoModulo) > 0) {
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
    public void obtenerValidacionHorario(){
        listaVerificacionTiempo=horarioDAO.getListaValidacion(docente.getId_docente());
        System.out.println("    "+listaVerificacionTiempo.size());
    }

    public void onDateSelect(SelectEvent<LocalDateTime> selectEvent) {
        if (integracionMaestria.getIdMaestria() < 1) {
            showWarn("Seleccione una de las maestría.");
        } else if ("".equals(periodo.getNombrePeriodo())) {
            showWarn("Ingrese una descripción.");
        } else if (periodo.getFechaInicio() == null) {
            showWarn("Ingrese una fecha de inicio.");
        } else if (periodo.getFechaFin() == null) {
            showWarn("Seleccione una fecha de finalización.");
        } else if (periodo.getFechaFin().before(periodo.getFechaInicio())) {
            showWarn("La fecha no puede ser anterior a la fecha de inicio del Periodo.");
        } else if (convertToDateViaSqlTimestamp(selectEvent.getObject()).before(periodo.getFechaInicio())) {
            showWarn("La fecha que selecciono no puede ser anterior a la fecha de inicio del Periodo.");
        } else if (convertToDateViaSqlTimestamp(selectEvent.getObject()).after(periodo.getFechaFin())) {
            showWarn("La fecha que selecciono no puede pasar a la fecha de finalización del periodo.");
        } else {
            event = DefaultScheduleEvent.builder()
                    .startDate(selectEvent.getObject())
                    .endDate(selectEvent.getObject().plusHours(1))
                    .build();
            tiempoModulo.setFechaAsignacion(convertToDateViaSqlTimestamp(selectEvent.getObject()));
            PrimeFaces.current().executeScript("PF('seleccionFecha').show()");
            estado = true;
        }

    }

    public Date convertToDateViaSqlTimestamp(LocalDateTime dateToConvert) {
        return java.sql.Timestamp.valueOf(dateToConvert);
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
        listaDocente = horarioDAO.getListaDocente(integracionMaestria.getIdMaestria());
        listaModulo = horarioDAO.getListaModulo(integracionMaestria.getIdMaestria());
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

    public void addEvent() {
        boolean verificar=false;
        if (modulo.getIdMateria() < 1) {
            showWarn("Seleccione uno de los módulos.");
        } else if (docente.getId_docente() < 1) {
            showWarn("Seleccione a uno de los docentes.");
        } else if ("".equals(tiempoModulo.getDescripcion())) {
            showWarn("Ingrese una descripción.");
        } else {
            DefaultScheduleEvent<?> event = DefaultScheduleEvent.builder()
                    .title(tiempoModulo.getDescripcion())
                    .startDate(convertToLocalDateTimeViaInstant(tiempoModulo.getFechaAsignacion()))
                    .endDate(convertToLocalDateTimeViaInstant(tiempoModulo.getFechaAsignacion()))
                    .borderColor("orange")
                    .build();
            for(TiempoModulo horario:listaVerificacionTiempo){
                if(tiempoModulo.getFechaAsignacion().compareTo(horario.getFechaAsignacion())==0){
                    verificar=true;
                    break;
                }
            }
            if(!verificar){
                eventModel.addEvent(event);
            listaTiempoModulo.add(tiempoModulo);
            estadoAsignacion = true;
            PrimeFaces.current().executeScript("PF('seleccionFecha').hide()");
            }else{
                showWarn("Erese Gay");
            }
            
        }

    }

    public LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();
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
