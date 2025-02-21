package com.tarea.entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "Tareas")
public class Tarea {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "fecha")
    private LocalDate fecha;
    @Column(name = "hecho")
    private boolean hecho;

    public Tarea() {}

    public Tarea( String titulo, String descripcion, boolean hecho, LocalDate fecha) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.hecho = hecho;
        this.fecha = fecha;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setHecho(boolean realizar) {
        this.hecho = realizar;
    }

    public String getTitulo() {
        return this.titulo;
    }

    public void setTitulo(String texto) {
        this.titulo = texto;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String texto) {
        this.descripcion = texto;
    }

    public long getId() {
        return this.id;
    }

    public boolean getHecho(){return this.hecho;}



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("+-----------------------------+\n");
        sb.append("| Tarea                       \n");
        sb.append("+-----------------------------+\n");
        sb.append("| ID: " + id + "\n");
        sb.append("| Título: " + titulo + "\n");
        sb.append("| Descripción: " + descripcion + "\n");
        sb.append("| Fecha: " + fecha + "\n");
        sb.append("| Hecho: " + hecho + "\n");
        sb.append("+-----------------------------+\n");
        return sb.toString();
    }

}
