package com.example.lorena.releasemaps;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class Reportes extends AppCompatActivity {

    private static final int MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1;
    private String[] header = {"Id", "Nombre", "Apellido"};
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
    private String fecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reportes);

        int checkPermisos = ContextCompat.checkSelfPermission(Reportes.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (checkPermisos != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(Reportes.this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        Date date = new Date();
        fecha = dateFormat.format(date);

    }

    private ArrayList<String[]> getDatosMantenimiento() {

        ArrayList<String[]> rows = new ArrayList<>();
        ReporteMantenimiento instancia = new ReporteMantenimiento();
        String cedula = null;
        String nombre = null;
            int i;
            int j;
            for (i = 0; i < instancia.cedula.length; i++) {

                cedula = instancia.getCedula()[i];
            }
            for (j = 0; j < instancia.nombre.length; j++) {
                nombre = instancia.getNombre()[j];
            }
            rows.add(new String[]{cedula, nombre, "Lopez"});
            /**rows.add(new String[]{"1223","Lorena", "oper"});
             rows.add(new String[]{"1223","Lorena", "oper"});
             rows.add(new String[]{"1223","Lorena", "oper"});**/


        return rows;

    }

    public void onClicInstallPDF(View view) {
        plantillaPDF = new PlantillaPDF(getApplicationContext());
        plantillaPDF.openDocument();//
        plantillaPDF.addMetaData("no tengo", "que", "mostrar");
        plantillaPDF.addTitles("Reporte de Instalaciones", "Instalaciones realizadas", fecha);
        plantillaPDF.addParagraph(textoCorto);
        plantillaPDF.addParagraph(testoLargo);
        plantillaPDF.createTable(getDatosMantenimiento());
        plantillaPDF.closeDocument();
        plantillaPDF.pdfView();

    }

    public void onClicAlarmasPDF(View view) {
        plantillaPDF = new PlantillaPDF(getApplicationContext());
        plantillaPDF.openDocument();//
        plantillaPDF.addMetaData("no tengo", "que", "mostrar");
        plantillaPDF.addTitles("Reporte de Alarmas", "Alarmas activadas", fecha);
        plantillaPDF.addParagraph(textoCorto);
        plantillaPDF.addParagraph(testoLargo);
        //plantillaPDF.createTable(header, getDatosMantenimiento());
        plantillaPDF.closeDocument();
        plantillaPDF.pdfView();

    }

    public void onClicMantenimientoPDF(View view) {
        plantillaPDF = new PlantillaPDF(getApplicationContext());
        plantillaPDF.openDocument();//
        plantillaPDF.addMetaData("Mantenimiento", "Reporte", "Admin");
        plantillaPDF.addTitles("Reporte de Mantenimiento", "Mantenimientos realizados", fecha);
        plantillaPDF.addParagraph(textoCorto);
        plantillaPDF.addParagraph(testoLargo);
        plantillaPDF.createTable(getDatosMantenimiento());
        plantillaPDF.closeDocument();
        plantillaPDF.pdfView();

    }

    /**
     * public void onClicAppPDF(View v) {
     * plantillaPDF.aplicationViewPDF(this);
     * <p>
     * }
     **/

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
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
