package com.proyecto.rutaschool;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.proyecto.rutaschool.datoFirebase.FirebaseReferencias;
import com.proyecto.rutaschool.datoFirebase.Localizacion;

public class UsuarioMaps extends FragmentActivity implements OnMapReadyCallback{

    public static double latitud;
    public static double longitud;
    private GoogleMap mMap;
    Button btnCerrar, btnNota;
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
        btnCerrar = (Button) findViewById(R.id.sigoutGoogleBtn);
        btnCerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference(FirebaseReferencias.RUTA_REFERENCIA);
        myRef.child(FirebaseReferencias.DATOS_REFERENCIA).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Localizacion localizacion = dataSnapshot.getValue(Localizacion.class);
                latitud =  localizacion.getLatitud();
                longitud = localizacion.getLongitud();
                Log.i("latitud",String.valueOf(latitud));
                LatLng posicionRuta = new LatLng(latitud, longitud);
                mMap.addMarker(new MarkerOptions().position(posicionRuta).title("Ruta").icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(posicionRuta));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private  void signOut(){
        FirebaseAuth.getInstance().signOut();
        Intent cerrar = new Intent(this, ingreso.class);
        startActivity(cerrar);
    }

}
