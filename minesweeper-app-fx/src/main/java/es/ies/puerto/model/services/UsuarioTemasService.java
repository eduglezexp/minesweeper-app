package es.ies.puerto.model.services;

import java.sql.SQLException;

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

}
