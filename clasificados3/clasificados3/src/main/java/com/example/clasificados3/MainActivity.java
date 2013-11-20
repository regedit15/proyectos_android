package com.example.clasificados3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.clasificados3.Clases.Categoria;
import com.example.clasificados3.Clases.Usuario;
import com.example.clasificados3.Controladores.Metodos;

import java.util.ArrayList;

public class MainActivity extends ActionBarActivity
{
    public static String ip = "10.0.0.3";
    public static String pathImagenesServidor = "http://" + MainActivity.ip + "/prueba/uploads/";
    public static Usuario usuario = new Usuario();
    Metodos metodos = new Metodos(ip);
    public static ArrayList<Categoria> categorias = new ArrayList<Categoria>();
    EditText et_usuario;
    EditText et_password;
    ProgressBar pg_progressBar;



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pg_progressBar = (ProgressBar)findViewById(R.id.pg_progressBar);
        pg_progressBar.setVisibility(View.INVISIBLE);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        //----- Solucion Error HttpPost. Solo para emmulador
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitNetwork().build();
        StrictMode.setThreadPolicy(policy);
        //-------------------------------


        //------ se cargan las categorias + la categoria todas, con id -1
        Categoria todas = new Categoria();
        todas.setId(-1);
        todas.setNombre("Todas");

        categorias.add(todas);

        ArrayList<Categoria> categoriasBD = metodos.getCategorias();
        for(int i=0; i < categoriasBD.size(); i++)
        {
            categorias.add(categoriasBD.get(i));
        }
        //----------------------------------------------------------------------



        et_usuario = (EditText)findViewById(R.id.et_usuario);
        et_password = (EditText)findViewById(R.id.et_password);

//        Intent i = new Intent(this, ModificarClasificado.class );
//        startActivity(i);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }


    public void registrarse(View view)
    {
        Intent i = new Intent(this, RegistrarUsuario.class );
        startActivity(i);
    }

    public void iniciarSesion(View view)
    {
        int x = metodos.validarUsuario(et_usuario.getText().toString(), et_password.getText().toString());

        if(x == 1)
        {
            usuario = metodos.getUsuario(et_usuario.getText().toString());

            Intent i = new Intent(this, Home.class );
            startActivity(i);
        }
        else
        {
            new AlertDialog.Builder(this)
                    .setTitle("El usuario o la contraseña no son correctas")
                    .setPositiveButton("Aceptar",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {}
                            }).show();
        }
    }




}