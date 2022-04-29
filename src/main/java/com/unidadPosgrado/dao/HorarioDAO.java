/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unidadPosgrado.dao;

import com.global.config.Conexion;
import com.unidadPosgrado.modelo.Docente;
import com.unidadPosgrado.modelo.Maestria;
import com.unidadPosgrado.modelo.Modulo;
import com.unidadPosgrado.modelo.TiempoModulo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
                listadoDocente.add(new Docente(resultSet.getInt("_id_docente"), resultSet.getString("_nombre_docente"), resultSet.getString("_apellido_docente"),
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

    public List<TiempoModulo> registrarHorarioAsignaciones(Modulo modulo, Docente docente, Maestria maestria, TiempoModulo tiempoModulo, List<TiempoModulo> tiempo) {
        int mensaje = 0;
        List<TiempoModulo> listadoFecha = new ArrayList<>();
        String json = "[";
        for (TiempoModulo moduloT : tiempo) {
            json += "{\n"
                    + "  \"fechainicio\": \"" + moduloT.getFechaAsignacion() + "\"\n"
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
                listadoFecha.add(new TiempoModulo(resultSet.getInt("validacion"),resultSet.getDate("_fecharetorno")));
            }
            return listadoFecha;
        } catch (SQLException e) {
            return listadoFecha;
        } finally {
            conexion.desconectar();
        }
    }

    public List<TiempoModulo> getListaValidacion(int idDocente) {
        List<TiempoModulo> listadoFecha = new ArrayList<>();
        sentencia = String.format("SELECT public.\"validacionFechaHorario\"(" + idDocente + ");");
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

}
