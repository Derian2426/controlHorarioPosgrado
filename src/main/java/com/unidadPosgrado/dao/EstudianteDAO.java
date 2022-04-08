/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unidadPosgrado.dao;

import com.global.config.Conexion;
import com.unidadPosgrado.modelo.Estudiante;
import com.unidadPosgrado.modelo.Inscripcion;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alex
 */
public class EstudianteDAO {

    Conexion conexion;
    String sentencia;
    ResultSet resultSet;

    public EstudianteDAO() {
        conexion = new Conexion();
    }

    public List<Estudiante> getListaEstudiante() {
        List<Estudiante> listadoEstudiante = new ArrayList<>();
        sentencia = String.format("select * from public.\"getListaEstudiantes\"()");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                listadoEstudiante.add(new Estudiante(resultSet.getInt("_id_estudiante"), resultSet.getString("_nombre_estudiante"), resultSet.getString("_apellido_estudiante"),
                        resultSet.getString("_telefono_estudiante"), resultSet.getString("_cedula_estudiante"), resultSet.getString("_sexo"), resultSet.getString("_correo_estudiante")));
            }
            return listadoEstudiante;
        } catch (SQLException e) {
            return listadoEstudiante;
        } finally {
            conexion.desconectar();
        }
    }

    public int registrarEstudiante(Estudiante estudia) {
        int mensaje = 0;
        sentencia = String.format("SELECT public.\"registrarEstudiante\"(\n"
                + "	'" + estudia.getNombre_estudiante() + "', \n"
                + "	'" + estudia.getApellido_estudiante() + "', \n"
                + "	'" + estudia.getTelefono_estudiante() + "', \n"
                + "	'" + estudia.getCedula_estudiante() + "', \n"
                + "	'" + estudia.getSexo() + "', \n"
                + "	'" + estudia.getCorreo_estudiante() + "'\n"
                + ")");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                mensaje = Integer.parseInt(resultSet.getString("registrarEstudiante"));
            }
            return mensaje;
        } catch (SQLException e) {
            return mensaje;
        } finally {
            conexion.desconectar();
        }
    }

    public int editarEstudiante(Estudiante estudia) {
        int mensaje = 0;
        sentencia = String.format("SELECT public.\"actualizarEstudiante\"(\n"
                + "	" + estudia.getId_estudiante() + ", \n"
                + "	'" + estudia.getNombre_estudiante() + "', \n"
                + "	'" + estudia.getApellido_estudiante() + "', \n"
                + "	'" + estudia.getTelefono_estudiante() + "', \n"
                + "	'" + estudia.getCedula_estudiante() + "', \n"
                + "	'" + estudia.getSexo() + "', \n"
                + "	'" + estudia.getCorreo_estudiante() + "'\n"
                + ")");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                mensaje = Integer.parseInt(resultSet.getString("actualizarEstudiante"));
            }
            return mensaje;
        } catch (SQLException e) {
            return mensaje;
        } finally {
            conexion.desconectar();
        }
    }

    public int registraEstudiante(List<Inscripcion> inscripcion) {
        int mensaje = 0;
        try {
            String json = "[";
            for (Inscripcion i : inscripcion) {
                json += "{\n"
                        + "  \"idMaestria\": " + i.getIdMaestria() + ",\n"
                        + "  \"idEstudiante\": " + i.getIdEstudiante() + ",\n"
                        + "  \"fecha_inscripcion\":\"" + i.getFecha_inscripcion() + "\",\n"
                        + "  \"descripcion\":\"" + i.getDescripcion() + "\",\n"
                        + "  \"valor\":" + i.getValor() + "\n"
                        + "},";
            }
            json = json.substring(0, json.length() - 1);
            json += "]";
            sentencia = String.format("SELECT public.\"inscripcionMestria\"(\n"
                    + "	'" + json + "'\n"
                    + ")");
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                mensaje = Integer.parseInt(resultSet.getString("inscripcionMestria"));
            }
            return mensaje;
        } catch (NumberFormatException | SQLException e) {
            return mensaje;
        }

    }
}
