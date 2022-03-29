/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.global.config;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author HP
 */
public class Conexion implements Serializable {

    public Connection connection;
    private Statement statement;
    private ResultSet result;
    private boolean estado;
    private String mensaje;

    //Credenciales para la conexion
    private String url = "jdbc:postgresql://ec2-52-73-155-171.compute-1.amazonaws.com:5432/den7jr6ljf2a50";
    private String usuario = "yrhofcfdicsqcs";
    private String clave = "225e5c1761e1d504e5725d164430bc9b755aafadd05cd519921290d8797e8370";
    String classForName = "org.postgresql.Driver";

    public Conexion() {
        estado = true;
    }

    public Conexion(String user, String pass, String url) {
        this.usuario = user;
        this.clave = pass;
        this.url = url;
        this.estado = true;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    //  MÉTODO CONECTAR PARA INICIAR UNA CONEXIÓN A LA BASE DE DATOS

    public boolean conectar() {
        try {
            try {
                Class.forName(classForName);
            } catch (ClassNotFoundException e) {
                mensaje = e.getMessage();
            }
            if (connection == null || connection.isClosed()) {
                connection = DriverManager.getConnection(url, usuario, clave);
                statement = connection.createStatement();
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            mensaje = e.getMessage();
            return false;
        }
    }

    //  MÉTODO DESCONECTAR PARA CERRAR UNA CONEXIÓN A LA BASE DE DATOS
    public boolean desconectar() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                statement.close();
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            mensaje = ex.getMessage();
            return false;
        }
    }

    //  EJECUTAR CONSULTAS SQL
    public ResultSet ejecutarSql(String sql) {
        try {
            if (conectar()) {
                result = statement.executeQuery(sql);
            }
        } catch (SQLException ex) {
            mensaje = ex.getMessage();
            System.out.println("Error: No se ejecuto la consulta: " + ex.getMessage());
        }
        return result;
    }

    public int insertar(String sql) {
        int retorno = -1;
        try {
            if (conectar()) {
                retorno = statement.executeUpdate(sql);
                mensaje = "Se guardó correctamente : ";
            }
        } catch (SQLException exc) {
            mensaje = exc.getMessage();
        } finally {
            desconectar();
        }
        return retorno;
    }

}
