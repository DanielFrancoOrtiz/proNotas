package com.proyect.notas;


import android.os.Environment;

import java.io.File;

public class Info {

    public File creaCarpeta(){
     return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),"GaleriaNotas");
    }
    public String NRImagen(){
        Long consecutivo = System.currentTimeMillis()/1000;
       return  consecutivo.toString()+ ".jpg";
    }
    public String NRVideo(){
        Long consecutivo = System.currentTimeMillis()/1000;
        return  consecutivo.toString()+ ".mp4";
    }
    public String NRAudio(){
        Long consecutivo = System.currentTimeMillis()/1000;
        return  consecutivo.toString()+ ".mp3";
    }
    public String direccion(String nombre){
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) +
                File.separator+"GaleriaNotas"+File.separator + nombre;
    }

}
