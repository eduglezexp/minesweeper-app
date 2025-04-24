package es.ies.puerto.config;

import es.ies.puerto.model.entities.UsuarioEntity;

/**
 * @author eduglezexp
 * @version 1.0.0
 */

public class Sesion {
    private static String cssTemaActivo;
    private static UsuarioEntity usuario;

    public static boolean setCssTemaActivo(String css) {
        if (css == null || css.isBlank()) return false;
        cssTemaActivo = css;
        return true;
    }

    public static String getCssTemaActivo() {
        return cssTemaActivo;
    }

    public static void setUsuario(UsuarioEntity usuario) {
        Sesion.usuario = usuario;
    }

    public static UsuarioEntity getUsuario() {
        return usuario;
    }
}
