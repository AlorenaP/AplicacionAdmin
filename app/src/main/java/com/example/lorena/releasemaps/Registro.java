package com.example.lorena.releasemaps;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class Registro extends AppCompatActivity {

    private EditText nombre;
    private EditText cedula;
    private EditText nombreEmpresa;
    private EditText telefono;
    private EditText correo;
    private EditText genero;
    private  EditText empresa;
    private Button btnRegistro;
    private RadioButton checkOp;
    private RadioButton checkInst;
    private RadioButton checkAdmin;
    private String rol;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //inicializacion

        nombre = findViewById(R.id.editNombre);
        cedula = findViewById(R.id.editCedula);
        nombreEmpresa = findViewById(R.id.editEmpresa);
        telefono = findViewById(R.id.editTelefono);
        correo = findViewById(R.id.editCorreo);
        genero = findViewById(R.id.editGenero);
        empresa =findViewById(R.id.editEmpresa);
        btnRegistro = findViewById(R.id.btnRegistro);
        checkAdmin = findViewById(R.id.admin);
        checkInst = findViewById(R.id.instal);
        checkOp = findViewById(R.id.op);
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nombreUsuaruio = nombre.getText().toString();
                String cedulaUsuario = cedula.getText().toString();
                String nombreEmpre = nombreEmpresa.getText().toString();
                String numCelular = telefono.getText().toString();
                String email = correo.getText().toString();
                String generoUsu = genero.getText().toString();

                if(checkAdmin.isChecked()){
                   rol= checkAdmin.getText().toString();
                }else if (checkInst.isChecked()){
                    rol=checkInst.getText().toString();
                }else{
                    rol=checkOp.getText().toString();
                }

                new Insertar(Registro.this).execute();
            }
        });

    }


    private boolean registrar(String cedula, String nombre, String rol, String correo, String genero, String celular, String empresa){

        HttpClient httpClient;
        List<NameValuePair> nameValuePairs;
        HttpPost httpPost;
        httpClient = new DefaultHttpClient();
        httpPost = new HttpPost("http://172.30.200.99/testgeo/usuarios.php");//url del servidor
        //empezamos añadir nuestros dato
        nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("cedula", nombre.trim()));
        nameValuePairs.add(new BasicNameValuePair("nombre",cedula.trim()));
        nameValuePairs.add(new BasicNameValuePair("rol", rol.trim()));
        nameValuePairs.add(new BasicNameValuePair("correo", rol.trim()));
        nameValuePairs.add(new BasicNameValuePair("genero", rol.trim()));
        nameValuePairs.add(new BasicNameValuePair("telefono", rol.trim()));
        nameValuePairs.add(new BasicNameValuePair("empresa", rol.trim()));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httpClient.execute(httpPost);
            return true;
        } catch(UnsupportedEncodingException e){
            e.printStackTrace();
        }catch (ClientProtocolException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
        return  false;
    }


    //AsyncTask para insertar Datos
    class Insertar extends AsyncTask<String,String,String> {
        private Activity context;
        Insertar(Activity context){
            this.context=context;
        }
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            if(registrar(cedula.toString(),nombre.toString(),rol,correo.toString(),genero.toString(), telefono.toString(),empresa.toString()))
                context.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Toast.makeText(context, "Datos insertado con éxito", Toast.LENGTH_LONG).show();

                        cedula.setText("");
                        nombre.setText("");
                        correo.setText("");
                        genero.setText("");
                        telefono.setText("");
                        empresa.setText("");
                    }
                });
            else
                context.runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Toast.makeText(context, "Datos no insertado con éxito", Toast.LENGTH_LONG).show();
                    }
                });
            return null;
        }
    }

}
