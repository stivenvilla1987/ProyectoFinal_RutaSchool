package com.proyecto.rutaschool;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Juan David on 05/10/2016.
 */

public class SQLite_OpenHelper extends SQLiteOpenHelper {

    private static final String DBNAME = "usuarios.db";
    private static final int VERSION = 1;
    private static final String TABLE_NAME = "UsuariosRuta";

    public SQLite_OpenHelper(Context context) {
        super(context, DBNAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " + TABLE_NAME + "(_ID INTEGER PRIMARY KEY, Nombre TEXT NOT NULL, Apellidos TEXT NOT NULL," +
                "Direccion TEXT NOT NULL, Correo TEXT NOT NULL, Password TEXT NOT NULL);";
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
        valores.put("Nombre",name);
        valores.put("Apellidos",lastName);
        valores.put("Direccion",dir);
        valores.put("Correo",email);
        valores.put("Password",pass);
        this.getWritableDatabase().insert("usuarios",null,valores);
    }

}
