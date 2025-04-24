package es.ies.puerto.model.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.ies.puerto.model.entities.UsuarioEntity;
import es.ies.puerto.model.entities.UsuarioTemasEntity;
import javafx.scene.Scene;

/**
 * @author eduglezexp
 * @version 1.0.0
 */

public class UsuarioTemasService extends UsuarioService {
    /**
     * Constructor por defecto
     */
    public UsuarioTemasService() {

    }

    /**
     * Contructor con la ruta del archivo de la bbdd
     * @param unaRutaArchivoBD path de la bbdd
     * @throws SQLException error controlado
     */
    public UsuarioTemasService(String unaRutaArchivoBD) throws SQLException {
        super(unaRutaArchivoBD);
    }

    /**
     * Metodo que obtiene los temas de un usuario por su id
     * @param usuarioId id del usuario
     * @return lista de temas del usuario
     * @throws SQLException error controlado
     */
    public List<UsuarioTemasEntity> obtenerUsuariosTemasPorId(int usuarioId) throws SQLException {
        String sql = "SELECT * FROM usuario_temas WHERE usuario_id = ?";
        return obtenerUsuarioPowerups(sql, String.valueOf(usuarioId));
    }

    /**
     * Metodo que obtiene todos los temas de un usuario
     * @return lista de temas del usuario
     * @throws SQLException error controlado
     */
    public List<UsuarioTemasEntity> obtenerUsuariosTemas() throws SQLException{
        String sql = "SELECT * FROM usuario_temas";
        return obtenerUsuarioPowerups(sql);
    }

    /**
     * Metodo que obtiene los temas de un usuario por su id
     * @param usuarioId id del usuario
     * @return lista de temas del usuario
     * @throws SQLException error controlado
     */
    private List<UsuarioTemasEntity> obtenerUsuarioPowerups(String sql, String... parametros) throws SQLException{
        List<UsuarioTemasEntity> temas = new ArrayList<UsuarioTemasEntity>();
        try {
            PreparedStatement sentencia = getConnection().prepareStatement(sql);
            for (int i = 0; i < parametros.length; i++) {
                sentencia.setString(i + 1, parametros[i]);
            }
            ResultSet resultado = sentencia.executeQuery();
            while(resultado.next()){
                int usuarioId = resultado.getInt("usuario_id");
                int temaId = resultado.getInt("tema_id");
                boolean activo = resultado.getBoolean("activo");
                UsuarioTemasEntity usuarioTemasEntity = new UsuarioTemasEntity(usuarioId, temaId, activo);
                temas.add(usuarioTemasEntity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrar();
        }
        return temas;
    }

    /**
     * Método para actualizar el tema activo de un usuario
     * @param activo true para activar, false para desactivar
     * @param usuarioId id del usuario
     * @param temaId id del tema
     * @return true si se actualizó correctamente
     */
    public boolean actualizarUsuarioTemas(boolean activo, int usuarioId, int temaId) throws SQLException {
        try {
            if (activo) {
                String desactivarSql = "UPDATE usuario_temas SET activo = 0 WHERE usuario_id = ?";
                try (PreparedStatement stmt = getConnection().prepareStatement(desactivarSql)) {
                    stmt.setInt(1, usuarioId);
                    stmt.executeUpdate();
                }
            }
            String sql = "UPDATE usuario_temas SET activo = ? WHERE usuario_id = ? AND tema_id = ?";
            try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
                stmt.setBoolean(1, activo);
                stmt.setInt(2, usuarioId);
                stmt.setInt(3, temaId);
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
    public boolean tieneUsuarioTema(int usuarioId, int temaId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM usuario_temas WHERE usuario_id = ? AND tema_id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            stmt.setInt(2, temaId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    /**
     * Obtiene la ruta CSS de un tema
     */
    public String obtenerCssPorTemaId(int temaId) throws SQLException {
        String sql = "SELECT css FROM temas WHERE id = ?";
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            stmt.setInt(1, temaId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getString("css") : "/css/style.css";
        }
    }

    /**
     * Obtiene la ruta CSS del tema activo de un usuario
     * @param usuarioId id del usuario
     * @return ruta CSS del tema activo
     * @throws SQLException error controlado
     */
    public String obtenerCssTemaActivo(int usuarioId) throws SQLException {
        String sql = "SELECT t.css FROM temas t " +
                     "JOIN usuario_temas ut ON t.id = ut.tema_id " +
                     "WHERE ut.usuario_id = ? AND ut.activo = 1";
        try (Connection conn = getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            return rs.next() ? rs.getString("css") : "/es/ies/puerto/css/style.css";
        }
    }

    /**
     * Metodo para comprar un tema
     * @param usuario usuario que compra el tema
     * @param temaId id del tema
     * @return true si se ha comprado el tema, false si no se ha podido comprar
     * @throws SQLException error controlado
     */
    public boolean comprarTema(UsuarioEntity usuario, int temaId) throws SQLException {
        String sql = "INSERT INTO usuario_temas (usuario_id, tema_id, activo) VALUES (?, ?, true) " +
                     "ON CONFLICT(usuario_id, tema_id) DO UPDATE SET activo = true";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setInt(1, usuario.getId());
            preparedStatement.setInt(2, temaId);
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
