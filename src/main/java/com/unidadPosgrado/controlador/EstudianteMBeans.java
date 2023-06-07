/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unidadPosgrado.controlador;

import com.seguridad.modelo.Usuario;
import com.unidadPosgrado.dao.EstudianteDAO;
import com.unidadPosgrado.dao.MaestriaDAO;
import com.unidadPosgrado.modelo.Estudiante;
import com.unidadPosgrado.modelo.Inscripcion;
import com.unidadPosgrado.modelo.Maestria;
import java.io.IOException;
import java.io.InputStream;
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
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author Alex
 */
public class EstudianteMBeans implements Serializable {

    private Estudiante estudiante;
    EstudianteDAO estudianteDAO;
    private List<Estudiante> listaEstudiante;
    private List<Estudiante> listaEstudianteSeleccionado;
    List<Estudiante> busquedaEstudiante;
    List<Estudiante> busquedaEstudianteAux;
    private Maestria integracionMaestria;
    List<Inscripcion> listaInscripcion;
    private Inscripcion inscripcion;
    private List<Maestria> listaMaestria;
    MaestriaDAO maestriaDAO;
    private Maestria maestriaBusqueda;
    List<Maestria> busquedaMaestria;
    List<Maestria> busquedaMaestriaAux;
    Usuario user;
    FacesContext contexto;
    private Estudiante estudianteBusqueda;

    public EstudianteMBeans() {
        estudiante = new Estudiante();
        estudianteDAO = new EstudianteDAO();
        listaEstudiante = new ArrayList<>();
        estudianteBusqueda = new Estudiante();
        listaEstudianteSeleccionado = new ArrayList<>();
        busquedaEstudiante = new ArrayList<>();
        busquedaEstudianteAux = new ArrayList<>();
        integracionMaestria = new Maestria();
        listaInscripcion = new ArrayList<>();
        inscripcion = new Inscripcion();
        inscripcion.setFecha_inscripcion(new Date());
        maestriaDAO = new MaestriaDAO();
        maestriaBusqueda = new Maestria();
        busquedaMaestria = new ArrayList<>();
        busquedaMaestriaAux = new ArrayList<>();
        user = new Usuario();
    }

    @PostConstruct
    public void init() {
        contexto = FacesContext.getCurrentInstance();
        user = (Usuario) contexto.getExternalContext().getSessionMap().get("usuario");
        listaEstudiante = estudianteDAO.getListaEstudiante();
        busquedaEstudianteAux = listaEstudiante;
        listaMaestria = maestriaDAO.getListaMaestriaPeriodoEstudiante(user);
        busquedaMaestriaAux = listaMaestria;
    }

    public void verificarInstanciaEstudiante() {
        String cedula = "";
        if (estudiante.getCedula_estudiante().length() > 8) {
            cedula = estudiante.getCedula_estudiante();
            estudiante = estudianteDAO.verificarEstudiante(estudiante);
            if (cedula.equals(estudiante.getCedula_estudiante())) {
                showWarn(estudiante.getNombre_estudiante() + " ya se encuentra registrado como docente. Si procede con el registro, se deshabilitará como docente y se registrará como estudiante.");
            } else {
                estudiante = new Estudiante();
                estudiante.setCedula_estudiante(cedula);
            }
        }
    }

    public void registrarEstudiante() {
        try {
            if ("".equals(estudiante.getNombre_estudiante().trim())) {
                showWarn("Debe ingresar un nombre.");
            } else if ("".equals(estudiante.getApellido_estudiante().trim())) {
                showWarn("Debe ingresar un apellido.");
            } else if ("".equals(estudiante.getTelefono_estudiante().trim())) {
                showWarn("Debe ingresar un teléfono.");
            } else if ("".equals(estudiante.getCedula_estudiante().trim())) {
                showWarn("Debe ingresar una cédula.");
            } else if ("".equals(estudiante.getSexo().trim())) {
                showWarn("Debe ingresar su género.");
            } else if ("".equals(estudiante.getCorreo_estudiante().trim())) {
                showWarn("Debe ingresar un correo.");
            } else {
                int resultadoRegistro = estudianteDAO.registrarEstudiante(estudiante);
                if (resultadoRegistro > 0) {
                    showInfo(estudiante.getNombre_estudiante().trim().replace(".", ".") + " Registrado exitoso.");
                    listaEstudiante = estudianteDAO.getListaEstudiante();
                    busquedaEstudianteAux = listaEstudiante;
                    PrimeFaces.current().executeScript("PF('dlgEstudiante').hide()");
                } else {
                    showWarn(estudiante.getNombre_estudiante().trim().replace(".", ".") + " ya se encuentra en el sistema.");
                }
                estudiante = new Estudiante();
            }
        } catch (Exception e) {
            showWarn(e.getMessage());
        }
        PrimeFaces.current().executeScript("PF('dlg_loader').hide()");
    }

    public String formatoFecha(Date fecha) {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE dd-MMM-yyyy HH:mm:ss", new Locale("es", "ES"));
        return sdf.format(fecha).toUpperCase();
    }

    public void onRowEdit(RowEditEvent<Estudiante> event) {
        try {
            if ("".equals(event.getObject().getNombre_estudiante().trim())) {
                showWarn("No se puede modificar el registro porque el campo esta vacio.");
            } else if ("".equals(event.getObject().getApellido_estudiante().trim())) {
                showWarn("No se puede modificar el registro porque el campo esta vacio.");
            } else if ("".equals(event.getObject().getCedula_estudiante().trim())) {
                showWarn("No se puede modificar el registro porque el campo esta vacio.");
            } else if ("".equals(event.getObject().getTelefono_estudiante().trim())) {
                showWarn("No se puede modificar el registro porque el campo esta vacio.");
            } else if ("".equals(event.getObject().getSexo().trim())) {
                showWarn("No se puede modificar el registro porque el campo esta vacio.");
            } else if ("".equals(event.getObject().getCorreo_estudiante().trim())) {
                showWarn("No se puede modificar el registro porque el campo esta vacio.");
            } else {
                Estudiante editEstudiante = new Estudiante(event.getObject().getId_estudiante(),
                        event.getObject().getNombre_estudiante(), event.getObject().getApellido_estudiante(),
                        event.getObject().getTelefono_estudiante(), event.getObject().getCedula_estudiante(),
                        event.getObject().getSexo(), event.getObject().getCorreo_estudiante());
                int resultadoRegistro = estudianteDAO.editarEstudiante(editEstudiante);
                if (resultadoRegistro > 0) {
                    showInfo("Se actualizo con éxito, " + editEstudiante.getNombre_estudiante().trim());
                } else {
                    showInfo("Se actualizon los campos.");
                }
            }
            listaEstudiante = new ArrayList<>();
            listaEstudiante = estudianteDAO.getListaEstudiante();
            busquedaEstudianteAux = listaEstudiante;
        } catch (Exception e) {
            showWarn(e.getMessage());
        }
    }

    public void registrarEstudianteMaestria() {
        try {
            if (listaEstudianteSeleccionado.size() < 1) {
                showWarn("Seleccione al menos a un estudiante.");
            } else if (integracionMaestria.getIdMaestria() < 1 || "".equals(integracionMaestria.getNombre())) {
                showWarn("Seleccione una Maestria.");
            } else if (inscripcion.getValor() < 1) {
                showWarn("Ingrese un valor o costo de la maestría.");
            } else {
                for (Estudiante student : listaEstudianteSeleccionado) {
                    listaInscripcion.add(new Inscripcion(integracionMaestria.getIdCurso(),
                            student.getId_estudiante(), inscripcion.getFecha_inscripcion(), integracionMaestria.getNombre(), inscripcion.getValor()));
                }
                if (estudianteDAO.registraEstudiante(listaInscripcion, listaEstudianteSeleccionado) > 0) {
                    showInfo("Registro exitoso");
                    estudianteBusqueda = new Estudiante();
                    listaInscripcion = new ArrayList<>();
                    listaEstudianteSeleccionado = new ArrayList<>();
                    integracionMaestria = new Maestria();
                    inscripcion = new Inscripcion();
                    listaEstudiante = new ArrayList<>();
                    listaEstudiante = estudianteDAO.getListaEstudiante();
                    busquedaEstudianteAux = listaEstudiante;
                    inscripcion.setFecha_inscripcion(new Date());
                    maestriaBusqueda = new Maestria();
                    listaMaestria = maestriaDAO.getListaMaestriaPeriodoEstudiante(user);
                    busquedaMaestriaAux = listaMaestria;
                    PrimeFaces.current().executeScript("PF('dlgInscripciones').hide()");
                } else {
                    showWarn("No se pudo generar el registro");
                }
            }
        } catch (Exception e) {
            showWarn(e.getMessage());
        }
        PrimeFaces.current().executeScript("PF('dlg_loader').hide()");
    }

    public void cancelarRegistro() {
        listaInscripcion = new ArrayList<>();
        listaEstudianteSeleccionado = new ArrayList<>();
        integracionMaestria = new Maestria();
        inscripcion = new Inscripcion();
        listaEstudiante = new ArrayList<>();
        listaEstudiante = estudianteDAO.getListaEstudiante();
        busquedaEstudianteAux = listaEstudiante;
        estudianteBusqueda = new Estudiante();
        inscripcion.setFecha_inscripcion(new Date());
        maestriaBusqueda = new Maestria();
        showWarn("Registro cancelado.");
    }

    public void llenaMaestria(Maestria maestria) {
        integracionMaestria.setIdMaestria(maestria.getIdMaestria());
        integracionMaestria.setNombre(maestria.getNombre());
        integracionMaestria.setDescripcion(maestria.getDescripcion());
        integracionMaestria.setNombreParalelo(maestria.getNombreParalelo());
        integracionMaestria.setIdCurso(maestria.getIdCurso());
    }

    public void onRowCancel(RowEditEvent<Estudiante> event) {
        try {
            showWarn("Editar el nombre " + event.getObject().getNombre_estudiante() + " fue cancelado.");
        } catch (Exception e) {
            showWarn(e.getMessage());
        }
    }

    public void buscarEstudiante() {
        if (estudianteBusqueda.getNombre_estudiante() == null || "".equals(estudianteBusqueda.getNombre_estudiante())) {
            listaEstudiante = busquedaEstudianteAux;
        } else {
            listaEstudiante = busquedaEstudianteAux;
            for (Estudiante busqueda : listaEstudiante) {
                if (busqueda.getNombre_estudiante().toUpperCase().contains(estudianteBusqueda.getNombre_estudiante().toUpperCase())
                        || busqueda.getApellido_estudiante().toUpperCase().contains(estudianteBusqueda.getNombre_estudiante().toUpperCase())) {
                    busquedaEstudiante.add(busqueda);
                }
            }
            listaEstudiante = busquedaEstudiante;
            busquedaEstudiante = new ArrayList<>();
//            estudianteBusqueda = new Estudiante();
        }
    }

    public void addEstudiantes(Estudiante objestudiante) {
        try {
            if (objestudiante.isVerifica()) {
                listaEstudianteSeleccionado.add(objestudiante);
            } else {
                listaEstudianteSeleccionado.remove(objestudiante);
            }

        } catch (Exception e) {
            showWarn("Error" + e.getMessage());
        }

    }

    public void obtenerDatosEstudianteExistente() {
        if (estudiante.getCedula_estudiante().length() > 9) {
            showInfo("Se dio");
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

    public void uploadedFile(FileUploadEvent event) throws IOException {
        // Obtener el archivo cargado
        UploadedFile file = event.getFile();
        InputStream inputStream = file.getInputStream();
        // Crear un objeto Workbook para el archivo de Excel
        Workbook workbook = null;
        if (file.getFileName().endsWith(".xlsx")) {
            workbook = new XSSFWorkbook(inputStream); // Si es un archivo xlsx
        } else if (file.getFileName().endsWith(".xls")) {
            workbook = new HSSFWorkbook(inputStream); // Si es un archivo xls
        }
        // Obtener la hoja de trabajo (worksheet) que se va a leer (en este caso, la primera hoja)
        Sheet sheet = workbook.getSheetAt(0);
        int encabezadoInicio = buscarNombre(sheet);
        if (encabezadoInicio > 0) {
            Row encabezados = sheet.getRow(encabezadoInicio);
            int columnaNombres = -1, columnaApellidos = -1, columnaSexo = -1, columnaEmail = -1, columnaCelular = -1, columnaIdentificacion = -1;
            for (Cell celda : encabezados) {
                String valorCelda = celda.getStringCellValue().trim();
                if (valorCelda.equalsIgnoreCase("NOMBRES")) {
                    columnaNombres = celda.getColumnIndex();
                } else if (valorCelda.equalsIgnoreCase("APELLIDOS")) {
                    columnaApellidos = celda.getColumnIndex();
                } else if (valorCelda.equalsIgnoreCase("SEXO")) {
                    columnaSexo = celda.getColumnIndex();
                } else if (valorCelda.equalsIgnoreCase("EMAILINST")) {
                    columnaEmail = celda.getColumnIndex();
                } else if (valorCelda.equalsIgnoreCase("CELULAR")) {
                    columnaCelular = celda.getColumnIndex();
                } else if (valorCelda.equalsIgnoreCase("IDENTIFICACIÓN")) {
                    columnaIdentificacion = celda.getColumnIndex();
                }
            }
            if (columnaNombres > 0) {
                for (int i = 4; i <= sheet.getLastRowNum(); i++) {
                    Row fila = sheet.getRow(i);
                    // Leer las celdas específicas de la fila
                    String nombres = fila.getCell(columnaNombres).getStringCellValue();
                    String apellidos = fila.getCell(columnaApellidos).getStringCellValue();
                    String sexo = fila.getCell(columnaSexo).getStringCellValue();
                    String email = fila.getCell(columnaEmail).getStringCellValue();
                    String celular = fila.getCell(columnaCelular).getStringCellValue();
                    String identificacion = fila.getCell(columnaIdentificacion).getStringCellValue();
                    listaEstudianteSeleccionado.add(new Estudiante(nombres, apellidos, celular,
                            identificacion, sexo, email));
                }
                showInfo("Se han cargado los datos del archivo correctamente.");
            } else {
                showWarn("Imposible leer el archivo, por favor intente con otro archivo.");
            }

        } else {
            showWarn("Imposible leer el archivo, por favor intente con otro archivo.");
        }

        // Cerrar el archivo y el objeto Workbook
        inputStream.close();
        workbook.close();
    }

    public int buscarNombre(Sheet sheet) {
        try {
            for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                Row fila = sheet.getRow(i);
                if (fila != null) { // Verificar si la fila no es nula
                    for (Cell celda : fila) {
                        if (celda != null && celda.getCellType() == CellType.STRING) { // Verificar si la celda no es nula y es de tipo String
                            String valorCelda = celda.getStringCellValue().trim();
                            if (valorCelda.equalsIgnoreCase("PERIODO") || valorCelda.equalsIgnoreCase("NOMBRES")) {
                                return i;
                            }
                        }
                    }
                }
            }
            return -1;
        } catch (Exception e) {
            return -1;
        }
    }

    public void redireccionDetalle() throws IOException {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        externalContext.redirect("detalleInscripcionMaestria.xhtml");
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

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public EstudianteDAO getEstudianteDAO() {
        return estudianteDAO;
    }

    public void setEstudianteDAO(EstudianteDAO estudianteDAO) {
        this.estudianteDAO = estudianteDAO;
    }

    public List<Estudiante> getListaEstudiante() {
        return listaEstudiante;
    }

    public Estudiante getEstudianteBusqueda() {
        return estudianteBusqueda;
    }

    public void setEstudianteBusqueda(Estudiante estudianteBusqueda) {
        this.estudianteBusqueda = estudianteBusqueda;
    }

    public void setListaEstudiante(List<Estudiante> listaEstudiante) {
        this.listaEstudiante = listaEstudiante;
    }

    public List<Estudiante> getListaEstudianteSeleccionado() {
        return listaEstudianteSeleccionado;
    }

    public void setListaEstudianteSeleccionado(List<Estudiante> listaEstudianteSeleccionado) {
        this.listaEstudianteSeleccionado = listaEstudianteSeleccionado;
    }

    public Maestria getIntegracionMaestria() {
        return integracionMaestria;
    }

    public void setIntegracionMaestria(Maestria integracionMaestria) {
        this.integracionMaestria = integracionMaestria;
    }

    public Inscripcion getInscripcion() {
        return inscripcion;
    }

    public void setInscripcion(Inscripcion inscripcion) {
        this.inscripcion = inscripcion;
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

    public void saludo() {
        System.out.println("Holas");
    }
//    public List<String> completeText(String query) {
//        String queryLowerCase = query.toLowerCase();
//        List<String> estudianteList = new ArrayList<>();
//        for (Estudiante student : listaEstudiante) {
//            estudianteList.add(student.getNombre_estudiante());
//        }
//        
//        return estudianteList.stream().filter(t -> t.toLowerCase().startsWith(queryLowerCase)).collect(Collectors.toList());
//    }

}
