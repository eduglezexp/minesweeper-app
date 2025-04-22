package es.ies.puerto.model.entities;

import java.util.Objects;

/**
 * @author eduglezexp
 * @version 1.1.0
 */

public class UsuarioEntity {

    private int id;  
    private String user;
    private String name;
    private String email;
    private String password;
    private int puntos;
    private int victorias;
    private int derrotas;
    private int idNivel;
    private int rachaActual;   
    private int mejorRacha;

    /**
     * Constructor por defecto
     */
    public UsuarioEntity() {
    }

    /**
     * Constructor con las propiedades identificativas
     * @param user del usuario
     * @param email del usuario
     */
    public UsuarioEntity(String user, String email) {
        this.user = user;
        this.email = email;
    }

    /**
     * Constructor con algunas propiedades
     * @param usuario nick del usuario
     * @param email del usuario
     * @param nombre del usuario
     * @param password del usuario
     */
    public UsuarioEntity(String user, String email, String name, String password) {
        this.user = user;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    /**
     * Constructor con todas las propiedades
     * @param id identificador del usuario
     * @param usuario nick del usuario
     * @param email del usuario
     * @param nombre del usuario
     * @param password del usuario
     * @param puntos del usuario
     * @param victorias del usuario
     * @param derrotas del usuario
     * @param idNivel del usuario
     * @param rachaActual del usuario
     * @param mejorRacha del usuario
     */
    public UsuarioEntity(int id, String user, String email, String name, String password, int puntos,
        int victorias, int derrotas, int idNivel, int rachaActual, int mejorRacha) {
        this.id = id;
        this.user = user;
        this.email = email;
        this.name = name;
        this.password = password;
        this.puntos = puntos;
        this.victorias = victorias;
        this.derrotas = derrotas;
        this.idNivel = idNivel;
        this.rachaActual = rachaActual;
        this.mejorRacha = mejorRacha;
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
    
    public String getUser() {
        return user;
    }

    public void setUser(String usuario) {
        this.user = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String nombre) {
        this.name = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public int getVictorias() {
        return victorias;
    }

    public void setVictorias(int victorias) {
        this.victorias = victorias;
    }

    public int getDerrotas() {
        return derrotas;
    }

    public void setDerrotas(int derrotas) {
        this.derrotas = derrotas;
    }  

    public int getIdNivel() {
        return idNivel;
    }

    public void setIdNivel(int idNivel) {
        this.idNivel = idNivel;
    }  

    public int getRachaActual() {
        return rachaActual;
    }

    public void setRachaActual(int rachaActual) {
        this.rachaActual = rachaActual;
    }

    public int getMejorRacha() {
        return mejorRacha;
    }

    public void setMejorRacha(int mejorRacha) {
        this.mejorRacha = mejorRacha;
    }

    @Override
    public String toString() {
        return "UsuarioEntity [id=" + id + ", user=" + user + ", name=" + name + ", email=" + email + ", password="
                + password + ", puntos=" + puntos + ", victorias=" + victorias + ", derrotas=" + derrotas
                + ", rachaActual=" + rachaActual + ", mejorRacha=" + mejorRacha + ", idNivel=" + idNivel + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UsuarioEntity)) {
            return false;
        }
        UsuarioEntity usuarioEntitySqlite = (UsuarioEntity) o;
        return Objects.equals(user, usuarioEntitySqlite.user) && Objects.equals(email, usuarioEntitySqlite.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, email);
    }
}