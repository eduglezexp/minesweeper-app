package es.ies.puerto.model.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.ies.puerto.model.abstractas.Conexion;
import es.ies.puerto.model.entities.UsuarioEntity;
import es.ies.puerto.model.entities.UsuarioPowerupsEntity;

/**
 * @author eduglezexp
 * @version 1.0.0
 */

public class UsuarioPowerupsService extends Conexion{

    /**
     * Constructor por defecto
     */
    public UsuarioPowerupsService() {

    }

    /**
     * Contructor con la ruta del archivo de la bbdd
     * @param unaRutaArchivoBD path de la bbdd
     * @throws SQLException error controlado
     */
    public UsuarioPowerupsService(String unaRutaArchivoBD) throws SQLException {
        super(unaRutaArchivoBD);
    }

    /**
     * Metodo que obtiene los powerups de un usuario por su id
     * @param usuarioId id del usuario
     * @return lista de powerups del usuario
     * @throws SQLException error controlado
     */
    public List<UsuarioPowerupsEntity> obtenerUsuariosPowerupsPorId(int usuarioId) throws SQLException {
        String sql = "SELECT * FROM usuario_powerups WHERE usuario_id = ?";
        return obtenerUsuarioPowerups(sql, String.valueOf(usuarioId));
    }

    /**
     * Metodo que obtiene todos los powerups de un usuario
     * @return lista de poderes del usuario
     * @throws SQLException error controlado
     */
    public List<UsuarioPowerupsEntity> obtenerUsuariosPowerups() throws SQLException{
        String sql = "SELECT * FROM usuario_powerups";
        return obtenerUsuarioPowerups(sql);
    }

    /**
     * Metodo para obtener la cantidad de powerups de un usuario
     * @param sql sentencia sql
     * @param parametros email o nombre de usuario
     * @return cantidad de powerups del usuario
     * @throws SQLException error controlado
     */
    private List<UsuarioPowerupsEntity> obtenerUsuarioPowerups(String sql, String... parametros) throws SQLException{
        List<UsuarioPowerupsEntity> powerups = new ArrayList<UsuarioPowerupsEntity>();
        try {
            PreparedStatement sentencia = getConnection().prepareStatement(sql);
            for (int i = 0; i < parametros.length; i++) {
                sentencia.setString(i + 1, parametros[i]);
            }
            ResultSet resultado = sentencia.executeQuery();
            while(resultado.next()){
                int usuarioId = resultado.getInt("usuario_id");
                int powerupId = resultado.getInt("powerup_id");
                int cantidad = resultado.getInt("cantidad");
                UsuarioPowerupsEntity usuarioPowerupsEntity = new UsuarioPowerupsEntity(usuarioId, powerupId, cantidad);
                powerups.add(usuarioPowerupsEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrar();
        }
        return powerups;
    }

    /**
     * Metodo para actualizar la cantidad de powerups de un usuario
     * @param usuarioId id del usuario
     * @param powerupId id del powerup
     * @param cantidad cantidad de powerups a actualizar
     * @return true si se ha actualizado la cantidad, false si no se ha podido actualizar
     * @throws SQLException error controlado
     */
    public boolean actualizarUsuarioPoderes(int cantidad, int usuarioId, int powerupId) throws SQLException {
        String sql = "UPDATE usuario_powerups SET cantidad = ? WHERE usuario_id = ? AND powerup_id = ?";
        try (PreparedStatement sentencia = getConnection().prepareStatement(sql)) {
            sentencia.setInt(1, cantidad);
            sentencia.setInt(2, usuarioId);
            sentencia.setInt(3, powerupId);
            sentencia.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            cerrar();
        }
    }

    /**
     * Metodo para comprar un powerup
     * @param usuario usuario que compra el powerup
     * @param powerupId id del powerup
     * @return true si se ha comprado el powerup, false si no se ha podido comprar
     * @throws SQLException error controlado
     */
    public boolean comprarPowerUp(UsuarioEntity usuario, int powerupId) throws SQLException {
        String sql = "INSERT INTO usuario_powerups (usuario_id, powerup_id, cantidad) VALUES (?, ?, 1) " +
                     "ON CONFLICT(usuario_id, powerup_id) DO UPDATE SET cantidad = cantidad + 1";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setInt(1, usuario.getId());
            preparedStatement.setInt(2, powerupId);
            preparedStatement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally {
            cerrar();
        }
    }
}
