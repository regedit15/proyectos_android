package com.example.clasificados3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.clasificados3.Clases.Clasificado;
import com.example.clasificados3.Clases.Imagen;
import com.example.clasificados3.Clases_Auxiliares.AdaptadorListado;
import com.example.clasificados3.Controladores.Metodos;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by martincho on 16/11/13.
 */
public class ModificarClasificado extends Activity implements AdapterView.OnItemSelectedListener
{
    Metodos metodos = new Metodos(MainActivity.ip);
    int categoriaSeleccionada;
    Clasificado clasificado = new Clasificado();
    ListView lv_imagenes;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modificar_clasificado);


        //------ Cargar combo/spinner Categoria
        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.sp_categorias);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> items = new ArrayList<String>();

        for (int i = 0; i < MainActivity.categorias.size() ;i++)
        {
            items.add(MainActivity.categorias.get(i).getNombre());
        }
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
        //--------------------------------------------------------------

        //se obtiene el clasificado segun el id obtenido por parametro
        Bundle bundle = getIntent().getExtras();
//        clasificado.setId(bundle.getInt("idClasificado"));
        clasificado.setId(49);
        clasificado = metodos.getClasificado(clasificado);



        //--------------------------        Carga de clasificados

//        clasificados = metodos.getClasificados();

        lv_imagenes = (ListView)findViewById(R.id.lv_imagenes);
        lv_imagenes.setAdapter(new AdaptadorListado(this, R.layout.imagen_modificar_clasificado, clasificado.getImagenes()) {
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada != null)
                {
                    ImageView iv_imagen = (ImageView)view.findViewById(R.id.iv_imagen);
                    if (iv_imagen != null)
                    {
                        iv_imagen.setImageDrawable(metodos.imageOperations(MainActivity.pathImagenesServidor + ((Imagen)entrada).getNombre()));
                    }
                }
            }
        });


        //se sobreescribe el metodo on click de la imagen
//        lv_imagenes.setOnItemClickListener(new AdapterView.OnItemClickListener()
//        {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
//            {
//                eliminarImagen(position);
//            }
//        });


        //setea el largo de la lista en funcion de la suma del tamaño de todos sus hijos
        setListViewHeightBasedOnChildren(lv_imagenes);








    }


    public void setListViewHeightBasedOnChildren(ListView listView)
    {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }



    public void eliminarImagen(int posicion)
    {
        new AlertDialog.Builder(this)
                .setTitle("" + posicion)
                .setPositiveButton("Aceptar",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {}
                        }).show();
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

