package com.example.lorena.releasemaps;

public class Encriptar {

    public Encriptar() {

    }

    public String encriptar(String texto, String clave) {
        int tamText = texto.length();
        int tamClave = clave.length();
        int temp, p = 0;
        String encriptado = "";

        int textAscii[] = new int[tamText];
        int claveAscii[] = new int[tamClave];

        /* se guardan los caracteres de cada String
        en números correspondientes al Ascii*/
        for (int i = 0; i < tamText; i++) {
            textAscii[i] = texto.charAt(i);
        }

        for (int j = 0; j < tamClave; j++) {
            claveAscii[j] = clave.charAt(j);
        }
        //Inicia encriptado

        for (int i=0;i<tamText;i++)
        {
            p++;
            if (p>=tamClave) {
                p = 0;
                temp = textAscii[i] + claveAscii[p];

                if (temp > 255) {
                    temp = temp - 255;
                }
                encriptado = encriptado + temp;
            }
        }
        return encriptado;
    }

}
