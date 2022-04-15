/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unidadPosgrado.controlador;

import com.unidadPosgrado.dao.MaestriaDAO;
import com.unidadPosgrado.modelo.Maestria;
import com.unidadPosgrado.modelo.Modulo;
import com.unidadPosgrado.modelo.Periodo;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author HP
 */
public class MaestriaMBeans {

    private Maestria maestria;
    private Maestria maestriaBusqueda;
    private Maestria integracionMaestria;
    MaestriaDAO maestriaDAO;
    private List<Maestria> listaMaestria;
    List<Maestria> busquedaMaestria;
    List<Maestria> listaMaestriaxModulo;
    private List<Modulo> listaModulos;
    private TreeNode rootIntegracion;
    TreeNode maestriaTree;
    TreeNode moduloNode;
    List<Modulo> listaModulosNode;
    List<Modulo> listaModulosVerificacion;
    private Periodo periodo;
    List<Maestria> busquedaMaestriaAux;

    public MaestriaMBeans() {
        maestria = new Maestria();
        maestriaBusqueda = new Maestria();
        maestriaDAO = new MaestriaDAO();
        integracionMaestria = new Maestria();
        busquedaMaestria = new ArrayList<>();
        listaModulos = new ArrayList<>();
        listaMaestriaxModulo = new ArrayList<>();
        listaModulosVerificacion = new ArrayList<>();
        rootIntegracion = new DefaultTreeNode("Root Node", null);
        periodo = new Periodo();
        busquedaMaestriaAux = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        listaMaestria = maestriaDAO.getListaMaestria();
        listaMaestriaxModulo = maestriaDAO.getListaMaestriaxModulo();
        busquedaMaestriaAux = listaMaestria;
        llenarLista();
    }

    public Maestria getMaestria() {
        return maestria;
    }

    public void setMaestria(Maestria maestria) {
        this.maestria = maestria;
    }

    public List<Maestria> getListaMaestria() {
        return listaMaestria;
    }

    public void setListaMaestria(List<Maestria> listaMaestria) {
        this.listaMaestria = listaMaestria;
    }

    public Maestria getIntegracionMaestria() {
        return integracionMaestria;
    }

    public void setIntegracionMaestria(Maestria integracionMaestria) {
        this.integracionMaestria = integracionMaestria;
    }

    public List<Modulo> getListaModulos() {
        return listaModulos;
    }

    public void setListaModulos(List<Modulo> listaModulos) {
        this.listaModulos = listaModulos;
    }

    public TreeNode getRootIntegracion() {
        return rootIntegracion;
    }

    public void setRootIntegracion(TreeNode rootIntegracion) {
        this.rootIntegracion = rootIntegracion;
    }

    public Maestria getMaestriaBusqueda() {
        return maestriaBusqueda;
    }

    public void setMaestriaBusqueda(Maestria maestriaBusqueda) {
        this.maestriaBusqueda = maestriaBusqueda;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public void registrarMaestria() {
        try {
            if ("".equals(maestria.getNombre().trim())) {
                showWarn("Debe ingresar un nombre a la maestría.");
            } else if ("".equals(maestria.getDescripcion().trim())) {
                showWarn("Debe ingresar una descripción a la maestría.");
            } else {
                int resultadoRegistro = maestriaDAO.registrarMaestria(maestria);
                if (resultadoRegistro > 0) {
                    showInfo(maestria.getNombre().trim().replace(".", ",") + " registrada con éxito.");
                    maestria = new Maestria();
                    PrimeFaces.current().executeScript("PF('dlgMaestria').hide()");
                    listaMaestria = maestriaDAO.getListaMaestria();
                    busquedaMaestriaAux = listaMaestria;
                } else {
                    showWarn(maestria.getNombre().trim().replace(".", ",") + " ya se encuentra registrada.");
                }
                maestria = new Maestria();
            }
        } catch (Exception e) {
            showWarn(e.getMessage());
        }
    }

    public void onRowEdit(RowEditEvent<Maestria> event) {
        try {
            if ("".equals(event.getObject().getNombre().trim())) {
                showWarn("No se puede modificar el registro porque el campo esta vacio.");
            } else if ("".equals(event.getObject().getDescripcion().trim())) {
                showWarn("No se puede modificar el registro porque el campo esta vacio.");
            } else {
                Maestria editMaestria = new Maestria(event.getObject().getIdMaestria(),
                        event.getObject().getNombre(), event.getObject().getDescripcion());
                int resultadoRegistro = maestriaDAO.editarMaestria(editMaestria);
                if (resultadoRegistro > 0) {
                    showInfo("Se actualizo con éxito, " + editMaestria.getNombre().trim());
                } else {
                    showWarn(editMaestria.getNombre().trim().replace(".", ",") + " no pudo ser actualizada.");
                }
            }
            listaMaestria = new ArrayList<>();
            maestria = new Maestria();
            listaMaestria = maestriaDAO.getListaMaestria();
            busquedaMaestriaAux = listaMaestria;
        } catch (Exception e) {
            showWarn(e.getMessage());
        }
    }

    public void onRowCancel(RowEditEvent<Maestria> event) {
        try {
            showWarn("Editar la " + event.getObject().getNombre() + " fue cancelada.");
        } catch (Exception e) {
            showWarn(e.getMessage());
        }
    }

    public void llenaMaestria(Maestria maestria) {
        integracionMaestria.setIdMaestria(maestria.getIdMaestria());
        integracionMaestria.setNombre(maestria.getNombre());
        integracionMaestria.setDescripcion(maestria.getDescripcion());
        listaModulosVerificacion = maestriaDAO.getListaModulo(maestria.getIdMaestria());
        if (listaModulosVerificacion.size() > 0) {
            showWarn("La maestria Seleccionada ya se encuetra con módulos Integrados, se agregaran solo "
                    + "los módulos que no se encuentren en el registro.");
        }
    }

    public void llenaMaestriaPeriodo(Maestria maestria) {
        integracionMaestria.setIdMaestria(maestria.getIdMaestria());
        integracionMaestria.setNombre(maestria.getNombre());
        integracionMaestria.setDescripcion(maestria.getDescripcion());
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

    public void deleteFila(Modulo modulo) {
        listaModulos.remove(modulo);
    }

    public void addModulos(Modulo modulo) {
        try {
            if (!verfifiaIntegracio(modulo.getIdMateria())) {
                if (modulo.isVerifica() && verificaModulo(modulo.getIdMateria())) {
                    listaModulos.add(modulo);
                    integracionMaestria.setTiempoMaestria(integracionMaestria.getTiempoMaestria() + modulo.getHora_materia());
                } else {
                    listaModulos.remove(modulo);
                    integracionMaestria.setTiempoMaestria(integracionMaestria.getTiempoMaestria() - modulo.getHora_materia());
                }
            } else {
                showWarn(modulo.getNombreMateria().replace(".", ",") + " ya se encuentra registrada, seleccione otro módulo.");
                modulo.setVerifica(false);
            }

        } catch (Exception e) {
            showWarn("Error" + e.getMessage());
        }

    }

    public boolean verfifiaIntegracio(int idModulo) {
        boolean verifica = false;
        for (Modulo modulo : listaModulosVerificacion) {
            if (modulo.getIdMateria() == idModulo) {
                verifica = true;
                break;
            }
        }
        return verifica;
    }

    public boolean verificaModulo(int idModulo) {
        boolean verifica = true;
        for (Modulo modulo : listaModulos) {
            if (modulo.getIdMateria() == idModulo) {
                verifica = false;
                break;
            }
        }
        return verifica;
    }

    public void vaciarCamposIntegracionModulo() {
        integracionMaestria = new Maestria();
        maestria = new Maestria();
        listaModulos = new ArrayList<>();
        showWarn("El registro se Cancelo.");
    }

    public void registarIntegracionModulo() {
        try {
            if (integracionMaestria.getNombre() == null && integracionMaestria.getIdMaestria() == 0) {
                showWarn("Seleccione una Maestría.");
            } else if (listaModulos.size() < 1) {
                showWarn("Seleccione al menos un módulo.");
            }
            if (maestriaDAO.registrarIntegracionModulo(integracionMaestria, listaModulos) > 0) {
                showInfo("Integración de Módulos registrado con éxito.");
                integracionMaestria = new Maestria();
                listaModulos = new ArrayList<>();
                listaMaestria = new ArrayList<>();
                rootIntegracion = new DefaultTreeNode("Root Node", null);
                listaMaestriaxModulo = new ArrayList<>();
                maestriaBusqueda = new Maestria();
                listaMaestria = busquedaMaestriaAux;
                listaMaestriaxModulo = maestriaDAO.getListaMaestriaxModulo();
                PrimeFaces.current().executeScript("PF('listadoModuloMaestria').hide()");
                llenarLista();
            } else {
                showWarn("Transacción fallida.");
            }
        } catch (Exception e) {
            showWarn("Error" + e.getMessage());
        }
    }

    public void llenarLista() {
        for (Maestria maestriaT : listaMaestriaxModulo) {
            maestriaTree = new DefaultTreeNode(new Maestria(maestriaT.getIdMaestria(),
                    maestriaT.getNombre(), maestriaT.getDescripcion(), maestriaT.getTiempoMaestria()), this.rootIntegracion);
            listaModulosNode = maestriaDAO.getListaModulo(maestriaT.getIdMaestria());
            for (Modulo moduloN : listaModulosNode) {
                moduloNode = new DefaultTreeNode(new Maestria(moduloN.getIdMateria(), moduloN.getNombreMateria(), moduloN.getDescripcion(), moduloN.getHora_materia()), maestriaTree);
            }
        }
    }

    public void verificaCampos() {
        if (integracionMaestria.getNombre() == null && integracionMaestria.getIdMaestria() == 0) {
            showWarn("Seleccione una Maestría.");
        } else {
            PrimeFaces.current().executeScript("PF('listModulosSeleccion').show()");
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

}
