package prueba.proyectos4;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import Clases.Producto;
import prueba.proyectos4.Clases.Metodos;


/**
 * Created by martin on 13/10/13.
 */
public class Listado extends Activity
{
    MainActivity m = new MainActivity();

    Metodos metodos = new Metodos(m.ip);
    private ListView lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listado);



        ArrayList<Producto> productos = metodos.getProductos();


        lista = (ListView) findViewById(R.id.lv_listado);
        lista.setAdapter(new AdaptadorListado(this, R.layout.fila, productos){
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada != null) {
                    TextView tv_nombre = (TextView) view.findViewById(R.id.tv_nombre);
                    if (tv_nombre != null)
                        tv_nombre.setText(((Producto) entrada).getNombre());

                    TextView tv_precio = (TextView) view.findViewById(R.id.tv_precio);
                    if (tv_precio != null)
                        tv_precio.setText("" + ((Producto) entrada).getPrecio());
                }
            }
        });








    }
}
