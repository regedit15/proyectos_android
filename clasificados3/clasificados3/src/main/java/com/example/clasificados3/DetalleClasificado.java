package com.example.clasificados3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.clasificados3.Clases.Clasificado;
import com.example.clasificados3.Controladores.Metodos;

/**
 * Created by martincho on 14/11/13.
 */
public class DetalleClasificado extends Activity
{
    Metodos metodos = new Metodos(MainActivity.ip);
    Clasificado clasificado = new Clasificado();
    TextView tv_titulo;
    TextView tv_descripcion;
    TextView tv_precio;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_clasificado);


        tv_titulo = (TextView)findViewById(R.id.tv_titulo);
        tv_descripcion = (TextView)findViewById(R.id.tv_descripcion);
        tv_precio = (TextView)findViewById(R.id.tv_precio);

        //se obtiene el clasificado segun el id obtenido por parametro
        Bundle bundle = getIntent().getExtras();
        clasificado.setId(bundle.getInt("idClasificado"));
        clasificado = metodos.getClasificado(clasificado);

        //se setean los textviews
        tv_titulo.setText(clasificado.getTitulo());
        tv_descripcion.setText(clasificado.getDescripcion());
        tv_precio.setText("Precio: $" + clasificado.getPrecio().toString());

        //se agregan las imagenes del clasificado al layout
        for (int i=0; i<clasificado.getImagenes().size(); i++)
        {
            LinearLayout layout = (LinearLayout) findViewById(R.id.layout_clasificado);
            ImageView iv = new ImageView(this);

            final String path = MainActivity.pathImagenesServidor + clasificado.getImagenes().get(i).getNombre();

            iv.setImageDrawable(metodos.imageOperations(path));
            iv.setLayoutParams(new LinearLayout.LayoutParams(200, 200));

            iv.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View v)
                {
                    verImagen(path);
                }

            });

            layout.addView(iv);
        }

    }




    public void verImagen(String pathImagen)
    {
        Intent i = new Intent(this, VerImagen.class );
        i.putExtra("nombreImagen", pathImagen);
        startActivity(i);
    }

}
