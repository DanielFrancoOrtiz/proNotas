package com.proyect.notas.Daos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MiSQLiteOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "dbNotas";
    private static final int DB_VERSION = 2;

    public static final String TABLE_NOTAS_NAME="Notas";
    public static final String TABLE_IMAG_VIDEO_NAME = "imagenesVideos";


    public static final String[] COLUMNS_NOTAS = {"_id","titulo","descripcion","tipo","fecha","hora","realizada"};
    public static final String[] COLUMNS_IMAG_VIDEO = {"_id","nombre","direccion","tipo","descripcion","idNota"};

    private static final String TABLE_NOTAS = "create table " + TABLE_NOTAS_NAME +" ("
            + COLUMNS_NOTAS[0] + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMNS_NOTAS[1] + " varchar(30) not null, "
            + COLUMNS_NOTAS[2] + " text  null,"
            + COLUMNS_NOTAS[3] + " int not null,"
            + COLUMNS_NOTAS[4] + " date null,"
            + COLUMNS_NOTAS[5] + " time null,"
            + COLUMNS_NOTAS[6] + " boolean null );";

    private static final String TABLE_IMAG_VIDEO = "create table "+  TABLE_IMAG_VIDEO_NAME + "("
            + COLUMNS_IMAG_VIDEO[0] + " integer primary key autoincrement, "
            + COLUMNS_IMAG_VIDEO[1] + " varchar(30) not null, "
            + COLUMNS_IMAG_VIDEO[2] + " text not null,"
            + COLUMNS_IMAG_VIDEO[3] + " int not null,"
            + COLUMNS_IMAG_VIDEO[4] + " text null,"
            + COLUMNS_IMAG_VIDEO[5] + " int null, FOREIGN KEY ("
            + COLUMNS_IMAG_VIDEO[5]+") REFERENCES "
            + TABLE_NOTAS_NAME +" ( "+ COLUMNS_NOTAS[0]+" ));";


    public MiSQLiteOpenHelper(Context contexto) {
        super(contexto, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_NOTAS);
        sqLiteDatabase.execSQL(TABLE_IMAG_VIDEO);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
         sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+DB_NAME);
         onCreate(sqLiteDatabase);
    }
}
