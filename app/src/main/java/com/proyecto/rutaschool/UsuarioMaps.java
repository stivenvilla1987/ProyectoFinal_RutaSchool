package com.proyecto.rutaschool;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class UsuarioMaps extends FragmentActivity implements OnMapReadyCallback,
        View.OnClickListener{

    private GoogleMap mMap;
    Button btnVideo, btnNota;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        NotaFragment fragment1 = new NotaFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.contenedor,fragment1);
        btnVideo = (Button) findViewById(R.id.boton_video);
        btnNota = (Button) findViewById(R.id.boton_nota);

        btnNota.setOnClickListener(this);
        btnVideo.setOnClickListener(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
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
