package com.proyect.notas.Daos;

/**
 * Created by Franco on 20/11/2017.
 */

public class FotoVideo {
    int id;
    String nombre;
    String direccion;
    int tipo;

    public FotoVideo(int id, String nombre, String direccion,int tipo) {
        this.id = id;
        this.nombre = nombre;
        this.direccion = direccion;
        this.tipo=tipo;
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

    public String getdireccion() {
        return direccion;
    }

    public void setdireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }
}
