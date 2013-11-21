package com.example.clasificados3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.clasificados3.Clases.Clasificado;
import com.example.clasificados3.Clases_Auxiliares.AdaptadorListado;
import com.example.clasificados3.Controladores.Metodos;

import java.util.ArrayList;

/**
 * Created by martincho on 16/11/13.
 */
public class MisClasificados extends Activity
{
    Metodos metodos = new Metodos(MainActivity.ip);
    ListView lv_misClasificados;
    ArrayList<Clasificado> clasificados;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mis_clasificados);


        //--------------------------        Carga de clasificados por Usuario
        clasificados = metodos.getClasificadosPorUsuario(MainActivity.usuario);

        lv_misClasificados = (ListView)findViewById(R.id.lv_misClasificados);
        lv_misClasificados.setAdapter(new AdaptadorListado(this, R.layout.fila, clasificados)
        {
            @Override
            public void onEntrada(Object entrada, View view) {
                if (entrada != null) {
                    TextView tv_nombre = (TextView) view.findViewById(R.id.tv_nombre);
                    if (tv_nombre != null) {
                        tv_nombre.setText(((Clasificado) entrada).getTitulo());
                    }

                    TextView tv_precio = (TextView) view.findViewById(R.id.tv_precio);
                    if (tv_precio != null) {
                        tv_precio.setText("" + ((Clasificado) entrada).getPrecio());
                    }


                    ImageView iv_imagen = (ImageView) view.findViewById(R.id.iv_imagen);
                    if (iv_imagen != null) {
                        //se muestra solo la primer imagen, si es que tiene
                        if (((Clasificado) entrada).getImagenes().size() > 0)
                        {
                            iv_imagen.setImageDrawable(metodos.imageOperations(MainActivity.pathImagenesServidor + ((Clasificado) entrada).getImagenes().get(0).getNombre()));
                        }
                    }
                }
            }
        });

        //se sobreescribe el metodo on click de la fila
        lv_misClasificados.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                irAModificarClasificado(position);
            }
        });

    }


    public void irAModificarClasificado(int itemSeleccionado)
    {
        Intent i = new Intent(this, ModificarClasificado.class );
        i.putExtra("idClasificado", clasificados.get(itemSeleccionado).getId());
        startActivity(i);
    }

    public void volver(View view)
    {
        Intent i = new Intent(this, Home.class );
        startActivity(i);
    }
}
