package com.boostmyfool.beastore.models;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "productos")
public class Productos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;
    private String marca;
    private String categoria;
    private double precio;

    @Column(columnDefinition = "TEXT")
    private String descripcion;
    private Date fechaCreado;
    private String imagenArchivo;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechaCreado() {
        return fechaCreado;
    }

    public void setFechaCreado(Date fechaCreado) {
        this.fechaCreado = fechaCreado;
    }

    public String getImagenArchivo() {
        return imagenArchivo;
    }

    public void setImagenArchivo(String imagenArchivo) {
        this.imagenArchivo = imagenArchivo;
    }
}