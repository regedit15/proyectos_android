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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.example.clasificados3.Clases.Categoria;
import com.example.clasificados3.Clases.Clasificado;
import com.example.clasificados3.Clases.Imagen;
import com.example.clasificados3.Controladores.Metodos;

import java.io.File;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by martincho on 10/11/13.
 */
public class AltaClasificado extends Activity implements AdapterView.OnItemSelectedListener
{
    Metodos metodos = new Metodos(MainActivity.ip);
    EditText et_titulo;
    EditText et_descripcion;
    EditText et_precio;
    int categoriaSeleccionada;
    List<String> pathImagenes = new ArrayList <String>();


    private static final int SELECT_PICTURE = 1;
    private ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alta_clasificado);

        et_titulo = (EditText)findViewById(R.id.et_titulo);
        et_descripcion = (EditText)findViewById(R.id.et_descripcion);
        et_precio = (EditText)findViewById(R.id.et_precio);

        ArrayList<Categoria> categorias = MainActivity.categorias;
        categorias.remove(0);
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

        img = (ImageView)findViewById(R.id.iv_imagen1);
    }




    //-------------------------------- Butones
    public void nuevoClasificado(View view)
    {
        Clasificado x = new Clasificado();

        x.setUsuario(MainActivity.usuario);
        x.setTitulo(et_titulo.getText().toString());
        x.setDescripcion(et_descripcion.getText().toString());
        x.setPrecio(Double.parseDouble(et_precio.getText().toString()));
        x.setCategoria(MainActivity.categorias.get(categoriaSeleccionada));

        try
        {
            int idClasificado = metodos.insertarClasificado(x);

            Clasificado claisficado = new Clasificado();
            claisficado.setId(idClasificado);

            for (int i = 0; i < pathImagenes.size(); i++)
            {
                metodos.uploadFile(pathImagenes.get(i));

                Imagen imagen = new Imagen();

                imagen.setClasificado(claisficado);

                //se extrae el nombre del archivo desde el path
                File f = new File(pathImagenes.get(i));
                imagen.setNombre(f.getName());

                metodos.insertarImagen(imagen);
            }

            pathImagenes.clear();

            new AlertDialog.Builder(this)
                    .setTitle("Se ha creado un nuevo clasificado")
                    .setPositiveButton("Aceptar",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).show();
        }
        catch (Exception e)
        {
            e.printStackTrace();

            new AlertDialog.Builder(this)
                    .setTitle("Error...")
                    .setPositiveButton("Aceptar",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {}
                            }).show();
        }

        Intent i = new Intent(this, Home.class );
        startActivity(i);
    }

    public void cancelar(View view)
    {
        Intent i = new Intent(this, Home.class );
        startActivity(i);
    }

    public void seleccionarImagen(View view)
    {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_PICTURE);
    }

    //-------------------------------------------------------------


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
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
//                selectedImagePath = getPath(selectedImageUri);

//                System.out.println("Image Path : " + selectedImagePath);
                img.setImageURI(selectedImageUri);

                pathImagenes.add(getPath(selectedImageUri));
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
    //-------------------------------------------------------

}
