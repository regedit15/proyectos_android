package prueba.proyectos4.Clases;

import android.util.Log;

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


/**
 * Created by martincho on 14/10/13.
 */
public class Metodos
{
    String ip;
    public Metodos(String ip)
    {
        this.ip = ip;
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

    public void insertarProducto(Producto p)
    {
        httpGetData("http://" + ip + "/prueba/registrarProducto.php?nombre=" + p.getNombre() + "&precio=" + p.getPrecio());
    }


    public Producto getProducto(String id)
    {
        Producto x = new Producto();

        JSONArray ja=null;
        try
        {
            String data;
            //data=httpGetData("http://10.0.2.2/prueba/consultarProducto.php?id=" + ed_id.getText());
            //data=httpGetData("http://" + ip + "/prueba/consultarProducto.php?id=" + id);
            data=httpGetData("http://10.0.0.5/prueba/consultarProducto.php?id=15");
            if(data.length()>1)
            {
                ja=new JSONArray(data);
            }

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        try
        {
            x.setId(ja.getInt(0));
            x.setNombre(ja.getString(1));
            x.setPrecio(ja.getDouble(2));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return x;
    }


    public void eliminarProducto(String id)
    {
        try
        {
            //httpGetData("http://10.0.2.2/prueba/eliminarProducto.php?id=" + ed_id.getText());
            httpGetData("http://" + ip + "/prueba/eliminarProducto.php?id=" + id);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    public void modificarProducto(Producto x)
    {
        try
        {
            //httpGetData("http://10.0.2.2/prueba/modificarProducto.php?id=" + ed_id.getText() + "&nombre=" + ed_nombre.getText() + "&precio=" + ed_precio.getText());
            httpGetData("http://" + ip + "/prueba/modificarProducto.php?id=" + x.getId() + "&nombre=" + x.getNombre() + "&precio=" + x.getPrecio());
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


    public ArrayList<Producto> getProductos()
    {
        String jsonResult2 = httpGetData("http://" + ip + "/prueba/listarProductos.php");
        ArrayList<Producto> productos = new ArrayList<Producto>();
        try
        {
            JSONObject jsonResponse = new JSONObject(jsonResult2);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("lista_productos");


            for(int i = 0; i<jsonMainNode.length();i++)
            {
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                int id = jsonChildNode.optInt("id");
                String nombre = jsonChildNode.optString("nombre");
                double precio = jsonChildNode.optDouble("precio");

                Producto x = new Producto();
                x.setId(id);
                x.setNombre(nombre);
                x.setPrecio(precio);
                productos.add(x);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return productos;
    }



}
