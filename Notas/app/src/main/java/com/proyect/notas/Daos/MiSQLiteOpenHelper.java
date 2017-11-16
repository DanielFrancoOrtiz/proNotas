package com.proyect.notas.Daos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MiSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "dbNotas";

    public static final String TABLE_NOTAS_NAME="Notas";
    private static final int DB_VERSION = 2;
    public static final String[]COLUMNS_NOTAS = {"id","titulo","descripcion","tipo","fecha","hora"};

    private static final String TABLE_NOTAS = "create table " + TABLE_NOTAS_NAME +" ("
            + COLUMNS_NOTAS[0] + " int autoincrement primay key, "
            + COLUMNS_NOTAS[1] + " varchar(30) not null, "
            + COLUMNS_NOTAS[2] + " text  null,"
            + COLUMNS_NOTAS[3] + " int not null,"
            + COLUMNS_NOTAS[4] + " date null,"
            + COLUMNS_NOTAS[5] + " time null );";


    public MiSQLiteOpenHelper(Context contexto) {
        super(contexto, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_NOTAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
         sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DB_NAME);
         onCreate(sqLiteDatabase);
    }
}
