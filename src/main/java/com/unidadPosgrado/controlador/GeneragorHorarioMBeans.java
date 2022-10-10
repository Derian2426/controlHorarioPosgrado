/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unidadPosgrado.controlador;

import com.unidadPosgrado.dao.HorarioDAO;
import com.unidadPosgrado.dao.MaestriaDAO;
import com.unidadPosgrado.modelo.Horario;
import com.unidadPosgrado.modelo.Maestria;
import com.unidadPosgrado.modelo.Periodo;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.PrimeFaces;
import org.primefaces.component.export.ExcelOptions;

/**
 *
 * @author HP
 */
public class GeneragorHorarioMBeans {

    private Maestria integracionMaestria;
    private Periodo periodo;
    MaestriaDAO maestriaDAO;
    private Maestria maestriaBusqueda;
    private List<Maestria> listaMaestriaPeriodo;
    List<Maestria> busquedaMaestriaAuxP;

    HorarioDAO horarioDAO;
    private List<Maestria> listaMaestria;
    private List<Horario> listadoModulo;
    private List<Horario> listadoAsignaciones;
    private List<Periodo> listaPeriodo;
    private ExcelOptions excelOpt;

    public GeneragorHorarioMBeans() {
        maestriaDAO = new MaestriaDAO();
        periodo = new Periodo();
        integracionMaestria = new Maestria();
        maestriaBusqueda = new Maestria();
        listaMaestriaPeriodo = new ArrayList<>();
        busquedaMaestriaAuxP = new ArrayList<>();

        horarioDAO = new HorarioDAO();
        listaMaestria = new ArrayList<>();
        listadoModulo = new ArrayList<>();
        excelOpt = new ExcelOptions();
        listadoAsignaciones = new ArrayList<>();
        listaPeriodo = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        listaMaestria = horarioDAO.getListaMaestriaPeriodo();
        listaMaestriaPeriodo = maestriaDAO.getListaMaestria_Periodo();
        busquedaMaestriaAuxP = listaMaestriaPeriodo;
        listaPeriodo = maestriaDAO.getListaPeriodo();
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

    public List<Horario> getListadoAsignaciones() {
        return listadoAsignaciones;
    }

    public void setListadoAsignaciones(List<Horario> listadoAsignaciones) {
        this.listadoAsignaciones = listadoAsignaciones;
    }

    public Maestria getIntegracionMaestria() {
        return integracionMaestria;
    }

    public void setIntegracionMaestria(Maestria integracionMaestria) {
        this.integracionMaestria = integracionMaestria;
    }

    public Periodo getPeriodo() {
        return periodo;
    }

    public void setPeriodo(Periodo periodo) {
        this.periodo = periodo;
    }

    public Maestria getMaestriaBusqueda() {
        return maestriaBusqueda;
    }

    public void setMaestriaBusqueda(Maestria maestriaBusqueda) {
        this.maestriaBusqueda = maestriaBusqueda;
    }

    public List<Maestria> getListaMaestriaPeriodo() {
        return listaMaestriaPeriodo;
    }

    public void setListaMaestriaPeriodo(List<Maestria> listaMaestriaPeriodo) {
        this.listaMaestriaPeriodo = listaMaestriaPeriodo;
    }

    public List<Periodo> getListaPeriodo() {
        return listaPeriodo;
    }

    public void setListaPeriodo(List<Periodo> listaPeriodo) {
        this.listaPeriodo = listaPeriodo;
    }

    public void actualizaLista() {
        listaMaestria = horarioDAO.getListaMaestriaPeriodo();
    }

    public void generarArchivoExcel(int idCurso, String maestria, Date fechaInicio, Date fechaFin) {
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().
                getResponse();
        response.addHeader("Content-disposition", "attachment; filename=PLANIFICACIÓN DE " + maestria.toUpperCase() + ".xlsx");
        response.setContentType("application/vnd.ms-excel");
        List<Date> mesesFormado;
        List<Date> anioFormato;
        listadoModulo = horarioDAO.getListaModulo(idCurso);
        Workbook libroExcel = new XSSFWorkbook();
        Sheet hojaNueva = (Sheet) libroExcel.createSheet("CRONOGRAMA DE " + maestria.toUpperCase());
        hojaNueva.setColumnWidth(0, 3500);
        hojaNueva.setColumnWidth(1, 5500);
        hojaNueva.setColumnWidth(2, 5500);
        Row fila = hojaNueva.createRow(0);

        //Estilos para el archivo Excel
        CellStyle cellStyle = libroExcel.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.TOP);
        cellStyle.setWrapText(true);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        fila.createCell(0).setCellValue("UNIVERSIDAD TECNICA ESTATAL DE QUEVEDO");
        fila = hojaNueva.createRow(1);
        fila.createCell(0).setCellValue("UNIDAD DE POSGRADO");
        fila = hojaNueva.createRow(2);
        fila.createCell(0).setCellValue("CALENDARIO ACADEMICO DE LA " + maestria.toUpperCase());
        fila = hojaNueva.createRow(3);
        fila.createCell(0).setCellValue("DESDE " + fechaInicio + " A " + fechaFin);
        //Encabezado Excel
        fila = hojaNueva.createRow(4);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        fila.createCell(0).setCellValue((listadoModulo.size() > 0) ? listadoModulo.get(0).getParalelo().toUpperCase() : "PARALELO ?");
        fila.createCell(1);
        fila.createCell(2);
        fila.createCell(3);
        hojaNueva.addMergedRegion(CellRangeAddress.valueOf("A5:D5"));
        //Aqui para luego cambiar el color
        fila.getSheet().getRow(4).getCell(0).setCellStyle(cellStyle);
        fila.getSheet().getRow(4).getCell(1).setCellStyle(cellStyle);
        fila.getSheet().getRow(4).getCell(2).setCellStyle(cellStyle);
        fila.getSheet().getRow(4).getCell(3).setCellStyle(cellStyle);
        SimpleDateFormat getYearFormat = new SimpleDateFormat("yyyy");
        fila.createCell(4).setCellValue("'" + getYearFormat.format(fechaInicio) + "'");
        fila.getCell(4).setCellStyle(cellStyle);
        fila = hojaNueva.createRow(5);
        fila.createCell(0).setCellValue("ORDEN");
        fila.getCell(0).setCellStyle(cellStyle);
        fila.createCell(1).setCellValue("MODULOS");
        fila.getCell(1).setCellStyle(cellStyle);
        fila.createCell(2).setCellValue("DOCENTES");
        fila.getCell(2).setCellStyle(cellStyle);
        fila.createCell(3).setCellValue("HORAS");
        fila.getCell(3).setCellStyle(cellStyle);
        mesesFormado = getListaEntreFechas(fechaInicio, fechaFin);
        anioFormato = getListaEntreAños(mesesFormado);
        int columna = 4;
        int contador = 0;
        int cantidadAnio = anioFormato.size() - 1;
        int posicion = 0;

        //Posiciones en la hoja de excel
        Row fila_aux = hojaNueva.getRow(4);
        for (Date mes : mesesFormado) {
            int mesI = mes.getMonth();
            switch (mesI) {
                case 0:
                    fila.createCell(columna).setCellValue("ENERO");
                    fila.getCell(columna).setCellStyle(cellStyle);
                    fila_aux.createCell(columna + 1);
                    contador++;
                    if (posicion < cantidadAnio) {
                        if (contador >= 1) {
                            fila_aux.createCell(columna).setCellValue("'" + getYearFormat.format(anioFormato.get(posicion + 1)) + "'");
                            fila_aux.getCell(columna).setCellStyle(cellStyle);
                            posicion++;
                        }
                    }
                    break;
                case 1:
                    fila.createCell(columna).setCellValue("FEBRERO");
                    fila.getCell(columna).setCellStyle(cellStyle);
                    fila_aux.createCell(columna + 1);
                    break;
                case 2:
                    fila.createCell(columna).setCellValue("MARZO");
                    fila.getCell(columna).setCellStyle(cellStyle);
                    fila_aux.createCell(columna + 1);
                    break;
                case 3:
                    fila.createCell(columna).setCellValue("ABRIL");
                    fila.getCell(columna).setCellStyle(cellStyle);
                    fila_aux.createCell(columna + 1);
                    break;
                case 4:
                    fila.createCell(columna).setCellValue("MAYO");
                    fila.getCell(columna).setCellStyle(cellStyle);
                    fila_aux.createCell(columna + 1);
                    break;
                case 5:
                    fila.createCell(columna).setCellValue("JUNIO");
                    fila.getCell(columna).setCellStyle(cellStyle);
                    fila_aux.createCell(columna + 1);
                    break;
                case 6:
                    fila.createCell(columna).setCellValue("JULIO");
                    fila.getCell(columna).setCellStyle(cellStyle);
                    fila_aux.createCell(columna + 1);
                    break;
                case 7:
                    fila.createCell(columna).setCellValue("AGOSTO");
                    fila.getCell(columna).setCellStyle(cellStyle);
                    fila_aux.createCell(columna + 1);
                    break;
                case 8:
                    fila.createCell(columna).setCellValue("SEPTIEMBRE");
                    fila.getCell(columna).setCellStyle(cellStyle);
                    fila_aux.createCell(columna + 1);
                    break;
                case 9:
                    fila.createCell(columna).setCellValue("OCTUBRE");
                    fila.getCell(columna).setCellStyle(cellStyle);
                    fila_aux.createCell(columna + 1);
                    break;
                case 10:
                    fila.createCell(columna).setCellValue("NOVIEMBRE");
                    fila.getCell(columna).setCellStyle(cellStyle);
                    fila_aux.createCell(columna + 1);
                    break;
                case 11:
                    fila.createCell(columna).setCellValue("DICIEMBRE");
                    fila.getCell(columna).setCellStyle(cellStyle);
                    fila_aux.createCell(columna + 1);
                    break;
                default:
                    break;
            }
            columna++;
        }
        int celda_aux = 4;
        int firstRow = 4;
        int lastRow = 4;
        int contadoEspacios = 0;
        while (celda_aux <= columna) {
            if ("".equals(fila.getSheet().getRow(4).getCell(celda_aux).getStringCellValue()) && celda_aux < columna) {
                //Aqui para luego cambiar el color
                fila.getSheet().getRow(4).getCell(celda_aux).setCellStyle(cellStyle);
                contadoEspacios++;
            } else {
                if (contadoEspacios > 0) {
                    hojaNueva.addMergedRegion(new CellRangeAddress(firstRow, lastRow, (celda_aux - contadoEspacios) - 1, celda_aux - 1));
                    contadoEspacios = 0;
                }
            }
            hojaNueva.setColumnWidth(celda_aux, 3800);
            celda_aux++;
        }
        int filaExcel = 6;
        int orden = 1;
        int anioContador;
        for (Horario horario : listadoModulo) {
            fila = hojaNueva.createRow(filaExcel);
            fila.createCell(0).setCellValue(orden);
            fila.getCell(0).setCellStyle(cellStyle);
            fila.createCell(1).setCellValue(horario.getNombreModulo());
            fila.getCell(1).setCellStyle(cellStyle);
            fila.createCell(2).setCellValue(horario.getNombreDocente());
            fila.getCell(2).setCellStyle(cellStyle);
            fila.createCell(3).setCellValue(horario.getHora());
            fila.getCell(3).setCellStyle(cellStyle);

            String dia = "";
            int celda;
            anioContador = anioFormato.size();
            String anioCelda = "";
            String anioLista = "";
            int maximoAnio = 0;
            int sumadorCeldas = 4;
            while (maximoAnio < anioContador) {
                listadoAsignaciones = horarioDAO.getListaAsignacionDocente(idCurso, horario.getIdDocente(), anioFormato.get(maximoAnio));
                for (Horario fechaAsignacion : listadoAsignaciones) {
                    switch (fechaAsignacion.getFechaAsignacion().getMonth()) {
                        case 0:
                            celda = sumadorCeldas;
                            while (!"ENERO".equals(fila.getSheet().getRow(5).getCell(celda).getStringCellValue())
                                    && !anioCelda.equals(anioLista)) {
                                celda++;
                            }

                            try {
                                if ("".equals(fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue())) {
                                    dia += fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                } else {
                                    dia = fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                }
                                dia += fechaAsignacion.getFechaAsignacion().getDate() + ",";
                                fila.createCell(celda).setCellValue(dia);
                                fila.getCell(celda).setCellStyle(cellStyle);
                                dia = " ";
                            } catch (Exception e) {
                                fila.createCell(celda);
                                if ("".equals(fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue())) {
                                    dia += fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                } else {
                                    dia = fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                }
                                dia += fechaAsignacion.getFechaAsignacion().getDate() + ",";
                                fila.createCell(celda).setCellValue(dia);
                                fila.getCell(celda).setCellStyle(cellStyle);
                                dia = " ";
                            }
                            break;
                        case 1:
                            celda = sumadorCeldas;
                            while (!"FEBRERO".equals(fila.getSheet().getRow(5).getCell(celda).getStringCellValue())) {
                                celda++;
                            }
                            try {
                                if ("".equals(fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue())) {
                                    dia += fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                } else {
                                    dia = fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                }
                                dia += fechaAsignacion.getFechaAsignacion().getDate() + ",";
                                fila.createCell(celda).setCellValue(dia);
                                fila.getCell(celda).setCellStyle(cellStyle);
                                dia = " ";
                            } catch (Exception e) {
                                fila.createCell(celda);
                                if ("".equals(fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue())) {
                                    dia += fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                } else {
                                    dia = fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                }
                                dia += fechaAsignacion.getFechaAsignacion().getDate() + ",";
                                fila.createCell(celda).setCellValue(dia);
                                fila.getCell(celda).setCellStyle(cellStyle);
                                dia = " ";
                            }
                            break;
                        case 2:
                            celda = sumadorCeldas;
                            while (!"MARZO".equals(fila.getSheet().getRow(5).getCell(celda).getStringCellValue())) {
                                celda++;
                            }
                            try {
                                if ("".equals(fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue())) {
                                    dia += fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                } else {
                                    dia = fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                }
                                dia += fechaAsignacion.getFechaAsignacion().getDate() + ",";
                                fila.createCell(celda).setCellValue(dia);
                                fila.getCell(celda).setCellStyle(cellStyle);
                                dia = " ";
                            } catch (Exception e) {
                                fila.createCell(celda);
                                if ("".equals(fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue())) {
                                    dia += fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                } else {
                                    dia = fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                }
                                dia += fechaAsignacion.getFechaAsignacion().getDate() + ",";
                                fila.createCell(celda).setCellValue(dia);
                                fila.getCell(celda).setCellStyle(cellStyle);
                                dia = " ";
                            }
                            break;
                        case 3:
                            celda = sumadorCeldas;
                            while (!"ABRIL".equals(fila.getSheet().getRow(5).getCell(celda).getStringCellValue())) {
                                celda++;
                            }
                            try {
                                if ("".equals(fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue())) {
                                    dia += fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                } else {
                                    dia = fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                }
                                dia += fechaAsignacion.getFechaAsignacion().getDate() + ",";
                                fila.createCell(celda).setCellValue(dia);
                                fila.getCell(celda).setCellStyle(cellStyle);
                                dia = " ";
                            } catch (Exception e) {
                                fila.createCell(celda);
                                if ("".equals(fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue())) {
                                    dia += fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                } else {
                                    dia = fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                }
                                dia += fechaAsignacion.getFechaAsignacion().getDate() + ",";
                                fila.createCell(celda).setCellValue(dia);
                                fila.getCell(celda).setCellStyle(cellStyle);
                                dia = " ";
                            }
                            break;
                        case 4:
                            celda = sumadorCeldas;
                            while (!"MAYO".equals(fila.getSheet().getRow(5).getCell(celda).getStringCellValue())) {
                                celda++;
                            }
                            try {
                                if ("".equals(fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue())) {
                                    dia += fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                } else {
                                    dia = fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                }
                                dia += fechaAsignacion.getFechaAsignacion().getDate() + ",";
                                fila.createCell(celda).setCellValue(dia);
                                fila.getCell(celda).setCellStyle(cellStyle);
                                dia = " ";
                            } catch (Exception e) {
                                fila.createCell(celda);
                                if ("".equals(fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue())) {
                                    dia += fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                } else {
                                    dia = fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                }
                                dia += fechaAsignacion.getFechaAsignacion().getDate() + ",";
                                fila.createCell(celda).setCellValue(dia);
                                fila.getCell(celda).setCellStyle(cellStyle);
                                dia = " ";
                            }
                            break;
                        case 5:
                            celda = sumadorCeldas;
                            while (!"JUNIO".equals(fila.getSheet().getRow(5).getCell(celda).getStringCellValue())) {
                                celda++;
                            }
                            try {
                                if ("".equals(fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue())) {
                                    dia += fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                } else {
                                    dia = fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                }
                                dia += fechaAsignacion.getFechaAsignacion().getDate() + ",";
                                fila.createCell(celda).setCellValue(dia);
                                fila.getCell(celda).setCellStyle(cellStyle);
                                dia = " ";
                            } catch (Exception e) {
                                fila.createCell(celda);
                                if ("".equals(fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue())) {
                                    dia += fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                } else {
                                    dia = fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                }
                                dia += fechaAsignacion.getFechaAsignacion().getDate() + ",";
                                fila.createCell(celda).setCellValue(dia);
                                fila.getCell(celda).setCellStyle(cellStyle);
                                dia = " ";
                            }
                            break;
                        case 6:
                            celda = sumadorCeldas;
                            while (!"JULIO".equals(fila.getSheet().getRow(5).getCell(celda).getStringCellValue())) {
                                celda++;
                            }
                            try {
                                if ("".equals(fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue())) {
                                    dia += fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                } else {
                                    dia = fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                }
                                dia += fechaAsignacion.getFechaAsignacion().getDate() + ",";
                                fila.createCell(celda).setCellValue(dia);
                                fila.getCell(celda).setCellStyle(cellStyle);
                                dia = " ";
                            } catch (Exception e) {
                                fila.createCell(celda);
                                if ("".equals(fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue())) {
                                    dia += fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                } else {
                                    dia = fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                }
                                dia += fechaAsignacion.getFechaAsignacion().getDate() + ",";
                                fila.createCell(celda).setCellValue(dia);
                                fila.getCell(celda).setCellStyle(cellStyle);
                                dia = " ";
                            }
                            break;
                        case 7:
                            celda = sumadorCeldas;
                            while (!"AGOSTO".equals(fila.getSheet().getRow(5).getCell(celda).getStringCellValue())) {
                                celda++;
                            }
                            try {
                                if ("".equals(fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue())) {
                                    dia += fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                } else {
                                    dia = fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                }
                                dia += fechaAsignacion.getFechaAsignacion().getDate() + ",";
                                fila.createCell(celda).setCellValue(dia);
                                fila.getCell(celda).setCellStyle(cellStyle);
                                dia = " ";
                            } catch (Exception e) {
                                fila.createCell(celda);
                                if ("".equals(fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue())) {
                                    dia += fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                } else {
                                    dia = fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                }
                                dia += fechaAsignacion.getFechaAsignacion().getDate() + ",";
                                fila.createCell(celda).setCellValue(dia);
                                fila.getCell(celda).setCellStyle(cellStyle);
                                dia = " ";
                            }
                            break;
                        case 8:
                            celda = sumadorCeldas;
                            while (!"SEPTIEMBRE".equals(fila.getSheet().getRow(5).getCell(celda).getStringCellValue())) {
                                celda++;
                            }
                            try {
                                if ("".equals(fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue())) {
                                    dia += fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                } else {
                                    dia = fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                }
                                dia += fechaAsignacion.getFechaAsignacion().getDate() + ",";
                                fila.createCell(celda).setCellValue(dia);
                                fila.getCell(celda).setCellStyle(cellStyle);
                                dia = " ";
                            } catch (Exception e) {
                                fila.createCell(celda);
                                if ("".equals(fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue())) {
                                    dia += fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                } else {
                                    dia = fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                }
                                dia += fechaAsignacion.getFechaAsignacion().getDate() + ",";
                                fila.createCell(celda).setCellValue(dia);
                                fila.getCell(celda).setCellStyle(cellStyle);
                                dia = " ";
                            }
                            break;
                        case 9:
                            celda = sumadorCeldas;
                            while (!"OCTUBRE".equals(fila.getSheet().getRow(5).getCell(celda).getStringCellValue())) {
                                celda++;
                            }
                            try {
                                if ("".equals(fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue())) {
                                    dia += fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                } else {
                                    dia = fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                }
                                dia += fechaAsignacion.getFechaAsignacion().getDate() + ",";
                                fila.createCell(celda).setCellValue(dia);
                                fila.getCell(celda).setCellStyle(cellStyle);
                                dia = " ";
                            } catch (Exception e) {
                                fila.createCell(celda);
                                if ("".equals(fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue())) {
                                    dia += fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                } else {
                                    dia = fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                }
                                dia += fechaAsignacion.getFechaAsignacion().getDate() + ",";
                                fila.createCell(celda).setCellValue(dia);
                                fila.getCell(celda).setCellStyle(cellStyle);
                                dia = " ";
                            }
                            break;
                        case 10:
                            celda = sumadorCeldas;
                            while (!"NOVIEMBRE".equals(fila.getSheet().getRow(5).getCell(celda).getStringCellValue())) {
                                celda++;
                            }
                            try {
                                if ("".equals(fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue())) {
                                    dia += fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                } else {
                                    dia = fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                }
                                dia += fechaAsignacion.getFechaAsignacion().getDate() + ",";
                                fila.createCell(celda).setCellValue(dia);
                                fila.getCell(celda).setCellStyle(cellStyle);
                                dia = " ";
                            } catch (Exception e) {
                                fila.createCell(celda);
                                if ("".equals(fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue())) {
                                    dia += fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                } else {
                                    dia = fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                }
                                dia += fechaAsignacion.getFechaAsignacion().getDate() + ",";
                                fila.createCell(celda).setCellValue(dia);
                                fila.getCell(celda).setCellStyle(cellStyle);
                                dia = " ";
                            }
                            break;
                        case 11:
                            celda = sumadorCeldas;
                            while (!"DICIEMBRE".equals(fila.getSheet().getRow(5).getCell(celda).getStringCellValue())) {
                                celda++;
                            }
                            try {
                                if ("".equals(fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue())) {
                                    dia += fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                } else {
                                    dia = fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                }
                                dia += fechaAsignacion.getFechaAsignacion().getDate() + ",";
                                fila.createCell(celda).setCellValue(dia);
                                fila.getCell(celda).setCellStyle(cellStyle);
                                dia = " ";
                            } catch (Exception e) {
                                fila.createCell(celda);
                                if ("".equals(fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue())) {
                                    dia += fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                } else {
                                    dia = fila.getSheet().getRow(filaExcel).getCell(celda).getStringCellValue();
                                }
                                dia += fechaAsignacion.getFechaAsignacion().getDate() + ",";
                                fila.createCell(celda).setCellValue(dia);
                                fila.getCell(celda).setCellStyle(cellStyle);
                                dia = " ";
                            }
                            break;
                        default:
                            break;
                    }
                }
                sumadorCeldas++;
                try {
                    while ("".equals(fila.getSheet().getRow(4).getCell(sumadorCeldas).getStringCellValue())) {
                        sumadorCeldas++;
                    }
                } catch (Exception e) {
                    System.out.println("    " + e.getMessage());
                    break;
                }
                maximoAnio++;
            }
            String diaAux;
            int cendasRellena = 4;
            while (cendasRellena < columna) {
                try {
                    if ("".equals(fila.getSheet().getRow(filaExcel).getCell(cendasRellena).getStringCellValue())) {
                        fila.createCell(cendasRellena);
                        fila.getSheet().getRow(filaExcel).getCell(cendasRellena).setCellStyle(cellStyle);
                    } else {
                        diaAux = fila.getSheet().getRow(filaExcel).getCell(cendasRellena).getStringCellValue();
                        diaAux = diaAux.substring(0, diaAux.length() - 1);
                        fila.getSheet().getRow(filaExcel).getCell(cendasRellena).setCellValue(diaAux);
//                            
                    }
                } catch (Exception e) {
                    fila.createCell(cendasRellena);
                    fila.getSheet().getRow(filaExcel).getCell(cendasRellena).setCellStyle(cellStyle);
                }
                cendasRellena++;
            }
            filaExcel++;
            orden++;
        }
        try {
            OutputStream fileOut = response.getOutputStream();
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

    public List<Date> getListaEntreAños(List<Date> mesesFormado) {
        SimpleDateFormat getYearFormat = new SimpleDateFormat("yyyy");
        List<Date> listaFechas = new ArrayList<Date>();
        listaFechas.add(mesesFormado.get(0));

        for (Date anio : mesesFormado) {
            if (!verificaFecha(anio, listaFechas)) {
                listaFechas.add(anio);
            }
        }
        return listaFechas;
    }

    public boolean verificaFecha(Date fecha, List<Date> mesesFormado) {
        boolean verifica = false;
        SimpleDateFormat getYearFormat = new SimpleDateFormat("yyyy");
        for (Date fechaV : mesesFormado) {
            if (getYearFormat.format(fecha).equals(getYearFormat.format(fechaV))) {
                verifica = true;
                break;
            }
        }
        return verifica;
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
            } else if (periodo.getCantidadEstudiante() < 1) {
                showWarn("Ingrese una cantidad de estudiantes para el paralelo.");
            } else {
                if (maestriaDAO.registrarPeriodo(integracionMaestria, periodo) > 0) {
                    showInfo("Periodo registrado con exito.");
                    PrimeFaces.current().executeScript("PF('dlgPlanificacion').hide()");
                    integracionMaestria = new Maestria();
                    periodo = new Periodo();
                    maestriaBusqueda = new Maestria();
                    listaPeriodo = maestriaDAO.getListaPeriodo();
                    listaMaestria = horarioDAO.getListaMaestriaPeriodo();
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

    public void buscarMaestriaPeriodo() {
        List<Maestria> busquedaM = new ArrayList<>();

        if (maestriaBusqueda.getNombre() == null || "".equals(maestriaBusqueda.getNombre())) {
            listaMaestriaPeriodo = busquedaMaestriaAuxP;
        } else {
            listaMaestriaPeriodo = busquedaMaestriaAuxP;
            for (Maestria busqueda : listaMaestriaPeriodo) {
                if (busqueda.getNombre().toUpperCase().contains(maestriaBusqueda.getNombre().toUpperCase())) {
                    busquedaM.add(busqueda);
                }
            }
            listaMaestriaPeriodo = busquedaM;
            busquedaM = new ArrayList<>();
//            maestriaBusqueda = new Maestria();
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

}
