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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import org.primefaces.PrimeFaces;

/**
 *
 * @author HP
 */
public class DetalleInscripcionMBeans implements Serializable {

    private List<Estudiante> listaEstudiante;
    private List<Maestria> listaMaestria;
    private Maestria maestria;
    MaestriaDAO maestriaDAO;
    Usuario user;
    FacesContext contexto;
    EstudianteDAO estudianteDAO;

    public DetalleInscripcionMBeans() {
        listaMaestria = new ArrayList<>();
        listaEstudiante = new ArrayList<>();
        maestriaDAO = new MaestriaDAO();
        user = new Usuario();
        estudianteDAO = new EstudianteDAO();
        maestria = new Maestria();
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
        listaEstudiante = estudianteDAO.getListaEstudianteMaestria(maestria);
        this.maestria = maestriaDAO.inscripcionMaestria(maestria);
        PrimeFaces.current().executeScript("PF('dlgEstudiante').show()");
    }

    public String formatoFecha(Date fecha) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd-MMM-yyyy HH:mm:ss", new Locale("es", "ES"));
        return sdf.format(fecha).toUpperCase();
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

}
