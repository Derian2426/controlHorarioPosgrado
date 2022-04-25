/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unidadPosgrado.dao;

import com.global.config.Conexion;
import com.unidadPosgrado.modelo.Maestria;
import com.unidadPosgrado.modelo.Modulo;
import com.unidadPosgrado.modelo.Periodo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HP
 */
public class MaestriaDAO {

    Conexion conexion;
    String sentencia;
    ResultSet resultSet;

    public MaestriaDAO() {
        conexion = new Conexion();
    }

    public List<Maestria> getListaMaestria() {
        List<Maestria> listadoMaestria = new ArrayList<>();
        sentencia = String.format("select * from public.\"getListaMaestrias\"()");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                listadoMaestria.add(new Maestria(resultSet.getInt("_id_maestria"), resultSet.getString("_nombre_maestria"), resultSet.getString("_descripcion")));
            }
            return listadoMaestria;
        } catch (SQLException e) {
            return listadoMaestria;
        } finally {
            conexion.desconectar();
        }
    }

    public List<Maestria> getListaMaestriaPeriodo() {
        List<Maestria> listadoMaestria = new ArrayList<>();
        sentencia = String.format("SELECT* from public.\"getListaMaestriasPeriodo\"()");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                listadoMaestria.add(new Maestria(resultSet.getInt("_id_maestria"), resultSet.getString("_nombre_maestria"), resultSet.getString("_nombre_periodo"), resultSet.getDate("_fecha_inicio"), resultSet.getDate("_fecha_fin"),
                        resultSet.getInt("_id_curso"), resultSet.getString("_nombre_curso")));
            }
            return listadoMaestria;
        } catch (SQLException e) {
            return listadoMaestria;
        } finally {
            conexion.desconectar();
        }
    }

    public List<Maestria> getListaMaestriaxModulo() {
        List<Maestria> listadoMaestria = new ArrayList<>();
        sentencia = String.format("SELECT * from public.\"tiempoMaestria\"()");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                listadoMaestria.add(new Maestria(resultSet.getInt("_id_maestria"), resultSet.getString("_nombre_maestria"), resultSet.getString("_descripcion"), resultSet.getFloat("_hora")));
            }
            return listadoMaestria;
        } catch (SQLException e) {
            return listadoMaestria;
        } finally {
            conexion.desconectar();
        }
    }

    public int registrarMaestria(Maestria maestria) {
        int mensaje = 0;
        sentencia = String.format("SELECT public.\"registrarMaestria\"(\n"
                + "	'" + maestria.getNombre().trim() + "', \n"
                + "	'" + maestria.getDescripcion().trim() + "'\n"
                + ")");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                mensaje = Integer.parseInt(resultSet.getString("registrarMaestria"));
            }
            return mensaje;
        } catch (SQLException e) {
            return mensaje;
        } finally {
            conexion.desconectar();
        }
    }

    public int editarMaestria(Maestria maestria) {
        int mensaje = 0;
        sentencia = String.format("SELECT public.\"actualizarMaestria\"(\n"
                + "	" + maestria.getIdMaestria() + ", \n"
                + "	'" + maestria.getNombre() + "', \n"
                + "	'" + maestria.getDescripcion() + "'\n"
                + ")");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                mensaje = Integer.parseInt(resultSet.getString("actualizarMaestria"));
            }
            return mensaje;
        } catch (SQLException e) {
            return mensaje;
        } finally {
            conexion.desconectar();
        }
    }

    public int registrarIntegracionModulo(Maestria integracionMaestria, List<Modulo> listaModulos) {
        int mensaje = 0;
        String consulta;
        try {
            sentencia = "[";
            for (Modulo modulo : listaModulos) {
                sentencia += "{\n"
                        + "  \"idMatera\": " + modulo.getIdMateria() + ",\n"
                        + "  \"idMaestria\": " + integracionMaestria.getIdMaestria() + "\n"
                        + "},";
            }
            sentencia = sentencia.substring(0, sentencia.length() - 1);
            sentencia += "]";
            consulta = "SELECT public.\"registrarIntegracion\"(\n"
                    + "	'" + sentencia + "'\n"
                    + ")";
            resultSet = conexion.ejecutarSql(consulta);
            while (resultSet.next()) {
                mensaje = Integer.parseInt(resultSet.getString("registrarIntegracion"));
            }
            return mensaje;
        } catch (NumberFormatException | SQLException e) {
            return mensaje;
        } finally {
            conexion.desconectar();
        }
    }

    public List<Modulo> getListaModulo(int idModulo) {
        List<Modulo> listadoModulo = new ArrayList<>();
        sentencia = String.format("SELECT * from public.\"listaModuloxMaestria\"(\n"
                + "	" + idModulo + "\n"
                + ")");
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

    public int registrarPeriodo(Maestria maestria, Periodo periodo) {
        int mensaje = 0;
        sentencia = String.format("SELECT public.\"registrarPeriodo\"(\n"
                + "	" + maestria.getIdMaestria() + ", \n"
                + "	'" + periodo.getNombrePeriodo() + "', \n"
                + "	'" + periodo.getFechaInicio() + "', \n"
                + "	'" + periodo.getFechaFin() + "', \n"
                + "	'" + "Paralelo " + periodo.getNombreParalelo() + "', \n"
                + "	" + periodo.getCantidadEstudiante() + "\n"
                + ")");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                mensaje = Integer.parseInt(resultSet.getString("registrarPeriodo"));
            }
            return mensaje;
        } catch (SQLException e) {
            return mensaje;
        } finally {
            conexion.desconectar();
        }
    }

}
