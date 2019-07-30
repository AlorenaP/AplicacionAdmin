package com.example.lorena.releasemaps;

import android.os.StrictMode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;



public class ReporteMantenimiento {


    StringBuilder sbResult;
    InputStream input = null;
    private String result = null;
    private JSONArray json;
    String[] cedula = null;
    String[] nombre = null;


    public ReporteMantenimiento() {

        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        datosMantenimientos();

    }

    public void datosMantenimientos() {
        URL url = null;

        try {
            url = new URL("http://172.30.200.99/testgeo/reportMantenimiento.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            input = new BufferedInputStream(conn.getInputStream());
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //read is content into a string
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            sbResult = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                sbResult.append(line);
            }
            input.close();
            result = sbResult.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //parse Json data

        try {
            json = new JSONArray(result);
            JSONObject jsonObject = null;
            cedula = new String[json.length()];
            nombre = new String[json.length()];


            for (int i = 0; i < json.length(); i++) {
                jsonObject = json.getJSONObject(i);
                cedula[i] = jsonObject.getString("cedula");
                nombre[i] = jsonObject.getString("nombre");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String[] getCedula() {
        return cedula;
    }

    public String[] getNombre() {
        return nombre;
    }
}
