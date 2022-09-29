/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unidadPosgrado.controlador;

import com.unidadPosgrado.dao.HorarioDAO;
import com.unidadPosgrado.modelo.Horario;
import com.unidadPosgrado.modelo.Maestria;
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
    private List<Horario> listadoAsignaciones;
    private ExcelOptions excelOpt;

    public GeneragorHorarioMBeans() {
        horarioDAO = new HorarioDAO();
        listaMaestria = new ArrayList<>();
        listadoModulo = new ArrayList<>();
        excelOpt = new ExcelOptions();
        listadoAsignaciones = new ArrayList<>();
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

    public List<Horario> getListadoAsignaciones() {
        return listadoAsignaciones;
    }

    public void setListadoAsignaciones(List<Horario> listadoAsignaciones) {
        this.listadoAsignaciones = listadoAsignaciones;
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
        fila.createCell(1).setCellValue(fechaInicio.getYear());

        fila = hojaNueva.createRow(5);
        fila.createCell(0).setCellValue("ORDEN");
        fila.getCell(0).getCellStyle().setWrapText(true);
        fila.createCell(1).setCellValue("MODULOS");
        fila.getCell(1).getCellStyle().setWrapText(true);
        fila.createCell(2).setCellValue("DOCENTES");
        fila.getCell(2).getCellStyle().setWrapText(true);
        fila.createCell(3).setCellValue("HORAS");
        fila.getCell(3).getCellStyle().setWrapText(true);
        mesesFormado = getListaEntreFechas(fechaInicio, fechaFin);
        anioFormato = getListaEntreAños(fechaInicio, fechaFin);
        int columna = 4;
        int contador = 0;
        for (Date mes : mesesFormado) {
            int mesI = mes.getMonth();
            switch (mesI) {
                case 0:
                    fila.createCell(columna).setCellValue("ENERO");
                    fila.getCell(columna).getCellStyle().setWrapText(true);
                    break;
                case 1:
                    fila.createCell(columna).setCellValue("FEBRERO");
                    fila.getCell(columna).getCellStyle().setWrapText(true);
                    break;
                case 2:
                    fila.createCell(columna).setCellValue("MARZO");
                    fila.getCell(columna).getCellStyle().setWrapText(true);
                    break;
                case 3:
                    fila.createCell(columna).setCellValue("ABRIL");
                    fila.getCell(columna).getCellStyle().setWrapText(true);
                    break;
                case 4:
                    fila.createCell(columna).setCellValue("MAYO");
                    fila.getCell(columna).getCellStyle().setWrapText(true);
                    break;
                case 5:
                    fila.createCell(columna).setCellValue("JUNIO");
                    fila.getCell(columna).getCellStyle().setWrapText(true);
                    break;
                case 6:
                    fila.createCell(columna).setCellValue("JULIO");
                    fila.getCell(columna).getCellStyle().setWrapText(true);
                    break;
                case 7:
                    fila.createCell(columna).setCellValue("AGOSTO");
                    fila.getCell(columna).getCellStyle().setWrapText(true);
                    break;
                case 8:
                    fila.createCell(columna).setCellValue("SEPTIEMBRE");
                    fila.getCell(columna).getCellStyle().setWrapText(true);
                    break;
                case 9:
                    fila.createCell(columna).setCellValue("OCTUBRE");
                    fila.getCell(columna).getCellStyle().setWrapText(true);
                    break;
                case 10:
                    fila.createCell(columna).setCellValue("NOVIEMBRE");
                    fila.getCell(columna).getCellStyle().setWrapText(true);
                    break;
                case 11:
                    fila.createCell(columna).setCellValue("DICIEMBRE");
                    fila.getCell(columna).getCellStyle().setWrapText(true);
//                    contador++;
//                    if (anioFormato.size() > 1) {
//                        if (contador > 1) {
//                            fila.createCell(contador).setCellValue(fechaFin.getYear());
//                        }
//                    }
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
            fila.getCell(0).getCellStyle().setWrapText(true);
            fila.createCell(1).setCellValue(horario.getNombreModulo());
            fila.getCell(1).getCellStyle().setWrapText(true);
            fila.createCell(2).setCellValue(horario.getNombreDocente());
            fila.getCell(2).getCellStyle().setWrapText(true);
            fila.createCell(3).setCellValue(horario.getHora());
            fila.getCell(3).getCellStyle().setWrapText(true);
            listadoAsignaciones = horarioDAO.getListaAsignacionDocente(idCurso, horario.getIdDocente());
            String dia = "Ases ";
            int celda;
            for (Horario fechaAsignacion : listadoAsignaciones) {
                switch (fechaAsignacion.getFechaAsignacion().getMonth()) {
                    case 0:
                        celda = 4;
                        while (!"ENERO".equals(fila.getSheet().getRow(5).getCell(celda).getStringCellValue())) {
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
                            fila.getCell(celda).getCellStyle().setWrapText(true);
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
                            fila.getCell(celda).getCellStyle().setWrapText(true);
                            fila.getCell(celda).getCellStyle().setWrapText(true);
                            dia = " ";
                        }
                        break;
                    case 1:
                        celda = 4;
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
                            fila.getCell(celda).getCellStyle().setWrapText(true);
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
                            fila.getCell(celda).getCellStyle().setWrapText(true);
                            dia = " ";
                        }
                        break;
                    case 2:
                        celda = 4;
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
                            fila.getCell(celda).getCellStyle().setWrapText(true);
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
                            fila.getCell(celda).getCellStyle().setWrapText(true);
                            dia = " ";
                        }
                        break;
                    case 3:
                        celda = 4;
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
                            fila.getCell(celda).getCellStyle().setWrapText(true);
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
                            fila.getCell(celda).getCellStyle().setWrapText(true);
                            dia = " ";
                        }
                        break;
                    case 4:
                        celda = 4;
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
                            fila.getCell(celda).getCellStyle().setWrapText(true);
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
                            fila.getCell(celda).getCellStyle().setWrapText(true);
                            dia = " ";
                        }
                        break;
                    case 5:
                        celda = 4;
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
                            fila.getCell(celda).getCellStyle().setWrapText(true);
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
                            fila.getCell(celda).getCellStyle().setWrapText(true);
                            dia = " ";
                        }
                        break;
                    case 6:
                        celda = 4;
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
                            fila.getCell(celda).getCellStyle().setWrapText(true);
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
                            fila.getCell(celda).getCellStyle().setWrapText(true);
                            dia = " ";
                        }
                        break;
                    case 7:
                        celda = 4;
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
                            fila.getCell(celda).getCellStyle().setWrapText(true);
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
                            fila.getCell(celda).getCellStyle().setWrapText(true);
                            dia = " ";
                        }
                        break;
                    case 8:
                        celda = 4;
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
                            fila.getCell(celda).getCellStyle().setWrapText(true);
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
                            fila.getCell(celda).getCellStyle().setWrapText(true);
                            dia = " ";
                        }
                        break;
                    case 9:
                        celda = 4;
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
                            fila.getCell(celda).getCellStyle().setWrapText(true);
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
                            fila.getCell(celda).getCellStyle().setWrapText(true);
                            dia = " ";
                        }
                        break;
                    case 10:
                        celda = 4;
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
                            fila.getCell(celda).getCellStyle().setWrapText(true);
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
                            fila.getCell(celda).getCellStyle().setWrapText(true);
                            dia = " ";
                        }
                        break;
                    case 11:
                        celda = 4;
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
                            fila.getCell(celda).getCellStyle().setWrapText(true);
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
                            fila.getCell(celda).getCellStyle().setWrapText(true);
                            dia = " ";
                        }
                        break;
                    default:
                        break;
                }
            }
            filaExcel++;
            orden++;
        }
        filaExcel = 0;
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
