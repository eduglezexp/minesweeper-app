package es.ies.puerto.model.entities;

import java.util.Objects;

/**
 * @author eduglezexp
 * @version 1.1.0
 */

public class UsuarioTemasEntity {
    private int usuarioId;
    private int temaId;
    private boolean activo = true;

    /**
     * Constructor por defecto
     */
    public UsuarioTemasEntity() {
    }

    /**
     * Constructor con todas las propiedades
     * @param usuarioId del usuario
     * @param temaId del tema
     */
    public UsuarioTemasEntity(int usuarioId, int temaId, boolean activo) {
        this.usuarioId = usuarioId;
        this.temaId = temaId;
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

    public int getTemaId() {
        return temaId;
    }

    public void setTemaId(int temaId) {
        this.temaId = temaId;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    @Override
    public String toString() {
        return "UsuarioTemasEntity [usuarioId=" + usuarioId + ", temaId=" + temaId + ", activo=" + activo + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UsuarioTemasEntity)) {
            return false;
        }
        UsuarioTemasEntity usuarioTemasEntity = (UsuarioTemasEntity) o;
        return usuarioId == usuarioTemasEntity.usuarioId && temaId == usuarioTemasEntity.temaId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuarioId, temaId);
    }
}
