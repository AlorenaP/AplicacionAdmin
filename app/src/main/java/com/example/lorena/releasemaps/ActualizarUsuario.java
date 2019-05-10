package com.example.lorena.releasemaps;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class ActualizarUsuario extends AppCompatActivity {

    Adaptador adaptador;
    ListView usuarios;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_usuario);
    }
}
