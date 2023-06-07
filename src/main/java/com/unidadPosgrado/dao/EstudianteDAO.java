/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unidadPosgrado.dao;

import com.global.config.Conexion;
import com.unidadPosgrado.modelo.Estudiante;
import com.unidadPosgrado.modelo.Inscripcion;
import com.unidadPosgrado.modelo.Maestria;
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

    public List<Estudiante> getListaEstudianteMaestria(Maestria maestria) {
        List<Estudiante> listadoEstudiante = new ArrayList<>();
        sentencia = String.format("select * from public.\"getListaEstudiantesMaestria\"(" + maestria.getIdCurso() + ")");
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
                + "	" + estudia.getId_estudiante() + ", \n"
                + "	'" + estudia.getNombre_estudiante() + "', \n"
                + "	'" + estudia.getApellido_estudiante() + "', \n"
                + "	'" + estudia.getTelefono_estudiante() + "', \n"
                + "	'" + estudia.getCedula_estudiante() + "', \n"
                + "	'" + estudia.getSexo() + "', \n"
                + "	'" + estudia.getCorreo_estudiante() + "',\n"
                + "	' Ninguno'\n"
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

    public Estudiante verificarEstudiante(Estudiante estudiante) {
        Estudiante listadoEstudiante = new Estudiante();
        sentencia = String.format("SELECT * from public.verificar_estudiante('" + estudiante.getCedula_estudiante() + "');");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                listadoEstudiante = (new Estudiante(resultSet.getInt("_id_estudiante"), resultSet.getString("_nombre_estudiante"), resultSet.getString("_apellido_estudiante"),
                        resultSet.getString("_telefono_estudiante"), resultSet.getString("_cedula_estudiante"), resultSet.getString("_sexo"), resultSet.getString("_correo_estudiante")));
            }
            return listadoEstudiante;
        } catch (SQLException e) {
            return listadoEstudiante;
        } finally {
            conexion.desconectar();
        }
    }

    public int deleteEstudianteMaestria(int idEstudiante, int idCurso) {
        int mensaje = 0;
        sentencia = String.format("SELECT public.delete_alumno_inscripcion_maestria(" + idCurso + "," + idEstudiante + ");");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                mensaje = Integer.parseInt(resultSet.getString("delete_alumno_inscripcion_maestria"));
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

    public int registraEstudiante(List<Inscripcion> inscripcion, List<Estudiante> listaEstudiantes) {
        int mensaje = 0;
        try {
            String fechaInscripcion = "";
            String descripcion = "";
            int contador = 0;
            int curso = 0;
            float valor = 0f;
            String json = "[";

            for (Inscripcion i : inscripcion) {
                if (i.getIdEstudiante() > 0) {
                    contador++;
                    json += "{\n"
                            + "  \"idCurso\": " + i.getIdCurso() + ",\n"
                            + "  \"idEstudiante\": " + i.getIdEstudiante() + ",\n"
                            + "  \"fecha_inscripcion\":\"" + i.getFecha_inscripcion() + "\",\n"
                            + "  \"descripcion\":\"" + i.getDescripcion() + "\",\n"
                            + "  \"valor\":" + i.getValor() + "\n"
                            + "},";
                }
                fechaInscripcion = i.getFecha_inscripcion().toString();
                descripcion = i.getDescripcion();
                valor = i.getValor();
                curso = i.getIdCurso();
            }
            if (contador > 0) {
                json = json.substring(0, json.length() - 1);
                json += "]";
            } else {
                json = "[]";
            }
            String jsonEstudiantes = "[";
            for (Estudiante e : listaEstudiantes) {
                jsonEstudiantes += "{\n"
                        + "  \"nombreEstudiante\":\"" + e.getNombre_estudiante() + "\",\n"
                        + "  \"apellidoEstudiante\":\"" + e.getApellido_estudiante() + "\",\n"
                        + "  \"telefonoEstudiante\":\"" + e.getTelefono_estudiante() + "\",\n"
                        + "  \"cedulaEstudiante\":\"" + e.getCedula_estudiante() + "\",\n"
                        + "  \"sexo\":\"" + e.getSexo() + "\",\n"
                        + "  \"fecha_inscripcion\":\"" + fechaInscripcion + "\",\n"
                        + "  \"descripcion\":\"" + descripcion + "\",\n"
                        + "  \"valor\":" + valor + ",\n"
                        + "  \"idCurso\": " + curso + ",\n"
                        + "  \"correoEstudiante\":\"" + e.getCorreo_estudiante() + "\"\n"
                        + "},";
            }
            jsonEstudiantes = jsonEstudiantes.substring(0, jsonEstudiantes.length() - 1);
            jsonEstudiantes += "]";
            sentencia = String.format("SELECT public.\"inscripcionMaestriaEstudiantes\"(\n"
                    + "	'" + json + "',\n"
                    + "	'" + jsonEstudiantes + "'\n"
                    + ")");
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                mensaje = Integer.parseInt(resultSet.getString("inscripcionMaestriaEstudiantes"));
            }
            return mensaje;
        } catch (NumberFormatException | SQLException e) {
            return mensaje;
        } finally {
            conexion.desconectar();
        }
    }

    public int editListaEstudianteInscripcion(int idCurso, float valor, List<Estudiante> listaEstudiantes) {
        int mensaje = -1;
        try {
            String jsonEstudiantes = "[";
            for (Estudiante e : listaEstudiantes) {
                jsonEstudiantes += "{\n"
                        + "  \"idEstudiante\": " + e.getId_estudiante() + "\n"
                        + "},";
            }
            jsonEstudiantes = jsonEstudiantes.substring(0, jsonEstudiantes.length() - 1);
            jsonEstudiantes += "]";
            sentencia = String.format("SELECT public.\"editInscripcionMaestriaEstudiante\"('" + jsonEstudiantes + "'," + idCurso + "," + valor + ");");
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                mensaje = Integer.parseInt(resultSet.getString("editInscripcionMaestriaEstudiante"));
            }
            return mensaje;
        } catch (NumberFormatException | SQLException e) {
            return mensaje;
        } finally {
            conexion.desconectar();
        }
    }
}
