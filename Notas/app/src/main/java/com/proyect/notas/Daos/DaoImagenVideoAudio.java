package com.proyect.notas.Daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Franco on 21/11/2017.
 */

public class DaoImagenVideoAudio {
    private Context contexto;
    private SQLiteDatabase database;

    public DaoImagenVideoAudio(Context contexto) {
        this.contexto = contexto;
        this.database = new MiSQLiteOpenHelper(contexto).getWritableDatabase();
    }

    public long Insert(FotoVideoAudio obj){
        ContentValues cv = new ContentValues();

        cv.put(MiSQLiteOpenHelper.COLUMNS_IMAG_VIDEO[1],obj.getNombre());
        cv.put(MiSQLiteOpenHelper.COLUMNS_IMAG_VIDEO[2],obj.getDireccion());
        cv.put(MiSQLiteOpenHelper.COLUMNS_IMAG_VIDEO[3],obj.getTipo());
        cv.put(MiSQLiteOpenHelper.COLUMNS_IMAG_VIDEO[4], obj.getDescripcion());

        return  database.insert(MiSQLiteOpenHelper.TABLE_IMAG_VIDEO_NAME,null,cv);
    }
    public long Update(FotoVideoAudio obj){
        ContentValues cv = new ContentValues();
        cv.put(MiSQLiteOpenHelper.COLUMNS_IMAG_VIDEO[0],obj.getId());
        cv.put(MiSQLiteOpenHelper.COLUMNS_IMAG_VIDEO[1],obj.getNombre());
        cv.put(MiSQLiteOpenHelper.COLUMNS_IMAG_VIDEO[2],obj.getDireccion());
        cv.put(MiSQLiteOpenHelper.COLUMNS_IMAG_VIDEO[3],obj.getTipo());
        cv.put(MiSQLiteOpenHelper.COLUMNS_IMAG_VIDEO[4], obj.getDescripcion());
        cv.put(MiSQLiteOpenHelper.COLUMNS_IMAG_VIDEO[5],obj.getIdNota());
        return database.update(MiSQLiteOpenHelper.TABLE_IMAG_VIDEO_NAME,cv,"_id = ?",new String[]{String.valueOf(obj.getId())});
    }
    public long Delete(int id){
        return database.delete(MiSQLiteOpenHelper.TABLE_IMAG_VIDEO_NAME,"_id = ?",new String[]{String.valueOf(id)});
    }

    public List<FotoVideoAudio> getAll(){
        List<FotoVideoAudio> lista = null;
        Cursor cur = database.query(MiSQLiteOpenHelper.TABLE_IMAG_VIDEO_NAME,MiSQLiteOpenHelper.COLUMNS_IMAG_VIDEO
                ,null,null,null,null,null);
        if(cur.moveToFirst()){
            lista = new ArrayList<>();
            do{
                FotoVideoAudio fot =  new FotoVideoAudio(cur.getInt(0),
                        cur.getString(1),cur.getString(2),cur.getInt(3),
                        cur.getString(4),cur.getInt(5));
               lista.add(fot);
            }while (cur.moveToNext());
        }
        return lista;
    }
    public List<FotoVideoAudio> getAllFotos(){
        List<FotoVideoAudio> lista = null;
        Cursor cur = database.query(MiSQLiteOpenHelper.TABLE_IMAG_VIDEO_NAME,MiSQLiteOpenHelper.COLUMNS_IMAG_VIDEO
                ,"tipo = ?",new String[]{String.valueOf(1)},null,null,null);
        if(cur.moveToFirst()){
            lista = new ArrayList<>();
            do{
                FotoVideoAudio fot =  new FotoVideoAudio(cur.getInt(0),
                        cur.getString(1),cur.getString(2),
                        cur.getInt(3), cur.getString(4),cur.getInt(5));
                lista.add(fot);
            }while (cur.moveToNext());
        }
        return lista;
    }

    public List<FotoVideoAudio> getAllVideos(){
        List<FotoVideoAudio> lista = null;
        Cursor cur = database.query(MiSQLiteOpenHelper.TABLE_IMAG_VIDEO_NAME,MiSQLiteOpenHelper.COLUMNS_IMAG_VIDEO
                ,"tipo = ?",new String[]{String.valueOf(2)},null,null,null);
        if(cur.moveToFirst()){
            lista = new ArrayList<>();
            do{
                FotoVideoAudio fot =  new FotoVideoAudio(cur.getInt(0),
                        cur.getString(1),cur.getString(2),cur.getInt(3)
                ,cur.getString(4),cur.getInt(5));
                lista.add(fot);
            }while (cur.moveToNext());
        }
        return lista;
    }
    public List<FotoVideoAudio> getAllAudios(){
        List<FotoVideoAudio> lista = null;
        Cursor cur = database.query(MiSQLiteOpenHelper.TABLE_IMAG_VIDEO_NAME,MiSQLiteOpenHelper.COLUMNS_IMAG_VIDEO
                ,"tipo = ?",new String[]{String.valueOf(3)},null,null,null);
        if(cur.moveToFirst()){
            lista = new ArrayList<>();
            do{
                FotoVideoAudio fot =  new FotoVideoAudio(cur.getInt(0),
                        cur.getString(1),cur.getString(2),cur.getInt(3),
                        cur.getString(4),cur.getInt(5));
                lista.add(fot);
            }while (cur.moveToNext());
        }
        return lista;
    }


    public FotoVideoAudio getNotaTarea(int id){
        Cursor cur = database.query(MiSQLiteOpenHelper.TABLE_IMAG_VIDEO_NAME,MiSQLiteOpenHelper.COLUMNS_IMAG_VIDEO
                ,"_id = ?",new String[]{String.valueOf(id)},null,null,null);
        if(cur.moveToFirst()){
            return new FotoVideoAudio(cur.getInt(0), cur.getString(1),
                    cur.getString(2),cur.getInt(3), cur.getString(4),cur.getInt(5));
        }
        return null;
    }


    public List<FotoVideoAudio> getMultimediaNota( int idNota){
        List<FotoVideoAudio> lista = null;
        Cursor cur = database.query(MiSQLiteOpenHelper.TABLE_IMAG_VIDEO_NAME,MiSQLiteOpenHelper.COLUMNS_IMAG_VIDEO
                ,"idNota = ?",new String[]{String.valueOf(idNota)},null,null,null);
        if(cur.moveToFirst()){
            lista = new ArrayList<>();
            do{
                FotoVideoAudio fot =  new FotoVideoAudio(cur.getInt(0),
                        cur.getString(1),cur.getString(2),cur.getInt(3),
                        cur.getString(4),cur.getInt(5));
                lista.add(fot);
            }while (cur.moveToNext());
        }
        return lista;
    }



}
