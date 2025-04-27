package es.ies.puerto.config;

import es.ies.puerto.model.entities.UsuarioEntity;

/**
 * @author eduglezexp
 * @version 1.0.0
 */

public class Sesion {
    private static String cssTemaActivo;
    private static String imgAvatarAtivo;
    private static UsuarioEntity usuario;

    /**
     * Getters and Setters
     */
    public static boolean setCssTemaActivo(String css) {
        if (css == null || css.isBlank()) return false;
        cssTemaActivo = css;
        return true;
    }

    public static String getCssTemaActivo() {
        return cssTemaActivo;
    }

    public static boolean setImgAvatarAtivo(String img) {
        if (img == null || img.isBlank()) return false;
        Sesion.imgAvatarAtivo = img;
        return true;
    }

    public static String getImgAvatarAtivo() {
        return imgAvatarAtivo;
    }

    public static void setUsuario(UsuarioEntity usuario) {
        Sesion.usuario = usuario;
    }

    public static UsuarioEntity getUsuario() {
        return usuario;
    }
}
