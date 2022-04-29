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
import java.sql.Timestamp;
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
    List<TiempoModulo> listaTiempoAsignados;
    List<TiempoModulo> aux;
    String descripcion;
    private List<Maestria> listaHorario;
    
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
        listaTiempoAsignados = new ArrayList<>();
        listaHorario = new ArrayList<>();
        aux = new ArrayList<>();
    }
    
    @PostConstruct
    public void init() {
        eventModel = new DefaultScheduleModel();
        listaMaestria = maestriaDAO.getListaMaestriaPeriodo();
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
    
    public List<Maestria> getListaHorario() {
        return listaHorario;
    }
    
    public void setListaHorario(List<Maestria> listaHorario) {
        this.listaHorario = listaHorario;
    }
    
    public void registrarPeriodo() {
        try {
            if (!verificaTiempoAsignacion() && !verificaTiempoSeleccion()) {
                listaTiempoModulo.add(tiempoModulo);
                event = DefaultScheduleEvent.builder()
                        .title(tiempoModulo.getDescripcion())
                        .startDate(convertToLocalDateTimeViaInstant(tiempoModulo.getFechaAsignacion()))
                        .endDate(convertToLocalDateTimeViaInstant(tiempoModulo.getFechaAsignacion()))
                        .borderColor("orange")
                        .build();
                eventModel.addEvent(event);
            }
            if ("".equals(integracionMaestria.getNombre()) || integracionMaestria.getNombre() == null) {
                showWarn("Seleccione una maestría.");
            } else if ("".equals(integracionMaestria.getDescripcion())) {
                showWarn("Ingrese una descripción.");
            } else if (integracionMaestria.getFechaInicio() == null) {
                showWarn("Seleccione una fecha de inicio.");
            } else if (integracionMaestria.getFechaFin() == null) {
                showWarn("Seleccione una fecha de finaalización.");
            } else if (integracionMaestria.getFechaFin().before(integracionMaestria.getFechaInicio())) {
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
                String mensaje = "Estas fechas ya se encuentran asignadas -->";
                int estado = -1;
                aux = horarioDAO.registrarHorarioAsignaciones(modulo, docente, integracionMaestria, tiempoModulo, listaTiempoModulo);
                
                for (TiempoModulo tm : aux) {
                    if (tm.getIdTiempo() == 1) {
                        mensaje += tm.getFechaAsignacion() + " ,";
                        estado = tm.getIdTiempo();
                    } else {
                        if (tm.getIdTiempo() == 0) {
                            estado = tm.getIdTiempo();
                            break;
                        }
                    }
                    
                }
                if (estado == 1) {
                    showWarn(mensaje);
                } else {
                    showInfo("OK");
                }

//                if (verificaTiempo().size() < 1) {
//                    if (horarioDAO.registrarHorarioAsignacion(modulo, docente, integracionMaestria, tiempoModulo, listaTiempoModulo) > 0) {
//                        showInfo("Asignación registrada con exito.");
//                        docente = new Docente();
//                        modulo = new Modulo();
//                        tiempoModulo = new TiempoModulo();
//                        listaHorario = horarioDAO.getListaHorario(integracionMaestria.getIdCurso());
//                        estadoAsignacion = false;
//                        maestriaBusqueda = new Maestria();
//                        listaDocente = horarioDAO.getListaDocente(integracionMaestria.getIdMaestria(), integracionMaestria.getIdCurso());
//                        listaModulo = horarioDAO.getListaModulo(integracionMaestria.getIdMaestria(), integracionMaestria.getIdCurso());
//                    } else {
//                        showWarn("Error al registrar el periodo, esta fecha ya se ha utilizado para la planificación"
//                                + " de esta maestría.");
//                    }
//                    PrimeFaces.current().executeScript("PF('seleccionFecha').hide()");
//                } else {
//                    String fechasUtilizadas = "";
//                    for (TiempoModulo horario : listaTiempoAsignados) {
//                        fechasUtilizadas += horario.getFechaAsignacion() + " ,";
//                    }
//                    showWarn("Estas fechas se encuentran asignadas " + fechasUtilizadas);
//                }
            }
            
        } catch (Exception e) {
            showError(e.getMessage() + "Error al registrar el periodo, vuelva a intentarlo.");
        }
        
    }
    
    public List<TiempoModulo> verificaTiempo() {
        listaTiempoAsignados = new ArrayList<>();
        listaVerificacionTiempo = horarioDAO.getListaValidacion(docente.getId_docente());
        for (TiempoModulo horario : listaVerificacionTiempo) {
            if (tiempoModulo.getFechaAsignacion().compareTo(horario.getFechaAsignacion()) == 0) {
                listaTiempoAsignados.add(horario);
            }
        }
        return listaTiempoAsignados;
    }
    
    public boolean verificaTiempoSeleccion() {
        boolean verifica = false;
        for (TiempoModulo horario : listaTiempoModulo) {
            if (tiempoModulo.getFechaAsignacion().compareTo(horario.getFechaAsignacion()) == 0) {
                verifica = true;
                break;
            }
        }
        return verifica;
    }
    
    public boolean verificaTiempoAsignacion() {
        boolean verificarTiempo = false;
        for (TiempoModulo horario : listaVerificacionTiempo) {
            if (tiempoModulo.getFechaAsignacion().compareTo(horario.getFechaAsignacion()) == 0) {
                verificarTiempo = true;
                break;
            }
        }
        return verificarTiempo;
    }
    
    public void confirm() {
        if (modulo.getIdMateria() < 1) {
            showWarn("Seleccione uno de los módulos.");
        } else if (docente.getId_docente() < 1) {
            showWarn("Seleccione a uno de los docentes.");
        } else if ("".equals(tiempoModulo.getDescripcion())) {
            showWarn("Ingrese una descripción.");
        } else {
            
            if (!verificaTiempoAsignacion() && !verificaTiempoSeleccion()) {
                event = DefaultScheduleEvent.builder()
                        .title(tiempoModulo.getDescripcion())
                        .startDate(convertToLocalDateTimeViaInstant(tiempoModulo.getFechaAsignacion()))
                        .endDate(convertToLocalDateTimeViaInstant(tiempoModulo.getFechaAsignacion()))
                        .borderColor("orange")
                        .build();
                eventModel.addEvent(event);
                listaTiempoModulo.add(tiempoModulo);
                estadoAsignacion = true;
                descripcion = tiempoModulo.getDescripcion();
                tiempoModulo = new TiempoModulo();
                tiempoModulo.setDescripcion(descripcion);
                PrimeFaces.current().executeScript("PF('seleccionFecha').hide()");
            } else {
                showWarn("Ya se asigno el docente en este horario.");
            }
        }
    }
    
    public void obtenerValidacionHorario() {
        listaVerificacionTiempo = horarioDAO.getListaValidacion(docente.getId_docente());
    }
    
    public void onDateSelect(SelectEvent<LocalDateTime> selectEvent) {
        if (integracionMaestria.getIdMaestria() < 1) {
            showWarn("Seleccione una de las maestría.");
        } else if ("".equals(integracionMaestria.getDescripcion())) {
            showWarn("Ingrese una descripción.");
        } else if (integracionMaestria.getFechaInicio() == null) {
            showWarn("Ingrese una fecha de inicio.");
        } else if (integracionMaestria.getFechaFin() == null) {
            showWarn("Seleccione una fecha de finalización.");
        } else if (integracionMaestria.getFechaFin().before(integracionMaestria.getFechaInicio())) {
            showWarn("La fecha no puede ser anterior a la fecha de inicio del Periodo.");
        } else if (convertToDateViaSqlTimestamp(selectEvent.getObject()).before(integracionMaestria.getFechaInicio())) {
            showWarn("La fecha que selecciono no puede ser anterior a la fecha de inicio del Periodo.");
        } else if (convertToDateViaSqlTimestamp(selectEvent.getObject()).after(integracionMaestria.getFechaFin())) {
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
    
    public void onEventSelect(SelectEvent<ScheduleEvent<?>> selectEvent) {
        event = selectEvent.getObject();
        for (Maestria mHorario : listaHorario) {
            if (Integer.parseInt(event.getId()) == mHorario.getIdMaestria()
                    && Integer.parseInt(event.getDescription()) == mHorario.getIdDocente()
                    && convertToDateViaSqlTimestamp(event.getStartDate()).compareTo(mHorario.getFechaInicio()) == 0) {
                modulo.setIdMateria(mHorario.getIdMaestria());
                modulo.setNombreMateria(mHorario.getNombre());
                docente.setNombre_docente(mHorario.getNombreDocente());
                docente.setId_docente(mHorario.getIdDocente());
                tiempoModulo.setDescripcion(mHorario.getDescripcion());
                tiempoModulo.setFechaAsignacion(mHorario.getFechaInicio());
                break;
            }
        }
//        docente = new Docente();
//        modulo = new Modulo();
//        tiempoModulo = new TiempoModulo();
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
        integracionMaestria.setFechaInicio(maestria.getFechaInicio());
        integracionMaestria.setFechaFin(maestria.getFechaFin());
        integracionMaestria.setIdCurso(maestria.getIdCurso());
        listaDocente = horarioDAO.getListaDocente(integracionMaestria.getIdMaestria(), integracionMaestria.getIdCurso());
        listaModulo = horarioDAO.getListaModulo(integracionMaestria.getIdMaestria(), integracionMaestria.getIdCurso());
        listaHorario = horarioDAO.getListaHorario(integracionMaestria.getIdCurso());
        llenaFechasHorario();
        estadoAsignacion = false;
    }
    
    public void llenaFechasHorario() {
        try {
            Timestamp ts;
            event = new DefaultScheduleEvent<>();
            eventModel = new DefaultScheduleModel();
            for (Maestria mHorario : listaHorario) {
                ts = new Timestamp(mHorario.getFechaInicio().getTime());
                event = DefaultScheduleEvent.builder()
                        .id(String.valueOf(mHorario.getIdMaestria()))
                        .title(mHorario.getIdDocente() + " " + mHorario.getNombreDocente() + " "
                                + mHorario.getNombre())
                        .description(String.valueOf(mHorario.getIdDocente()))
                        .startDate(convertToLocalDateTimeViaInstant(ts))
                        .endDate(convertToLocalDateTimeViaInstant(ts))
                        .borderColor("orange")
                        .build();
                eventModel.addEvent(event);
            }
        } catch (Exception e) {
            System.out.println("Hola" + e.getMessage());
        }
        
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
            if (!verificaTiempoAsignacion() && !verificaTiempoSeleccion()) {
                eventModel.addEvent(event);
                listaTiempoModulo.add(tiempoModulo);
                estadoAsignacion = true;
                descripcion = tiempoModulo.getDescripcion();
                tiempoModulo = new TiempoModulo();
                tiempoModulo.setDescripcion(descripcion);
                PrimeFaces.current().executeScript("PF('seleccionFecha').hide()");
            } else {
                showWarn("Ya se asigno el docente en este horario.");
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
