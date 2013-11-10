package com.example.clasificados3.Clases;

/**
 * Created by martincho on 09/11/13.
 */
public class Usuario
{
    int id;
    String usuario;
    String password;
    String correo;

    public Usuario(int id, String usuario, String password, String correo)
    {
        this.id = id;
        this.usuario = usuario;
        this.password = password;
        this.correo = correo;
    }

    public Usuario()
    {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
