package com.example.lorena.releasemaps;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ViewSwitcher;

public class Reportes extends AppCompatActivity {

    private String textoCorto="Hola, esta fuuncionando";
    private String testoLargo="At the railway station I ask\n" +
            "if there’s a train to where you are.\n" +
            "\n" +
            "I’m told there is one but it’s left already,\n" +
            "so has tomorrow’s and the day after’s.\n" +
            "\n" +
            "Somewhere in the trees, painted in\n" +
            "degrees of green, the morning\n" +
            "\n" +
            "paint still wet, a magpie robin sets up its\n" +
            "musical kit. You hear it in your garden,";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes);
        PlantillaPDF plantillaPDF=new PlantillaPDF(getApplicationContext());
        plantillaPDF.openDocument();
        plantillaPDF.addMetaData("","","");
        plantillaPDF.addTitles("","","");
        plantillaPDF.addParagraph(textoCorto);
        plantillaPDF.addParagraph(testoLargo);
        plantillaPDF.closeDocument();

    }


    public void pdfView(View view){



    }
}
