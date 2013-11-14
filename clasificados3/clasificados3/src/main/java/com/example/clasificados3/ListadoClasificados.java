package com.example.clasificados3;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.clasificados3.Clases.Clasificado;
import com.example.clasificados3.Clases_Auxiliares.AdaptadorListado;
import com.example.clasificados3.Controladores.Metodos;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by martincho on 14/11/13.
 */
public class ListadoClasificados extends Activity
{
    Metodos metodos = new Metodos(MainActivity.ip);
    private ListView lista;
    String pathServidor = "http://10.0.0.3/prueba/uploads/";


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.listado_clasificados);



        ArrayList<Clasificado> clasificados = metodos.getClasificados();


        lista = (ListView) findViewById(R.id.lv_listado);
        lista.setAdapter(new AdaptadorListado(this, R.layout.fila, clasificados){
            @Override
            public void onEntrada(Object entrada, View view)
            {
                if (entrada != null)
                {
                    TextView tv_nombre = (TextView) view.findViewById(R.id.tv_nombre);
                    if (tv_nombre != null)
                    {
                        tv_nombre.setText(((Clasificado) entrada).getTitulo());
                    }

                    TextView tv_precio = (TextView) view.findViewById(R.id.tv_precio);
                    if (tv_precio != null)
                    {
                        tv_precio.setText("" + ((Clasificado) entrada).getPrecio());
                    }


                    ImageView iv_imagen = (ImageView)view.findViewById(R.id.iv_imagen);
                    if (tv_precio != null)
                    {
                        //se muestra solo la primer imagen, si es que tiene
                        if(((Clasificado) entrada).getImagenes().size() > 0)
                        {
                            Drawable image = ImageOperations(pathServidor + ((Clasificado)entrada).getImagenes().get(0).getNombre());
                            iv_imagen.setImageDrawable(image);
                        }
                    }


                    //aaaaaaaaaaaaa


                }
            }
        });
    }




    //------------------- Metodos para Cargar Una imagen desde una URL
    private Drawable ImageOperations(String url) {
        try {
            InputStream is = (InputStream) this.fetch(url);
            Drawable d = Drawable.createFromStream(is, "src");
            return d;
        } catch (MalformedURLException e) {
            return null;
        } catch (IOException e) {
            return null;
        }
    }

    public Object fetch(String address) throws MalformedURLException,IOException {
        URL url = new URL(address);
        Object content = url.getContent();
        return content;
    }
    //----------------------------------------------------------------
}
