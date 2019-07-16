package com.example.lorena.releasemaps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class Reportes extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1 ;
    private String textoCorto = "Hola, esta fuuncionando";
    private String testoLargo = "At the railway station I ask\n" +
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
    PlantillaPDF plantillaPDF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes);

        int checkPermisos = ContextCompat.checkSelfPermission(Reportes.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (checkPermisos != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(Reportes.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
            }

    }

    public void onClicInstallPDF(View view) {
        plantillaPDF = new PlantillaPDF(getApplicationContext());
        plantillaPDF.openDocument();//
        plantillaPDF.addMetaData("notengo", "que", "mostrar");
        plantillaPDF.addTitles("Reporte de Instalaciones", "Instalaciones realizadas", "fecha");
        plantillaPDF.addParagraph(textoCorto);
        plantillaPDF.addParagraph(testoLargo);
        plantillaPDF.closeDocument();
        plantillaPDF.pdfView();

    }

    public void onClicAlarmasPDF(View view){
        plantillaPDF = new PlantillaPDF(getApplicationContext());
        plantillaPDF.openDocument();//
        plantillaPDF.addMetaData("notengo", "que", "mostrar");
        plantillaPDF.addTitles("Reporte de Alarmas", "Alarmas activadas", " fecha");
        plantillaPDF.addParagraph(textoCorto);
        plantillaPDF.addParagraph(testoLargo);
        plantillaPDF.closeDocument();
        plantillaPDF.pdfView();

    }

    public void onClicMantenimientoPDF(View view){
        plantillaPDF = new PlantillaPDF(getApplicationContext());
        plantillaPDF.openDocument();//
        plantillaPDF.addMetaData("Mantenimiento", "Reporte", "Admin");
        plantillaPDF.addTitles("Reporte de Mantenimiento", "Mantenimientos realizados", " fecha");
        plantillaPDF.addParagraph(textoCorto);
        plantillaPDF.addParagraph(testoLargo);
        plantillaPDF.closeDocument();
        plantillaPDF.pdfView();

    }

    /**public void onClicAppPDF(View v) {
        plantillaPDF.aplicationViewPDF(this);

    }**/

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    // permission denied, return
                    onBackPressed();
                }
                return;
            }
        }
    }

}
