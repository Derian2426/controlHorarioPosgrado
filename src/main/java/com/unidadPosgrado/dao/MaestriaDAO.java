/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unidadPosgrado.dao;

import com.global.config.Conexion;
import com.seguridad.modelo.Usuario;
import com.unidadPosgrado.modelo.Maestria;
import com.unidadPosgrado.modelo.Modulo;
import com.unidadPosgrado.modelo.Periodo;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

    //TABLE(_id_periodo integer, _nombre_maestria character varying, _fecha_inicio date, _fecha_fin date,_estado character(1))
    public List<Periodo> getListaPeriodo(Usuario user) {
        List<Periodo> listadoPeriodo = new ArrayList<>();
        sentencia = String.format("SELECT * from public.\"getListaPeriodo\"(" + user.getIdUsuarioSesion() + ");");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            String estado;
            while (resultSet.next()) {
                if ("A".equals(resultSet.getString("_estado").trim())) {
                    estado = "Activo";
                } else {
                    estado = "Terminado";
                }
                listadoPeriodo.add(new Periodo(resultSet.getInt("_id_maestria"), resultSet.getInt("_id_periodo"), resultSet.getString("_nombre_maestria"), resultSet.getDate("_fecha_inicio"),
                        resultSet.getDate("_fecha_fin"), estado));
            }
            return listadoPeriodo;
        } catch (SQLException e) {
            return listadoPeriodo;
        } finally {
            conexion.desconectar();
        }
    }

    public String editarPeriodo(Periodo periodo) {
        String estado = "";
        sentencia = String.format("SELECT public.\"editaPeriodoAcademico\"(\n"
                + "	" + periodo.getIdPeriodo() + ", \n"
                + "	'" + periodo.getFechaInicio() + "', \n"
                + "	'" + periodo.getFechaFin() + "', \n"
                + "	" + periodo.getIdMaestria() + ", \n"
                + "	'" + "Paralelo " + periodo.getNombreParalelo() + "', \n"
                + "	" + periodo.getCantidadEstudiante() + ", \n"
                + "	'" + periodo.getNombrePeriodo() + "'\n"
                + ")");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                estado = resultSet.getString("editaPeriodoAcademico");
            }
            return estado;
        } catch (SQLException e) {
            return estado;
        } finally {
            conexion.desconectar();
        }
    }

    public List<Maestria> getListaMaestria(int idUsuario) {
        List<Maestria> listadoMaestria = new ArrayList<>();
        sentencia = String.format("select * from public.\"getListaMaestrias\"(" + idUsuario + ")");
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

    public Maestria inscripcionMaestria(Maestria maestria) {
        Maestria mInscripcion = new Maestria();
        sentencia = String.format("select * from public.\"getDetalleInscripcion\"(" + maestria.getIdCurso() + ")");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                mInscripcion = new Maestria(resultSet.getInt("_id_curso"), resultSet.getString("_nombre_maestria"), resultSet.getString("_nombre"),
                        resultSet.getFloat("_valor"), resultSet.getDate("_fecha_inscripcion"));
            }
            return mInscripcion;
        } catch (SQLException e) {
            return mInscripcion;
        } finally {
            conexion.desconectar();
        }
    }

    public List<Maestria> getListaMaestria_Periodo(Usuario user) {
        List<Maestria> listadoMaestria = new ArrayList<>();
        sentencia = String.format("select * from public.\"getListaMaestria_Periodo\"(" + user.getIdUsuarioSesion() + ")");
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

    public List<Maestria> getListaMaestriaPeriodo(Usuario user) {
        List<Maestria> listadoMaestria = new ArrayList<>();
        sentencia = String.format("SELECT* from public.\"getListaMaestriasPeriodo\"(" + user.getIdUsuarioSesion() + ")");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                listadoMaestria.add(new Maestria(resultSet.getInt("_id_maestria"), resultSet.getString("_nombre_maestria"), resultSet.getString("_nombre_periodo"), resultSet.getDate("_fecha_inicio"), resultSet.getDate("_fecha_fin"),
                        resultSet.getInt("_id_curso"), resultSet.getString("_nombre_curso")));
            }
            return listadoMaestria;
        } catch (SQLException e) {
            return listadoMaestria;
        } finally {
            conexion.desconectar();
        }
    }

    public List<Maestria> getListaMaestriaPeriodoEstudiante(Usuario user) {
        List<Maestria> listadoMaestria = new ArrayList<>();
        sentencia = String.format("SELECT* from public.\"getListaMaestriasPeriodoEstudiante\"(" + user.getIdUsuarioSesion() + ")");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                listadoMaestria.add(new Maestria(resultSet.getInt("_id_maestria"), resultSet.getString("_nombre_maestria"), resultSet.getString("_nombre_periodo"), resultSet.getDate("_fecha_inicio"), resultSet.getDate("_fecha_fin"),
                        resultSet.getInt("_id_curso"), resultSet.getString("_nombre_curso")));
            }
            return listadoMaestria;
        } catch (SQLException e) {
            return listadoMaestria;
        } finally {
            conexion.desconectar();
        }
    }

    public List<Maestria> getListaMaestriaPeriodoEstudianteInscripcion(Usuario user) {
        List<Maestria> listadoMaestria = new ArrayList<>();
        sentencia = String.format("SELECT* from public.\"getListaMaestriasPeriodoEstudianteInscripcion\"(" + user.getIdUsuarioSesion() + ")");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                listadoMaestria.add(new Maestria(resultSet.getInt("_id_maestria"), resultSet.getString("_nombre_maestria"), resultSet.getString("_nombre_periodo"), resultSet.getDate("_fecha_inicio"), resultSet.getDate("_fecha_fin"),
                        resultSet.getInt("_id_curso"), resultSet.getString("_nombre_curso")));
            }
            return listadoMaestria;
        } catch (SQLException e) {
            return listadoMaestria;
        } finally {
            conexion.desconectar();
        }
    }

    public List<Maestria> getListaMaestriaxModulo(int idUsuario) {
        List<Maestria> listadoMaestria = new ArrayList<>();
        sentencia = String.format("SELECT * from public.\"tiempoMaestria\"(" + idUsuario + ")");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                listadoMaestria.add(new Maestria(null, resultSet.getInt("_id_maestria"), resultSet.getString("_nombre_maestria"), resultSet.getString("_descripcion"), resultSet.getFloat("_hora")));
            }
            return listadoMaestria;
        } catch (SQLException e) {
            return listadoMaestria;
        } finally {
            conexion.desconectar();
        }
    }

    public int deleteModuloMaestria(Modulo modulo) {
        int mensaje = 0;
        sentencia = String.format("SELECT public.\"deleteModuloMaestria\"(" + modulo.getIdMateria() + ");");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                mensaje = Integer.parseInt(resultSet.getString("deleteModuloMaestria"));
            }
            return mensaje;
        } catch (SQLException e) {
            return mensaje;
        } finally {
            conexion.desconectar();
        }
    }

    public int registrarMaestria(Maestria maestria) {
        int mensaje = 0;
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
        } finally {
            conexion.desconectar();
        }
    }

    public int editarMaestria(Maestria maestria) {
        int mensaje = 0;
        sentencia = String.format("SELECT public.\"actualizarMaestria\"(\n"
                + "	" + maestria.getIdMaestria() + ", \n"
                + "	'" + maestria.getNombre() + "', \n"
                + "	'" + maestria.getDescripcion() + "'\n"
                + ")");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                mensaje = Integer.parseInt(resultSet.getString("actualizarMaestria"));
            }
            return mensaje;
        } catch (SQLException e) {
            return mensaje;
        } finally {
            conexion.desconectar();
        }
    }

    public int registrarIntegracionModulo(Maestria integracionMaestria, List<Modulo> listaModulos) {
        int mensaje = 0;
        String consulta;
        try {
            sentencia = "[";
            for (Modulo modulo : listaModulos) {
                sentencia += "{\n"
                        + "  \"idMateria\": " + modulo.getIdMateria() + ",\n"
                        + "  \"idMaestria\": " + integracionMaestria.getIdMaestria() + ",\n"
                        + "  \"nombreModulo\": \"" + modulo.getNombreMateria() + "\",\n"
                        + "  \"descripcionModulo\": \"" + modulo.getDescripcion() + "\",\n"
                        + "  \"hora\": " + modulo.getHora_materia() + "\n"
                        + "},";
            }
            sentencia = sentencia.substring(0, sentencia.length() - 1);
            sentencia += "]";
            consulta = "SELECT public.\"registrarIntegracion\"(\n"
                    + "	'" + sentencia + "'\n"
                    + ")";
            resultSet = conexion.ejecutarSql(consulta);
            while (resultSet.next()) {
                mensaje = Integer.parseInt(resultSet.getString("registrarIntegracion"));
            }
            return mensaje;
        } catch (NumberFormatException | SQLException e) {
            return mensaje;
        } finally {
            conexion.desconectar();
        }
    }

    public int editarIntegracionModulo(Maestria integracionMaestria, List<Modulo> listaModulos) {
        int mensaje = 0;
        String consulta;
        try {
            sentencia = "[";
            for (Modulo modulo : listaModulos) {
                sentencia += "{\n"
                        + "  \"idMateria\": " + modulo.getIdMateria() + ",\n"
                        + "  \"idMaestria\": " + integracionMaestria.getIdMaestria() + ",\n"
                        + "  \"nombreModulo\": \"" + modulo.getNombreMateria() + "\",\n"
                        + "  \"descripcionModulo\": \"" + modulo.getDescripcion() + "\",\n"
                        + "  \"hora\": " + modulo.getHora_materia() + "\n"
                        + "},";
            }
            sentencia = sentencia.substring(0, sentencia.length() - 1);
            sentencia += "]";
            consulta = "SELECT public.\"modificarIntegracion\"(\n"
                    + "	'" + sentencia + "'\n"
                    + ")";
            resultSet = conexion.ejecutarSql(consulta);
            while (resultSet.next()) {
                mensaje = Integer.parseInt(resultSet.getString("modificarIntegracion"));
            }
            return mensaje;
        } catch (NumberFormatException | SQLException e) {
            return mensaje;
        } finally {
            conexion.desconectar();
        }
    }

    public List<Modulo> getListaModulo(int idModulo) {
        List<Modulo> listadoModulo = new ArrayList<>();
        sentencia = String.format("SELECT * from public.\"listaModuloxMaestria1\"(\n"
                + "	" + idModulo + "\n"
                + ")");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                listadoModulo.add(new Modulo(resultSet.getInt("_id_materia"), resultSet.getString("_nombre_materia"), resultSet.getString("_descripcion"), resultSet.getFloat("_hora")));
            }
            return listadoModulo;
        } catch (SQLException e) {
            return listadoModulo;
        } finally {
            conexion.desconectar();
        }
    }

    public int registrarPeriodo(Maestria maestria, Periodo periodo) {
        int mensaje = 0;
        sentencia = String.format("SELECT public.\"registrarPeriodoAcademico\"(\n"
                + "	" + maestria.getIdMaestria() + ", \n"
                + "	'" + periodo.getNombrePeriodo() + "', \n"
                + "	'" + periodo.getFechaInicio() + "', \n"
                + "	'" + periodo.getFechaFin() + "', \n"
                + "	'" + "Paralelo " + periodo.getNombreParalelo() + "', \n"
                + "	" + periodo.getCantidadEstudiante() + "\n"
                + ")");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                mensaje = Integer.parseInt(resultSet.getString("registrarPeriodoAcademico"));
            }
            return mensaje;
        } catch (SQLException e) {
            return mensaje;
        } finally {
            conexion.desconectar();
        }
    }

    public int verificaCantidadDocente(Maestria maestria) {
        int mensaje = 0;
        sentencia = String.format("SELECT public.verifica_cantidad_docente(" + maestria.getIdMaestria() + ")");
        try {
            resultSet = conexion.ejecutarSql(sentencia);
            while (resultSet.next()) {
                mensaje = Integer.parseInt(resultSet.getString("verifica_cantidad_docente"));
            }
            return mensaje;
        } catch (SQLException e) {
            return mensaje;
        } finally {
            conexion.desconectar();
        }
    }

    public List<Maestria> getEditListaMaestria(String cedula) {
        List<Maestria> listadoMaestria = new ArrayList<>();
        sentencia = String.format("SELECT * from public.get_lista_maestriaxdocente('" + cedula + "')");
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

    public void actualizaEstadoPeriodo() {
        String dateTime = DateTimeFormatter.ofPattern("yyyy MMM dd")
                .format(LocalDateTime.now());
        sentencia = String.format("SELECT * from public.actualiza_estado_periodo('" + dateTime + "')");
        resultSet = conexion.ejecutarSql(sentencia);
        conexion.desconectar();
    }
}
