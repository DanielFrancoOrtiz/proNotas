package com.proyect.notas.Daos;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

public class NotaTarea implements Serializable {

    private int id;
    private String titulo;
    private String descripcion;
    private int tipo;
    private Date fecha;
    private Time hora;
    private boolean realizada;
    private String imagen;
    private String descripcionImagen;


    public NotaTarea(int id, String titulo, String descripcion, int tipo, Date fecha,
                     Time hora, boolean realizada, String imagen,String descripcionImagen) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.fecha = fecha;
        this.hora = hora;
        this.realizada = realizada;
        this.imagen=imagen;
        this.descripcionImagen = descripcionImagen;
    }

    public NotaTarea(String titulo, String descripcion, int tipo) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    @Override
    public String toString() {
        return getTitulo();
    }

    public boolean isRealizada() {
        return realizada;
    }

    public void setRealizada(boolean realizada) {
        this.realizada = realizada;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDescripcionImagen() {
        return descripcionImagen;
    }

    public void setDescripcionImagen(String descripcionImagen) {
        this.descripcionImagen = descripcionImagen;
    }
}
