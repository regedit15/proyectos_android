package Clases;

/**
 * Created by martincho on 01/10/13.
 */
public class Producto
{
    int id;
    String nombre;
    double precio;

    public Producto(int id, String nombre, double precio)
    {
        id = this.id;
        nombre = this.nombre;
        precio = this.precio;
    }

    public Producto()
    {

    }


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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
