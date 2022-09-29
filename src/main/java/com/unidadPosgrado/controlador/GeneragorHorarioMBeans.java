/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unidadPosgrado.controlador;

import com.unidadPosgrado.dao.HorarioDAO;
import com.unidadPosgrado.modelo.Horario;
import com.unidadPosgrado.modelo.Maestria;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.component.export.ExcelOptions;

/**
 *
 * @author HP
 */
public class GeneragorHorarioMBeans {

    HorarioDAO horarioDAO;
    private List<Maestria> listaMaestria;
    private List<Horario> listadoModulo;
    private ExcelOptions excelOpt;

    public GeneragorHorarioMBeans() {
        horarioDAO = new HorarioDAO();
        listaMaestria = new ArrayList<>();
        listadoModulo = new ArrayList<>();
        excelOpt = new ExcelOptions();
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

    public List<Horario> getListadoModulo() {
        return listadoModulo;
    }

    public void setListadoModulo(List<Horario> listadoModulo) {
        this.listadoModulo = listadoModulo;
    }

    public ExcelOptions getExcelOpt() {
        return excelOpt;
    }

    public void setExcelOpt(ExcelOptions excelOpt) {
        this.excelOpt = excelOpt;
    }

    public void generarArchivoExcel(int idCurso, String maestria, Date fechaInicio, Date fechaFin) {
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                getResponse();
        response.addHeader("Content-disposition", "attachment; filename=Planificacion" + maestria.toUpperCase() + ".xlsx");
        response.setContentType("application/vnd.ms-excel");
        List<Date> mesesFormado;
        List<Date> anioFormato;

        listadoModulo = horarioDAO.getListaModulo(idCurso);
        Workbook libroExcel = new XSSFWorkbook();
        Sheet hojaNueva = (Sheet) libroExcel.createSheet("CRONOGRAMA " + maestria);
        Row fila = hojaNueva.createRow(0);
        fila.createCell(0).setCellValue("UNIVERSIDAD TECNICA ESTATAL DE QUEVEDO");
        fila = hojaNueva.createRow(1);
        fila.createCell(0).setCellValue("UNIDAD DE POSGRADO");
        fila = hojaNueva.createRow(2);
        fila.createCell(0).setCellValue("CALENDARIO ACADEMICO DE LA " + maestria.toUpperCase());
        fila = hojaNueva.createRow(3);
        fila.createCell(0).setCellValue("DESDE " + fechaInicio + " A " + fechaFin);
        //Encabezado Excel
        fila = hojaNueva.createRow(4);
        fila.createCell(0).setCellValue("PARALELO");
        fila.createCell(1).setCellValue(fechaFin.getYear());
//        fila.createCell(2).setCellValue("DOCENTES");

        fila = hojaNueva.createRow(5);
        fila.createCell(0).setCellValue("ORDEN");
        fila.createCell(1).setCellValue("MODULOS");
        fila.createCell(2).setCellValue("DOCENTES");
        fila.createCell(3).setCellValue("HORAS");
        mesesFormado = getListaEntreFechas(fechaInicio, fechaFin);
        anioFormato = getListaEntreAños(fechaInicio, fechaFin);
        int columna = 4;
        int contador = 0;
        for (Date mes : mesesFormado) {
            int mesI = mes.getMonth();
            switch (mesI) {
                case 0:
                    fila.createCell(columna).setCellValue("ENERO");
                    break;
                case 1:
                    fila.createCell(columna).setCellValue("FEBRERO");
                    break;
                case 2:
                    fila.createCell(columna).setCellValue("MARZO");
                    break;
                case 3:
                    fila.createCell(columna).setCellValue("ABRIL");
                    break;
                case 4:
                    fila.createCell(columna).setCellValue("MAYO");
                    break;
                case 5:
                    fila.createCell(columna).setCellValue("JUNIO");
                    break;
                case 6:
                    fila.createCell(columna).setCellValue("JULIO");
                    break;
                case 7:
                    fila.createCell(columna).setCellValue("AGOSTO");
                    break;
                case 8:
                    fila.createCell(columna).setCellValue("SEPTIEMBRE");
                    break;
                case 9:
                    fila.createCell(columna).setCellValue("OCTUBRE");
                    break;
                case 10:
                    fila.createCell(columna).setCellValue("NOVIEMBRE");
                    break;
                case 11:
                    fila.createCell(columna).setCellValue("DICIEMBRE");
                    contador++;
                    if (anioFormato.size() > 1) {
                        if (contador > 1) {
                            fila.createCell(contador).setCellValue(fechaFin.getYear());
                        }
                    }
                    break;
                default:
                    break;
            }
            columna++;
        }

        int filaExcel = 6;
        int orden = 1;
        for (Horario horario : listadoModulo) {
            fila = hojaNueva.createRow(filaExcel);
            fila.createCell(0).setCellValue(orden);
            fila.createCell(1).setCellValue(horario.getNombreModulo());
            fila.createCell(2).setCellValue(horario.getNombreDocente());
            fila.createCell(3).setCellValue(horario.getHora());
            filaExcel++;
            orden++;
        }
        filaExcel = 0;
        try {
            OutputStream fileOut= response.getOutputStream();
            libroExcel.write(fileOut);
            response.getOutputStream().flush();
            FacesContext.getCurrentInstance().responseComplete();
        } catch (IOException ex) {
            Logger.getLogger(GeneragorHorarioMBeans.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public List<Date> getListaEntreFechas(Date fechaInicio, Date fechaFin) {
        // Convertimos la fecha a Calendar, mucho más cómodo para realizar
        // operaciones a las fechas
        Calendar c1 = Calendar.getInstance();
        c1.setTime(fechaInicio);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(fechaFin);

        // Lista donde se irán almacenando las fechas
        List<Date> listaFechas = new ArrayList<Date>();

        // Bucle para recorrer el intervalo, en cada paso se le suma un día.
        while (!c1.after(c2)) {
            listaFechas.add(c1.getTime());
            c1.add(Calendar.MONTH, 1);
        }
        return listaFechas;
    }

    public List<Date> getListaEntreAños(Date fechaInicio, Date fechaFin) {
        // Convertimos la fecha a Calendar, mucho más cómodo para realizar
        // operaciones a las fechas
        Calendar c1 = Calendar.getInstance();
        c1.setTime(fechaInicio);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(fechaFin);

        // Lista donde se irán almacenando las fechas
        List<Date> listaFechas = new ArrayList<Date>();

        // Bucle para recorrer el intervalo, en cada paso se le suma un día.
        while (!c1.after(c2)) {
            listaFechas.add(c1.getTime());
            c1.add(Calendar.YEAR, 1);
        }
        return listaFechas;
    }

}
