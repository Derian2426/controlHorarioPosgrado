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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DefaultScheduleEvent;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.DualListModel;
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
    private DualListModel<Date> tiempoHorario;
    List<Date> horaSource;
    List<Date> horaTarget;

    private ScheduleModel eventModel;
    private Periodo periodo;
    private Maestria integracionMaestria;
    private Maestria maestriaBusqueda;
    private List<Maestria> listaMaestria;
    List<Maestria> busquedaMaestriaAux;
    MaestriaDAO maestriaDAO;
    HorarioDAO horarioDAO;
    List<Maestria> busquedaMaestria;
    private String mensaje;
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
        listaModulo = new ArrayList<>();
        aux = new ArrayList<>();
        horaSource = new ArrayList<>();
        horaTarget = new ArrayList<>();
        tiempoHorario = new DualListModel<>(horaSource, horaTarget);
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

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public void registrarPeriodo() {
        try {
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
            } else if (horaTarget.size() < 1) {
                showWarn("Seleccione al menos una fecha para la asignación del docente.");
            } else if (modulo.getIdMateria() < 1) {
                showWarn("Seleccione uno de los módulos.");
            } else if (docente.getId_docente() < 1) {
                showWarn("Seleccione a uno de los docentes.");
            } else if ("".equals(tiempoModulo.getDescripcion())) {
                showWarn("Ingrese una descripción.");
            } else {
                mensaje = "Estas fechas ya se encuentran asignadas: ";
                int estado = -1;
                aux = horarioDAO.registrarHorarioAsignaciones(modulo, docente, integracionMaestria, tiempoModulo, horaTarget);
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
                    mensaje = mensaje.substring(0, mensaje.length() - 1);
                    mensaje += ".";
                    showWarn(mensaje);
                    eventModel = new DefaultScheduleModel();
                    event = new DefaultScheduleEvent<>();
                    docente = new Docente();
                    modulo = new Modulo();
                    tiempoModulo = new TiempoModulo();
                    listaHorario = horarioDAO.getListaHorario(integracionMaestria.getIdCurso());
                    estadoAsignacion = false;
                    maestriaBusqueda = new Maestria();
                    listaDocente = horarioDAO.getListaDocente(integracionMaestria.getIdMaestria(), integracionMaestria.getIdCurso());
                    listaModulo = horarioDAO.getListaModulo(integracionMaestria.getIdMaestria(), integracionMaestria.getIdCurso());

                    listaTiempoModulo = new ArrayList<>();
                    horaSource = new ArrayList<>();
                    horaTarget = new ArrayList<>();
                    tiempoHorario = new DualListModel<>(horaSource, horaTarget);
                    PrimeFaces.current().executeScript("PF('seleccionFecha').hide()");
                    PrimeFaces.current().executeScript("PF('transaccion').show()");
                    llenaFechasHorario();

                } else {
                    showInfo("Asignación registrada con exito.");
                    eventModel = new DefaultScheduleModel();
                    event = new DefaultScheduleEvent<>();
                    docente = new Docente();
                    modulo = new Modulo();
                    tiempoModulo = new TiempoModulo();
                    listaHorario = horarioDAO.getListaHorario(integracionMaestria.getIdCurso());
                    estadoAsignacion = false;
                    maestriaBusqueda = new Maestria();
                    listaDocente = horarioDAO.getListaDocente(integracionMaestria.getIdMaestria(), integracionMaestria.getIdCurso());
                    listaModulo = horarioDAO.getListaModulo(integracionMaestria.getIdMaestria(), integracionMaestria.getIdCurso());
                    listaTiempoModulo = new ArrayList<>();

                    horaSource = new ArrayList<>();
                    horaTarget = new ArrayList<>();
                    tiempoHorario = new DualListModel<>(horaSource, horaTarget);
                    PrimeFaces.current().executeScript("PF('seleccionFecha').hide()");
                    llenaFechasHorario();
                    if (listaModulo.size() == 0) {
                        horarioDAO.registrarMaestria(integracionMaestria.getIdCurso());
                    }
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

    public void cerrarDialigo() {
        horaSource = new ArrayList<>();
        horaTarget = new ArrayList<>();
        tiempoHorario = new DualListModel<>(horaSource, horaTarget);
        listaDocente = new ArrayList<>();
        listaModulo = new ArrayList<>();
        listaModulo = horarioDAO.getListaModulo(integracionMaestria.getIdMaestria(), integracionMaestria.getIdCurso());
        listaDocente = horarioDAO.getListaDocente(integracionMaestria.getIdMaestria(), integracionMaestria.getIdCurso());
    }

    public List<TiempoModulo> verificaTiempo() {
        listaTiempoAsignados = new ArrayList<>();
        listaVerificacionTiempo = horarioDAO.getListaValidacion(docente.getId_docente(), integracionMaestria.getFechaInicio(), integracionMaestria.getFechaFin());
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
                        .id(String.valueOf(docente.getId_docente()))
                        .title(tiempoModulo.getDescripcion())
                        .description(String.valueOf(modulo.getIdMateria()))
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
        horaTarget = new ArrayList<>();
        horaSource = new ArrayList<>();
        listaVerificacionTiempo = horarioDAO.getListaValidacion(docente.getId_docente(), integracionMaestria.getFechaInicio(), integracionMaestria.getFechaFin());
        horaSource = getListaEntreFechas(integracionMaestria.getFechaInicio(), integracionMaestria.getFechaFin());
        eliminaFechaRepetidas();
        asignaFecha(tiempoModulo.getFechaAsignacion());
        tiempoHorario = new DualListModel<>(horaSource, horaTarget);
    }

    public void eliminaFechaRepetidas() {
        for (TiempoModulo tiempo : listaVerificacionTiempo) {
            for (Date tiempoDisponible : horaSource) {
                if (tiempo.getFechaAsignacion().getDate() == tiempoDisponible.getDate()
                        && tiempo.getFechaAsignacion().getMonth() == tiempoDisponible.getMonth()
                        && tiempo.getFechaAsignacion().getDay() == tiempoDisponible.getDay()) {
                    horaSource.remove(tiempoDisponible);
                    break;
                }
            }
        }
        for (Maestria date : listaHorario) {
            for (Date tiempoDisponible : horaSource) {
                if (date.getFechaInicio().getDate() == tiempoDisponible.getDate()
                        && date.getFechaInicio().getMonth() == tiempoDisponible.getMonth()
                        && date.getFechaInicio().getDay() == tiempoDisponible.getDay()) {
                    horaSource.remove(tiempoDisponible);
                    break;
                }
            }
        }
    }

    public void asignaFecha(Date fecha) {
        if (!busquedaFechaTarget(fecha)) {
            for (Date tiempoDisponible : horaSource) {
                if (fecha.getYear() == tiempoDisponible.getYear()
                        && fecha.getMonth() == tiempoDisponible.getMonth()
                        && fecha.getDay() == tiempoDisponible.getDay()) {
                    horaTarget.add(tiempoDisponible);
                    horaSource.remove(tiempoDisponible);
                    break;
                }
            }
        }

    }

    public boolean busquedaFechaTarget(Date fechaDate) {
        boolean verifica = false;
        for (Date fechaSource : horaTarget) {
            if (fechaSource.getMonth() == fechaDate.getMonth() && fechaSource.getDay() == fechaDate.getDay()
                    && fechaSource.getYear() == fechaDate.getYear()) {
                if (busquedaFecha(fechaDate)) {
                    horaSource.remove(fechaSource);
                }
                verifica = true;
                break;
            }
        }
        return verifica;
    }

    public boolean busquedaFecha(Date fechaDate) {
        boolean verifica = false;
        for (Date fechaSource : horaSource) {
            if (fechaSource.getMonth() == fechaDate.getMonth() && fechaSource.getDay() == fechaDate.getDay()
                    && fechaSource.getYear() == fechaDate.getYear()) {
                verifica = true;
                break;
            }
        }
        return verifica;
    }

    public void onDateSelect(SelectEvent<LocalDateTime> selectEvent) {
        //Quitar si no funciona
        tiempoModulo = new TiempoModulo();
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

    public void verificaFecha(Date dateHorario) {
        for (Date fecha : horaSource) {
            if (dateHorario.getDate() == fecha.getDate() && dateHorario.getDay() == fecha.getDay()
                    && dateHorario.getMonth() == fecha.getMonth()) {
                horaTarget.add(dateHorario);
                tiempoHorario = new DualListModel<>(horaSource, horaTarget);
                horaSource.remove(fecha);
                break;
            }
        }

    }

    public void onEventSelect(SelectEvent<ScheduleEvent<?>> selectEvent) {
        event = selectEvent.getObject();
        modulo.setDescripcion(null);
        tiempoModulo = new TiempoModulo();
        for (Maestria mHorario : listaHorario) {
            if (Integer.parseInt(event.getGroupId()) == mHorario.getIdMaestria()
                    && Integer.parseInt(event.getDescription()) == mHorario.getIdDocente()
                    && convertToDateViaSqlTimestamp(event.getStartDate()).compareTo(mHorario.getFechaInicio()) == 0) {
                modulo.setIdMateria(mHorario.getIdMaestria());
                modulo.setNombreMateria(mHorario.getNombre());
                modulo.setDescripcion(mHorario.getNombre());
                docente.setNombre_docente(mHorario.getNombreDocente());
                docente.setId_docente(mHorario.getIdDocente());
                tiempoModulo.setDescripcion(mHorario.getDescripcion());
                break;
            }
        }
        tiempoModulo.setFechaAsignacion(convertToDateViaSqlTimestamp(event.getStartDate()));

        if (modulo.getDescripcion() == null) {
            for (Modulo moduloB : listaModulo) {
                if (moduloB.getIdMateria() == modulo.getIdMateria()) {
                    modulo.setNombreMateria(moduloB.getNombreMateria());
                    break;
                }
            }
            for (Docente docenteB : listaDocente) {
                if (docenteB.getId_docente() == docente.getId_docente()) {
                    docente.setNombre_docente(docenteB.getNombre_docente() + " " + docenteB.getApellido_docente());
                    break;
                }
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
                        .title(mHorario.getIdDocente() + " " + mHorario.getNombreDocente() + " "
                                + mHorario.getNombre())
                        .description(String.valueOf(mHorario.getIdDocente()))
                        .groupId(String.valueOf(mHorario.getIdMaestria()))
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

    public List<Date> getListaEntreFechas(Date fechaInicio, Date fechaFin) {
        // Convertimos la fecha a Calendar, mucho más cómodo para realizar
        // operaciones a las fechas
        Calendar c1 = Calendar.getInstance();
        c1.setTime(fechaInicio);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(fechaFin);

        // Lista donde se irán almacenando las fechas
        List<Date> listaFechas = new ArrayList<Date>();

        // Bucle para recorrer el intervalo, en cada paso se le suma un día.
        while (!c1.after(c2)) {
            listaFechas.add(c1.getTime());
            c1.add(Calendar.DAY_OF_MONTH, 1);
        }
        return listaFechas;
    }

    public void onTransfer(TransferEvent event) {
        SimpleDateFormat formato = new SimpleDateFormat("EE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        Date fechaDate = null;
        for (Object item : event.getItems()) {
            String fecha = item.toString();
            System.out.println("");
            try {
                fechaDate = formato.parse(fecha);
                if (!busquedaFechaSource(fechaDate)) {
                    for (Date fechaTarget : horaTarget) {
                        if (fechaTarget.getMonth() == fechaDate.getMonth() && fechaTarget.getDay() == fechaDate.getDay()
                                && fechaTarget.getYear() == fechaDate.getYear()) {
                            horaSource.add(fechaTarget);
                            horaTarget.remove(fechaTarget);
                            break;
                        }
                    }
                }
            } catch (ParseException ex) {
                Logger.getLogger(HorarioMBeans.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean busquedaFechaSource(Date fechaDate) {
        boolean verifica = false;
        for (Date fechaSource : horaSource) {
            if (fechaSource.getMonth() == fechaDate.getMonth() && fechaSource.getDay() == fechaDate.getDay()
                    && fechaSource.getYear() == fechaDate.getYear()) {
                horaTarget.add(fechaSource);
                horaSource.remove(fechaSource);
                verifica = true;
                break;
            }
        }
        return verifica;
    }

    public void actualizaListas() {
        listaMaestria = maestriaDAO.getListaMaestriaPeriodo();
        busquedaMaestriaAux = listaMaestria;
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

    public DualListModel<Date> getTiempoHorario() {
        return tiempoHorario;
    }

    public void setTiempoHorario(DualListModel<Date> tiempoHorario) {
        this.tiempoHorario = tiempoHorario;
    }

    public List<TiempoModulo> getListaVerificacionTiempo() {
        return listaVerificacionTiempo;
    }

    public void setListaVerificacionTiempo(List<TiempoModulo> listaVerificacionTiempo) {
        this.listaVerificacionTiempo = listaVerificacionTiempo;
    }

}
