/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unidadPosgrado.controlador;

import com.unidadPosgrado.dao.HorarioDAO;
import com.unidadPosgrado.modelo.Maestria;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 *
 * @author HP
 */
public class GeneragorHorarioMBeans {

    HorarioDAO horarioDAO;
    private List<Maestria> listaMaestria;

    public GeneragorHorarioMBeans() {
        horarioDAO = new HorarioDAO();
        listaMaestria = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        listaMaestria = horarioDAO.getListaMaestriaPeriodo();
    }

    public List<Maestria> getListaMaestria() {
        return listaMaestria;
    }

    public void setListaMaestria(List<Maestria> listaMaestria) {
        this.listaMaestria = listaMaestria;
    }

}
