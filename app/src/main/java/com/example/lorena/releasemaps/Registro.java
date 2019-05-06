package com.example.lorena.releasemaps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Registro extends AppCompatActivity {

    private EditText nombre;
    private EditText cedula;
    private EditText nombreEmpresa;
    private EditText celular;
    private EditText correo;
    private EditText genero;
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
        celular = findViewById(R.id.editTelefono);
        correo = findViewById(R.id.editCorreo);
        genero = findViewById(R.id.editGenero);
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
                String numCelular = celular.getText().toString();
                String email = correo.getText().toString();
                String generoUsu = genero.getText().toString();

                if(checkAdmin.isChecked()){
                   rol= checkAdmin.getText().toString();
                }else if (checkInst.isChecked()){
                    rol=checkInst.getText().toString();
                }else{
                    rol=checkOp.getText().toString();
                }

                //aqui llamo al metodo que va a guardar al usuario en la base de datos: request

            }
        });

    }


}
