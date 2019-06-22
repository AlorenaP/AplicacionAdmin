package com.example.lorena.releasemaps;

import android.app.AlertDialog;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

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

public class ActualizarUsuario extends AppCompatActivity {

    ListView listViewUsuarios;
    StringBuilder sbResult;
    String result = null;
    InputStream input = null;
    private String[] cedula = null;
    private String[] nombre = null;
    private String[] rol = null;
    private JSONArray json;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_usuario);

        listViewUsuarios = (ListView) findViewById(R.id.list_usuarios);

        //allow network in main thread
        StrictMode.setThreadPolicy((new StrictMode.ThreadPolicy.Builder().permitNetwork().build()));
        //retrieve
        data();
        //adapter
        Adaptador adaptador = new Adaptador(ActualizarUsuario.this, cedula, nombre, rol);
        listViewUsuarios.setAdapter(adaptador);

    }


    public void data() {
        URL url = null;

        try {
            url = new URL("http://172.30.200.99/testgeo/view_registros.php");
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
            rol = new String[json.length()];

            for (int i = 0; i < json.length(); i++) {
                jsonObject = json.getJSONObject(i);
                cedula[i] = jsonObject.getString("cedula");
                nombre[i] = jsonObject.getString("nombre");
                rol[i] = jsonObject.getString("rol");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void onClicDelete(View v) {

        AlertDialog.Builder mBuilder = new AlertDialog.Builder(v.getContext());
        View mView = getLayoutInflater().inflate(R.layout.alert_dialog, null);
        Button btn_ok = mView.findViewById(R.id.btn_ok);
        Button btn_cancel = mView.findViewById(R.id.btn_cancel);

        mBuilder.setView(mView);

        final AlertDialog alertDialog = mBuilder.create();
        alertDialog.setCanceledOnTouchOutside(false);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.dismiss();

            }
        });


        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //enviar el aupdate de cambio de estado en base de datos.

            }
        });

    }

    public void onClicUpdate(View v){

    }

}