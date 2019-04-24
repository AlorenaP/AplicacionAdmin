package com.example.lorena.releasemaps;

import android.app.Activity;
import android.app.Dialog;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        if (status == ConnectionResult.SUCCESS) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map); //referencia a el fragment del mapa de cali
            mapFragment.getMapAsync(this);

        } else {
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, (Activity) getApplicationContext(), 10);
            dialog.show();
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Cali, Colombia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Cali and move the camera
        LatLng cali = new LatLng(3.42158, -76.5205);
        mMap.addMarker(new MarkerOptions().position(cali).title("Esta en Cali").icon(
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW))); //titulo del marcador, tambien se le cambia el color al marcador
        float zoomlevel = 12;
        //googleMap.moveCamera(CameraUpdateFactory.newLatLng(cali));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cali, zoomlevel));

    }

    /**
     * Este metodo permite actualizar las coordenadas de ubicación en el mapa
     * para asi, permitir el desplazamiento hacia la zona indicada y se aplica zoom.  Para esto se toma como parametro
     *
     * @param lat coordenas en latitud.
     * @param lng coordenadas en longitud
     */
    private void goToLocationZoom(double lat, double lng, float zoom) {
        LatLng ll = new LatLng(lat, lng);
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(ll, zoom);
        mMap.moveCamera(update);
    }


    Marker marker;

    /**
     * @param view
     * @throws IOException
     */

    public void geoLocate(View view) throws IOException {
        List<Address> list = null;
        EditText et = (EditText) findViewById(R.id.editText);
        location = et.getText().toString();
        // se ingresa el nombre de la zona a buscar, si esta no es vacia o null, entonces entra a buscar y ubicar en el mapa
        if (!location.equals("") || !location.isEmpty()) {
            Geocoder gc = new Geocoder(this);
            //tengo problema aqui
            list = gc.getFromLocationName(location, 1);

            Address address = list.get(0);
            String locality = address.getLocality();
            Toast.makeText(this, locality, Toast.LENGTH_LONG).show();

            double lat = address.getLatitude();
            double lng = address.getLongitude();
            goToLocationZoom(lat, lng, 15);
            setMarker(locality, lat, lng);

        } else {
            Toast.makeText(MapsActivity.this, "Por favor ingresar una el nombre del lugar a ubicar", Toast.LENGTH_LONG).show();
        }

    }

    ArrayList<Marker> markers = new ArrayList<Marker>();
    //  static final int POLYGON_POINTS = 5;
    //Polygon shape;

    /**
     * permite agregar o pintar los marcadores en el mapa, en la posición indicada que entra por parametro
     * @param locality
     * @param lat
     * @param lng
     */

    private void setMarker(String locality, double lat, double lng) {
    /**
      if (markers.size() == POLYGON_POINTS) {
      removeEverything();
     }**/

//lo cambie
        locality = location;
        MarkerOptions options = new MarkerOptions()
                .title(locality)
                .draggable(false)
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher))
                .position(new LatLng(lat, lng))
                .snippet("I am Here");

        markers.add(mMap.addMarker(options));

        //if (markers.size() == POLYGON_POINTS) {
        //  drawPolygon();
        // }

//        if(marker1 == null) {
//            marker1 = mGoogleMap.addMarker(options);
//        } else if(marker2 == null) {
//            marker2 = mGoogleMap.addMarker(options);
//            drawLine();
//        } else {
//            removeEverything();
//            marker1 = mGoogleMap.addMarker(options);
//        }

//        circle = drawCircle(new LatLng(lat, lng));
    }


    /** private void drawPolygon() {
     PolygonOptions options = new PolygonOptions()
     .fillColor(0x330000FF)
     .strokeWidth(3)
     .strokeColor(Color.RED);

     for(int i=0; i<POLYGON_POINTS;i++){
     options.add(markers.get(i).getPosition());
     }
     shape = mMap.addPolygon(options);

     }**/

    /** private void removeEverything() {
     for(Marker marker : markers) {
     marker.remove();
     }
     markers.clear();
     shape.remove();
     shape = null;

     }**/

}
