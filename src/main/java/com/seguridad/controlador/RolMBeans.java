/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.controlador;

import com.seguridad.dao.RolDAO;
import com.seguridad.modelo.Rol;
import com.unidadPosgrado.dao.MaestriaDAO;
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
public class RolMBeans {

    RolDAO rolDAO;
    private Rol rol;
    private List<Rol> listaRol;
    private List<Maestria> listaMaestria;
    private List<Maestria> listaMaestriaSeleccion;
    MaestriaDAO maestriaDAO;

    public RolMBeans() {
        rolDAO = new RolDAO();
        rol = new Rol();
        listaRol = new ArrayList<>();
        listaMaestria = new ArrayList<>();
        listaMaestriaSeleccion = new ArrayList<>();
        maestriaDAO = new MaestriaDAO();
    }

    @PostConstruct
    public void init() {
        listaRol = rolDAO.getListaRol();
        listaMaestria = maestriaDAO.getListaMaestria_Periodo();
    }

    public void registrarRol() {
        try {
            if ("".equals(rol.getNombre().trim())) {
                showWarn("Debe ingresar un nombre.");
            } else if ("".equals(rol.getDetalle().trim())) {
                showWarn("Debe ingresar una descripción.");
            } else if ("".equals(rol.isEstado())) {
                showWarn("Campo vacio.");
            } else if (listaMaestriaSeleccion.size() == 0) {
                showWarn("Seleccione al menos una de las maestrias.");
            } else {
                int resultadoRegistro = rolDAO.registrarRol(rol, listaMaestriaSeleccion);
                if (resultadoRegistro > 0) {
                    showInfo(rol.getNombre().trim().replace(".", ".") + " Registrado exitoso.");
                    listaRol = rolDAO.getListaRol();
                    listaMaestria = maestriaDAO.getListaMaestria_Periodo();
                    listaMaestriaSeleccion = new ArrayList<>();
                    PrimeFaces.current().executeScript("PF('dlgRol').hide()");
                    rol = new Rol();
                } else {
                    showWarn(rol.getNombre().trim().replace(".", ".") + " ya se encuentra en el sistema.");
                }
            }
        } catch (Exception e) {
            showWarn(e.getMessage());
        }
    }

    public void onRowEdit(RowEditEvent<Rol> event) {
        try {
            if ("".equals(event.getObject().getNombre().trim())) {
                showWarn("No se puede modificar el registro porque el campo esta vacio.");
            } else if ("".equals(event.getObject().getDetalle().trim())) {
                showWarn("No se puede modificar el registro porque el campo esta vacio.");
            } else if ("".equals(event.getObject().isEstado())) {
                showWarn("No se puede modificar el registro porque el campo esta vacio.");
            } else {
                Rol editRol = new Rol(event.getObject().getIdRol(),
                        event.getObject().getNombre(), event.getObject().getDetalle(),
                        event.getObject().isEstado());
                int resultadoRegistro = rolDAO.editarRol(editRol);
                if (resultadoRegistro > 0) {
                    showInfo("Se actualizo con éxito, " + editRol.getNombre().trim());
                } else {
                    showWarn(editRol.getNombre().trim().replace(".", ",") + " se actualizaron los otros campos.");
                }
            }
            listaRol = new ArrayList<>();
            listaRol = rolDAO.getListaRol();
        } catch (Exception e) {
            showWarn(e.getMessage());
        }
    }

    public void actualizaRol() {
        listaRol = rolDAO.getListaRol();
    }

    public void addMaestria(Maestria maestria) {
        try {
            if (maestria.isVerifica() && verificaModulo(maestria.getIdMaestria())) {
                listaMaestriaSeleccion.add(maestria);
            } else {
                listaMaestriaSeleccion.remove(maestria);
            }
        } catch (Exception e) {
            showWarn("Error" + e.getMessage());
        }

    }

    public boolean verificaModulo(int idMaestria) {
        boolean verifica = true;
        for (Maestria maestria : listaMaestriaSeleccion) {
            if (maestria.getIdMaestria() == idMaestria) {
                verifica = false;
                break;
            }
        }
        return verifica;
    }

    public void onRowCancel(RowEditEvent<Rol> event) {
        try {
            showWarn("Edición del rol: " + event.getObject().getNombre() + ", fue cancelado.");
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

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public List<Rol> getListaRol() {
        return listaRol;
    }

    public void setListaRol(List<Rol> listaRol) {
        this.listaRol = listaRol;
    }

    public List<Maestria> getListaMaestria() {
        return listaMaestria;
    }

    public void setListaMaestria(List<Maestria> listaMaestria) {
        this.listaMaestria = listaMaestria;
    }

    public List<Maestria> getListaMaestriaSeleccion() {
        return listaMaestriaSeleccion;
    }

    public void setListaMaestriaSeleccion(List<Maestria> listaMaestriaSeleccion) {
        this.listaMaestriaSeleccion = listaMaestriaSeleccion;
    }

}
