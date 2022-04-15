/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unidadPosgrado.controlador;

import javax.annotation.PostConstruct;
import org.primefaces.model.DefaultScheduleModel;
import org.primefaces.model.ScheduleModel;

/**
 *
 * @author HP
 */
public class HorarioMBeans {

    /**
     * Creates a new instance of HorarioMBeans
     */
    private ScheduleModel eventModel;
    public HorarioMBeans() {
    }
    @PostConstruct
    public void init() {
        eventModel = new DefaultScheduleModel();
    }

    public ScheduleModel getEventModel() {
        return eventModel;
    }

    public void setEventModel(ScheduleModel eventModel) {
        this.eventModel = eventModel;
    }
    
    
}
