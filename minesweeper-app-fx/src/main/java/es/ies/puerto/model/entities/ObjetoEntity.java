package es.ies.puerto.model.entities;

import java.util.Objects;

public class ObjetoEntity {
    private int id;
    private String objeto;
    private String ejemplo;

    /**
     * Constructor por defecto
     */
    public ObjetoEntity() {
    }

    /**
     * Constructor con las propiedad identificativa id
     * @param id de la palabra
     */
    public ObjetoEntity(int id) {
        this.id = id;
    }

    /**
     * Constructor con todas las propiedades
     * @param id del objeto
     * @param objeto del usuario
     * @param ejemplo del objeto
     */
    public ObjetoEntity(int id, String objeto, String ejemplo) {
        this.id = id;
        this.objeto = objeto;
        this.ejemplo = ejemplo;
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

    public String getObjeto() {
        return objeto;
    }

    public void setObjeto(String objeto) {
        this.objeto = objeto;
    }

    public String getEjemplo() {
        return ejemplo;
    }

    public void setEjemplo(String ejemplo) {
        this.ejemplo = ejemplo;
    }

    @Override
    public String toString() {
        return "ObjetoEntity [id=" + id + ", objeto=" + objeto + ", ejemplo=" + ejemplo + "]";
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof ObjetoEntity)) {
            return false;
        }
        ObjetoEntity objetoEntity = (ObjetoEntity) o;
        return id == objetoEntity.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }    
}
