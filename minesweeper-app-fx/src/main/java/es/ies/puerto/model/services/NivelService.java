package es.ies.puerto.model.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.ies.puerto.model.abstractas.Conexion;
import es.ies.puerto.model.entities.NivelEntity;

/**
 * @author eduglezexp
 * @version 1.0.0
 */

public class NivelService extends Conexion{

    /**
     * Constructor por defecto
     */
    public NivelService() {

    }

    /**
     * Contructor con la ruta del archivo de la bbdd
     * @param unaRutaArchivoBD path de la bbdd
     * @throws SQLException error controlado
     */
    public NivelService(String unaRutaArchivoBD) throws SQLException {
        super(unaRutaArchivoBD);
    }
    
    /**
     * Metodo para obtener el nivel del usuario
     * @throws SQLException error controlado
     */
    public List<NivelEntity> obtenerNivelPorUsuario(int idNivel, String idioma) throws SQLException {
        String sql = "SELECT n.id, nt.dificultad, nt.descripcion " +
                     "FROM niveles n " +
                     "JOIN nivel_traducciones nt ON n.id = nt.id_nivel " +
                     "WHERE n.id = ? AND nt.idioma = ?";
        return obtenerNivel(sql, String.valueOf(idNivel), idioma);
    }

    /**
     * Metodo para obtener el nivel 
     * @throws SQLException error controlado
     */
    private List<NivelEntity> obtenerNivel(String sql, String... parametros) throws SQLException{
        List<NivelEntity> niveles = new ArrayList<NivelEntity>();
        try {
            PreparedStatement sentencia = getConnection().prepareStatement(sql);
            for (int i = 0; i < parametros.length; i++) {
                sentencia.setString(i + 1, parametros[i]);
            }
            ResultSet resultado = sentencia.executeQuery();
            while(resultado.next()){
                int nivelId = resultado.getInt("id");
                String dificultadStr = resultado.getString("dificultad");
                String descripcionStr = resultado.getString("descripcion");
                NivelEntity nivel = new NivelEntity(nivelId, dificultadStr, descripcionStr);
                niveles.add(nivel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrar();
        }
        return niveles;
    }
}
