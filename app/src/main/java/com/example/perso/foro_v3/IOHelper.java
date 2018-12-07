package com.example.perso.foro_v3;

import java.util.ArrayList;

public class IOHelper {
    public static ArrayList<String> StringtoArrayList(String taqs){
        ArrayList<String> TAQS= new ArrayList<>();
        String frase = taqs;
        int pos = frase.indexOf(",");
        while(pos!=-1){
            taqs = frase.substring(0,pos);
            frase = frase.substring(pos+1,frase.length());
            TAQS.add(taqs);
            pos=frase.indexOf(",");
        }
        TAQS.add(frase);
        return TAQS;
    }
}
