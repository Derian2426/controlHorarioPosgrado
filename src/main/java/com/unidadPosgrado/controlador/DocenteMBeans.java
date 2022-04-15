/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unidadPosgrado.controlador;

import com.unidadPosgrado.dao.DocenteDAO;
import com.unidadPosgrado.dao.MaestriaDAO;
import com.unidadPosgrado.modelo.Docente;
import com.unidadPosgrado.modelo.Maestria;
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
    private Docente docenteBusqueda;
    DocenteDAO docenteDAO;
    private List<Docente> listaDocente;
    private List<Maestria> listaMaestria;
    MaestriaDAO maestriaDAO;
    private Maestria maestriaBusqueda;
    List<Maestria> busquedaMaestria;
    List<Maestria> busquedaMaestriaAux;
    private List<Maestria> seleccionMaestria;
    List<Docente> listaDocenteBusqueda;
    List<Docente> listaDocenteBusquedaAux;

    public DocenteMBeans() {
        docente = new Docente();
        docenteDAO = new DocenteDAO();
        listaDocente = new ArrayList<>();
        listaMaestria = new ArrayList<>();
        maestriaDAO = new MaestriaDAO();
        maestriaBusqueda = new Maestria();
        busquedaMaestria = new ArrayList<>();
        busquedaMaestriaAux = new ArrayList<>();
        seleccionMaestria = new ArrayList<>();
        docenteBusqueda = new Docente();
        listaDocenteBusqueda = new ArrayList<>();
        listaDocenteBusquedaAux = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        listaMaestria = maestriaDAO.getListaMaestria();
        listaDocente = docenteDAO.getListaDocente();
        busquedaMaestriaAux = listaMaestria;
        listaDocenteBusqueda = listaDocente;
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
            } else if (seleccionMaestria.size() < 1) {
                showWarn("Debe asignar al menos una maestría al docente.");
            } else {
                int resultadoRegistro = docenteDAO.registrarDocente(docente, seleccionMaestria);
                if (resultadoRegistro > 0) {
                    showInfo(docente.getNombre_docente().trim().replace(".", ",") + " Guardado con éxito.");
                    docente = new Docente();
                    seleccionMaestria = new ArrayList<>();
                    PrimeFaces.current().executeScript("PF('dlgDocente').hide()");
                    listaDocente = docenteDAO.getListaDocente();
                    listaDocenteBusqueda = listaDocente;
                    listaMaestria = maestriaDAO.getListaMaestria();
                    maestriaBusqueda = new Maestria();
                } else {
                    showWarn("La cédula " + docente.getCedula_docente().trim().replace(".", ",") + " ya se encuentra registrada.");
                    docente = new Docente();
                    seleccionMaestria = new ArrayList<>();
                    listaDocente = docenteDAO.getListaDocente();
                    listaDocenteBusqueda = listaDocente;
                    listaMaestria = maestriaDAO.getListaMaestria();
                    maestriaBusqueda = new Maestria();
                }
            }
        } catch (Exception e) {
        }
    }

    public void cancelarRegistro() {
        docente = new Docente();
        seleccionMaestria = new ArrayList<>();
        listaMaestria = maestriaDAO.getListaMaestria();
        maestriaBusqueda = new Maestria();
        showWarn("Registro cancelado.");
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
            listaDocenteBusqueda = listaDocente;
        } catch (Exception e) {
            showWarn(e.getMessage());
        }
    }

    public void onRowCancel(RowEditEvent<Docente> event) {
        try {
            showWarn("Edición del nombre " + event.getObject().getNombre_docente() + " fue cancelado.");
        } catch (Exception e) {
            showWarn(e.getMessage());
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

    public void addMaestria(Maestria maestria) {
        try {
            if (maestria.isVerifica() && verificaModulo(maestria.getIdMaestria())) {
                seleccionMaestria.add(maestria);
            } else {
                seleccionMaestria.remove(maestria);
            }
        } catch (Exception e) {
            showWarn("Error" + e.getMessage());
        }

    }

    public boolean verificaModulo(int idMaestria) {
        boolean verifica = true;
        for (Maestria maestria : seleccionMaestria) {
            if (maestria.getIdMaestria() == idMaestria) {
                verifica = false;
                break;
            }
        }
        return verifica;
    }

    public void buscarDocente() {
        if (docenteBusqueda.getNombre_docente() == null || "".equals(docenteBusqueda.getNombre_docente())) {
            listaDocente = listaDocenteBusqueda;
        } else {
            listaDocente = listaDocenteBusqueda;
            for (Docente busqueda : listaDocente) {
                if (busqueda.getNombre_docente().toUpperCase().contains(docenteBusqueda.getNombre_docente().toUpperCase())
                        || busqueda.getApellido_docente().toUpperCase().contains(docenteBusqueda.getNombre_docente().toUpperCase())) {
                    listaDocenteBusquedaAux.add(busqueda);
                }
            }
            listaDocente = listaDocenteBusquedaAux;
            listaDocenteBusquedaAux = new ArrayList<>();
//            estudianteBusqueda = new Estudiante();
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

    public List<Maestria> getSeleccionMaestria() {
        return seleccionMaestria;
    }

    public void setSeleccionMaestria(List<Maestria> seleccionMaestria) {
        this.seleccionMaestria = seleccionMaestria;
    }

    public Docente getDocenteBusqueda() {
        return docenteBusqueda;
    }

    public void setDocenteBusqueda(Docente docenteBusqueda) {
        this.docenteBusqueda = docenteBusqueda;
    }

}