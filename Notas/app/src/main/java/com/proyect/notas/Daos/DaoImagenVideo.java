package com.proyect.notas.Daos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Franco on 21/11/2017.
 */

public class DaoImagenVideo {
    private Context contexto;
    private SQLiteDatabase database;

    public DaoImagenVideo(Context contexto) {
        this.contexto = contexto;
        this.database = new MiSQLiteOpenHelper(contexto).getWritableDatabase();
    }

    public long Insert(FotoVideo obj){
        ContentValues cv = new ContentValues();
        cv.put(MiSQLiteOpenHelper.COLUMNS_IMAG_VIDEO[1],obj.getNombre());
        cv.put(MiSQLiteOpenHelper.COLUMNS_IMAG_VIDEO[2],obj.getdireccion());
        return  database.insert(MiSQLiteOpenHelper.TABLE_IMAG_VIDEO_NAME,null,cv);
    }
    public long Update(FotoVideo obj){
        ContentValues cv = new ContentValues();
        cv.put(MiSQLiteOpenHelper.COLUMNS_IMAG_VIDEO[0],obj.getId());
        cv.put(MiSQLiteOpenHelper.COLUMNS_IMAG_VIDEO[1],obj.getNombre());
        cv.put(MiSQLiteOpenHelper.COLUMNS_IMAG_VIDEO[2],obj.getdireccion());

        return database.update(MiSQLiteOpenHelper.TABLE_NOTAS_NAME,cv,"_id = ?",new String[]{String.valueOf(obj.getId())});
    }
    public long Delete(int id){
        return database.delete(MiSQLiteOpenHelper.TABLE_IMAG_VIDEO_NAME,"_id = ?",new String[]{String.valueOf(id)});
    }

    public List<FotoVideo> getAll(){
        List<FotoVideo> lista = null;
        Cursor cur = database.query(MiSQLiteOpenHelper.TABLE_IMAG_VIDEO_NAME,MiSQLiteOpenHelper.COLUMNS_IMAG_VIDEO
                ,null,null,null,null,null);
        if(cur.moveToFirst()){
            lista = new ArrayList<>();
            do{
                FotoVideo fot =  new FotoVideo(cur.getInt(0),
                        cur.getString(1),cur.getString(2));
               lista.add(fot);
            }while (cur.moveToNext());
        }
        return lista;
    }
    public FotoVideo getNotaTarea(int id){
        Cursor cur = database.query(MiSQLiteOpenHelper.TABLE_IMAG_VIDEO_NAME,MiSQLiteOpenHelper.COLUMNS_IMAG_VIDEO
                ,"_id = ?",new String[]{String.valueOf(id)},null,null,null);
        if(cur.moveToFirst()){
            return new FotoVideo(cur.getInt(0), cur.getString(1),cur.getString(2));
        }
        return null;

    }



}
