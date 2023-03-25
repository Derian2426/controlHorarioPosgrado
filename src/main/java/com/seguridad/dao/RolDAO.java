/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.dao;

import com.global.config.Conexion;
import com.seguridad.modelo.Rol;
import com.unidadPosgrado.modelo.Maestria;
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

    public RolDAO() {
        conexion = new Conexion();
        rol = new Rol();
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public List<Rol> getRolesByUsers(int idUsuario) {
        List<Rol> roles = new ArrayList<>();
        Rol rolAux;
        String query = "select r.* from public.\"rolusuario\" ru inner join public.rol r on ru.\"idrol\" = r.\"id_rol\" where ru.\"idusuario\" = " + String.valueOf(idUsuario) + ";";
        ResultSet rs;
        try {
            rs = conexion.ejecutarSql(query);
            while (rs.next()) {
                rolAux = new Rol();
                rolAux.setIdRol(rs.getInt("id_rol"));
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

    public int registrarRol(Rol rol, List<Maestria> listaMaestria) {
        int mensaje = 0;
        String jsonMaestrias = "[";
        for (Maestria maestria : listaMaestria) {
            jsonMaestrias += "{\n"
                    + "  \"idMaestria\": " + maestria.getIdMaestria() + "\n"
                    + "},";
        }
        jsonMaestrias = jsonMaestrias.substring(0, jsonMaestrias.length() - 1);
        jsonMaestrias += "]";
        sentencia = String.format("SELECT public.\"registrarRol\"(\n"
                + "	'" + rol.getNombre() + "', \n"
                + "	'" + rol.getDetalle() + "', \n"
                + "	" + rol.isEstado() + ", \n"
                + "	'" + jsonMaestrias + "'\n"
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

    public int editarRol(Rol rol, List<Maestria> listaMaestrias) {
        int mensaje = 0;
        String jsonMaestrias = "[";
        for (Maestria maestria : listaMaestrias) {
            jsonMaestrias += "{\n"
                    + "  \"idMaestria\": " + maestria.getIdMaestria() + "\n"
                    + "},";
        }
        jsonMaestrias = jsonMaestrias.substring(0, jsonMaestrias.length() - 1);
        jsonMaestrias += "]";
        sentencia = String.format("SELECT public.\"editarRol\"(\n"
                + "	" + rol.getIdRol() + ", \n"
                + "	'" + rol.getNombre() + "', \n"
                + "	'" + rol.getDetalle() + "', \n"
                + "	" + rol.isEstado() + ", \n"
                + "	'" + jsonMaestrias + "'\n"
                + ")");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                mensaje = Integer.parseInt(resultSet.getString("editarRol"));
            }
            return mensaje;
        } catch (NumberFormatException | SQLException e) {
            return mensaje;
        } finally {
            conexion.desconectar();
        }
    }

    public List<Maestria> listaMestriasRol(int idRol) {
        List<Maestria> listaMaestria = new ArrayList<>();
        sentencia = String.format("SELECT * from public.\"getListRolMaestria\"(" + idRol + ");");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                listaMaestria.add(new Maestria(resultSet.getInt("_id_maestria"),
                        resultSet.getString("_nombre_maestria"),
                        resultSet.getString("_descripcion")));
            }
            return listaMaestria;
        } catch (SQLException ex) {
            return listaMaestria;
        } finally {
            conexion.desconectar();
        }
    }

    public int deleteRolMaestria(Rol rol, Maestria maestria) {
        int mensaje = 0;
        sentencia = String.format("SELECT public.\"deleteRolMaestria\"(\n"
                + "	" + rol.getIdRol() + ", \n"
                + "	" + maestria.getIdMaestria() + " \n"
                + ")");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                mensaje = Integer.parseInt(resultSet.getString("deleteRolMaestria"));
            }
            return mensaje;
        } catch (NumberFormatException | SQLException e) {
            return mensaje;
        } finally {
            conexion.desconectar();
        }
    }
}
