package com.example.clasificados3;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.clasificados3.Controladores.Metodos;

/**
 * Created by martincho on 16/11/13.
 */


//la funcion de esta clase es ampliar la imagen del detalle del clasificado
public class VerImagen extends Activity
{
    Metodos metodos = new Metodos(MainActivity.ip);

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ver_imagen);

        ImageView iv_imagen  = (ImageView)findViewById(R.id.iv_imagen);
        Bundle bundle = getIntent().getExtras();
        //levanta la imagen
        iv_imagen.setImageDrawable(metodos.imageOperations(bundle.getString("nombreImagen")));
    }
}
