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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author HP
 */
public class UsuarioDAO {

    Conexion conexion;
    private Usuario usuario;
    private AES encryptAES;
    String sentencia;
    ResultSet resultSet;
    
    boolean estado = true;

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
    
    public List<Usuario> getListaUsuario() {
        List<Usuario> listaUsuario = new ArrayList<>();
        sentencia = String.format("SELECT * from public.\"getListaUsuarios\"();");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                listaUsuario.add(new Usuario(resultSet.getInt("_id_usuario"), resultSet.getString("_nombre"), resultSet.getString("_apellido"),
                        resultSet.getString("_correo"), resultSet.getString("_nombre_usuario"), resultSet.getBoolean("_estado")));
            }
            return listaUsuario;
        } catch (SQLException ex) {
            return listaUsuario;
        }  finally {
            conexion.desconectar();
        }
    }
    public int registrarUsuario(Usuario user) {
        int mensaje = 0;
        sentencia = String.format("SELECT public.\"registrarUsuario\"(\n"
                + "	'" + user.getNombre()+ "', \n"
                + "	'" + user.getApellido()+ "', \n"
                + "	'" + user.getCorreo()+ "', \n"
                + "	'" + user.getNombreUsuario()+ "', \n"
                + "	'" + encryptAES.getAESEncrypt(user.getPassword())+ "', \n"
                + "	'" + estado + "'\n"
                + ")");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                mensaje = Integer.parseInt(resultSet.getString("registrarUsuario"));
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
            resultSet = conexion.ejecutarSql2(sentencia);

            while (resultSet.next()) {
                usuarioAcceso = new Usuario(
                        resultSet.getInt("code"),
                        resultSet.getString("reslt"),
                        resultSet.getInt("iduser"),
                        resultSet.getString("name"),
                        resultSet.getString("firname"),
                        resultSet.getString("nickname"),
                        resultSet.getString("pswrd"),
                        resultSet.getString("mail")
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
