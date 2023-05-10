package com.unidadPosgrado.controlador;

import com.unidadPosgrado.dao.MaestriaDAO;
import com.unidadPosgrado.modelo.Maestria;
import java.io.IOException;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author HP
 */
public class DetalleInscripcionMBeans {

    private List<Maestria> listaMaestria;
    MaestriaDAO maestriaDAO;

    public DetalleInscripcionMBeans() {
        maestriaDAO = new MaestriaDAO();
    }
    

    @PostConstruct
    public void init() {
        listaMaestria = maestriaDAO.getListaMaestriaPeriodoEstudiante();
    }

    public void redireccionDetalle() throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect("estudiantes.xhtml");
    }

    public List<Maestria> getListaMaestria() {
        return listaMaestria;
    }

    public void setListaMaestria(List<Maestria> listaMaestria) {
        this.listaMaestria = listaMaestria;
    }

}
