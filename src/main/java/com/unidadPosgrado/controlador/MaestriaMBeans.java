/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unidadPosgrado.controlador;

import com.seguridad.modelo.Usuario;
import com.unidadPosgrado.dao.MaestriaDAO;
import com.unidadPosgrado.modelo.Maestria;
import com.unidadPosgrado.modelo.Modulo;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

/**
 *
 * @author HP
 */
public class MaestriaMBeans implements Serializable{

    private Maestria maestria;
    private Modulo modulo;
    private Maestria maestriaBusqueda;
    private Maestria integracionMaestria;
    MaestriaDAO maestriaDAO;
    private List<Maestria> listaMaestria;
    List<Maestria> busquedaMaestria;
    List<Maestria> listaMaestriaxModulo;
    private List<Modulo> listaModulos;
    private List<Modulo> listaModulosEdit;
    private TreeNode rootIntegracion;
    TreeNode maestriaTree;
    TreeNode moduloNode;
    List<Modulo> listaModulosNode;
    List<Modulo> listaTextModulo;
    List<Modulo> listaModulosVerificacion;
    List<Maestria> busquedaMaestriaAux;
    Usuario user;
    FacesContext contexto;

    public MaestriaMBeans() {
        maestria = new Maestria();
        modulo = new Modulo();
        maestriaBusqueda = new Maestria();
        maestriaDAO = new MaestriaDAO();
        integracionMaestria = new Maestria();
        busquedaMaestria = new ArrayList<>();
        listaModulos = new ArrayList<>();
        listaMaestriaxModulo = new ArrayList<>();
        listaModulosVerificacion = new ArrayList<>();
        listaTextModulo = new ArrayList<>();
        listaModulosEdit = new ArrayList<>();
        rootIntegracion = new DefaultTreeNode("Root Node", null);
        busquedaMaestriaAux = new ArrayList<>();
        user = new Usuario();
    }

    @PostConstruct
    public void init() {
        contexto = FacesContext.getCurrentInstance();
        user = (Usuario) contexto.getExternalContext().getSessionMap().get("usuario");
        listaMaestria = maestriaDAO.getListaMaestria(user.getIdUsuarioSesion());
        listaMaestriaxModulo = maestriaDAO.getListaMaestriaxModulo(user.getIdUsuarioSesion());
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

    public Modulo getModulo() {
        return modulo;
    }

    public void setModulo(Modulo modulo) {
        this.modulo = modulo;
    }

    public List<Modulo> getListaModulosEdit() {
        return listaModulosEdit;
    }

    public void setListaModulosEdit(List<Modulo> listaModulosEdit) {
        this.listaModulosEdit = listaModulosEdit;
    }

    public void registrarMaestria() {
        try {
            if ("".equals(maestria.getNombre().trim())) {
                showWarn("Debe ingresar un nombre a la maestría.");
            } else if ("".equals(maestria.getDescripcion().trim())) {
                showWarn("Debe ingresar una descripción a la maestría.");
            } else {
                maestria.setNombre(puntoFinal(maestria.getNombre()));
                maestria.setDescripcion(puntoFinal(maestria.getDescripcion()));
                int resultadoRegistro = maestriaDAO.registrarMaestria(maestria);
                if (resultadoRegistro > 0) {
                    showInfo(maestria.getNombre().trim().replace(".", ",") + " registrada con éxito.");
                    maestria = new Maestria();
                    PrimeFaces.current().executeScript("PF('dlgMaestria').hide()");
                    listaMaestria = maestriaDAO.getListaMaestria(user.getIdUsuarioSesion());
                    busquedaMaestriaAux = listaMaestria;
                } else {
                    showWarn(maestria.getNombre().trim().replace(".", ",") + " ya se encuentra registrada.");
                }
                maestria = new Maestria();
            }
        } catch (Exception e) {
            showWarn(e.getMessage());
        }
        PrimeFaces.current().executeScript("PF('dlg_loader').hide()");
    }

    public void registroIntegracionOpen() {
        integracionMaestria = new Maestria();
        integracionMaestria.setVerifica(true);
        listaModulos = new ArrayList<>();
        PrimeFaces.current().executeScript("PF('listadoModuloMaestria').show()");
    }

    public void editarIntegracion(Maestria maestria) {
        integracionMaestria = new Maestria();
        integracionMaestria = maestria;
        integracionMaestria.setVerifica(false);
        listaModulosEdit = new ArrayList<>();
        listaModulosEdit = maestriaDAO.getListaModulo(integracionMaestria.getIdMaestria());
        int contador = 0;
        for (Modulo mod : listaModulosEdit) {
            contador += mod.getHora_materia();
            integracionMaestria.setTiempoMaestria(contador);
        }
        PrimeFaces.current().executeScript("PF('dlg_loader').hide()");
        PrimeFaces.current().executeScript("PF('editListadoModuloMaestria').show()");
    }

    public String puntoFinal(String texto) {
        if (texto.isEmpty() || texto.charAt(texto.length() - 1) != '.') {
            return texto + '.';
        }
        return texto;
    }

    public void verificarModulosPreseleccionados() {
        if (listaModulos.size() > 0) {
            for (Modulo m : listaTextModulo) {
                boolean verificaLista = false;
                for (Modulo mod : listaModulos) {
                    if (m.getNombreMateria().equals(mod.getNombreMateria())) {
                        verificaLista = true;
                        break;
                    }
                }
                if (!verificaLista) {
                    if (m.getNombreMateria().length() > 98) {
                        m.setNombreMateria(puntoFinal(m.getNombreMateria().substring(0, 99)));
                        m.setDescripcion(puntoFinal(m.getDescripcion().substring(0, 99)));
                    }
                    m.setNombreMateria(puntoFinal(m.getNombreMateria()));
                    m.setDescripcion(puntoFinal(m.getDescripcion()));
                    if (!verificaIntegracionModulos(m.getNombreMateria())) {
                        listaModulos.add(m);
                    } else {
                        showWarn("Esta maestría " + m.getNombreMateria() + " ya se encuentra registrada, se agregaran solo las que no se encuentren.");
                    }
                }
            }
        } else {

            for (Modulo m : listaTextModulo) {
                if (m.getNombreMateria().length() > 98) {
                    m.setNombreMateria(puntoFinal(m.getNombreMateria().substring(0, 99)));
                    m.setDescripcion(puntoFinal(m.getDescripcion().substring(0, 99)));
                }
                m.setNombreMateria(puntoFinal(m.getNombreMateria()));
                m.setDescripcion(puntoFinal(m.getDescripcion()));
                if (!verificaIntegracionModulos(m.getNombreMateria())) {
                    listaModulos.add(m);
                } else {
                    showWarn("Esta maestría " + m.getNombreMateria() + " ya se encuentra registrada, se agregaran solo las que no se encuentren.");
                }
            }
        }
        modulo = new Modulo();
        modulo.setVerifica(false);
        listaTextModulo = new ArrayList<>();
        PrimeFaces.current().executeScript("PF('listLoadModulos').hide()");
    }

    public boolean verificaIntegracionModulos(String modulo) {
        boolean verifica = false;
        for (Modulo mod : listaModulosVerificacion) {
            if (mod.getNombreMateria().equals(modulo)) {
                verifica = true;
                break;
            }
        }
        return verifica;
    }

    public void formatearTexto() {
        if (!"".equals(modulo.getNombreMateria())) {
            modulo.setNombreMateria(enumerarLineas(modulo.getNombreMateria()));
            modulo.setVerifica(true);
        }
    }

    private String enumerarLineas(String texto) {
        StringBuilder resultado = new StringBuilder();
        String[] lineas = texto.split("\\r?\\n");
        List<String> elementos = new ArrayList<>();

        for (int i = 0; i < lineas.length; i++) {
            String linea = lineas[i].trim();

            // Verificar si la línea ya tiene una enumeración
            if (!linea.matches("^\\d+\\.-.*$")) {
                elementos.add(linea);
                resultado.append((i + 1)).append(".-").append(linea);
            } else {
                resultado.append(linea);
            }

            if (i < lineas.length - 1) {
                resultado.append(System.lineSeparator());
            }
        }

        for (int i = 0; i < elementos.size(); i++) {
            listaTextModulo.add(new Modulo(0, elementos.get(i), elementos.get(i)));
        }

        return resultado.toString();
    }

    public void actualizaTiempo() {
        float tiempoAcumulado = 0;
        for (Modulo total : listaModulos) {
            if (total.getHora_materia() < 0) {
                total.setHora_materia(total.getHora_materia() * -1);
            }
            tiempoAcumulado += total.getHora_materia();
        }
        integracionMaestria.setTiempoMaestria(tiempoAcumulado);
    }

    public void actualizaTiempoEdit() {
        float tiempoAcumulado = 0;
        for (Modulo total : listaModulosEdit) {
            if (total.getHora_materia() < 0) {
                total.setHora_materia(total.getHora_materia() * -1);
            }
            tiempoAcumulado += total.getHora_materia();
        }
        integracionMaestria.setTiempoMaestria(tiempoAcumulado);
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
            listaMaestria = maestriaDAO.getListaMaestria(user.getIdUsuarioSesion());
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

        if (maestriaDAO.verificaCantidadDocente(maestria) > 0) {
            integracionMaestria.setIdMaestria(maestria.getIdMaestria());
            integracionMaestria.setNombre(maestria.getNombre());
            integracionMaestria.setDescripcion(maestria.getDescripcion());
        } else {
            //Cambiar presentacion
            showWarn("La maestria no tiene asignado docentes suficientes ");
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

    public void deleteFila(Modulo modulo) {
        if (listaModulosEdit.size() <= 1) {
            showWarn("Al menos debe tener un módulo registrado.");
        } else {
            int confirmacion = maestriaDAO.deleteModuloMaestria(modulo);
            switch (confirmacion) {
                case 0:
                    showWarn("No se puede eliminar el módulo " + modulo.getNombreMateria() + " porque ya existen planificaciones de horarios.");
                    break;
                case -1:
                    showWarn("Transacción Fallida");
                    break;
                default:
                    showInfo(modulo.getNombreMateria() + " se elimino con exito.");
                    integracionMaestria.setTiempoMaestria(integracionMaestria.getTiempoMaestria() - modulo.getHora_materia());
                    listaModulosEdit.remove(modulo);
                    break;
            }
        }
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

    public void addModulosEdit(Modulo modulo) {
        try {
            if (!verificaListaModulosIntegracion(modulo.getNombreMateria())) {
                if (modulo.isVerifica()) {
                    listaModulosEdit.add(modulo);
                    integracionMaestria.setTiempoMaestria(integracionMaestria.getTiempoMaestria() + modulo.getHora_materia());
                } else {
                    listaModulosEdit.remove(modulo);
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

    public boolean verificaListaModulosIntegracion(String modulo) {
        boolean verifica = false;
        for (Modulo mod : listaModulosEdit) {
            if (modulo.equals(mod.getNombreMateria())) {
                verifica = true;
                break;
            }
        }
        return verifica;
    }

    public boolean verfifiaIntegracio(int idModulo) {
        boolean verifica = false;
        for (Modulo mod : listaModulosVerificacion) {
            if (mod.getIdMateria() == idModulo) {
                verifica = true;
                break;
            }
        }
        return verifica;
    }

    public boolean verificaModulo(int idModulo) {
        boolean verifica = true;
        for (Modulo mod : listaModulos) {
            if (mod.getIdMateria() == idModulo) {
                verifica = false;
                break;
            }
        }
        return verifica;
    }

    public void vaciarCamposIntegracionModulo() {
        listaTextModulo = new ArrayList<>();
        integracionMaestria = new Maestria();
        maestria = new Maestria();
        listaModulosEdit = new ArrayList<>();
        listaModulos = new ArrayList<>();
        modulo = new Modulo();
        modulo.setVerifica(false);
        maestriaBusqueda = new Maestria();
        showWarn("El registro se Cancelo.");
    }

    public void registarIntegracionModulo() {
        try {
            if (integracionMaestria.getNombre() == null && integracionMaestria.getIdMaestria() == 0) {
                showWarn("Seleccione una Maestría.");
            } else if (listaModulos.size() < 1) {
                showWarn("Seleccione al menos un módulo.");
            }
            if (!verificaHoras()) {
                showWarn("Ingrese las horas en los módulos.");
            } else if (maestriaDAO.registrarIntegracionModulo(integracionMaestria, listaModulos) > 0) {
                showInfo("Integración de Módulos registrado con éxito.");
                integracionMaestria = new Maestria();
                listaModulos = new ArrayList<>();
                listaMaestria = new ArrayList<>();
                rootIntegracion = new DefaultTreeNode("Root Node", null);
                listaMaestriaxModulo = new ArrayList<>();
                maestriaBusqueda = new Maestria();
                listaMaestria = busquedaMaestriaAux;
                listaMaestriaxModulo = maestriaDAO.getListaMaestriaxModulo(user.getIdUsuarioSesion());
                PrimeFaces.current().executeScript("PF('listadoModuloMaestria').hide()");
                llenarLista();
            } else {
                showWarn("Transacción fallida.");
            }
        } catch (Exception e) {
            showWarn("Error" + e.getMessage());
        }
        PrimeFaces.current().executeScript("PF('dlg_loader').hide()");
    }

    public void editarIntegracionModulo() {
        try {
            if (integracionMaestria.getNombre() == null && integracionMaestria.getIdMaestria() == 0) {
                showWarn("Seleccione una Maestría.");
            } else if (listaModulosEdit.size() < 1) {
                showWarn("Seleccione al menos un módulo.");
            }
            if (!verificaHorasEdit()) {
                showWarn("Ingrese las horas en los módulos.");
            } else if (maestriaDAO.editarIntegracionModulo(integracionMaestria, listaModulosEdit) > 0) {
                showInfo("Integración de Módulos modificada con éxito.");
                integracionMaestria = new Maestria();
                listaModulosEdit = new ArrayList<>();
                listaMaestria = new ArrayList<>();
                rootIntegracion = new DefaultTreeNode("Root Node", null);
                listaMaestriaxModulo = new ArrayList<>();
                maestriaBusqueda = new Maestria();
                listaMaestria = busquedaMaestriaAux;
                listaMaestriaxModulo = maestriaDAO.getListaMaestriaxModulo(user.getIdUsuarioSesion());
                PrimeFaces.current().executeScript("PF('editListadoModuloMaestria').hide()");
                llenarLista();
            } else {
                showWarn("Transacción fallida.");
            }
        } catch (Exception e) {
            showWarn("Error" + e.getMessage());
        }
        PrimeFaces.current().executeScript("PF('dlg_loader').hide()");
    }

    public boolean verificaHoras() {
        boolean verifica = true;
        for (Modulo mod : listaModulos) {
            if (mod.getHora_materia() < 1) {
                verifica = false;
            }
        }
        return verifica;
    }

    public boolean verificaHorasEdit() {
        boolean verifica = true;
        for (Modulo mod : listaModulosEdit) {
            if (mod.getHora_materia() < 1) {
                verifica = false;
            }
        }
        return verifica;
    }

    public void llenarLista() {
        int indiceMaestria = 1;
        int indiceModulo = 1;
        for (Maestria maestriaT : listaMaestriaxModulo) {
            maestriaTree = new DefaultTreeNode(new Maestria("", maestriaT.getIdMaestria(),
                    (indiceMaestria) + ". " + maestriaT.getNombre(), maestriaT.getDescripcion(), maestriaT.getTiempoMaestria()), this.rootIntegracion);
            listaModulosNode = maestriaDAO.getListaModulo(maestriaT.getIdMaestria());

            for (Modulo moduloN : listaModulosNode) {
                moduloNode = new DefaultTreeNode(new Maestria(maestriaT.getNombreParalelo(), moduloN.getIdMateria(), (indiceMaestria) + "." + (indiceModulo) + ". " + moduloN.getNombreMateria(), moduloN.getDescripcion(), moduloN.getHora_materia()), maestriaTree);
                indiceModulo++;
            }
            indiceModulo = 1;
            indiceMaestria++;

        }
    }

    public void redireccionInicio() throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect("../Global/principal.xhtml");
    }

    public void verificaCampos() {
        if (integracionMaestria.getNombre() == null && integracionMaestria.getIdMaestria() == 0) {
            showWarn("Seleccione una Maestría.");
        } else {
            PrimeFaces.current().executeScript("PF('listModulosSeleccion').show()");
        }
    }

    public void verificaCamposEdit() {
        if (integracionMaestria.getNombre() == null && integracionMaestria.getIdMaestria() == 0) {
            showWarn("Seleccione una Maestría.");
        } else {
            PrimeFaces.current().executeScript("PF('listModulosSeleccionEdit').show()");
        }
    }

    public void verificaCamposMaestria() {
        if (integracionMaestria.getNombre() == null && integracionMaestria.getIdMaestria() == 0) {
            showWarn("Seleccione una Maestría.");
        } else {
            PrimeFaces.current().executeScript("PF('listLoadModulos').show()");
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
        PrimeFaces.current().executeScript("PF('dlg_loader').hide()");
    }
}
