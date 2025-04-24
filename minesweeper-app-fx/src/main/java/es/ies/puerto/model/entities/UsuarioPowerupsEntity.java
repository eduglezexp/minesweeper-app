package es.ies.puerto.model.entities;

import java.util.Objects;

/**
 * @author eduglezexp
 * @version 1.1.0
 */

public class UsuarioPowerupsEntity {
    private int usuarioId;
    private int powerupId;
    private int cantidad;

    /**
     * Constructor por defecto
     */
    public UsuarioPowerupsEntity() {
    }

    /**
     * Constructor con todas las propiedades
     * @param usuarioId del usuario
     * @param powerupId del powerup
     * @param cantidad de powerups
     */
    public UsuarioPowerupsEntity(int usuarioId, int powerupId, int cantidad) {
        this.usuarioId = usuarioId;
        this.powerupId = powerupId;
        this.cantidad = cantidad;
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

    public int getPowerupId() {
        return powerupId;
    }

    public void setPowerupId(int powerupId) {
        this.powerupId = powerupId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "UsuarioPowerupsEntity [usuarioId=" + usuarioId + ", powerupId=" + powerupId + ", cantidad=" + cantidad + "]";
    }


    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UsuarioPowerupsEntity)) {
            return false;
        }
        UsuarioPowerupsEntity usuarioPowerupsEntity = (UsuarioPowerupsEntity) o;
        return usuarioId == usuarioPowerupsEntity.usuarioId && powerupId == usuarioPowerupsEntity.powerupId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuarioId, powerupId);
    }
}   