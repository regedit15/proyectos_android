package com.example.clasificados3.Controladores;

import android.util.Log;

import com.example.clasificados3.Clases.Categoria;
import com.example.clasificados3.Clases.Clasificado;
import com.example.clasificados3.Clases.Imagen;
import com.example.clasificados3.Clases.Usuario;
import com.example.clasificados3.MainActivity;

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

/**
 * Created by martincho on 09/11/13.
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

    public int validarNombreUsuario(String usuario)
    {
        int x = 0;
        String jsonResult = httpGetData("http://" + ip + "/prueba/Clasificados_ValidarNombreUsuario.php?usuario=" + usuario);

        try
        {
            JSONObject jsonResponse = new JSONObject(jsonResult);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("lista");

            x = jsonMainNode.getInt(0);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return x;
    }

    public int validarUsuario(String usuario, String password)
    {
        int x = 0;
        String jsonResult = httpGetData("http://" + ip + "/prueba/Clasificados_ValidarUsuario.php?usuario=" + usuario + "&password=" + password);

        try
        {
            JSONObject jsonResponse = new JSONObject(jsonResult);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("lista");

            x = jsonMainNode.getInt(0);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return x;

    }

    public int insertarUsuario(Usuario x)
    {
        int id = 0;

        String popo = "http://" + ip + "/prueba/Clasificados_RegistrarUsuario.php?usuario=" + x.getUsuario() + "&password=" + x.getPassword() + "&correo=" + x.getCorreo();
        String jsonResult = httpGetData(popo);

        try
        {
            JSONObject jsonResponse = new JSONObject(jsonResult);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("lista");

            id = jsonMainNode.getInt(0);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return id;
    }

    public int insertarClasificado(Clasificado x)
    {
        int id = 0;

        String jsonResult = httpGetData("http://" + ip + "/prueba/Clasificados_InsertarClasificado.php?id_usuario="+ MainActivity.usuario.getId() +"&titulo=" + x.getTitulo() + "&descripcion=" + x.getDescripcion() + "&precio=" + x.getPrecio() + "&imagen=" + x.getImagen() + "&id_categoria=" + x.getCategoria().getId());

        try
        {
            JSONObject jsonResponse = new JSONObject(jsonResult);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("lista");

            id = jsonMainNode.getInt(0);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return id;
    }

    public int insertarImagen(Imagen x)
    {
        int id = 0;

        String jsonResult = httpGetData("http://" + ip + "/prueba/Clasificados_InsertarImagen.php?id_clasificado=" + x.getClasificado().getId() + "&nombre=" + x.getNombre());

        try
        {
            JSONObject jsonResponse = new JSONObject(jsonResult);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("lista");

            id = jsonMainNode.getInt(0);
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return id;
    }

    public ArrayList<Categoria> getCategorias()
    {
        String jsonResult = httpGetData("http://" + ip + "/prueba/Clasificados_GetCategorias.php");
        ArrayList<Categoria> lista = new ArrayList<Categoria>();
        try
        {
            JSONObject jsonResponse = new JSONObject(jsonResult);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("lista");


            for(int i = 0; i<jsonMainNode.length();i++)
            {
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                int id = jsonChildNode.optInt("id");
                String nombre = jsonChildNode.optString("nombre");

                Categoria x = new Categoria();
                x.setId(id);
                x.setNombre(nombre);
                lista.add(x);
            }
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return lista;
    }






    public Usuario getUsuario(String nombreUsuario)
    {
        String jsonResult = httpGetData("http://" + ip + "/prueba/Clasificados_GetUsuario.php?usuario=" + nombreUsuario);
        Usuario x = new Usuario();

        try
        {
            JSONObject jsonResponse = new JSONObject(jsonResult);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("lista");

            JSONObject jsonChildNode = jsonMainNode.getJSONObject(0);
            int id = jsonChildNode.optInt("id");
            String usuario = jsonChildNode.optString("usuario");
            String password = jsonChildNode.optString("password");

            x.setId(id);
            x.setUsuario(usuario);
            x.setPassword(password);

        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return x;

//        Producto x = new Producto();
//
//        JSONArray ja=null;
//        try
//        {
//            String data;
//            //data=httpGetData("http://10.0.2.2/prueba/consultarProducto.php?id=" + ed_id.getText());
//            //data=httpGetData("http://" + ip + "/prueba/consultarProducto.php?id=" + id);
//            data=httpGetData("http://10.0.0.5/prueba/consultarProducto.php?id=15");
//            if(data.length()>1)
//            {
//                ja=new JSONArray(data);
//            }
//
//        }
//        catch (JSONException e)
//        {
//            e.printStackTrace();
//        }
//
//        try
//        {
//            x.setId(ja.getInt(0));
//            x.setNombre(ja.getString(1));
//            x.setPrecio(ja.getDouble(2));
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//
//        return x;
    }
//
//
//    public void eliminarProducto(String id)
//    {
//        try
//        {
//            //httpGetData("http://10.0.2.2/prueba/eliminarProducto.php?id=" + ed_id.getText());
//            httpGetData("http://" + ip + "/prueba/eliminarProducto.php?id=" + id);
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//    }
//
//
//    public void modificarProducto(Producto x)
//    {
//        try
//        {
//            //httpGetData("http://10.0.2.2/prueba/modificarProducto.php?id=" + ed_id.getText() + "&nombre=" + ed_nombre.getText() + "&precio=" + ed_precio.getText());
//            httpGetData("http://" + ip + "/prueba/modificarProducto.php?id=" + x.getId() + "&nombre=" + x.getNombre() + "&precio=" + x.getPrecio());
//        }
//        catch(Exception e)
//        {
//            e.printStackTrace();
//        }
//    }
//
//

}
