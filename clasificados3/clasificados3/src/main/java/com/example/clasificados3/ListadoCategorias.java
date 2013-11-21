package com.example.clasificados3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.clasificados3.Clases.Categoria;
import com.example.clasificados3.Controladores.Metodos;

import java.util.ArrayList;

/**
 * Created by martincho on 20/11/13.
 */
public class ListadoCategorias extends Activity
{
    Metodos metodos = new Metodos(MainActivity.ip);
    ListView lv_categorias;
    String[] vectorCategotias= new String[MainActivity.categorias.size() - 1];;
    ArrayList<Categoria> categoriasSinTODAS = new ArrayList<Categoria>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listado_categoria);

        //se cargan todos los nombres de las categorias menos el de la categoria TODAS,
        //por eso i=1 en vez de i=0
        categoriasSinTODAS = MainActivity.categorias;
        categoriasSinTODAS.remove(0);
        for (int i=0; i < categoriasSinTODAS.size(); i++)
        {
            vectorCategotias[i] = categoriasSinTODAS.get(i).getNombre();
        }

        lv_categorias = (ListView)findViewById(R.id.lv_categorias);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vectorCategotias);

        lv_categorias.setAdapter(adapter);


        //se sobreescribe el metodo on click de la fila y te envia al detalle del clasificado
        lv_categorias.setOnItemClickListener(new AdapterView.OnItemClickListener()
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
        Intent i = new Intent(this, ModificarCategoria.class );
        i.putExtra("idCategoria", categoriasSinTODAS.get(itemSeleccionado).getId());
        i.putExtra("nombreCategoria", categoriasSinTODAS.get(itemSeleccionado).getNombre());
        startActivity(i);
    }

    public void volver(View view)
    {
        Intent i = new Intent(this, Home.class );
        startActivity(i);
    }
}
