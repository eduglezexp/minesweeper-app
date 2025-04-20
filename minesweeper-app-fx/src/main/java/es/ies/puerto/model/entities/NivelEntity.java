package es.ies.puerto.model.entities;

import java.util.Objects;

/**
 * @author eduglezexp
 * @version 1.1.0
 */

public class NivelEntity {

    private int id;
    private String dificultad;
    private String descripcion;

    /**
     * Constructor por defecto
     */
    public NivelEntity() {
    }

    /**
     * Constructor con las propiedad identificativa id
     * @param id de la palabra
     */
    public NivelEntity(int id) {
        this.id = id;
    }

    /**
     * Constructor con todas las propiedades
     * @param id del nivel
     * @param nivel del usuario
     * @param descripcion del nivel
     */
    public NivelEntity(int id, String nivel, String descripcion) {
        this.id = id;
        this.dificultad = nivel;
        this.descripcion = descripcion;
    }

    /**
     * Getters and Setters
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String nivel) {
        this.dificultad = nivel;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "NivelEntity [id=" + id + ", dificultad=" + dificultad + ", descripcion=" + descripcion + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof NivelEntity)) {
            return false;
        }
        NivelEntity nivelEntitySqlite = (NivelEntity) o;
        return id == nivelEntitySqlite.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}