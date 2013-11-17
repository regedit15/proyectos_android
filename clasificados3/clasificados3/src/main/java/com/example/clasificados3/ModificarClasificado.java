package com.example.clasificados3;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
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
    EditText et_titulo;
    EditText et_descripcion;
    EditText et_precio;
    ArrayList<String> imagenes = new ArrayList<String>();


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



        //------------- se obtiene el clasificado segun el id obtenido por parametro
        Bundle bundle = getIntent().getExtras();
//        clasificado.setId(bundle.getInt("idClasificado"));
        clasificado.setId(49);
        clasificado = metodos.getClasificado(clasificado);
        //---------------------------------------

        //----- se setean los textos en funcion del clasificado
        et_titulo = (EditText) findViewById(R.id.et_titulo);
        et_descripcion = (EditText) findViewById(R.id.et_descripcion);
        et_precio = (EditText) findViewById(R.id.et_precio);

        et_titulo.setText(clasificado.getTitulo());
        et_descripcion.setText(clasificado.getDescripcion());
        et_precio.setText(clasificado.getPrecio().toString());
        //-------------------------------------------------------------

        //------ se guardan los nombre de las imagenes que posee el clasificado
        for (int i=0; i < clasificado.getImagenes().size(); i++)
        {
            imagenes.add(clasificado.getImagenes().get(i).getNombre());
        }
        //-----------------------------


        //carga las imagenes del clasificado al listView
        cargarImagenes();


    }



    //quita las imagenes seleccionadas de la lista imagenes
    public void quitarImagenes(View view)
    {
        ArrayList<Integer> indices = metodos.checkBoxSeleccionados(lv_imagenes, (CheckBox)lv_imagenes.findViewById(R.id.cb_checkBox));

        for (int i=0; i < indices.size(); i++)
        {
            int in = indices.get(i) - i;
            clasificado.getImagenes().remove(in);
        }
        cargarImagenes();
    }






    public void cargarImagenes()
    { //-----------------------  Carga de Imagenese del Clasificado

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

        //setea el largo de la lista en funcion de la suma del tamaño de todos sus hijos
        metodos.setListViewHeightBasedOnChildren(lv_imagenes);
        //--------------------------------------------------------------------------------
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

