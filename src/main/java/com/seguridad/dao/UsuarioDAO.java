/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.seguridad.dao;

import com.global.config.AES;
import com.global.config.Conexion;
import com.seguridad.modelo.Rol;
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

    boolean estado = false;

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
                listaUsuario.add(new Usuario(resultSet.getInt("_id_usuario"), resultSet.getInt("_id_rol"), resultSet.getString("_nombre"), resultSet.getString("_apellido"),
                        resultSet.getString("_correo"), resultSet.getString("_nombre_usuario"), resultSet.getString("_nombre_rol"), resultSet.getBoolean("_estado")));
            }
            return listaUsuario;
        } catch (SQLException ex) {
            return listaUsuario;
        } finally {
            conexion.desconectar();
        }
    }

    public int registrarIntegracionRol(Usuario integracionUsuario, List<Rol> listaRol) {
        int mensaje = 0;
        String consulta;
        try {
            sentencia = "[";
            for (Rol rol : listaRol) {
                sentencia += "{\n"
                        + "  \"idRol\": " + rol.getIdRol() + ",\n"
                        + "  \"idUsuario\": " + integracionUsuario.getIdUsuario() + "\n"
                        + "},";
            }
            sentencia = sentencia.substring(0, sentencia.length() - 1);
            sentencia += "]";
            consulta = "SELECT public.\"registrarUsuarioRol\"(\n"
                    + "	'" + sentencia + "'\n"
                    + ")";
            resultSet = conexion.ejecutarSql(consulta);
            while (resultSet.next()) {
                mensaje = Integer.parseInt(resultSet.getString("registrarUsuarioRol"));
            }
            return mensaje;
        } catch (NumberFormatException | SQLException e) {
            return mensaje;
        } finally {
            conexion.desconectar();
        }
    }

    public int registrarUsuario(Usuario user) {
        int mensaje = 0;
        sentencia = String.format("SELECT public.\"registrarUsuario\"(\n"
                + "	'" + user.getNombre() + "', \n"
                + "	'" + user.getApellido() + "', \n"
                + "	'" + user.getCorreo() + "', \n"
                + "	'" + user.getNombreUsuario() + "', \n"
                + "	'" + encryptAES.getAESEncrypt(user.getPassword()) + "', \n"
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

    public int editarUsuario(Usuario user) {
        int mensaje = 0;
        sentencia = String.format("SELECT public.\"actualizarUsuario\"(\n"
                + "	" + user.getIdUsuario() + ", \n"
                + "	" + user.getRol() + ", \n"
                + "	'" + user.getNombre() + "', \n"
                + "	'" + user.getApellido() + "', \n"
                + "	'" + user.getCorreo() + "', \n"
                + "	'" + user.getNombreUsuario() + "', \n"
                + "	" + user.isEstado() + "\n"
                + ")");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                mensaje = Integer.parseInt(resultSet.getString("actualizarUsuario"));
            }
            return mensaje;
        } catch (NumberFormatException | SQLException e) {
            return mensaje;
        } finally {
            conexion.desconectar();
        }
    }

    public int verificaCorreoUsuario(Usuario user) {
        int mensaje = 0;
        sentencia = String.format("SELECT public.\"verificaCredenciales\"(\n"
                + "	'" + user.getCorreoSesion() + "' \n"
                + ")");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                mensaje = Integer.parseInt(resultSet.getString("verificaCredenciales"));
            }
            return mensaje;
        } catch (NumberFormatException | SQLException e) {
            return mensaje;
        } finally {
            conexion.desconectar();
        }
    }

    public int cambioPassword(Usuario user) {
        int mensaje = 0;
        sentencia = String.format("SELECT public.\"actualizarPassword\"(\n"
                + "	'" + user.getCorreoSesion() + "', \n"
                + "'" + encryptAES.getAESEncrypt(user.getPassword()) + "', \n"
                + "'" + user.getNomUserSesion() + "' \n"
                + ")");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                mensaje = Integer.parseInt(resultSet.getString("actualizarPassword"));
            }
            return mensaje;
        } catch (NumberFormatException | SQLException e) {
            return mensaje;
        } finally {
            conexion.desconectar();
        }
    }

    public String cambioCredencialesUsuario(Usuario user) {
        String mensaje = "";
        sentencia = String.format("SELECT * from public.\"cambioCredencialesUsuario\"(\n"
                + "	'" + user.getCorreoSesion() + "' \n"
                + ")");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                mensaje = resultSet.getString("_nombre_usuario");
            }
            return mensaje;
        } catch (NumberFormatException | SQLException e) {
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
