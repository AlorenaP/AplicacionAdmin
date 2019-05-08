package com.example.lorena.releasemaps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Login extends AppCompatActivity {

    private EditText cedula;
    private EditText password;
    private Button btnIngresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        cedula = findViewById(R.id.editCedula);
        password=findViewById(R.id.editPassword);
        btnIngresar=findViewById(R.id.btnIngresar);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cedula.getText().toString().isEmpty() || password.getText().toString().isEmpty()) {

                    Toast.makeText(Login.this, "Ingresa los campos requeridos", Toast.LENGTH_SHORT).show();

                } else {
                    loginUsuario(cedula.getText().toString(), password.getText().toString());
                }

            }
        });
    }


    //Metodo que se encarga del login del Usuario
    public void loginUsuario(String cedula, String password) {

        //validar con la base de datos

        //auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
           // @Override
           // public void onComplete(@NonNull Task<AuthResult> task) {
              //  if (task.isSuccessful()) {
                    Intent i = new Intent(Login.this, Inicio.class);
                    startActivity(i);
                    finish();
                    Toast.makeText(Login.this, "Login correcto", Toast.LENGTH_SHORT).show();

                //} else {
                    Toast.makeText(Login.this, "Error en el correo y/o contraseña", Toast.LENGTH_SHORT).show();
               // }
           // }
       // });
    }

}
