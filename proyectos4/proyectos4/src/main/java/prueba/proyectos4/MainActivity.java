package prueba.proyectos4;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import Clases.Producto;
import prueba.proyectos4.Clases.Metodos;

public class MainActivity extends Activity
{
    public String ip = "10.0.0.5";
    Metodos metodos = new Metodos(ip);
    EditText ed_id;
    EditText ed_nombre;
    EditText ed_precio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //----- Solucion Error HttpPost
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitNetwork().build();
        StrictMode.setThreadPolicy(policy);
        //-------------------------------

        ed_id =(EditText)findViewById(R.id.ed_id);
        ed_nombre = (EditText)findViewById(R.id.ed_nombre);
        ed_precio = (EditText)findViewById(R.id.ed_precio);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    //---------  Eventos OnClick

    public void enviar(View v)
    {
        Producto x = new Producto();
        x.setNombre( "" + ed_nombre.getText());
        x.setPrecio( Double.parseDouble("" + ed_precio.getText()));
        metodos.insertarProducto(x);
    }

    public void buscarProducto(View v)
    {
        Producto x = metodos.getProducto("" + ed_id.getText());
        ed_id.setText("" + x.getId());
        ed_nombre.setText(x.getNombre());
        ed_precio.setText("" + x.getPrecio());
    }

    public void eliminar(View v)
    {
        metodos.eliminarProducto("" + ed_id.getText());
        ed_id.setText("");
        ed_nombre.setText("");
        ed_precio.setText("");
    }

    public void modificar(View v)
    {
        Producto x = new Producto();
        x.setId(Integer.parseInt("" + ed_id.getText()));
        x.setNombre( "" + ed_nombre.getText());
        x.setPrecio( Double.parseDouble("" + ed_precio.getText()));
        metodos.modificarProducto(x);
    }

    public void limpiar(View v)
    {
        ed_id.setText("");
        ed_nombre.setText("");
        ed_precio.setText("");
    }

    public void cambiarLayout(View v)
    {
        Intent i = new Intent(this, Listado.class );
        startActivity(i);
    }


}
