package com.boostmyfool.beastore.models;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public class ProductosDTO {
    @NotEmpty(message = "El nombre es requerido")
    private String nombre;
    @NotEmpty(message = "La marca es requerida")
    private String marca;
    @NotEmpty(message = "La categoria es requerida")
    private String categoria;
    @Min(0)
    private double precio;

    @Size(min = 10,message = "La descripcion debe contener por lo menos 10 caracteres")
    @Size(max = 2000,message = "La descripcion no debe superar los 2000 caracteres")
    private String descripcion;

    private MultipartFile imagenArchivo;

    public @NotEmpty(message = "El nombre es requerido") String getNombre() {
        return nombre;
    }

    public void setNombre(@NotEmpty(message = "El nombre es requerido") String nombre) {
        this.nombre = nombre;
    }

    public @NotEmpty(message = "La marca es requerida") String getMarca() {
        return marca;
    }

    public void setMarca(@NotEmpty(message = "La marca es requerida") String marca) {
        this.marca = marca;
    }

    public @NotEmpty(message = "La categoria es requerida") String getCategoria() {
        return categoria;
    }

    public void setCategoria(@NotEmpty(message = "La categoria es requerida") String categoria) {
        this.categoria = categoria;
    }

    @Min(0)
    public double getPrecio() {
        return precio;
    }

    public void setPrecio(@Min(0) double precio) {
        this.precio = precio;
    }

    public @Size(min = 10, message = "La descripcion debe contener por lo menos 10 caracteres") @Size(max = 2000, message = "La descripcion no debe superar los 2000 caracteres") String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(@Size(min = 10, message = "La descripcion debe contener por lo menos 10 caracteres") @Size(max = 2000, message = "La descripcion no debe superar los 2000 caracteres") String descripcion) {
        this.descripcion = descripcion;
    }

    public MultipartFile getImagenArchivo() {
        return imagenArchivo;
    }

    public void setImagenArchivo(MultipartFile imagenArchivo) {
        this.imagenArchivo = imagenArchivo;
    }
}
