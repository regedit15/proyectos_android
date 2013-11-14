package com.example.clasificados3.Clases;

import java.util.ArrayList;

/**
 * Created by martincho on 10/11/13.
 */
public class Clasificado
{
    int id;
    Usuario usuario;
    String titulo;
    String descripcion;
    Double precio;
    ArrayList<Imagen> imagenes = new ArrayList<Imagen>();
    Categoria categoria;

    public Clasificado(int id, Usuario usuario, String titulo, String descripcion, Double precio, ArrayList<Imagen> imagenes, Categoria categoria)
    {
        this.id = id;
        this.usuario = usuario;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagenes = imagenes;
        this.categoria = categoria;
    }

    public Clasificado()
    {

    }


    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
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

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public ArrayList<Imagen> getImagenes() {
        return imagenes;
    }

    public void setImagenes(ArrayList<Imagen> imagenes) {
        this.imagenes = imagenes;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
