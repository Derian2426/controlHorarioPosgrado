/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unidadPosgrado.dao;

import com.global.config.Conexion;
import com.unidadPosgrado.modelo.Docente;
import com.unidadPosgrado.modelo.Horario;
import com.unidadPosgrado.modelo.Maestria;
import com.unidadPosgrado.modelo.Modulo;
import com.unidadPosgrado.modelo.Periodo;
import com.unidadPosgrado.modelo.TiempoModulo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author HP
 */
public class HorarioDAO {

    Conexion conexion;
    String sentencia;
    ResultSet resultSet;

    public HorarioDAO() {
        conexion = new Conexion();
    }

    public List<Docente> getListaDocente(int idMaestria, int idCurso) {
        List<Docente> listadoDocente = new ArrayList<>();
        sentencia = String.format("SELECT * from public.\"getlistadodocentexmaestriaM\"(\n"
                + "	" + idMaestria + ", \n"
                + "	" + idCurso + "\n"
                + ")");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                listadoDocente.add(new Docente(resultSet.getInt("_id_docente"), resultSet.getString("_nombre_docente") + " " + resultSet.getString("_apellido_docente") + "-" + resultSet.getString("_cedula_docente"), resultSet.getString("_apellido_docente"),
                        resultSet.getString("_cedula_docente"), resultSet.getString("_telefono_docente"), resultSet.getString("_correo_docente"), resultSet.getBoolean("_estado")));
            }
            return listadoDocente;
        } catch (SQLException e) {
            return listadoDocente;
        } finally {
            conexion.desconectar();
        }
    }

    public List<Modulo> getListaModulo(int idMaestria, int idCurso) {
        List<Modulo> listadoModulo = new ArrayList<>();
        sentencia = String.format("SELECT * from public.\"listaModuloxMaestria\"(" + idMaestria + "," + idCurso + ");");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                listadoModulo.add(new Modulo(resultSet.getInt("_id_materia"), resultSet.getString("_nombre_materia"), resultSet.getString("_descripcion"), resultSet.getFloat("_hora")));
            }
            return listadoModulo;
        } catch (SQLException e) {
            return listadoModulo;
        } finally {
            conexion.desconectar();
        }
    }

    public int registrarHorarioAsignacion(Modulo modulo, Docente docente, Maestria maestria, TiempoModulo tiempoModulo, List<TiempoModulo> tiempo) {
        int mensaje = 0;
        String json = "[";
        for (TiempoModulo moduloT : tiempo) {
            json += "{\n"
                    + "  \"fechainicio\": \"" + moduloT.getFechaAsignacion() + "\"\n"
                    + "},";
        }
        json = json.substring(0, json.length() - 1);
        json += "]";
        sentencia = String.format("SELECT public.\"asignacionHorarioPrueba001\"(\n"
                + "	" + modulo.getIdMateria() + ", \n"
                + "	" + docente.getId_docente() + ", \n"
                + "	" + maestria.getIdCurso() + ", \n"
                + "	'" + tiempoModulo.getDescripcion() + "', \n"
                + "	" + tiempo.size() + ", \n"
                + "	'" + json + "'\n"
                + ")");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                mensaje = Integer.parseInt(resultSet.getString("asignacionHorarioPrueba001"));
            }
            return mensaje;
        } catch (SQLException e) {
            return mensaje;
        } finally {
            conexion.desconectar();
        }
    }

    public List<TiempoModulo> registrarHorarioAsignaciones(Modulo modulo, Docente docente, Maestria maestria, TiempoModulo tiempoModulo, List<Date> tiempo) {
        int mensaje = 0;
        List<TiempoModulo> listadoFecha = new ArrayList<>();
        String json = "[";
        for (Date moduloT : tiempo) {
            json += "{\n"
                    + "  \"fechainicio\": \"" + moduloT + "\"\n"
                    + "},";
        }
        json = json.substring(0, json.length() - 1);
        json += "]";
        sentencia = String.format("SELECT * from public.\"asignacionHorarioPrueba002\"(\n"
                + "	" + modulo.getIdMateria() + ", \n"
                + "	" + docente.getId_docente() + ", \n"
                + "	" + maestria.getIdCurso() + ", \n"
                + "	'" + tiempoModulo.getDescripcion() + "', \n"
                + "	" + tiempo.size() + ", \n"
                + "	'" + json + "'\n"
                + ")");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                listadoFecha.add(new TiempoModulo(resultSet.getInt("validacion"), resultSet.getDate("_fecharetorno")));
            }
            return listadoFecha;
        } catch (SQLException e) {
            return listadoFecha;
        } finally {
            conexion.desconectar();
        }
    }

    public List<TiempoModulo> getListaValidacion(int idDocente, Date fechaInicio, Date fechaFin) {
        List<TiempoModulo> listadoFecha = new ArrayList<>();
        sentencia = String.format("SELECT public.\"validacionFechaHorario\"(" + idDocente + ", '" + fechaInicio + "', '" + fechaFin + "');");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                listadoFecha.add(new TiempoModulo(resultSet.getDate("validacionFechaHorario")));
            }
            return listadoFecha;
        } catch (SQLException e) {
            return listadoFecha;
        } finally {
            conexion.desconectar();
        }
    }

    public List<Maestria> getListaHorario(int idCurso) {
        List<Maestria> listadoMaestria = new ArrayList<>();
        sentencia = String.format("SELECT * from public.\"getListHorario\"(\n"
                + "	" + idCurso + "\n"
                + ")");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                listadoMaestria.add(new Maestria(resultSet.getInt("_id_curso"), resultSet.getInt("_id_materia"),
                        resultSet.getString("_nombre_materia"), resultSet.getString("_nombre_curso"), resultSet.getString("_descripcion"),
                        resultSet.getDate("_fecha"), resultSet.getInt("_id_docente"), resultSet.getString("_nombre_docente") + " " + resultSet.getString("_apellido_docente")));
            }
            return listadoMaestria;
        } catch (SQLException e) {
            return listadoMaestria;
        } finally {
            conexion.desconectar();
        }
    }

    public List<Maestria> getListaMaestriaPeriodo() {
        String estado = "";
        List<Maestria> listadoMaestria = new ArrayList<>();
        sentencia = String.format("SELECT * from public.\"getListaHorariosMaestrias\"()");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                if (null != resultSet.getString("_estado")) {
                    switch (resultSet.getString("_estado")) {
                        case "N":
                            estado = "No Planificado";
                            break;
                        case "S":
                            estado = "Planificado";
                            break;
                        case "P":
                            estado = "En proceso";
                            break;
                        default:
                            estado = "No Planificado";
                            break;
                    }
                }
                listadoMaestria.add(new Maestria(resultSet.getInt("_id_curso"), resultSet.getInt("_id_maestria"), resultSet.getString("_nombre_maestria"),
                        resultSet.getDate("_fecha_inicio"), resultSet.getDate("_fecha_fin"), estado));
            }
            return listadoMaestria;
        } catch (SQLException e) {
            return listadoMaestria;
        } finally {
            conexion.desconectar();
        }
    }

    public void registrarMaestria(int idCurso) {
        sentencia = String.format("SELECT public.\"actualizarEstado\"(" + idCurso + ")");
        resultSet = conexion.ejecutarSql(sentencia);
        conexion.desconectar();
    }

    public void actualizarMaestria(int idCurso) {
        sentencia = String.format("SELECT public.\"actualizarEstadoP\"(" + idCurso + ")");
        resultSet = conexion.ejecutarSql(sentencia);
        conexion.desconectar();
    }

    public List<Horario> getListaModuloDocente(int idCurso, int id_maestria) {
        List<Horario> listadoModulo = new ArrayList<>();
        sentencia = String.format("SELECT * from public.\"getListaModuloxPeriodoMaestria\"(" + idCurso + "," + id_maestria + ");");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                listadoModulo.add(new Horario(resultSet.getInt("_id_periodo"), resultSet.getInt("_id_curso"),
                        resultSet.getInt("_id_docente"), resultSet.getString("_nombre_materia"),
                        resultSet.getString("_nombre_docente"), resultSet.getFloat("_hora"), resultSet.getString("_paralelo")));
            }
            return listadoModulo;
        } catch (SQLException e) {
            return listadoModulo;
        } finally {
            conexion.desconectar();
        }
    }

    public List<Horario> getListaAsignacionDocente(int idCurso, int idDocente, Date anio) {
        List<Horario> listadoModulo = new ArrayList<>();
        sentencia = String.format("SELECT* from public.fecha_asignacion_docente(" + idCurso + ", " + idDocente + ", '" + anio + "');");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                listadoModulo.add(new Horario(resultSet.getDate("_fecha")));
            }
            return listadoModulo;
        } catch (SQLException e) {
            return listadoModulo;
        } finally {
            conexion.desconectar();
        }
    }

    public List<Horario> getListaDetalleAsignacionDocente(int idCurso, int idDocente) {
        List<Horario> listadoModulo = new ArrayList<>();
        sentencia = String.format("SELECT* from public.fecha_detalle_asignacion_docente(" + idCurso + ", " + idDocente + ");");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                listadoModulo.add(new Horario(resultSet.getDate("_fecha")));
            }
            return listadoModulo;
        } catch (SQLException e) {
            return listadoModulo;
        } finally {
            conexion.desconectar();
        }
    }

    public Periodo getDescrionPeriodo(int idPeriodo) {
        Periodo periodo = new Periodo();
        sentencia = String.format("SELECT * from public.get_detalle_periodo(" + idPeriodo + ");");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                periodo = new Periodo(resultSet.getString("_nombre_maestria"), resultSet.getString("_descripcion"), resultSet.getDate("_fecha_inicio"), resultSet.getDate("_fecha_fin"),
                        resultSet.getInt("_cantidad_estudiante"), resultSet.getString("_estado"), resultSet.getString("_nombre_periodo"));
            }
            return periodo;
        } catch (SQLException e) {
            return periodo;
        } finally {
            conexion.desconectar();
        }
    }

}
