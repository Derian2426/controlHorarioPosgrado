/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.dao;

import com.global.config.AES;
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
    private AES encryptAES;
    String sentencia;
    ResultSet result;

    public UsuarioDAO() {
        conexion = new Conexion();
        usuario = new Usuario();
        encryptAES = new AES();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public int registrarUsuario(Usuario user) {
        int mensaje = 0;
        sentencia = String.format("SELECT public.\"registrarUsuario\"(\n"
                + "	'" + user.getNombre()+ "', \n"
                + "	'" + user.getApellido()+ "', \n"
                + "	'" + user.getNombreUsuario()+ "', \n"
                + "	'" + user.getCorreo()+ "', \n"
                + "	'" + encryptAES.getAESEncrypt(user.getPassword())+ "'\n"
                + ")");
        try {
            result = conexion.ejecutarSql(sentencia);
            while (result.next()) {
                mensaje = Integer.parseInt(result.getString("registrarUsuario"));
            }
            return mensaje;
        } catch (Exception e) {
            return mensaje;
        } finally {
            conexion.desconectar();
        }
    }

    public Usuario iniciarSesion(Usuario u) throws SQLException {
        Usuario usuarioAcceso = null;
        try {
            sentencia = String.format("SELECT * from public.iniciarsesion('%1$s','%2$s')",
                    u.getNomUserSesion(), encryptAES.getAESEncrypt(u.getPassSesion()));
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
        return usuarioAcceso;
    }

}
