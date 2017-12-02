package com.proyect.notas.Daos;

import java.io.Serializable;

/**
 * Created by Franco on 20/11/2017.
 */

public class FotoVideoAudio implements Serializable {
    int id;
    String nombre;
    String direccion;
    int tipo;
    String descripcion;
    int idNota;

    public FotoVideoAudio(int id, String nombre, String direccion, int tipo, String descripcion, int idNota) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.tipo=tipo;
        this.descripcion=descripcion;
        this.idNota=idNota;
    }

    public int getId() {
        return id;
    }
    
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getIdNota() {
        return idNota;
    }

    public void setIdNota(int idNota) {
        this.idNota = idNota;
    }
}
