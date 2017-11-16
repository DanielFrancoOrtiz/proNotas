package com.proyect.notas.Daos;

import java.sql.Date;
import java.sql.Time;

public class NotaTarea {

    private int id;
    private String titulo;
    private String descripcion;
    private int tipo;
    private Date fecha;
    private Time hora;

    public NotaTarea(int id, String titulo, String descripcion, int tipo, Date fecha, Time hora) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.fecha = fecha;
        this.hora = hora;
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
}
