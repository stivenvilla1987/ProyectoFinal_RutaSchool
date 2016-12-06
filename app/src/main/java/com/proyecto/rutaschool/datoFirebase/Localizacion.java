package com.proyecto.rutaschool.datoFirebase;

/**
 * Created by Juan David on 05/12/2016.
 */

public class Localizacion {

    double latitud;
    double longitud;

    public Localizacion() {
    }
    public Localizacion(double latitud, double longitud) {
        this.latitud = latitud;
        this.longitud = longitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }
}
