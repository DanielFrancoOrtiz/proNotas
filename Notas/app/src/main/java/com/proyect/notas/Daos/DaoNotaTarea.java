package com.proyect.notas.Daos;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class DaoNotaTarea {
    private Context contexto;
    private SQLiteDatabase database;

    public DaoNotaTarea(Context contexto) {
        this.contexto = contexto;
        this.database = new MiSQLiteOpenHelper(contexto).getWritableDatabase();
    }

    public long Insert(NotaTarea nota){
        ContentValues cv = new ContentValues();
        cv.put(MiSQLiteOpenHelper.COLUMNS_NOTAS[1],nota.getTitulo());
        cv.put(MiSQLiteOpenHelper.COLUMNS_NOTAS[2],nota.getDescripcion());
        cv.put(MiSQLiteOpenHelper.COLUMNS_NOTAS[3],nota.getTipo());
        cv.put(MiSQLiteOpenHelper.COLUMNS_NOTAS[4],nota.getFecha().toString());
        cv.put(MiSQLiteOpenHelper.COLUMNS_NOTAS[5],nota.getHora().toString());
        return database.insert(MiSQLiteOpenHelper.TABLE_NOTAS_NAME,null,cv);
    }
    public long Update(NotaTarea nota){
        ContentValues cv = new ContentValues();
        cv.put(MiSQLiteOpenHelper.COLUMNS_NOTAS[0],nota.getId());
        cv.put(MiSQLiteOpenHelper.COLUMNS_NOTAS[1],nota.getTitulo());
        cv.put(MiSQLiteOpenHelper.COLUMNS_NOTAS[2],nota.getDescripcion());
        cv.put(MiSQLiteOpenHelper.COLUMNS_NOTAS[3],nota.getTipo());
        cv.put(MiSQLiteOpenHelper.COLUMNS_NOTAS[4],nota.getFecha().toString());
        cv.put(MiSQLiteOpenHelper.COLUMNS_NOTAS[5],nota.getHora().toString());

        return database.update(MiSQLiteOpenHelper.TABLE_NOTAS_NAME,cv,"id = ?",new String[]{String.valueOf(nota.getId())});
    }

    public long Delete(int id){
        return database.delete(MiSQLiteOpenHelper.TABLE_NOTAS_NAME,"id = ?",new String[]{String.valueOf(id)});
    }

    public List<NotaTarea> getAll(){
        List<NotaTarea> lista = null;
        Cursor cur = database.query(MiSQLiteOpenHelper.TABLE_NOTAS_NAME,MiSQLiteOpenHelper.COLUMNS_NOTAS
                ,null,null,null,null,null);
        if(cur.moveToFirst()){
            lista = new ArrayList<>();
            do{
                NotaTarea not = new NotaTarea(cur.getInt(0),cur.getString(1),cur.getString(2),
                        cur.getInt(3), Date.valueOf(cur.getString(4)), Time.valueOf(cur.getString(5)));
                lista.add(not);
            }while (cur.moveToNext());
        }
        return lista;
    }

    public NotaTarea getNotaTarea(int id){
        Cursor cur = database.query(MiSQLiteOpenHelper.TABLE_NOTAS_NAME,MiSQLiteOpenHelper.COLUMNS_NOTAS
                ,"id = ?",new String[]{String.valueOf(id)},null,null,null);
        if(cur.moveToFirst()){
            return new NotaTarea(cur.getInt(0),cur.getString(1),cur.getString(2),
                    cur.getInt(3), Date.valueOf(cur.getString(4)), Time.valueOf(cur.getString(5)));
        }
        return null;

    }



}
