package com.proyecto.rutaschool;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ingreso  extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,View.OnClickListener{
    //Variables
    private EditText txtNameUsu ,txtPassUsu;
    private Cursor fila;
    private static int RC_SIGN_IN = 0;
    private static String TAG = "MAIN_ACTIVITY";
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference myRef;
    private FirebaseAuth.AuthStateListener authStateListener;
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

        mAuthListener = new FirebaseAuth.AuthStateListener(){

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){
                    Intent mapsUser = new Intent(getApplicationContext(), UsuarioMaps.class);
                    startActivity(mapsUser);
                }else{
                }
            }
        };
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();
        mAuth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if(user != null){

                    Log.d("AUTH","Usuario logueado en: " + user.getEmail());
                }
                else{
                    Log.d("AUTH","Usuario no logueado");
                }
            }
        };

        // Configura inicio de sesion
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        findViewById(R.id.siginGoogleBtn).setOnClickListener(this);
       // findViewById(R.id.sigoutGoogleBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.boton_sesion:
                helper.abrirDB();
                String email = txtNameUsu.getText().toString();
                String pass = txtPassUsu.getText().toString();
                FirebaseAuth.getInstance().signInWithEmailAndPassword(email,pass);
                Intent mapsUser = new Intent(this, UsuarioMaps.class);
                startActivity(mapsUser);

//                boolean si =  helper.loginUsuario(email, pass);
//                if(si == true && email.equals("admin")){
//                    Intent Coordenadas = new Intent(this, CoordenadasAdmin.class);
//                    startActivity(Coordenadas);
//                }
//                else if (si == true){
//                    Intent mapsUser = new Intent(this, UsuarioMaps.class);
//                    startActivity(mapsUser);
//                }
//                else{
//                    Toast.makeText(this, "Usuario incorrecto", Toast.LENGTH_SHORT).show();
//                }
                break;
            case R.id.boton_registro:
                Intent intent = new Intent(this, Registro.class);
                startActivity(intent);
                break;
            case R.id.siginGoogleBtn:
                signIn();
                break;
            case R.id.sigoutGoogleBtn:
                signOut();
                break;
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
        FirebaseAuth.getInstance().addAuthStateListener(mAuthListener);

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(mAuthListener != null){
            FirebaseAuth.getInstance().removeAuthStateListener(mAuthListener);
        }
        if(authStateListener != null){
            mAuth.removeAuthStateListener(authStateListener);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if(result.isSuccess()){
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }
            else{
                Log.d(TAG, "Falló inicio de sesión con Google");
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task){
                        Log.d("AUTH", "signInWithCredential:oncomplete: " + task.isSuccessful());
                    }
                });
    }

    private void signIn(){
        Intent signIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signIntent, RC_SIGN_IN);
        Intent Coordenadas = new Intent(this, CoordenadasAdmin.class);
        startActivity(Coordenadas);

    }

    private  void signOut(){
        FirebaseAuth.getInstance().signOut();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}