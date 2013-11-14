package com.example.clasificados3.Clases;

/**
 * Created by martincho on 13/11/13.
 */
public class Imagen
{
    int id;
    Clasificado clasificado;
    String nombre;

    public Imagen()
    {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Clasificado getClasificado() {
        return clasificado;
    }

    public void setClasificado(Clasificado clasificado) {
        this.clasificado = clasificado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
