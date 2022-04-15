/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unidadPosgrado.dao;

import com.global.config.Conexion;
import com.unidadPosgrado.modelo.Docente;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alex
 */
public class DocenteDAO {
    Conexion conexion;
    String sentencia;
    ResultSet resultSet;
    
    public DocenteDAO(){
        conexion = new Conexion();
    }
    
    public List<Docente> getListaDocente() {
        List<Docente> listadoDocente = new ArrayList<>();
        sentencia = String.format("select * from public.\"getListaDocentes\"()");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                listadoDocente.add(new Docente(resultSet.getInt("_id_docente"), resultSet.getString("_nombre_docente"), resultSet.getString("_apellido_docente"),
                        resultSet.getString("_cedula_docente"), resultSet.getString("_telefono_docente"), resultSet.getString("_correo_docente"), resultSet.getBoolean("_estado")));
            }
            return listadoDocente;
        } catch (Exception e) {
            return listadoDocente;
        } finally {
            conexion.desconectar();
        }
    }
    
    public int registrarDocente(Docente docent) {
        int mensaje = 0;
        sentencia = String.format("SELECT public.\"registrarDocente\"(\n"
                + "	'" + docent.getNombre_docente()+ "', \n"
                + "	'" + docent.getApellido_docente()+ "', \n"
                + "	'" + docent.getCedula_docente()+ "', \n"
                + "	'" + docent.getTelefono_docente()+ "', \n"
                + "	'" + docent.getCorreo_docente()+ "'\n"
                + ")");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                mensaje = Integer.parseInt(resultSet.getString("registrarDocente"));
            }
            return mensaje;
        } catch (SQLException e) {
            return mensaje;
        } finally {
            conexion.desconectar();
        }
    }
    public int editarDocente(Docente docent) {
        int mensaje = 0;
        sentencia = String.format("SELECT public.\"actualizarDocente\"(\n"
                + "	" + docent.getId_docente()+ ", \n"
                + "	'" + docent.getNombre_docente()+ "', \n"
                + "	'" + docent.getApellido_docente()+ "', \n"
                + "	'" + docent.getCedula_docente()+ "', \n"
                + "	'" + docent.getTelefono_docente()+ "', \n"
                + "	'" + docent.getCorreo_docente()+ "'\n"
                + ")");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                mensaje = Integer.parseInt(resultSet.getString("actualizarDocente"));
            }
            return mensaje;
        } catch (Exception e) {
            return mensaje;
        } finally {
            conexion.desconectar();
        }
    }
}
