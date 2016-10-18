package com.proyecto.rutaschool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registro extends AppCompatActivity {
    Button btnRegistro;
    private EditText txtName, txtLastName, txtDir, txtEmail, txtPass;
    SQLite_OpenHelper helper = new SQLite_OpenHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        txtName = (EditText) findViewById(R.id.nameEditText);
        txtLastName = (EditText) findViewById(R.id.lastNameEditText);
        txtEmail = (EditText) findViewById(R.id.correoEditText);
        txtPass = (EditText) findViewById(R.id.contrasenaEditText);
        txtDir = (EditText) findViewById(R.id.direccionEditText);
        btnRegistro = (Button) findViewById(R.id.boton_registro);
        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helper.abrirDB();
                helper.insertarUsuario(String.valueOf(txtName.getText()),
                        String.valueOf(txtLastName.getText()),
                        String.valueOf(txtDir.getText()),
                        String.valueOf(txtEmail.getText()),
                        String.valueOf(txtPass.getText()));
                helper.cerrarDB();
                Toast.makeText(getApplicationContext(),"Usuario registrado con éxito",
                        Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(),ingreso.class);
                startActivity(i);
            }
        });

    }
}
