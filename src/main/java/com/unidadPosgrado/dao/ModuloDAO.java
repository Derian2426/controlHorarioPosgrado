/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unidadPosgrado.dao;

import com.global.config.Conexion;
import com.unidadPosgrado.modelo.Modulo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HP
 */
public class ModuloDAO {

    Conexion conexion;
    String sentencia;
    ResultSet resultSet;

    public ModuloDAO() {
        conexion = new Conexion();
    }

    public List<Modulo> getListaModulo() {
        List<Modulo> listadoMaestria = new ArrayList<>();
        sentencia = String.format("SELECT * from public.\"getListaModulo\"()");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                listadoMaestria.add(new Modulo(resultSet.getInt("_id_materia"), resultSet.getString("_nombre_materia"), resultSet.getString("_descripcion"),resultSet.getFloat("_hora")));
            }
            return listadoMaestria;
        } catch (SQLException e) {
            return listadoMaestria;
        } finally {
            conexion.desconectar();
        }
    }

    public int registrarModulo(Modulo modulo) {
        int mensaje = 0;
        sentencia = String.format("SELECT public.\"registrarModulo\"(\n"
                + "	'" + modulo.getNombreMateria() + "', \n"
                + "	'" + modulo.getDescripcion() + "', \n"
                + "	" + modulo.getHora_materia() + "\n"
                + ")");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                mensaje = resultSet.getInt("registrarModulo");
            }
            return mensaje;
        } catch (SQLException e) {
            return mensaje;
        } finally {
            conexion.desconectar();
        }
    }

    public int editarModulo(Modulo modulo) {
        int mensaje = 0;
        sentencia = String.format("SELECT public.\"actualizarModulo\"(\n"
                + "	"+modulo.getIdMateria()+", \n"
                + "	'"+modulo.getNombreMateria()+"', \n"
                + "	'"+modulo.getDescripcion()+"', \n"
                + "	"+modulo.getHora_materia()+"\n"
                + ")");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                mensaje = resultSet.getInt("actualizarModulo");
            }
            return mensaje;
        } catch (SQLException e) {
            return mensaje;
        } finally {
            conexion.desconectar();
        }
    }
}
