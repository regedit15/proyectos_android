package com.example.clasificados3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.clasificados3.Clases.Usuario;
import com.example.clasificados3.Controladores.Metodos;

/**
 * Created by martincho on 09/11/13.
 */
public class RegistrarUsuario extends Activity
{

    Metodos metodos = new Metodos(MainActivity.ip);
    EditText et_usuario;
    EditText et_password;
    EditText et_correo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar_usuario);

        et_usuario = (EditText)findViewById(R.id.et_usuario);
        et_password = (EditText)findViewById(R.id.et_password);
        et_correo = (EditText)findViewById(R.id.et_correo);

    }


    public void registrarUsuario(View view)
    {

        if (metodos.validarNombreUsuario(et_usuario.getText().toString()) == 0)
        {
            Usuario x = new Usuario();

            x.setUsuario(et_usuario.getText().toString());
            x.setPassword(et_password.getText().toString());
            x.setCorreo(et_correo.getText().toString());

            metodos.insertarUsuario(x);

            new AlertDialog.Builder(this)
                    .setTitle("Para finalizar ve a tu cuenta de correo!")
                    .setPositiveButton("Aceptar",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {}
                            }).show();
        }
        else
        {
            new AlertDialog.Builder(this)
                    .setTitle("El usuario ingresado se encuentra en uso")
                    .setPositiveButton("Aceptar",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {}
                            }).show();
        }
    }


    public void cancelar(View view)
    {
        Intent i = new Intent(this, MainActivity.class );
        startActivity(i);
    }


}
