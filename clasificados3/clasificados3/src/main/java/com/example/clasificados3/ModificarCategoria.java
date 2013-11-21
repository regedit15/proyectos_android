package com.example.clasificados3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.clasificados3.Clases.Categoria;
import com.example.clasificados3.Controladores.Metodos;

/**
 * Created by martincho on 20/11/13.
 */
public class ModificarCategoria extends Activity
{
    Metodos metodos = new Metodos(MainActivity.ip);
    EditText et_nombre;
    Categoria categoria = new Categoria();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modificar_categoria);

        //se obtiene el clasificado segun el id obtenido por parametro
        Bundle bundle = getIntent().getExtras();
        categoria.setId(bundle.getInt("idCategoria"));
        categoria.setNombre(bundle.getString("nombreCategoria"));

        et_nombre = (EditText)findViewById(R.id.et_nombre);
        et_nombre.setText(categoria.getNombre());

    }

    //-------------------------------------------   Metodos

    public void confirmarEliminacionDeCategoria(View view)
    {
        new AlertDialog.Builder(this)
                .setMessage("¿Suguro desea eliminar esta categoria?")
                .setCancelable(false)
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        eliminarCategoria();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    public void confirmarModificacionDeCategoria(View view)
    {
        if (!et_nombre.getText().toString().equals(""))
        {
            new AlertDialog.Builder(this)
                    .setMessage("¿Suguro desea modificar esta categoria?")
                    .setCancelable(false)
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            modificarCategoria();
                        }
                    })
                    .setNegativeButton("Cancelar", null)
                    .show();
        }
        else
        {
            new AlertDialog.Builder(this)
                    .setTitle("Complete todos los campos!")
                    .setPositiveButton("Aceptar",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).show();
          }
    }
    
    public void modificarCategoria()
    {
        categoria.setNombre(et_nombre.getText().toString());

        try
        {
            metodos.modificarCategoria(categoria);
            MainActivity.actualizarCategorias();

            new AlertDialog.Builder(this)
                    .setTitle("Se ha modificado la categoria")
                    .setPositiveButton("Aceptar",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    volverAlListado();
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
                                public void onClick(DialogInterface dialog, int which) {
                                }
                            }).show();
        }
    }

    public void eliminarCategoria()
    {
        try
        {
            metodos.eliminarCategoria(categoria);
            MainActivity.actualizarCategorias();

            new AlertDialog.Builder(this)
                    .setTitle("Se ha eliminado la categoria")
                    .setPositiveButton("Aceptar",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which)
                                {
                                    volverAlListado();
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
    }

    public void volverAlListado()
    {
        Intent i = new Intent(this, ListadoCategorias.class );
        startActivity(i);
    }

    public void cancelar(View view)
    {
        volverAlListado();
    }
}
