/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unidadPosgrado.controlador;

import com.seguridad.modelo.Usuario;
import com.unidadPosgrado.dao.DocenteDAO;
import com.unidadPosgrado.dao.MaestriaDAO;
import com.unidadPosgrado.modelo.Docente;
import com.unidadPosgrado.modelo.Maestria;
import java.io.Serializable;
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
public class DocenteMBeans implements Serializable {

    private Docente editDocente;
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
    private List<Maestria> editListMaestria;
    List<Maestria> editListMaestriaConfirmada;
    List<Docente> listaDocenteBusqueda;
    List<Docente> listaDocenteBusquedaAux;
    Usuario user;
    FacesContext contexto;

    public DocenteMBeans() {
        docente = new Docente();
        editDocente = new Docente();
        docenteDAO = new DocenteDAO();
        listaDocente = new ArrayList<>();
        listaMaestria = new ArrayList<>();
        maestriaDAO = new MaestriaDAO();
        maestriaBusqueda = new Maestria();
        busquedaMaestria = new ArrayList<>();
        busquedaMaestriaAux = new ArrayList<>();
        seleccionMaestria = new ArrayList<>();
        editListMaestria = new ArrayList<>();
        editListMaestriaConfirmada = new ArrayList<>();
        docenteBusqueda = new Docente();
        listaDocenteBusqueda = new ArrayList<>();
        listaDocenteBusquedaAux = new ArrayList<>();
        user = new Usuario();
    }

    @PostConstruct
    public void init() {
        contexto = FacesContext.getCurrentInstance();
        user = (Usuario) contexto.getExternalContext().getSessionMap().get("usuario");
        listaMaestria = maestriaDAO.getListaMaestria(user.getIdUsuarioSesion());
        listaDocente = docenteDAO.getListaDocente();
        busquedaMaestriaAux = listaMaestria;
        listaDocenteBusqueda = listaDocente;
    }

    public void verificarPersona() {
        String cedula = "";
        if (docente.getCedula_docente().length() > 8) {
            cedula = docente.getCedula_docente();
            docente = docenteDAO.verificarDocente(docente);

            if (cedula.equals(docente.getCedula_docente())) {
                showWarn(docente.getNombre_docente() + " ya se encuentra registrado como estudiante. Si procede con el registro, se deshabilitará como estudiante y se registrará como docente.");
            } else {
                docente = new Docente();
                docente.setCedula_docente(cedula);
            }
        }
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
            } else if ("".equals(docente.getEspecializacion().trim())) {
                showWarn("Debe ingresar una Especialización.");
            } else if ("".equals(docente.getNivel_educativo().trim())) {
                showWarn("Debe ingresar un nivrl educativo.");
            } else if (seleccionMaestria.size() < 1) {
                showWarn("Debe asignar al menos una maestría al docente.");
            } else {
                int resultadoRegistro = docenteDAO.registrarDocente(docente, seleccionMaestria);
                switch (resultadoRegistro) {
                    case 1:
                        showInfo(docente.getNombre_docente().trim().replace(".", ",") + " Guardado con éxito.");
                        docente = new Docente();
                        seleccionMaestria = new ArrayList<>();
                        PrimeFaces.current().executeScript("PF('dlgDocente').hide()");
                        listaDocente = docenteDAO.getListaDocente();
                        listaDocenteBusqueda = docenteDAO.getListaDocente();
                        listaMaestria = maestriaDAO.getListaMaestria(user.getIdUsuarioSesion());
                        busquedaMaestriaAux = maestriaDAO.getListaMaestria(user.getIdUsuarioSesion());
                        maestriaBusqueda = new Maestria();
                        break;
                    case 2:
                        showInfo(docente.getNombre_docente().trim().replace(".", ",") + " ha sido registrado exitosamente y asignado correctamente.");
                        docente = new Docente();
                        seleccionMaestria = new ArrayList<>();
                        PrimeFaces.current().executeScript("PF('dlgDocente').hide()");
                        listaDocente = docenteDAO.getListaDocente();
                        listaDocenteBusqueda = docenteDAO.getListaDocente();
                        listaMaestria = maestriaDAO.getListaMaestria(user.getIdUsuarioSesion());
                        busquedaMaestriaAux = maestriaDAO.getListaMaestria(user.getIdUsuarioSesion());
                        maestriaBusqueda = new Maestria();
                        break;
                    default:
                        showWarn("La cédula " + docente.getCedula_docente().trim().replace(".", ",") + "ya se encuentra registrada como estudiante. Se habilitará para que sea un docente.");
                        PrimeFaces.current().executeScript("PF('dlgDocente').hide()");
                        docente = new Docente();
                        seleccionMaestria = new ArrayList<>();
                        listaDocente = docenteDAO.getListaDocente();
                        listaDocenteBusqueda = docenteDAO.getListaDocente();
                        listaMaestria = maestriaDAO.getListaMaestria(user.getIdUsuarioSesion());
                        busquedaMaestriaAux = maestriaDAO.getListaMaestria(user.getIdUsuarioSesion());
                        maestriaBusqueda = new Maestria();
                        break;
                }
            }
        } catch (Exception e) {
        }
        PrimeFaces.current().executeScript("PF('dlg_loader').hide()");
    }

    public void cancelarRegistro() {
        docente = new Docente();
        seleccionMaestria = new ArrayList<>();
        listaMaestria = maestriaDAO.getListaMaestria(user.getIdUsuarioSesion());
        maestriaBusqueda = new Maestria();
        listaDocenteBusqueda = docenteDAO.getListaDocente();
        editListMaestria = new ArrayList<>();
        showWarn("Registro cancelado.");
    }

    public void editarDocente() {
        try {
            if ("".equals(editDocente.getNombre_docente().trim())) {
                showWarn("No se puede modificar el registro porque el campo esta vacio.");
            } else if ("".equals(editDocente.getApellido_docente().trim())) {
                showWarn("No se puede modificar el registro porque el campo esta vacio.");
            } else if ("".equals(editDocente.getCedula_docente().trim())) {
                showWarn("No se puede modificar el registro porque el campo esta vacio.");
            } else if ("".equals(editDocente.getEspecializacion().trim())) {
                showWarn("No se puede modificar el registro porque el campo esta vacio.");
            } else if ("".equals(editDocente.getNivel_educativo().trim())) {
                showWarn("No se puede modificar el registro porque el campo esta vacio.");
            } else if ("".equals(editDocente.getTelefono_docente().trim())) {
                showWarn("No se puede modificar el registro porque el campo esta vacio.");
            } else if ("".equals(editDocente.getCorreo_docente().trim())) {
                showWarn("No se puede modificar el registro porque el campo esta vacio.");
            } else {
                int resultadoRegistro = docenteDAO.editarDocente(editDocente, editListMaestriaConfirmada);
                if (resultadoRegistro > 0) {
                    showInfo("Se actualizo con éxito, " + editDocente.getNombre_docente().trim());
                } else {
                    showInfo(editDocente.getNombre_docente().trim().replace(".", ",") + " , algunos de sus datos fueron actualizados.");
                }
            }
            editListMaestriaConfirmada = new ArrayList<>();
            listaDocente = new ArrayList<>();
            docente = new Docente();
            listaDocente = docenteDAO.getListaDocente();
            listaDocenteBusqueda = listaDocente;
            listaMaestria = maestriaDAO.getListaMaestria(user.getIdUsuarioSesion());
            busquedaMaestriaAux = maestriaDAO.getListaMaestria(user.getIdUsuarioSesion());
            maestriaBusqueda = new Maestria();
        } catch (Exception e) {
            showWarn(e.getMessage());
        }
        PrimeFaces.current().executeScript("PF('dlg_loader').hide()");
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
                showInfo("¡Has seleccionado " + maestria.getNombre() + "! Recuerda cerrar el apartado de selección una vez hayas terminado y haz clic en el botón 'Guardar' para finalizar el proceso.");
                seleccionMaestria.add(maestria);
            } else {
                showInfo("Has deseleccionado " + maestria.getNombre() + ". No olvides cerrar la sección de selección cuando hayas terminado y haz clic en el botón 'Guardar' para finalizar el proceso.");
                seleccionMaestria.remove(maestria);
            }
        } catch (Exception e) {
            showWarn("Error" + e.getMessage());
        }

    }

    public void addEditMaestria(Maestria maestria) {
        try {
            if (maestria.isVerifica() && verificaModulo(maestria.getIdMaestria()) && verificaListaRegistro(maestria.getIdMaestria())) {
                editListMaestria.add(maestria);
                editListMaestriaConfirmada.add(maestria);
                showInfo("¡Has seleccionado " + maestria.getNombre() + "! Recuerda cerrar el apartado de selección una vez hayas terminado y haz clic en el botón 'Guardar' para finalizar el proceso.");
            } else {
                showInfo("Has deseleccionado " + maestria.getNombre() + ". No olvides cerrar la sección de selección cuando hayas terminado y haz clic en el botón 'Guardar' para finalizar el proceso.");

                editListMaestria.remove(maestria);
            }
        } catch (Exception e) {
            showWarn("Error" + e.getMessage());
        }

    }

    public boolean verificaListaRegistro(int idMaestria) {
        boolean verifica = true;
        for (Maestria maestriaVerifica : editListMaestria) {
            if (maestriaVerifica.getIdMaestria() == idMaestria) {
                verifica = false;
                break;
            }
        }
        return verifica;
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

    public void recibirEditDocente(Docente editarDocente) {
        try {
            editDocente = editarDocente;
            editListMaestria = maestriaDAO.getEditListaMaestria(editDocente.getCedula_docente());
            PrimeFaces.current().executeScript("PF('dlgEditDocente').show()");
        } catch (Exception e) {
            showWarn(e.getMessage());
        }
        PrimeFaces.current().executeScript("PF('dlg_loader').hide()");
    }

    public void deleteFila(Maestria mEliminar) {
        editListMaestria.remove(mEliminar);
        if (docenteDAO.eliminarMaestria(editDocente.getId_docente(), mEliminar.getIdMaestria()) > 0) {
            showInfo("Eliminado con Exito.");
        } else {
            showInfo("Ocurrio un error.");
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

    public Docente getEditDocente() {
        return editDocente;
    }

    public void setEditDocente(Docente editDocente) {
        this.editDocente = editDocente;
    }

    public List<Maestria> getEditListMaestria() {
        return editListMaestria;
    }

    public void setEditListMaestria(List<Maestria> editListMaestria) {
        this.editListMaestria = editListMaestria;
    }

}
