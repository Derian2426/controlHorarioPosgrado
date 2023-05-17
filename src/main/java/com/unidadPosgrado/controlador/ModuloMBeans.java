/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unidadPosgrado.controlador;

import com.unidadPosgrado.dao.ModuloDAO;
import com.unidadPosgrado.modelo.Modulo;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author HP
 */
public class ModuloMBeans {

    private Modulo modulo;
    private Modulo moduloBusqueda;
    ModuloDAO moduloDAO;
    private List<Modulo> listaModulo;
    List<Modulo> busquedaModulo;
    List<Modulo> busquedaModuloAux;

    public ModuloMBeans() {
        modulo = new Modulo();
        moduloBusqueda = new Modulo();
        moduloDAO = new ModuloDAO();
        busquedaModulo = new ArrayList<>();
        busquedaModuloAux = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        listaModulo = moduloDAO.getListaModulo();
        busquedaModuloAux = listaModulo;
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

    public Modulo getModuloBusqueda() {
        return moduloBusqueda;
    }

    public void setModuloBusqueda(Modulo moduloBusqueda) {
        this.moduloBusqueda = moduloBusqueda;
    }

    public String puntoFinal(String texto) {
        if (texto.isEmpty() || texto.charAt(texto.length() - 1) != '.') {
            return texto + '.';
        }
        return texto;
    }

    public void registrarModulo() {
        try {
            if ("".equals(modulo.getNombreMateria())) {
                showWarn("Ingrese un nombre al módulo");
            } else if ("".equals(modulo.getDescripcion())) {
                showWarn("Ingrese una descripción al módulo");
            } else {
                modulo.setNombreMateria(puntoFinal(modulo.getNombreMateria()));
                modulo.setDescripcion(puntoFinal(modulo.getDescripcion()));
                int resultadoRegistro = moduloDAO.registrarModulo(modulo);
                if (resultadoRegistro > 0) {
                    showInfo(modulo.getNombreMateria().trim().replace(".", ",") + " registrado con éxito.");
                    PrimeFaces.current().executeScript("PF('dlgModulo').hide()");
                    listaModulo = moduloDAO.getListaModulo();
                    busquedaModuloAux = listaModulo;
                } else {
                    showWarn(modulo.getNombreMateria().trim().replace(".", ",") + " ya se encuentra registrado.");
                }
                modulo = new Modulo();
            }
        } catch (Exception e) {
            showWarn(e.getMessage());
        }
    }

    public void onRowEdit(RowEditEvent<Modulo> event) {
        try {

            if ("".equals(event.getObject().getNombreMateria())) {
                showWarn("No se puede modificar el registro porque el campo esta vacio.");
            } else if ("".equals(event.getObject().getDescripcion())) {
                showWarn("No se puede modificar el registro porque el campo esta vacio.");
            } else {
                Modulo editModulo = new Modulo(event.getObject().getIdMateria(),
                        event.getObject().getNombreMateria(), event.getObject().getDescripcion());
                int resultadoRegistro = moduloDAO.editarModulo(editModulo);
                if (resultadoRegistro > 0) {
                    showInfo("Se actualizo con éxito, " + editModulo.getNombreMateria().trim());
                } else {
                    showWarn(editModulo.getNombreMateria().trim().replace(".", ",") + " no se pudo actualizar por que el registro ya se encuentra registrado."
                            + " Solo se actualiza la descripción si el registro a editar es el mismo.");
                }
            }
            listaModulo = new ArrayList<>();
            modulo = new Modulo();
            listaModulo = moduloDAO.getListaModulo();
            busquedaModuloAux = listaModulo;
        } catch (Exception e) {
            showWarn(e.getMessage());
        }
    }

    public void onRowCancel(RowEditEvent<Modulo> event) {
        try {
            showWarn("La edición del módulo de " + event.getObject().getNombreMateria() + " fue cancelada.");
        } catch (Exception e) {
            showWarn(e.getMessage());
        }
    }

    public void buscarModulo() {
        if (moduloBusqueda.getNombreMateria() == null || "".equals(moduloBusqueda.getNombreMateria())) {
            listaModulo = busquedaModuloAux;
        } else {
            listaModulo = busquedaModuloAux;
            for (Modulo busqueda : listaModulo) {
                if (busqueda.getNombreMateria().toUpperCase().contains(moduloBusqueda.getNombreMateria().toUpperCase())) {
                    busquedaModulo.add(busqueda);
                }
            }
            listaModulo = busquedaModulo;
            busquedaModulo = new ArrayList<>();
//            moduloBusqueda = new Modulo();

        }
    }

    public void actualizaModulo() {
        moduloBusqueda = new Modulo();
        listaModulo = moduloDAO.getListaModulo();
        busquedaModuloAux = listaModulo;
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
