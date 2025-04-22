package es.ies.puerto.model.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.ies.puerto.model.abstractas.Conexion;
import es.ies.puerto.model.entities.UsuarioEntity;

/**
 * @author eduglezexp
 * @version 1.0.0
 */

public class UsuarioService extends Conexion{

    /**
     * Constructor por defecto
     */
    public UsuarioService() {

    }

    /**
     * Contructor con la ruta del archivo de la bbdd
     * @param unaRutaArchivoBD path de la bbdd
     * @throws SQLException error controlado
     */
    public UsuarioService(String unaRutaArchivoBD) throws SQLException {
        super(unaRutaArchivoBD);
    }

    /**
     * Metodo que obtiene un usuario por su email o nombre de usuario
     * @param input email o nombre de usuario
     * @return usuario encontrado o null si no existe
     * @throws SQLException error controlado
     */
    public List<UsuarioEntity> obtenerUsuarioPorEmailOUser(String input) throws SQLException {
        String sql = "SELECT * FROM usuarios " + "WHERE email = ? OR user = ?";
        return obtenerUsuario(sql, input, input);
    }

    /**
     * Metodo que obtiene un usuario dado su email, user, name o id
     * @param input email o nombre de usuario
     * @return usuario encontrado o null si no existe
     * @throws SQLException error controlado
     */
    public List<UsuarioEntity> obtenerUsuarioPorInput(String input) throws SQLException {
        String sql = "SELECT * FROM usuarios " + "WHERE email = ? OR user = ? OR name = ? OR id = ?";
        return obtenerUsuario(sql, input, input, input, input);
    }
    
    /**
     * Metodo que obtiene todos los usuarios de la base de datos
     * @return lista de usuarios
     * @throws SQLException error controlado
     */
    public List<UsuarioEntity> obtenerUsuarios() throws SQLException{
        String sql = "SELECT * FROM usuarios";
        return obtenerUsuario(sql);
    }

    /**
     * Metodo que ejecuta una consulta SQL para obtener usuarios
     * @param sql consulta SQL a ejecutar
     * @return lista de usuarios obtenidos
     * @throws SQLException error controlado
     */
    private List<UsuarioEntity> obtenerUsuario(String sql, String... parametros) throws SQLException{
        List<UsuarioEntity> usuarios = new ArrayList<UsuarioEntity>();
        try {
            PreparedStatement sentencia = getConnection().prepareStatement(sql);
            for (int i = 0; i < parametros.length; i++) {
                sentencia.setString(i + 1, parametros[i]);
            }
            ResultSet resultado = sentencia.executeQuery();
            while(resultado.next()){
                int usuarioId = resultado.getInt("id");
                String usuarioStr = resultado.getString("user");
                String emailStr = resultado.getString("email");
                String nombreStr = resultado.getString("name");
                String contraseniaStr = resultado.getString("password");
                int pointsStr = resultado.getInt("points");
                int victoriesStr = resultado.getInt("victories");
                int defeatsStr = resultado.getInt("defeats");
                int idNivel = resultado.getInt("id_nivel");
                int rachaActualStr = resultado.getInt("racha_actual");
                int mejorRachaStr = resultado.getInt("mejor_racha");
                UsuarioEntity usuarioEntityModel = new UsuarioEntity(usuarioId, usuarioStr, 
                emailStr, nombreStr, contraseniaStr, pointsStr, victoriesStr, defeatsStr, 
                idNivel, rachaActualStr, mejorRachaStr);
                usuarios.add(usuarioEntityModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrar();
        }
        return usuarios;
    }

    /**
     * Metodo que elimina a un usuario en especifico de la base de datos
     * @return lista de usuarios
     * @throws SQLException error controlado
     */
    public List<UsuarioEntity> eliminarUsuario(String email) throws SQLException{
        String sql = "DELETE FROM usuarios WHERE email = ?";
        return obtenerUsuario(sql, email);
    }

    /**
     * Metodo para insertar un usuario
     * @throws SQLException error controlado
     */
    public boolean insertarUsuario(UsuarioEntity usuario) throws SQLException {
        String sql = "INSERT INTO usuarios (user, email, name, password, id_nivel) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement sentencia = getConnection().prepareStatement(sql)) {
            sentencia.setString(1, usuario.getUser());
            sentencia.setString(2, usuario.getEmail());
            sentencia.setString(3, usuario.getName());
            sentencia.setString(4, usuario.getPassword());
            sentencia.setInt(5, usuario.getIdNivel());
            sentencia.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrar();
        }
        return false;
    }

    /**
     * Metodo para actualizar un usuario
     * @throws SQLException error controlado
     */
    public boolean actualizarUsuario(UsuarioEntity usuario) throws SQLException {
        String sql = "UPDATE usuarios SET user = ?, email = ?, name = ?, password = ? WHERE id = ?";
        try (PreparedStatement sentencia = getConnection().prepareStatement(sql)) {
            sentencia.setString(1, usuario.getUser());
            sentencia.setString(2, usuario.getEmail());
            sentencia.setString(3, usuario.getName());
            sentencia.setString(4, usuario.getPassword());
            sentencia.setInt(5, usuario.getId());
            sentencia.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrar();
        }
        return false;
    }

    /**
     * Metodo para actualizar los puntos y victorias de un usuario
     * @throws SQLException error controlado
     */
    public boolean actualizarPuntosVictorias(int puntos, int victorias, String email) throws SQLException {
        String sql = "UPDATE usuarios SET puntos = ?, victorias = ? WHERE email = ?";
        try (PreparedStatement sentencia = getConnection().prepareStatement(sql)) {
            sentencia.setInt(1, puntos);
            sentencia.setInt(2, victorias);
            sentencia.setString(3, email);
            sentencia.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrar();
        }
        return false;
    }
    
    /**
     * Metodo para actualizar los puntos y derrotas de un usuario
     * @throws SQLException error controlado
     */
    public boolean actualizarPuntosDerrotas(int puntos, int derrotas, String email) throws SQLException {
        String sql = "UPDATE usuarios SET puntos = ?, derrotas = ? WHERE email = ?";
        try (PreparedStatement sentencia = getConnection().prepareStatement(sql)) {
            sentencia.setInt(1, puntos);
            sentencia.setInt(2, derrotas);
            sentencia.setString(3, email);
            sentencia.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrar();
        }
        return false;
    }

    /**
     * Metodo para actualizar los puntos de un usuario
     * @throws SQLException error controlado
     */
    public boolean actualizarPuntos(int puntos, String email) throws SQLException {
        String sql = "UPDATE usuarios SET puntos = ? WHERE email = ?";
        try (PreparedStatement sentencia = getConnection().prepareStatement(sql)) {
            sentencia.setInt(1, puntos);
            sentencia.setString(2, email);
            sentencia.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrar();
        }
        return false;
    }

    /**
     * Metodo para actualizar el nivel de un usuario
     * @throws SQLException error controlado
     */
    public boolean actualizarNivel(int nivel, String email) throws SQLException {
        String sql = "UPDATE usuarios SET id_nivel = ? WHERE email = ?";
        try (PreparedStatement sentencia = getConnection().prepareStatement(sql)) {
            sentencia.setInt(1, nivel);
            sentencia.setString(2, email);
            sentencia.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cerrar();
        }
        return false;
    }
}