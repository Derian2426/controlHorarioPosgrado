/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.dao;

import com.global.config.Conexion;
import com.seguridad.modelo.Usuario;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author HP
 */
public class UsuarioDAO {

    Conexion conexion;
    private Usuario usuario;
    String sentencia;
    ResultSet result;

    public UsuarioDAO() {
        conexion = new Conexion();
        usuario = new Usuario();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario iniciarSesion(Usuario u) throws SQLException {
        Usuario usuarioAcceso = null;
        if (true) {
            try {
                sentencia = String.format("SELECT * from public.iniciarsesion('%1$s','%2$s')",
                        u.getNombreUsuario(), u.getPassword());
                result = conexion.ejecutarSql2(sentencia);

                while (result.next()) {
                    usuarioAcceso = new Usuario(
                            result.getInt("code"),
                            result.getString("reslt"),
                            result.getInt("iduser"),
                            result.getString("name"),
                            result.getString("firname"),
                            result.getString("nickname"),
                            result.getString("pswrd"),
                            result.getString("mail")
                    );
                }
            } catch (SQLException e) {
                System.out.println(e.toString());
            } finally {
                conexion.desconectar();
            }
            this.usuario = usuarioAcceso;
        }
        return usuarioAcceso;
    }

}
