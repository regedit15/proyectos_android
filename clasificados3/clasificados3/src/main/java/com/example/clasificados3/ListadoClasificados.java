package com.example.clasificados3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.clasificados3.Clases.Categoria;
import com.example.clasificados3.Clases.Clasificado;
import com.example.clasificados3.Clases_Auxiliares.AdaptadorListado;
import com.example.clasificados3.Controladores.Metodos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by martincho on 14/11/13.
 */
public class ListadoClasificados extends Activity implements AdapterView.OnItemSelectedListener
{
    Metodos metodos = new Metodos(MainActivity.ip);
    ListView lv_clasificados;
    ArrayList<Categoria> categorias;
    int categoriaSeleccionada;
    ArrayList<Clasificado> clasificados;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listado_clasificados);



        categorias = metodos.getCategorias();



        //------ Cargar combo/spinner Categoria
        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.sp_categorias);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> items = new ArrayList <String>();

        for (int i = 0; i < categorias.size() ;i++)
        {
            items.add(categorias.get(i).getNombre());
        }
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        //--------------------------------------------------------------




        clasificados = metodos.getClasificados();


        lv_clasificados = (ListView)findViewById(R.id.lv_clasificados);
        lv_clasificados.setAdapter(new AdaptadorListado(this, R.layout.fila, clasificados) {
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada != null) {
                    TextView tv_nombre = (TextView) view.findViewById(R.id.tv_nombre);
                    if (tv_nombre != null) {
                        tv_nombre.setText(((Clasificado) entrada).getTitulo());
                    }

                    TextView tv_precio = (TextView) view.findViewById(R.id.tv_precio);
                    if (tv_precio != null) {
                        tv_precio.setText("" + ((Clasificado) entrada).getPrecio());
                    }


                    ImageView iv_imagen = (ImageView) view.findViewById(R.id.iv_imagen);
                    if (tv_precio != null) {
                        //se muestra solo la primer imagen, si es que tiene
                        if (((Clasificado) entrada).getImagenes().size() > 0)
                        {
                            iv_imagen.setImageDrawable(metodos.imageOperations(MainActivity.pathImagenesServidor + ((Clasificado) entrada).getImagenes().get(0).getNombre()));
                        }
                    }
                }
            }
        });


        lv_clasificados.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
               verClasificado(position);
            }
        });

    }




    public void verClasificado(int itemSeleccionado)
    {
        Intent i = new Intent(this, DetalleClasificado.class );
        i.putExtra("idClasificado", clasificados.get(itemSeleccionado).getId());
        startActivity(i);
    }






    //--------------------   Categoria
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
    {
        categoriaSeleccionada = position;
    }

    public void onNothingSelected(AdapterView<?> arg0)
    {
        // TODO Auto-generated method stub
    }
    //--------------------------------------------------------------------------------
}
