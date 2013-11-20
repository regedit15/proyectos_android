package com.example.clasificados3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.example.clasificados3.Clases.Categoria;
import com.example.clasificados3.Clases.Clasificado;
import com.example.clasificados3.Clases.Imagen;
import com.example.clasificados3.Clases_Auxiliares.AdaptadorListado;
import com.example.clasificados3.Controladores.HttpFileUploader;
import com.example.clasificados3.Controladores.Metodos;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
    ArrayList<Imagen> imagenesAntesDeModificar = new ArrayList<Imagen>();
    int SELECT_PICTURE = 1;
    ProgressBar pg_progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modificar_clasificado);

        pg_progressBar = (ProgressBar)findViewById(R.id.pg_progressBar);
        pg_progressBar.setVisibility(View.INVISIBLE);

        //------------- se obtiene el clasificado segun el id obtenido por parametro
        Bundle bundle = getIntent().getExtras();
        clasificado.setId(bundle.getInt("idClasificado"));
//        clasificado.setId(56);
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


        //------ Cargar combo/spinner Categoria
        // Spinner element
        Spinner spinner = (Spinner) findViewById(R.id.sp_categorias);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> items = new ArrayList<String>();

        for (int i = 0; i < MainActivity.categorias.size() ;i++)
        {
            Categoria c = MainActivity.categorias.get(i);
            items.add(c.getNombre());
        }
        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);

        //--------------------------------------------------------------

        //se guarda una copia de las imagenes que tenia el clasificado originalmente.
        //lo siguiente, es porque no se puede poner imagenesAntesDeModificar = clasificado.getImagenes()
        //porque si se elimina una imagen de clasificado.getImagene tambien se elimina de
        //imagenesAntesDeModificar, java...
        for (int i=0; i < clasificado.getImagenes().size(); i++)
        {
            imagenesAntesDeModificar.add(clasificado.getImagenes().get(i));
        }

        //carga las imagenes del clasificado al listView
        cargarImagenes();


        for(int i=0; i < MainActivity.categorias.size(); i++)
        {
            if (clasificado.getCategoria().getId() == MainActivity.categorias.get(i).getId())
            {
                spinner.setSelection(i);
                break;
            }
        }

    }


    //------------------------------------- Metodos

    public void eliminarClasificado()
    {
        metodos.eliminarClasificado(clasificado);
        Intent i = new Intent(this, MisClasificados.class );
        startActivity(i);
    }

    public void modificarClasificado()
    {
        pg_progressBar.setVisibility(View.VISIBLE);

        for (int a=0; a < imagenesAntesDeModificar.size(); a++)
        {
            Imagen i1 = imagenesAntesDeModificar.get(a);

            //si es 1 se elimina, si es 0 no
            int eliminarImagen = 1;
            for (int b=0; b < clasificado.getImagenes().size(); b++)
            {
                Imagen i2 = clasificado.getImagenes().get(b);

                if (i1.getId() == i2.getId())
                {
                    eliminarImagen = 0;
                }

            }
            //eliminar imagen vieja
            if (eliminarImagen == 1)
            {
                //si la imagen vieja, no se encuentra en la nueva lista de imagenes
                //quiere decir que el usuario la borro, entonces se elimina de la base de datos
                metodos.eliminarImagen(i1);
            }
        }







        for (int a=0; a < clasificado.getImagenes().size(); a++)
        {
            Imagen i1 = clasificado.getImagenes().get(a);

            if(i1.getId() == 0)
            {
                //insertar imagen nueva, porque esta imagen no tiene id (id=0)
                //por lo tanto es una nueva imagen
                Clasificado x = new Clasificado();

                x.setId(clasificado.getId());
                i1.setClasificado(x);

                metodos.insertarImagen(i1);
            }
        }

        clasificado.setTitulo(et_titulo.getText().toString());
        clasificado.setDescripcion(et_descripcion.getText().toString());
        clasificado.setPrecio(Double.parseDouble(et_precio.getText().toString()));
        clasificado.setCategoria(MainActivity.categorias.get(categoriaSeleccionada));

        metodos.modificarClasificado(clasificado);

        pg_progressBar.setVisibility(View.INVISIBLE);

        Intent i = new Intent(this, MisClasificados.class );
        startActivity(i);
    }

    public void confirmarEliminacionDeClasificado(View view)
    {
        new AlertDialog.Builder(this)
                .setMessage("¿Suguro desea eliminar este clasificado?")
                .setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        eliminarClasificado();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    public void confirmarModificacionDeClasificado(View view)
    {
        new AlertDialog.Builder(this)
                .setMessage("¿Suguro desea modificar este clasificado?")
                .setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        modificarClasificado();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }
    
    public void confirmarQuitarImagenes(View view)
    {
        new AlertDialog.Builder(this)
                .setMessage("¿Suguro desea eliminar las imagenes seleccionadas?")
                .setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        quitarImagenes();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }


    //quita las imagenes seleccionadas de la lista imagenes
    public void quitarImagenes()
    {
        ArrayList<Integer> indices = metodos.checkBoxSeleccionados(lv_imagenes, (CheckBox)lv_imagenes.findViewById(R.id.cb_checkBox));

        for (int i=0; i < indices.size(); i++)
        {
            int in = indices.get(i) - i;
            clasificado.getImagenes().remove(in);
        }
        cargarImagenes();
    }

    public void seleccionarImagen(View view)
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }

    public void cargarImagenes()
    { //-----------------------  Carga de Imagenese del Clasificado

        lv_imagenes = (ListView)findViewById(R.id.lv_imagenes);
        lv_imagenes.setAdapter(new AdaptadorListado(this, R.layout.imagen_modificar_clasificado, clasificado.getImagenes()) {
            @Override
            public void onEntrada(Object entrada, View view)
            {
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


    //------------ Metodos Subir imagen e Intent

    //se le pasa el path de la imagen y la sube al servidor
    public void uploadFile(String filename)
    {
        String nombreArchivo = "";
        try
        {
            FileInputStream fis = new FileInputStream(filename);
            HttpFileUploader htfu = new HttpFileUploader("http://10.0.0.3/prueba/Clasificados_SubirImagen.php","noparamshere", filename);
            htfu.doStart(fis);
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();

                String path = getPath(selectedImageUri);

                metodos.uploadFile(path);
                //se extrae el nombre del archivo desde el path
                File f = new File(path);

                //se agrega la imagen a la lista del clasificado a modificar
                Imagen x = new Imagen();
                x.setNombre(f.getName());
                clasificado.getImagenes().add(x);

                //se refrezca el listView de imagenes
                cargarImagenes();
            }
        }
    }

    public String getPath(Uri uri)
    {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    //------------------------------------------------------------------

}

