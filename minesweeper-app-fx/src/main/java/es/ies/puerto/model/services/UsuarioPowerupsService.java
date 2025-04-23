package es.ies.puerto.model.services;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import es.ies.puerto.model.abstractas.Conexion;
import es.ies.puerto.model.entities.UsuarioEntity;

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
        } finally {
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
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, usuario.getId());
            stmt.setInt(2, powerupId);
            return stmt.executeUpdate() > 0;
        }
    }
}
