package com.example.clasificados3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

/**
 * Created by martincho on 10/11/13.
 */
public class Home extends Activity
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        TextView tv_usuario = (TextView)findViewById(R.id.et_usuario);
        tv_usuario.setText("Bienvenido " + MainActivity.usuario.getUsuario());
    }


    public void nuevoClasificado(View view)
    {
        Intent i = new Intent(this, AltaClasificado.class );
        startActivity(i);
    }

    public void listarClasificados(View view)
    {
        Intent i = new Intent(this, ListadoClasificados.class );
        startActivity(i);
    }


}
