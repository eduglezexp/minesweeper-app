package es.ies.puerto.model.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.ies.puerto.model.entities.UsuarioAvataresEntity;
import es.ies.puerto.model.entities.UsuarioEntity;

/**
 * @author eduglezexp
 * @version 1.0.0
 */

public class UsuarioAvataresService extends UsuarioService {
    /**
     * Constructor por defecto
     */
    public UsuarioAvataresService() {

    }

    /**
     * Contructor con la ruta del archivo de la bbdd
     * @param unaRutaArchivoBD path de la bbdd
     * @throws SQLException error controlado
     */
    public UsuarioAvataresService(String unaRutaArchivoBD) throws SQLException {
        super(unaRutaArchivoBD);
    }

    /**
     * Metodo que obtiene los avatares de un usuario por su id
     * @param usuarioId id del usuario
     * @return lista de avatares del usuario
     * @throws SQLException error controlado
     */
    public List<UsuarioAvataresEntity> obtenerUsuariosAvataresPorId(int usuarioId) throws SQLException {
        String sql = "SELECT * FROM usuario_avatares WHERE usuario_id = ?";
        return obtenerUsuarioAvatares(sql, String.valueOf(usuarioId));
    }

    /**
     * Metodo que obtiene todos los avatares de un usuario
     * @return lista de avatares del usuario
     * @throws SQLException error controlado
     */
    public List<UsuarioAvataresEntity> obtenerUsuariosAvatares() throws SQLException{
        String sql = "SELECT * FROM usuario_avatares";
        return obtenerUsuarioAvatares(sql);
    }

    /**
     * Metodo que obtiene los avatares de un usuario 
     * @param usuarioId id del usuario
     * @return lista de avatares del usuario
     * @throws SQLException error controlado
     */
    private List<UsuarioAvataresEntity> obtenerUsuarioAvatares(String sql, String... parametros) throws SQLException{
        List<UsuarioAvataresEntity> avatares = new ArrayList<UsuarioAvataresEntity>();
        try {
            PreparedStatement sentencia = getConnection().prepareStatement(sql);
            for (int i = 0; i < parametros.length; i++) {
                sentencia.setString(i + 1, parametros[i]);
            }
            ResultSet resultado = sentencia.executeQuery();
            while(resultado.next()){
                int usuarioId = resultado.getInt("usuario_id");
                int avatarId = resultado.getInt("avatar_id");
                boolean activo = resultado.getBoolean("activo");
                UsuarioAvataresEntity usuarioAvataresEntity = new UsuarioAvataresEntity(usuarioId, avatarId, activo);
                avatares.add(usuarioAvataresEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrar();
        }
        return avatares;
    }

    /**
     * Método para actualizar el avatar activo de un usuario
     * @param activo true para activar, false para desactivar
     * @param usuarioId id del usuario
     * @param avatarId id del avatar
     * @return true si se actualizó correctamente
     */
    public boolean actualizarUsuarioAvatares(boolean activo, int usuarioId, int avatarId) throws SQLException {
        try {
            if (activo) {
                String desactivarSql = "UPDATE usuario_avatares SET activo = 0 WHERE usuario_id = ?";
                try (PreparedStatement stmt = getConnection().prepareStatement(desactivarSql)) {
                    stmt.setInt(1, usuarioId);
                    stmt.executeUpdate();
                }
            }
            String sql = "UPDATE usuario_avatares SET activo = ? WHERE usuario_id = ? AND avatar_id = ?";
            try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
                stmt.setBoolean(1, activo);
                stmt.setInt(2, usuarioId);
                stmt.setInt(3, avatarId);
                int filasAfectadas = stmt.executeUpdate();
                return filasAfectadas > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            cerrar();
        }
    }

    /**
     * Verifica si el usuario tiene acceso al tema
     */
    public boolean tieneUsuarioAvatar(int usuarioId, int avatarId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuario_avatares WHERE usuario_id = ? AND avatar_id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            stmt.setInt(2, avatarId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    /**
     * Obtiene la ruta img de un avatar
     */
    public String obtenerImgPorTemaId(int avatarId) throws SQLException {
        String sql = "SELECT img FROM avatares WHERE id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, avatarId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getString("img") : "/es/ies/puerto/img/usuario.png";
        }
    }

    /**
     * Obtiene la ruta img del avatar activo de un usuario
     * @param usuarioId id del usuario
     * @return ruta img del avatar activo
     * @throws SQLException error controlado
     */
    public String obtenerImgAvatarActivo(int usuarioId) throws SQLException {
        String sql = "SELECT a.img FROM avatares a " +
                     "JOIN usuario_avatares ua ON a.id = ua.avatar_id " +
                     "WHERE ua.usuario_id = ? AND ua.activo = 1";
        try (Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getString("img") : "/es/ies/puerto/img/usuario.png";
        }
    }

    /**
     * Metodo para comprar un avatar
     * @param usuario usuario que compra el avatar
     * @param avatarId id del avatar
     * @return true si se ha comprado el avatar, false si no se ha podido comprar
     * @throws SQLException error controlado
     */
    public boolean comprarAvatar(UsuarioEntity usuario, int avatarId) throws SQLException {
        String sql = "INSERT INTO usuario_avatares (usuario_id, avatar_id, activo) VALUES (?, ?, true) " +
                     "ON CONFLICT(usuario_id, avatar_id) DO UPDATE SET activo = true";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setInt(1, usuario.getId());
            preparedStatement.setInt(2, avatarId);
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
