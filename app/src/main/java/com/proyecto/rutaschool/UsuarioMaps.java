package com.proyecto.rutaschool;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.proyecto.rutaschool.datoFirebase.FirebaseReferencias;
import com.proyecto.rutaschool.datoFirebase.Localizacion;

public class UsuarioMaps extends FragmentActivity implements OnMapReadyCallback,
        View.OnClickListener{

    double latitud;
    double longitud;
    private GoogleMap mMap;
    Button btnVideo, btnNota;
   // private FirebaseDatabase database;
   // private DatabaseReference myRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(FirebaseReferencias.RUTA_REFERENCIA);
        Localizacion localizacion = new Localizacion(6, -75);
        myRef.child(FirebaseReferencias.DATOS_REFERENCIA).setValue(localizacion);
        myRef.child(FirebaseReferencias.DATOS_REFERENCIA).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Localizacion localizacion = dataSnapshot.getValue(Localizacion.class);
                latitud =  localizacion.getLatitud();
                longitud = localizacion.getLongitud();
                //Log.i("LATITUD", String.valueOf(latitud));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("LATITUD", String.valueOf(latitud));
            }
        });

        NotaFragment fragment1 = new NotaFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.contenedor,fragment1);
        btnVideo = (Button) findViewById(R.id.boton_video);
        btnNota = (Button) findViewById(R.id.boton_nota);

        btnNota.setOnClickListener(this);
        btnVideo.setOnClickListener(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Log.i("LATITUD", String.valueOf(latitud));     // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(latitud, longitud);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Ruta"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.boton_nota:
                NotaFragment fragment1 = new NotaFragment();
                FragmentTransaction transition1 =  getSupportFragmentManager().beginTransaction();
                transition1.replace(R.id.contenedor,fragment1);
                transition1.commit();
                break;
            case R.id.boton_video:
                VideoFragment fragment2 = new VideoFragment();
                FragmentTransaction transition2 =  getSupportFragmentManager().beginTransaction();
                transition2.replace(R.id.contenedor,fragment2);
                transition2.commit();
                break;

        }

    }
}
