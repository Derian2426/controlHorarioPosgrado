/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unidadPosgrado.controlador;

import com.unidadPosgrado.dao.EstudianteDAO;
import com.unidadPosgrado.dao.MaestriaDAO;
import com.unidadPosgrado.modelo.Estudiante;
import com.unidadPosgrado.modelo.Inscripcion;
import com.unidadPosgrado.modelo.Maestria;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Alex
 */
public class EstudianteMBeans {

    private Estudiante estudiante;
    EstudianteDAO estudianteDAO;
    private List<Estudiante> listaEstudiante;
    private List<Estudiante> listaEstudianteSeleccionado;
    List<Estudiante> busquedaEstudiante;
    List<Estudiante> busquedaEstudianteAux;
    private Maestria integracionMaestria;
    List<Inscripcion> listaInscripcion;
    private Inscripcion inscripcion;
    private List<Maestria> listaMaestria;
    MaestriaDAO maestriaDAO;
    private Maestria maestriaBusqueda;
    List<Maestria> busquedaMaestria;
    List<Maestria> busquedaMaestriaAux;

    private Estudiante estudianteBusqueda;

    public EstudianteMBeans() {
        estudiante = new Estudiante();
        estudianteDAO = new EstudianteDAO();
        listaEstudiante = new ArrayList<>();
        estudianteBusqueda = new Estudiante();
        listaEstudianteSeleccionado = new ArrayList<>();
        busquedaEstudiante = new ArrayList<>();
        busquedaEstudianteAux = new ArrayList<>();
        integracionMaestria = new Maestria();
        listaInscripcion = new ArrayList<>();
        inscripcion = new Inscripcion();
        inscripcion.setFecha_inscripcion(new Date());
        maestriaDAO = new MaestriaDAO();
        maestriaBusqueda = new Maestria();
        busquedaMaestria = new ArrayList<>();
        busquedaMaestriaAux = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        listaEstudiante = estudianteDAO.getListaEstudiante();
        busquedaEstudianteAux = listaEstudiante;
        listaMaestria = maestriaDAO.getListaMaestriaPeriodo();
        busquedaMaestriaAux = listaMaestria;
    }

    public void registrarEstudiante() {
        try {
            if ("".equals(estudiante.getNombre_estudiante().trim())) {
                showWarn("Debe ingresar un nombre.");
            } else if ("".equals(estudiante.getApellido_estudiante().trim())) {
                showWarn("Debe ingresar un apellido.");
            } else if ("".equals(estudiante.getTelefono_estudiante().trim())) {
                showWarn("Debe ingresar un teléfono.");
            } else if ("".equals(estudiante.getCedula_estudiante().trim())) {
                showWarn("Debe ingresar una cédula.");
            } else if ("".equals(estudiante.getSexo().trim())) {
                showWarn("Debe ingresar su género.");
            } else if ("".equals(estudiante.getCorreo_estudiante().trim())) {
                showWarn("Debe ingresar un correo.");
            } else {
                int resultadoRegistro = estudianteDAO.registrarEstudiante(estudiante);
                if (resultadoRegistro > 0) {
                    showInfo(estudiante.getNombre_estudiante().trim().replace(".", ".") + " Registrado exitoso.");
                    listaEstudiante = estudianteDAO.getListaEstudiante();
                    busquedaEstudianteAux = listaEstudiante;
                    PrimeFaces.current().executeScript("PF('dlgEstudiante').hide()");
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
            if ("".equals(event.getObject().getNombre_estudiante().trim())) {
                showWarn("No se puede modificar el registro porque el campo esta vacio.");
            } else if ("".equals(event.getObject().getApellido_estudiante().trim())) {
                showWarn("No se puede modificar el registro porque el campo esta vacio.");
            } else if ("".equals(event.getObject().getCedula_estudiante().trim())) {
                showWarn("No se puede modificar el registro porque el campo esta vacio.");
            } else if ("".equals(event.getObject().getTelefono_estudiante().trim())) {
                showWarn("No se puede modificar el registro porque el campo esta vacio.");
            } else if ("".equals(event.getObject().getSexo().trim())) {
                showWarn("No se puede modificar el registro porque el campo esta vacio.");
            } else if ("".equals(event.getObject().getCorreo_estudiante().trim())) {
                showWarn("No se puede modificar el registro porque el campo esta vacio.");
            } else {
                Estudiante editEstudiante = new Estudiante(event.getObject().getId_estudiante(),
                        event.getObject().getNombre_estudiante(), event.getObject().getApellido_estudiante(),
                        event.getObject().getTelefono_estudiante(), event.getObject().getCedula_estudiante(),
                        event.getObject().getSexo(), event.getObject().getCorreo_estudiante());
                int resultadoRegistro = estudianteDAO.editarEstudiante(editEstudiante);
                if (resultadoRegistro > 0) {
                    showInfo("Se actualizo con éxito, " + editEstudiante.getNombre_estudiante().trim());
                } else {
                    showInfo("Se actualizo el registro con exito.");
                }
            }
            listaEstudiante = new ArrayList<>();
            listaEstudiante = estudianteDAO.getListaEstudiante();
            busquedaEstudianteAux = listaEstudiante;
        } catch (Exception e) {
            showWarn(e.getMessage());
        }
    }

    public void registrarEstudianteMaestria() {
        try {
            if (listaEstudianteSeleccionado.size() < 1) {
                showWarn("Seleccione al menos a un estudiante.");
            } else if (integracionMaestria.getIdMaestria() < 1 || "".equals(integracionMaestria.getNombre())) {
                showWarn("Seleccione una Maestria.");
            } else if (inscripcion.getValor() < 1) {
                showWarn("Ingrese un valor o costo de la maestría.");
            } else {
                for (Estudiante student : listaEstudianteSeleccionado) {
                    listaInscripcion.add(new Inscripcion(integracionMaestria.getIdMaestria(),
                            student.getId_estudiante(), inscripcion.getFecha_inscripcion(), integracionMaestria.getDescripcion(), inscripcion.getValor()));
                }
                if (estudianteDAO.registraEstudiante(listaInscripcion) > 0) {
                    showInfo("Registro exitoso");
                    estudianteBusqueda = new Estudiante();
                    listaInscripcion = new ArrayList<>();
                    listaEstudianteSeleccionado = new ArrayList<>();
                    integracionMaestria = new Maestria();
                    inscripcion = new Inscripcion();
                    listaEstudiante = new ArrayList<>();
                    listaEstudiante = estudianteDAO.getListaEstudiante();
                    busquedaEstudianteAux = listaEstudiante;
                    inscripcion.setFecha_inscripcion(new Date());
                    maestriaBusqueda = new Maestria();
                    PrimeFaces.current().executeScript("PF('dlgInscripciones').hide()");
                } else {
                    showWarn("No se pudo generar el registro");
                }
            }
        } catch (Exception e) {
            showWarn(e.getMessage());
        }
    }

    public void cancelarRegistro() {
        listaInscripcion = new ArrayList<>();
        listaEstudianteSeleccionado = new ArrayList<>();
        integracionMaestria = new Maestria();
        inscripcion = new Inscripcion();
        listaEstudiante = new ArrayList<>();
        listaEstudiante = estudianteDAO.getListaEstudiante();
        busquedaEstudianteAux = listaEstudiante;
        estudianteBusqueda = new Estudiante();
        inscripcion.setFecha_inscripcion(new Date());
        maestriaBusqueda = new Maestria();
        showWarn("Registro cancelado.");
    }

    public void llenaMaestria(Maestria maestria) {
        integracionMaestria.setIdMaestria(maestria.getIdMaestria());
        integracionMaestria.setNombre(maestria.getNombre());
        integracionMaestria.setDescripcion(maestria.getDescripcion());
    }

    public void onRowCancel(RowEditEvent<Estudiante> event) {
        try {
            showWarn("Editar el nombre " + event.getObject().getNombre_estudiante() + " fue cancelado.");
        } catch (Exception e) {
            showWarn(e.getMessage());
        }
    }

    public void buscarEstudiante() {
        if (estudianteBusqueda.getNombre_estudiante() == null || "".equals(estudianteBusqueda.getNombre_estudiante())) {
            listaEstudiante = busquedaEstudianteAux;
        } else {
            listaEstudiante = busquedaEstudianteAux;
            for (Estudiante busqueda : listaEstudiante) {
                if (busqueda.getNombre_estudiante().toUpperCase().contains(estudianteBusqueda.getNombre_estudiante().toUpperCase())
                        || busqueda.getApellido_estudiante().toUpperCase().contains(estudianteBusqueda.getNombre_estudiante().toUpperCase())) {
                    busquedaEstudiante.add(busqueda);
                }
            }
            listaEstudiante = busquedaEstudiante;
            busquedaEstudiante = new ArrayList<>();
//            estudianteBusqueda = new Estudiante();
        }
    }

    public void addEstudiantes(Estudiante objestudiante) {
        try {
            if (objestudiante.isVerifica()) {
                listaEstudianteSeleccionado.add(objestudiante);
            } else {
                listaEstudianteSeleccionado.remove(objestudiante);
            }

        } catch (Exception e) {
            showWarn("Error" + e.getMessage());
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

    public Estudiante getEstudianteBusqueda() {
        return estudianteBusqueda;
    }

    public void setEstudianteBusqueda(Estudiante estudianteBusqueda) {
        this.estudianteBusqueda = estudianteBusqueda;
    }

    public void setListaEstudiante(List<Estudiante> listaEstudiante) {
        this.listaEstudiante = listaEstudiante;
    }

    public List<Estudiante> getListaEstudianteSeleccionado() {
        return listaEstudianteSeleccionado;
    }

    public void setListaEstudianteSeleccionado(List<Estudiante> listaEstudianteSeleccionado) {
        this.listaEstudianteSeleccionado = listaEstudianteSeleccionado;
    }

    public Maestria getIntegracionMaestria() {
        return integracionMaestria;
    }

    public void setIntegracionMaestria(Maestria integracionMaestria) {
        this.integracionMaestria = integracionMaestria;
    }

    public Inscripcion getInscripcion() {
        return inscripcion;
    }

    public void setInscripcion(Inscripcion inscripcion) {
        this.inscripcion = inscripcion;
    }

    public List<Maestria> getListaMaestria() {
        return listaMaestria;
    }

    public void setListaMaestria(List<Maestria> listaMaestria) {
        this.listaMaestria = listaMaestria;
    }

    public Maestria getMaestriaBusqueda() {
        return maestriaBusqueda;
    }

    public void setMaestriaBusqueda(Maestria maestriaBusqueda) {
        this.maestriaBusqueda = maestriaBusqueda;
    }

    public void saludo() {
        System.out.println("Holas");
    }
//    public List<String> completeText(String query) {
//        String queryLowerCase = query.toLowerCase();
//        List<String> estudianteList = new ArrayList<>();
//        for (Estudiante student : listaEstudiante) {
//            estudianteList.add(student.getNombre_estudiante());
//        }
//        
//        return estudianteList.stream().filter(t -> t.toLowerCase().startsWith(queryLowerCase)).collect(Collectors.toList());
//    }

}
