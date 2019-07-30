package com.example.lorena.releasemaps;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.UUID;

/**
 * create by Lorena Pérez 10-07-019
 */

public class PlantillaPDF {


    private Context context;
    private File pdfFile;
    private Document document;
    private PdfWriter pdfWriter;
    private Paragraph paragraph;
    private Font fTitle = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
    private Font fSubTitle = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private Font fTex = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    private Font fHighText = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD, BaseColor.RED);


    public PlantillaPDF(Context contexto) {

        this.context = contexto;
    }

    public void openDocument() {
        createFile();
        try {
            document = new Document(PageSize.A4);
            Log.i("se creo el documento", document.getPageSize().toString());
            pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(pdfFile.toString()));
            document.open();

        } catch (Exception e) {
            Log.e("openDocument",e.toString());
            e.printStackTrace();

        }
    }

    private void createFile() {
        File nuevaCarpeta = new File(Environment.getExternalStorageDirectory().toString() +"/"+"PDF");
        String nombreAleatorioPDF= "reporte"+UUID.randomUUID().toString() + ".pdf";
        if (!nuevaCarpeta.exists()) {
            nuevaCarpeta.mkdirs();
            pdfFile = new File(nuevaCarpeta, nombreAleatorioPDF);
        }else{
            Log.d("TAG", "la carpeta ya existe");
            pdfFile = new File(nuevaCarpeta, nombreAleatorioPDF);

        }

    }

    public void closeDocument() {
        document.close();

    }

    public void addMetaData(String titulo, String tema, String autor) {
        document.addTitle(titulo);
        document.addSubject(tema);
        document.addAuthor(autor);
    }

    public void addTitles(String titulo, String subtitulo, String fecha) {

        try {
            paragraph = new Paragraph();
            addChildp(new Paragraph(titulo, fTitle));
            addChildp(new Paragraph(subtitulo, fSubTitle));
            addChildp(new Paragraph("Generado:" + fecha, fHighText));
            paragraph.setSpacingAfter(30);
            document.add(paragraph);
        }catch (Exception e){
            Log.e("addTitles", e.toString());
        }

    }

    private void addChildp(Paragraph parrafoHijo) {

        parrafoHijo.setAlignment(Element.ALIGN_CENTER);
        paragraph.add(parrafoHijo);
    }

    public void addParagraph(String text){
        try {
            paragraph = new Paragraph(text, fTex);
            paragraph.setSpacingAfter(5);
            paragraph.setSpacingBefore(5);
            document.add(paragraph);
        }catch (Exception e){
            Log.e("addParagraph", e.toString());
        }
    }


    /**public  void  table(ArrayList<String[]> nDatos){

        for (int i = 0; i<nDatos.size(); i++){
            tabla.addCell(nDatos.get(i).getNombre());
            tabla.addCell(nDatos.get(i).getApellidos());
            tabla.addCell(nDatos.get(i).getDNI());
            tabla.addCell(nDatos.get(i).getTlf_contacto());
            tabla.addCell(nDatos.get(i).getDireccion());
            tabla.addCell(nDatos.get(i).getCiudad());
            tabla.addCell(nDatos.get(i).getEmail());
        }
    }**/

    public void createTable(ArrayList<String[]> datos){
        try {
        paragraph=new Paragraph();
        paragraph.setFont(fTex);

        //PdfPTable pdfPTable=new PdfPTable(header.length);//titulos de las columnas
       // pdfPTable.setWidthPercentage(100);
        PdfPCell pdfPCell;
       // int indexC=0;
       /** while (indexC<header.length){
            pdfPCell=new PdfPCell(new Phrase(header[indexC++],fSubTitle));
            pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
            pdfPTable.addCell(pdfPCell);
        }**/
            pdfPCell=new PdfPCell();
        for (int indexR=0; indexR<datos.size();indexR++){
            String[] row=datos.get(indexR);
            for (int index=0;index<datos.size();index++){
                pdfPCell= new PdfPCell(new Phrase(row[index]));
                pdfPCell.setHorizontalAlignment(Element.ALIGN_CENTER);
                pdfPCell.setFixedHeight(40);
                //pdfPTable.addCell(pdfPCell);
            }
        }
        paragraph.add(pdfPCell);
        document.add(paragraph);
        }catch (Exception e){
            Log.e("create Table", e.toString());
        }
    }




    //plantilla propia pdf

    public void pdfView(){
        Intent intent= new Intent(context,ViewPDF.class);
        Log.i("ruta", pdfFile.getAbsolutePath().toString());
        intent.putExtra("path", pdfFile.getAbsolutePath());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }

   //para abrir con aplicacion de pdf externa, play store. falla

   /** public void aplicationViewPDF(Activity activity){
        if(pdfFile.exists()){
            Uri uri= Uri.fromFile(pdfFile);
            Intent intent= new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(uri,"appPDF");
            try {
                activity.startActivity(intent);
            }catch (ActivityNotFoundException e){
                activity.startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("market://details?id=com.adobe.reader")));
                Toast.makeText(activity.getApplicationContext(),"No cuentas con una palicacion para visualizar PDF", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(activity.getApplicationContext(),"No se encontro el archivo", Toast.LENGTH_LONG).show();
        }
    }**/

}
