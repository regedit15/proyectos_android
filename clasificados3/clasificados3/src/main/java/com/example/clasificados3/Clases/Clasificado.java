package com.example.clasificados3.Clases;

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
    String imagen;
    Categoria categoria;

    public Clasificado(int id, Usuario usuario, String titulo, String descripcion, Double precio, String imagen, Categoria categoria)
    {
        this.id = id;
        this.usuario = usuario;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagen = imagen;
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
