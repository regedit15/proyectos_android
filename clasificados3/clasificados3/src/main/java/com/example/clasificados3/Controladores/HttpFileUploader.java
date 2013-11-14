package com.example.clasificados3.Controladores;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class HttpFileUploader implements Runnable
{

    URL connectURL;
    String params;
    String responseString;
    String fileName;
    byte[] dataToServer;
    FileInputStream fileInputStream = null;

    public HttpFileUploader(String urlString, String params, String fileName){
        try{
            connectURL = new URL(urlString);
        }catch(Exception ex){
            Log.i("URL FORMATION", "MALFORMATED URL");
        }
        this.params = params+"=";
        this.fileName = fileName;
    }

    public void doStart(FileInputStream stream)
    {
        fileInputStream = stream;
        thirdTry();
    }

    public void thirdTry()
    {
        String exsistingFileName = fileName;

        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        String Tag="3rd";
        try
        {
            //------------------ CLIENT REQUEST

            Log.e(Tag,"Starting to bad things");
            // Abrimos una conexión http con la URL

            HttpURLConnection conn = (HttpURLConnection) connectURL.openConnection();

            // Permitimos Inputs
            conn.setDoInput(true);

            // Permitimos Outputs
            conn.setDoOutput(true);

            // Deshabilitamos el uso de la copia cacheada.
            conn.setUseCaches(false);

            // Usamos el método post esto podemos cambiarlo.
            conn.setRequestMethod("POST");

            conn.setRequestProperty("Connection", "Keep-Alive");

            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);

            DataOutputStream dos = new DataOutputStream( conn.getOutputStream() );

            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\"" + exsistingFileName +"\"" + lineEnd);
            dos.writeBytes(lineEnd);

            Log.e(Tag,"Headers are written");

            // creamos un buffer con el tamaño maximo de archivo, lo pondremos en 1MB

            int bytesAvailable = fileInputStream.available();
            int maxBufferSize = 1024;
            int bufferSize = Math.min(bytesAvailable, maxBufferSize);
            byte[] buffer = new byte[bufferSize];

            int bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0)
            {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            // enviar multipart form data

            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

            // cerramos
            Log.e(Tag,"File is written");
            fileInputStream.close();
            dos.flush();

            InputStream is = conn.getInputStream();
            // retrieve the response from server
            int ch;

            StringBuffer b =new StringBuffer();
            while( ( ch = is.read() ) != -1 ){
                b.append( (char)ch );
            }
            String s=b.toString();
            Log.i("Response", s);
            dos.close();

        }
        catch (MalformedURLException ex)
        {
            Log.e(Tag, "error: " + ex.getMessage(), ex);
        }

        catch (IOException ioe)
        {
            Log.e(Tag, "error: " + ioe.getMessage(), ioe);
        }
    }

    @Override
    public void run() {

    }

}