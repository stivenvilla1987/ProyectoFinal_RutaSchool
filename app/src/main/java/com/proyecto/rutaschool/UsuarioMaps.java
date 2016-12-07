package com.proyecto.rutaschool;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
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
    Marker marcador;
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

        //NotaFragment fragment1 = new NotaFragment();
        //getSupportFragmentManager().beginTransaction().add(R.id.contenedor,fragment1);
        //btnVideo = (Button) findViewById(R.id.boton_video);
        //btnNota = (Button) findViewById(R.id.boton_nota);

        //btnNota.setOnClickListener(this);
        //btnVideo.setOnClickListener(this);
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
                agregar_marcador(posicionRuta);
                /*mMap.addMarker(new MarkerOptions().position(posicionRuta).title("Ruta").icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(posicionRuta));
                mMap.animateCamera( CameraUpdateFactory.zoomTo( 15.0f ) );*/

            }

            private void agregar_marcador (LatLng posicionRuta){
                CameraUpdate miPisicion = CameraUpdateFactory.newLatLngZoom(posicionRuta,16);
                if (marcador!=null)marcador.remove();
                marcador=mMap.addMarker(new MarkerOptions()
                        .position(posicionRuta)
                        .title("Mi Ruta")
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
                mMap.animateCamera(miPisicion);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }

    /*@Override
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

    }*/
}
