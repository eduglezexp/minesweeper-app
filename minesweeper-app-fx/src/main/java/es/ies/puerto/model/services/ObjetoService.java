package es.ies.puerto.model.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import es.ies.puerto.model.abstractas.Conexion;
import es.ies.puerto.model.entities.NivelEntity;
import es.ies.puerto.model.entities.ObjetoEntity;

/**
 * @author eduglezexp
 * @version 1.0.0
 */

public class ObjetoService extends Conexion{

    /**
     * Constructor por defecto
     */
    public ObjetoService() {

    }

    /**
     * Contructor con la ruta del archivo de la bbdd
     * @param unaRutaArchivoBD path de la bbdd
     * @throws SQLException error controlado
     */
    public ObjetoService(String unaRutaArchivoBD) throws SQLException {
        super(unaRutaArchivoBD);
    }

    /**
     * Metodo para obtener el objeto 
     * @throws SQLException error controlado
     */
    private List<ObjetoEntity> obtenerObjeto(String sql, String... parametros) throws SQLException{
        List<ObjetoEntity> objetos = new ArrayList<ObjetoEntity>();
        try {
            PreparedStatement sentencia = getConnection().prepareStatement(sql);
            for (int i = 0; i < parametros.length; i++) {
                sentencia.setString(i + 1, parametros[i]);
            }
            ResultSet resultado = sentencia.executeQuery();
            while(resultado.next()){
                int nivelId = resultado.getInt("id");
                String objetoStr = resultado.getString("objeto");
                String ejemploStr = resultado.getString("ejemplo");
                ObjetoEntity objeto = new ObjetoEntity(nivelId, objetoStr, ejemploStr);
                objetos.add(objeto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            cerrar();
        }
        return objetos;
    }
}
