package com.example.clasificados3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.clasificados3.Clases.Clasificado;
import com.example.clasificados3.Controladores.Metodos;

/**
 * Created by martincho on 10/11/13.
 */
public class AltaClasificado extends Activity
{
    Metodos metodos = new Metodos(MainActivity.ip);
    EditText et_titulo;
    EditText et_descripcion;
    EditText et_precio;
    EditText et_categoria;
    EditText et_imagen;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alta_clasificado);

        et_titulo = (EditText)findViewById(R.id.et_titulo);
        et_descripcion = (EditText)findViewById(R.id.et_descripcion);
        et_precio = (EditText)findViewById(R.id.et_precio);
        et_categoria = (EditText)findViewById(R.id.et_categoria);
        et_imagen = (EditText)findViewById(R.id.et_imagen);
    }



    public void nuevoClasificado(View view)
    {
        Clasificado x = new Clasificado();

        x.setTitulo(et_titulo.getText().toString());
        x.setDescripcion(et_descripcion.getText().toString());
        x.setPrecio(Double.parseDouble(et_precio.getText().toString()));
        x.setCategoria(Integer.parseInt(et_categoria.getText().toString()));
        x.setImagen(et_imagen.getText().toString());

        try
        {
            metodos.insertarClasificado(x);

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

        et_titulo.setText("");
        et_descripcion.setText("");
        et_precio.setText("");
        et_imagen.setText("");
        et_categoria.setText("");

    }

    public void cancelar(View view)
    {
        Intent i = new Intent(this, MainActivity.class );
        startActivity(i);
    }
}
