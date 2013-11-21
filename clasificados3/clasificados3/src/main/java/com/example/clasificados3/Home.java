package com.example.clasificados3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by martincho on 10/11/13.
 */
public class Home extends Activity
{
    Button bt_alta_categoria;
    Button bt_listar_categoria;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        TextView tv_usuario = (TextView)findViewById(R.id.et_usuario);
        tv_usuario.setText("Bienvenido " + MainActivity.usuario.getUsuario());

        if (MainActivity.usuario.getAdmin() == 0)
        {
            bt_alta_categoria = (Button)findViewById(R.id.bt_alta_categoria);
            bt_listar_categoria = (Button)findViewById(R.id.bt_listar_categoria);
            bt_alta_categoria.setVisibility(View.GONE);
            bt_listar_categoria.setVisibility(View.GONE);
        }
    }


    public void nuevoClasificado(View view)
    {
        Intent i = new Intent(this, AltaClasificado.class);
        startActivity(i);
    }

    public void listarClasificados(View view)
    {
        Intent i = new Intent(this, ListadoClasificados.class);
        startActivity(i);
    }

    public void misClasificados(View view)
    {
        Intent i = new Intent(this, MisClasificados.class);
        startActivity(i);
    }

    public void cancelar(View view)
    {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void nuevaCategoria(View view)
    {
        Intent i = new Intent(this, AltaCategoria.class);
        startActivity(i);
    }

    public void listadoCategorias(View view)
    {
        Intent i = new Intent(this, ListadoCategorias.class);
        startActivity(i);
    }
}
