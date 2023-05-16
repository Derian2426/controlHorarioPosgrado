package com.unidadPosgrado.controlador;

import com.seguridad.modelo.Usuario;
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
    Usuario user;
    FacesContext contexto;

    public DetalleInscripcionMBeans() {
        maestriaDAO = new MaestriaDAO();
        user = new Usuario();
    }

    @PostConstruct
    public void init() {
        contexto = FacesContext.getCurrentInstance();
        user = (Usuario) contexto.getExternalContext().getSessionMap().get("usuario");
        listaMaestria = maestriaDAO.getListaMaestriaPeriodoEstudiante(user);
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
