package com.example.clasificados3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
    EditText et_contraseña;
    EditText et_correo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registrar_usuario);

        et_usuario = (EditText)findViewById(R.id.et_usuario);
        et_contraseña = (EditText)findViewById(R.id.et_contraseña);
        et_correo = (EditText)findViewById(R.id.et_correo);

    }


    public void registrarUsuario(View view)
    {




        if (metodos.validarUsuario("" + et_usuario.getText()) == 0)
        {
            Usuario x = new Usuario();

            x.setUsuario("" + et_usuario.getText());
            x.setPassword("" + et_contraseña.getText());
            x.setCorreo("" + et_correo.getText());

            metodos.insertarUsuario(x);
            //String aa = md5(mt_rand());
            //metodos.enviarCorreo("martinrossi9009@gmail.com", "<a href=http://localhost/prueba/Clasificados_ActivarUsuario.php?id=>Para activar su cuenta haga click aqui</a>");
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



    private void enviarrrr(String[] to, String[] cc,
                        String asunto, String mensaje) {
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:"));
        //String[] to = direccionesEmail;
        //String[] cc = copias;
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        emailIntent.putExtra(Intent.EXTRA_CC, cc);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, asunto);
        emailIntent.putExtra(Intent.EXTRA_TEXT, mensaje);
        emailIntent.setType("message/rfc822");
        startActivity(Intent.createChooser(emailIntent, "Email "));
    }

}
