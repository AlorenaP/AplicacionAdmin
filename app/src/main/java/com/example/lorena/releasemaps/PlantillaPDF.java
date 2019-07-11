package com.example.lorena.releasemaps;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.util.Log;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import org.w3c.dom.Document;

import java.io.File;
import java.io.FileOutputStream;

/**
 * create by Lorena Pérez 10-07-019
 */

public class PlantillaPDF {


    private Context context;
    private File pdfFile;
    private com.itextpdf.text.Document document;
    private PdfWriter pdfWriter;
    private Paragraph paragraph;
    private Font fTitle = new Font(Font.FontFamily.TIMES_ROMAN, 20, Font.BOLD);
    private Font fSubTitle = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
    private Font fTex = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
    private Font fHighText = new Font(Font.FontFamily.TIMES_ROMAN, 15, Font.BOLD, BaseColor.CYAN);


    public PlantillaPDF(Context contexto) {

        this.context = contexto;
    }

    public void openDocument() {
        createFile();
        try {
            document = new com.itextpdf.text.Document(PageSize.A4);
            pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(pdfFile));
            document.open();

        } catch (Exception e) {
            Log.e("openDocument",e.toString());
            e.printStackTrace();

        }
    }

    private void createFile() {
        File folder = new File(Environment.getExternalStorageDirectory().toString(), "PDF");
        if (!folder.exists()) {
            folder.mkdirs();
            pdfFile = new File(folder, "plantillapdf.pdf");

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

    public void pdfView(){

        Intent intent= new Intent(context,ViewPDF.class);
        intent.putExtra("path", pdfFile.getAbsolutePath());
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);



    }

}
