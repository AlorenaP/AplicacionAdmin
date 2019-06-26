package com.example.lorena.releasemaps;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
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
    private AutoCompleteTextView nombreEmpresa;
    private EditText telefono;
    private EditText correo;
    private AutoCompleteTextView genero;
    private Button btnRegistro;
    private RadioButton checkOp;
    private RadioButton checkInst;
    private RadioButton checkAdmin;
    private String rol;
    private String contrasena;
    private String nombreUsuaruio;
    private String cedulaUsuario;
    private String nombreEmpre;
    private String numCelular;
    private String email;
    private String generoUsu;
    private int estado;

    String [] empresas ={"Movistar", "Claro","Tigo", "Une","Emcali","Avantel"};
    String [] gener={"Femenino","Maculino"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //instanciación
        nombre = findViewById(R.id.editNombre);
        cedula = findViewById(R.id.editCedula);
        nombreEmpresa = findViewById(R.id.editEmpresa);
        telefono = findViewById(R.id.editTelefono);
        correo = findViewById(R.id.editCorreo);
        genero = findViewById(R.id.editGenero);
        btnRegistro = findViewById(R.id.btnRegistro);
        checkAdmin = findViewById(R.id.admin);
        checkInst = findViewById(R.id.instal);
        checkOp = findViewById(R.id.op);
        nombreEmpresa.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1,empresas));
        nombreEmpresa.setThreshold(1);
        genero.setAdapter(new ArrayAdapter(this, android.R.layout.simple_list_item_1,gener));
        genero.setThreshold(1);
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nombreUsuaruio = nombre.getText().toString();
                cedulaUsuario = cedula.getText().toString();
                nombreEmpre = nombreEmpresa.getText().toString();
                numCelular = telefono.getText().toString();
                email = correo.getText().toString();
                generoUsu = genero.getText().toString();
                contrasena = cedula.getText().toString();
                estado=1;

                if (checkAdmin.isChecked()) {
                    rol = checkAdmin.getText().toString();
                } else if (checkInst.isChecked()) {
                    rol = checkInst.getText().toString();
                } else {
                    rol = checkOp.getText().toString();
                }
                if (nombreUsuaruio.isEmpty() || cedulaUsuario.isEmpty() || nombreEmpre.isEmpty() ||
                        numCelular.isEmpty() || email.isEmpty() || generoUsu.isEmpty() || rol.isEmpty()) {
                    Toast.makeText(Registro.this, "Ningun campo debe estar vacio. Todos son requeridos", Toast.LENGTH_LONG).show();
                } else {
                    new Insertar(Registro.this).execute();
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(Registro.this);
                    final View mView = getLayoutInflater().inflate(R.layout.activity_alert_dialog, null);
                    Button btn_Si = mView.findViewById(R.id.btn_Si);
                    Button btn_No = mView.findViewById(R.id.btn_No);
                    //notificar en un mensaje que agrego con exito, y si desea agregar un nuevo usuario, o no,
                    // en el caso del NO la regresa al inicio en el caso del no
                    if(btn_Si.isClickable()){
                        btn_Si.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent regresa= new Intent(mView.getContext(), Registro.class);
                                startActivity(regresa);
                                finish();
                            }
                        });

                    }if (btn_No.isClickable()){
                        btn_No.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(mView.getContext(), Inicio.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                    }
                    mBuilder.setView(mView);
                    AlertDialog alDialog = mBuilder.create();
                    alDialog.show();
                }
            }
        });
    }


    private boolean registrar(String cedulaUs, String nombreUs, String contrasenia, String rolUs, int estado, String correoUs,
                              String generoUs, String celular, String empresaUs){

        HttpClient httpClient;
        List<NameValuePair> nameValuePairs;
        HttpPost httpPost;
        httpClient = new DefaultHttpClient();
        httpPost = new HttpPost("http://172.30.200.99/testgeo/registrar.php");//url del servidor
        //empezamos añadir nuestros dato
        nameValuePairs = new ArrayList<NameValuePair>();
        nameValuePairs.add(new BasicNameValuePair("cedula", cedulaUs.trim()));
        nameValuePairs.add(new BasicNameValuePair("nombre",nombreUs.trim()));
        nameValuePairs.add(new BasicNameValuePair("contrasena", contrasenia.trim()));
        nameValuePairs.add(new BasicNameValuePair("rol", rolUs.trim()));
        nameValuePairs.add(new BasicNameValuePair("estado", Integer.toString(estado)));
        nameValuePairs.add(new BasicNameValuePair("correo", correoUs.trim()));
        nameValuePairs.add(new BasicNameValuePair("genero", generoUs.trim()));
        nameValuePairs.add(new BasicNameValuePair("telefono", celular.trim()));
        nameValuePairs.add(new BasicNameValuePair("empresa", empresaUs.trim()));
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
            if(registrar(cedulaUsuario,nombreUsuaruio, contrasena, rol,estado,email, generoUsu, numCelular, nombreEmpre))
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Toast.makeText(context, "Datos insertado con éxito", Toast.LENGTH_LONG).show();

                        cedula.setText("");
                        nombre.setText("");
                        correo.setText("");
                        genero.setText("");
                        telefono.setText("");
                        nombreEmpresa.setText("");
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



    public void update(String cedulaUsu, String nombreUsu, String contrasenia, String rolUsu,
                       String emailUsu, String generoUsu, String numCelUsu, String empresaUsu){

        HttpClient httpClient;
        List<NameValuePair> nameValuePairs;
        HttpPost httpPost;
        httpClient = new DefaultHttpClient();
        httpPost = new HttpPost("http://172.30.200.99/testgeo/update.php");//url del servidor

    }

}
