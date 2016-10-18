package com.proyecto.rutaschool;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class ingreso  extends AppCompatActivity implements View.OnClickListener{
    //Variables
    private TextInputLayout tilUsuario;
    private TextInputLayout tilContrasena;
    private EditText txtNameUsu ,txtPassUsu;
    private Cursor fila;

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
                break;
            case R.id.boton_registro:
                Intent intent = new Intent(this, Registro.class);
                startActivity(intent);
                break;
        }
    }
}
