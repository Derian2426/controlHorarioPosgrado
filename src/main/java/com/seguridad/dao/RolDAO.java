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
    
    public RolDAO() {
        conexion = new Conexion();
    }
    
    public List<Rol> getRolesByUsers(int idUsuario) {
        List<Rol> roles = new ArrayList<>();
        Rol rolAux;
        String query = "select r.* from public.\"rolusuario\" ru inner join public.rol r on ru.\"idrol\" = r.\"id_rol\" where ru.\"idusuario\" = " + String.valueOf(idUsuario) + ";";
        ResultSet rs;
        try {
            rs = conexion.ejecutarSql(query);
            while(rs.next()){
                rolAux = new Rol();
                rolAux.setId(rs.getInt("id_rol"));
                rolAux.setNombre(rs.getString("nombre"));
                rolAux.setDetalle(rs.getString("detalle"));
                rolAux.setEstado(rs.getBoolean("estado"));
                roles.add(rolAux);
            }
            rs.close();
            return roles;
        } catch (SQLException e) {
            e.toString();
        } finally {
            conexion.desconectar();
        }
        
        return null;
    }
}
