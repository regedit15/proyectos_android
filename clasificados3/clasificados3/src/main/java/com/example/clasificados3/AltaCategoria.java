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
public class AltaCategoria extends Activity
{
    Metodos metodos = new Metodos(MainActivity.ip);
    EditText et_nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alta_categoria);

        et_nombre = (EditText)findViewById(R.id.et_nombre);
    }


    //-------------------------------- Butones
    public void nuevaCategoria(View view)
    {
        if (!et_nombre.getText().toString().equals(""))
        {
            Categoria x = new Categoria();

            x.setNombre(et_nombre.getText().toString());

            try
            {
                metodos.insertarCategoria(x);
                MainActivity.actualizarCategorias();

                new AlertDialog.Builder(this)
                        .setTitle("Se ha creado una nueva categoria")
                        .setPositiveButton("Aceptar",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which)
                                    {
                                        volverAlHome();
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
        else
        {
            new AlertDialog.Builder(this)
                    .setTitle("Complete todos los campos!")
                    .setPositiveButton("Aceptar",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {}
                            }).show();
        }
    }

    public void volverAlHome()
    {
        Intent i = new Intent(this, Home.class );
        startActivity(i);
    }

    public void cancelar(View view)
    {
        volverAlHome();
    }

}
