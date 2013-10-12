package prueba.productos3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import Clases.Producto;
import Clases.listar;

public class MainActivity extends Activity
{
    EditText ed_id;
    EditText ed_nombre;
    EditText ed_precio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //----- Solucion Error HttpPost
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitNetwork().build();
        StrictMode.setThreadPolicy(policy);
        //-------------------------------

        ed_id =(EditText)findViewById(R.id.ed_id);
        ed_nombre = (EditText)findViewById(R.id.ed_nombre);
        ed_precio = (EditText)findViewById(R.id.ed_precio);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }




    public String httpGetData(String mURL)
    {
        String response="";
        mURL=mURL.replace(" ", "%20");
        Log.i("LocAndroid Response HTTP Threas", "Ejecutando get 0: " + mURL);
        HttpClient httpclient = new DefaultHttpClient();

        Log.i("LocAndroid Response HTTP Thread","Ejecutando get 1");
        HttpGet httppost = new HttpGet(mURL);
        Log.i("LocAndroid Response HTTP Thread","Ejecutando get 2");
        try
        {

            Log.i("LocAndroid Response HTTP","Ejecutando get");
            // Execute HTTP Post Request
            ResponseHandler<String> responseHandler=new BasicResponseHandler();
            response = httpclient.execute(httppost,responseHandler);
            Log.i("LocAndroid Response HTTP",response);
        } catch (ClientProtocolException e) {
            Log.i("LocAndroid Response HTTP ERROR 1",e.getMessage());
            // TODO Auto-generated catch block
        } catch (IOException e) {

            Log.i("LocAndroid Response HTTP ERROR 2", e.getMessage());
            // TODO Auto-generated catch block
        }
        return response;
    }



    public void enviar(View v)
    {
        try
        {
            //httpGetData("http://10.0.2.2/prueba/registrarProducto.php?nombre=" + ed_nombre.getText() + "&precio=" + ed_precio.getText());
            httpGetData("http://10.0.0.5/prueba/registrarProducto.php?nombre=" + ed_nombre.getText() + "&precio=" + ed_precio.getText());
            Toast.makeText(getApplicationContext(), "Listo!", 1000).show();

            ed_nombre.setText("");
            ed_precio.setText("");
        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), 1000).show();
        }

    }

    public void buscarProducto(View v)
    {
        JSONArray ja=null;
        try {
            String data;
            //data=httpGetData("http://10.0.2.2/prueba/consultarProducto.php?id=" + ed_id.getText());
            data=httpGetData("http://10.0.0.5/prueba/consultarProducto.php?id=" + ed_id.getText());
            if(data.length()>1)
            {
                ja=new JSONArray(data);
            }

        }
        catch (JSONException e)
        {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error recuperando la informacion del servidor, verifique su conexion a internet y vuelva a intentarlo.", 1000).show();
        }

        try
        {
            ed_id.setText(ja.getString(0));
            ed_nombre.setText(ja.getString(1));
            ed_precio.setText(ja.getString(2));
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), "Error al setear tex edit", 1000).show();
        }
    }


    public void eliminar(View v)
    {
        try
        {
            //httpGetData("http://10.0.2.2/prueba/eliminarProducto.php?id=" + ed_id.getText());
            httpGetData("http://10.0.0.5/prueba/eliminarProducto.php?id=" + ed_id.getText());
            Toast.makeText(getApplicationContext(), "Producto eliminado", 1000).show();

            ed_id.setText("");
            ed_nombre.setText("");
            ed_precio.setText("");
        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), 1000).show();
        }

    }

    public void modificar(View v)
    {
        try
        {
            //httpGetData("http://10.0.2.2/prueba/modificarProducto.php?id=" + ed_id.getText() + "&nombre=" + ed_nombre.getText() + "&precio=" + ed_precio.getText());
            httpGetData("http://10.0.0.5/prueba/modificarProducto.php?id=" + ed_id.getText() + "&nombre=" + ed_nombre.getText() + "&precio=" + ed_precio.getText());

            Toast.makeText(getApplicationContext(), "Producto Modificado", 1000).show();
        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), 1000).show();
        }
    }

    public void limpiar(View v)
    {
        ed_id.setText("");
        ed_nombre.setText("");
        ed_precio.setText("");
    }


    public void cambiarLayout(View v)
    {

        Intent i = new Intent(this, listar.class );
        startActivity(i);


    }

    public void contar(View v)
    {

        JSONArray ja = null;
        try
        {
            String data;
            //data = httpGetData("http://10.0.2.2/prueba/listarProductos.php");
            data = httpGetData("http://10.0.0.5/prueba/listarProductos.php");
            if(data.length()>1)
            {
                ja = new JSONArray(data);
            }

        }
        catch (JSONException e)
        {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error recuperando la informacion del servidor, verifique su conexion a internet y vuelva a intentarlo.", 1000).show();
        }


        ArrayList<Producto> productos = new ArrayList<Producto>();

        for (int i = 0; i < ja.length(); i++)
        {
            JSONObject jsonObject = null;

            try
            {
                jsonObject = ja.getJSONObject(i);

                Producto x = new Producto();

                x.setId(jsonObject.getInt("id"));
                x.setNombre(jsonObject.getString("nombre"));
                x.setPrecio(jsonObject.getDouble("precio"));
                productos.add(x);
            }
            catch (JSONException e)
            {
                Toast.makeText(getApplicationContext(), "Error!: " + e.getMessage(), 1000).show();
                e.printStackTrace();
            }

        }


    }
    
}
