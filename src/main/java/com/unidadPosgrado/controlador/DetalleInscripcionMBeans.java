package com.unidadPosgrado.controlador;

import com.seguridad.modelo.Usuario;
import com.unidadPosgrado.dao.EstudianteDAO;
import com.unidadPosgrado.dao.MaestriaDAO;
import com.unidadPosgrado.modelo.Estudiante;
import com.unidadPosgrado.modelo.Maestria;
import java.io.IOException;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;

/**
 *
 * @author HP
 */
public class DetalleInscripcionMBeans implements Serializable {

    private List<Estudiante> listaEstudiante;
    private List<Estudiante> listaEstudianteSeleccion;
    private List<Maestria> listaMaestria;
    private Maestria maestria;
    MaestriaDAO maestriaDAO;
    Usuario user;
    FacesContext contexto;
    EstudianteDAO estudianteDAO;
    private Estudiante estudiante;
    private Estudiante estudianteBusqueda;
    List<Estudiante> busquedaEstudianteAux;
    List<Estudiante> busquedaEstudiante;

    public DetalleInscripcionMBeans() {
        estudiante = new Estudiante();
        listaEstudianteSeleccion = new ArrayList<>();
        listaMaestria = new ArrayList<>();
        listaEstudiante = new ArrayList<>();
        maestriaDAO = new MaestriaDAO();
        user = new Usuario();
        estudianteDAO = new EstudianteDAO();
        maestria = new Maestria();
        estudianteBusqueda = new Estudiante();
        busquedaEstudianteAux = new ArrayList<>();
        busquedaEstudiante = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        contexto = FacesContext.getCurrentInstance();
        user = (Usuario) contexto.getExternalContext().getSessionMap().get("usuario");
        listaMaestria = maestriaDAO.getListaMaestriaPeriodoEstudianteInscripcion(user);

    }

    public void redireccionDetalle() throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect("estudiantes.xhtml");
    }

    public void listaEstudiateRegistrado(Maestria maestria) {
        listaEstudiante = new ArrayList<>();
        this.maestria = new Maestria();
        listaEstudiante = estudianteDAO.getListaEstudianteMaestria(maestria);
        this.maestria = maestriaDAO.inscripcionMaestria(maestria);
        PrimeFaces.current().executeScript("PF('dlg_loader').hide()");
        PrimeFaces.current().executeScript("PF('dlgEstudiante').show()");
    }

    public void modificarEstudiateRegistrado(Maestria maestria) {
        listaEstudianteSeleccion = new ArrayList<>();
        busquedaEstudianteAux = new ArrayList<>();
        listaEstudiante = new ArrayList<>();
        this.maestria = new Maestria();
        listaEstudiante = estudianteDAO.getListaEstudianteMaestria(maestria);
        this.maestria = maestriaDAO.inscripcionMaestria(maestria);
        listaEstudianteSeleccion = estudianteDAO.getListaEstudiante();
        busquedaEstudianteAux = listaEstudianteSeleccion;
        PrimeFaces.current().executeScript("PF('dlg_loader').hide()");
        PrimeFaces.current().executeScript("PF('dlgEditEstudiante').show()");
    }

    public String formatoFecha(Date fecha) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd-MMM-yyyy HH:mm:ss", new Locale("es", "ES"));
        return sdf.format(fecha).toUpperCase();
    }

    public void buscarEstudiante() {
        if (estudianteBusqueda.getNombre_estudiante() == null || "".equals(estudianteBusqueda.getNombre_estudiante())) {
            listaEstudianteSeleccion = busquedaEstudianteAux;
        } else {
            listaEstudianteSeleccion = busquedaEstudianteAux;
            for (Estudiante busqueda : listaEstudianteSeleccion) {
                if (busqueda.getNombre_estudiante().toUpperCase().contains(estudianteBusqueda.getNombre_estudiante().toUpperCase())
                        || busqueda.getApellido_estudiante().toUpperCase().contains(estudianteBusqueda.getNombre_estudiante().toUpperCase())) {
                    busquedaEstudiante.add(busqueda);
                }
            }
            listaEstudianteSeleccion = busquedaEstudiante;
            busquedaEstudiante = new ArrayList<>();
//            estudianteBusqueda = new Estudiante();
        }
    }

    public void addEstudiantes(Estudiante objestudiante) {
        try {
            if (objestudiante.isVerifica()) {
                if (verificaAlumno(objestudiante)) {
                    showWarn("El estudiante " + objestudiante.getNombre_estudiante() + " ya se encuentra en la nomina.");
                    objestudiante.setVerifica(false);
                } else {
                    showInfo("El estudiante " + objestudiante.getNombre_estudiante() + " se agrego a la nomina.");
                    listaEstudiante.add(objestudiante);
                }
            } else {
                listaEstudiante.remove(objestudiante);
            }

        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }

    }

    public void deleteAlumnoInscripcionMaestria(Estudiante estudiante) {
        if (listaEstudiante.size() <= 1) {
            showWarn("Al menos debe tener un estudiante registrado.");
        } else {
            if (estudianteDAO.deleteEstudianteMaestria(estudiante.getId_estudiante(), maestria.getIdCurso()) > 0) {
                listaEstudiante.remove(estudiante);
                showInfo("Se elimino al estudiante " + estudiante.getNombre_estudiante());
            } else {
                showWarn("Transacción fallida.");
            }
        }
    }

    public void cancelarEdicionInscripcion() {
        listaEstudianteSeleccion = new ArrayList<>();
        busquedaEstudianteAux = new ArrayList<>();
        listaEstudiante = new ArrayList<>();
        this.maestria = new Maestria();
        PrimeFaces.current().executeScript("PF('dlgEditEstudiante').hide()");
        showWarn("Modificación cancelada.");
    }

    public void editarInscripcion() {
        if (listaEstudiante.size() > 0) {
            if (estudianteDAO.editListaEstudianteInscripcion(this.maestria.getIdCurso(), this.maestria.getTiempoMaestria(), listaEstudiante) > 0) {
                showInfo("Se modifico con exito la inscripción a la " + maestria.getNombre());
                PrimeFaces.current().executeScript("PF('dlgEditEstudiante').hide()");
            } else {
                showWarn("Transacción fallida, vuelva a intentarlo.");
            }
        } else {
            showInfo("La lista de estudiantes debe contener al menos un registro. Por favor, asegúrese de agregar al menos un estudiante antes de continuar.");
        }
        PrimeFaces.current().executeScript("PF('dlg_loader').hide()");
    }

    private boolean verificaAlumno(Estudiante objestudiante) {
        boolean verifica = false;
        for (Estudiante e : listaEstudiante) {
            if (e.getCedula_estudiante().equals(objestudiante.getCedula_estudiante())) {
                verifica = true;
                break;
            }
        }
        return verifica;
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

    public List<Estudiante> getListaEstudiante() {
        return listaEstudiante;
    }

    public void setListaEstudiante(List<Estudiante> listaEstudiante) {
        this.listaEstudiante = listaEstudiante;
    }

    public List<Maestria> getListaMaestria() {
        return listaMaestria;
    }

    public void setListaMaestria(List<Maestria> listaMaestria) {
        this.listaMaestria = listaMaestria;
    }

    public Maestria getMaestria() {
        return maestria;
    }

    public void setMaestria(Maestria maestria) {
        this.maestria = maestria;
    }

    public List<Estudiante> getListaEstudianteSeleccion() {
        return listaEstudianteSeleccion;
    }

    public void setListaEstudianteSeleccion(List<Estudiante> listaEstudianteSeleccion) {
        this.listaEstudianteSeleccion = listaEstudianteSeleccion;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Estudiante getEstudianteBusqueda() {
        return estudianteBusqueda;
    }

    public void setEstudianteBusqueda(Estudiante estudianteBusqueda) {
        this.estudianteBusqueda = estudianteBusqueda;
    }

}
