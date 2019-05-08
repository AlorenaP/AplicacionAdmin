package com.example.lorena.releasemaps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Inicio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onClickMapa(View v){
        Intent intent= new Intent(Inicio.this, MapsActivity.class);
        startActivity(intent);
    }
    public void onClickUsuarios(View v){

        Intent intent= new Intent(Inicio.this, Registro.class);
        startActivity(intent);
    }

    public void onClickReportes(View v){

    }

    public void onClickOtro(View v){

    }
}
