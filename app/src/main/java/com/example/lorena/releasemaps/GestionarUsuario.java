package com.example.lorena.releasemaps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class GestionarUsuario extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestionar_usuario);
    }


    public void onClicRegistrar(View v){

        Intent intent= new Intent(GestionarUsuario.this, Registro.class);
        startActivity(intent);

    }
    public void onClicUpdate(View v){

        Intent intent= new Intent(GestionarUsuario.this, ActualizarUsuario.class);
        startActivity(intent);

    }

    public void onClicDelete(View v){

    }


}
