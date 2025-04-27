package es.ies.puerto.model.entities;

import java.util.Objects;

/**
 * @author eduglezexp
 * @version 1.1.0
 */

public class UsuarioAvataresEntity {
    private int usuarioId;
    private int avatarId;
    private boolean activo = true;

    /**
     * Constructor por defecto
     */
    public UsuarioAvataresEntity() {
    }

    /**
     * Constructor con todas las propiedades
     * @param usuarioId del usuario
     * @param avatarId del avatar
     */
    public UsuarioAvataresEntity(int usuarioId, int avatarId, boolean activo) {
        this.usuarioId = usuarioId;
        this.avatarId = avatarId;
        this.activo = activo;
    }

    /**
     * Getters and Setters
     */
    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getAvatarId() {
        return avatarId;
    }

    public void setAvatarId(int temaId) {
        this.avatarId = temaId;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "UsuarioAvataresEntity [usuarioId=" + usuarioId + ", avatarId=" + avatarId + ", activo=" + activo + "]";
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UsuarioAvataresEntity)) {
            return false;
        }
        UsuarioAvataresEntity usuarioAvataresEntity = (UsuarioAvataresEntity) o;
        return usuarioId == usuarioAvataresEntity.usuarioId && avatarId == usuarioAvataresEntity.avatarId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuarioId, avatarId);
    }    
}
