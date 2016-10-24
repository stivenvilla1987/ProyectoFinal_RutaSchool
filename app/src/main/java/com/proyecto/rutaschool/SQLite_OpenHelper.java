package com.proyecto.rutaschool;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by Juan David on 05/10/2016.
 */

public class SQLite_OpenHelper extends SQLiteOpenHelper {

    //Metainformación de la base de datos
    private static final String DBNAME = "usuarios.db";
    private static final int VERSION = 1;
    private static final String TABLE_NAME = "UsuariosRuta";
    private static final String STRING_TYPE = "text";
    private static final String INT_TYPE = "integer";

    public SQLite_OpenHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }
    public static class columnasUsuarios{
        static final String ID_USUARIO = BaseColumns._ID;
        static final String NAME_USUARIO = "Nombre";
        static final String APELLIDO_USUARIO = "Apellidos";
        static final String DIRECCION_USUARIO = "Direccion";
        static final String CORREO_USUARIO = "Correo";
        static final String PASS_USUARIO = "Password";
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "(" +
                columnasUsuarios.ID_USUARIO + " " + INT_TYPE + " primary key autoincrement," +
                columnasUsuarios.NAME_USUARIO + " " + STRING_TYPE + " not null," +
                columnasUsuarios.APELLIDO_USUARIO + " " + STRING_TYPE + " not null," +
                columnasUsuarios.DIRECCION_USUARIO + " " + STRING_TYPE + " not null," +
                columnasUsuarios.CORREO_USUARIO + " " + STRING_TYPE + " not null," +
                columnasUsuarios.PASS_USUARIO + " " + STRING_TYPE + " not null," +")";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +  TABLE_NAME);
        onCreate(db);
    }

    //Método que permite abrir la base de datos
    public void abrirDB(){ this.getWritableDatabase(); }
    //Método que permite cerrar la base de datos
    public void cerrarDB(){
        this.close();
    }
    //Método que permite insertar registros en la tabla de usuarios
    public void insertarUsuario(String name,String lastName,String dir, String email, String pass){
        ContentValues valores = new ContentValues();
        valores.put(columnasUsuarios.NAME_USUARIO,name);
        valores.put(columnasUsuarios.APELLIDO_USUARIO,lastName);
        valores.put(columnasUsuarios.DIRECCION_USUARIO,dir);
        valores.put(columnasUsuarios.CORREO_USUARIO,email);
        valores.put(columnasUsuarios.CORREO_USUARIO,pass);
        this.getWritableDatabase().insert(TABLE_NAME,null,valores);
    }

    //Método que permite validar si el usuario existe
    public boolean loginUsuario(String email, String pass)throws SQLException{
        Cursor cursor = this.getReadableDatabase().rawQuery(
                "select * from " + TABLE_NAME + " where " + columnasUsuarios.CORREO_USUARIO + " ='"+email+"' AND " + columnasUsuarios.PASS_USUARIO + "='"+pass+"'", null);
        int value = cursor.getCount();
        cursor.close();
        return value == 1;
    }
}
