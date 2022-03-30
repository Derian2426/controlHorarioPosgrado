/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unidadPosgrado.dao;

import com.global.config.Conexion;
import com.unidadPosgrado.modelo.Maestria;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.AbstractList;
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

    public int registrarMaestria(Maestria maestria) {
        int mensaje=0;
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
        }finally{
            conexion.desconectar();
        }
    }

}
