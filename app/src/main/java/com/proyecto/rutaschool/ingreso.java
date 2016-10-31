package com.proyecto.rutaschool;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ingreso  extends AppCompatActivity implements View.OnClickListener{
    //Variables
    private EditText txtNameUsu ,txtPassUsu;
    private Cursor fila;
    SQLite_OpenHelper helper = new SQLite_OpenHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contenido_principal_ingreso);
        // Referencias TILs
       // tilUsuario = (TextInputLayout) findViewById(R.id.til_usuario);
       // tilContrasena = (TextInputLayout) findViewById(R.id.til_contrasena);
        txtNameUsu = (EditText) findViewById(R.id.usuarioEditText);
        txtPassUsu = (EditText) findViewById(R.id.contrasenaEditText);
        // Referencia Botones
        findViewById(R.id.boton_sesion).setOnClickListener(this);
        findViewById(R.id.boton_registro).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.boton_sesion:
                helper.abrirDB();
                String email = txtNameUsu.getText().toString();
                String pass = txtPassUsu.getText().toString();
                boolean si=  helper.loginUsuario(email, pass);
                Toast.makeText(this, "login "+si, Toast.LENGTH_SHORT).show();
                Intent mapsUser = new Intent(this, UsuarioMaps.class);
                startActivity(mapsUser);
                break;
            case R.id.boton_registro:
                Intent intent = new Intent(this, Registro.class);
                startActivity(intent);
                break;
        }
    }
}
