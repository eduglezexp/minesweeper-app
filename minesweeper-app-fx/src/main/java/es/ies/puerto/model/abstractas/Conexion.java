package es.ies.puerto.model.abstractas;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author eduglezexp
 * @version 1.0.0
 */

public abstract class Conexion {
    private String rutaArchivoBD;
    private Connection connection;

    /**
     * Constructor por defecto
     */
    public Conexion() {
    }

    /**
     * Constructor con path de conexion
     * @param unaRutaArchivoBD ruta de la bbdd
     * @throws SQLException error controlado de la bbdd
     * @throws FileNotFoundException error controlado de la ruta
     */
    public Conexion(String unaRutaArchivoBD) throws SQLException {
        if (unaRutaArchivoBD == null || unaRutaArchivoBD.isEmpty()) {
            throw new SQLException("El fichero es nulo o vacio");
        }
        File file = new File(unaRutaArchivoBD);
        if (!file.exists()) {
            throw new SQLException("No existe la bbdd: " +unaRutaArchivoBD);
        }
        rutaArchivoBD = unaRutaArchivoBD;
    }

    /**
     * Getters and Setters
     */
    public String getRutaArchivoBD() {
        return rutaArchivoBD;
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:sqlite:" + rutaArchivoBD);
    }

    /**
     * Funcion que conecta a la bbdd
     */
    protected Connection conectar() throws SQLException{
        if (connection == null) {
            connection = DriverManager.getConnection("jdbc:sqlite:" + rutaArchivoBD);
        }
        return connection;
    }

    /**
     * Funcion que cierra la conexion a la bbdd
     */
    protected void cerrar() throws SQLException{
        if (connection != null && !connection.isClosed()) {
            connection.close(); 
            connection = null;
        }
    }
}