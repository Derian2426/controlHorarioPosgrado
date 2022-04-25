/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.dao;

import com.global.config.Conexion;
import com.seguridad.modelo.Rol;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alex
 */
public class RolDAO {
    Conexion conexion;
    private Rol rol;
    String sentencia;
    ResultSet resultSet;
    
    public RolDAO(){
        conexion = new Conexion();
        rol = new Rol();
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
    
    public List<Rol> getListaRol() {
        List<Rol> listaRol = new ArrayList<>();
        sentencia = String.format("SELECT * from public.\"getListaRoles\"();");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                listaRol.add(new Rol(resultSet.getInt("_id_rol"), resultSet.getString("_nombre"), resultSet.getString("_detalle"),
                        resultSet.getBoolean("_estado")));
            }
            return listaRol;
        } catch (SQLException ex) {
            return listaRol;
        } finally {
            conexion.desconectar();
        }
    }
    
    public int registrarRol(Rol rol) {
        int mensaje = 0;
        sentencia = String.format("SELECT public.\"registrarRol\"(\n"
                + "	'" + rol.getNombre() + "', \n"
                + "	'" + rol.getDetalle()+ "', \n"
                + "	" + rol.isEstado()+ "\n"
                + ")");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                mensaje = Integer.parseInt(resultSet.getString("registrarRol"));
            }
            return mensaje;
        } catch (NumberFormatException | SQLException e) {
            return mensaje;
        } finally {
            conexion.desconectar();
        }
    }
    
    public int editarRol(Rol rol) {
        int mensaje = 0;
        sentencia = String.format("SELECT public.\"actualizarRol\"(\n"
                + "	" + rol.getIdRol()+ ", \n"
                + "	'" + rol.getNombre() + "', \n"
                + "	'" + rol.getDetalle()+ "', \n"
                + "	" + rol.isEstado()+ "\n"
                + ")");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                mensaje = Integer.parseInt(resultSet.getString("actualizarRol"));
            }
            return mensaje;
        } catch (NumberFormatException | SQLException e) {
            return mensaje;
        } finally {
            conexion.desconectar();
        }
    }
}
