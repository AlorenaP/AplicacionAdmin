package com.example.lorena.releasemaps;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.renderscript.Sampler;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    // url to get all nodes list
    private static String URL_ALL_NODES = "http://192.168.0.3/testgeo/nodos.php";
    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_GPS = "gps";
    private static final String TAG_ID = "id";
    private static final String TAG_DIRECCION = "direccion";
    private static final String TAG_LATITUD = "latitud";
    private static final String TAG_LONGITUD = "longitud";
    private static final String TAG_ESTADO ="estado";

    private GoogleMap mMap;
    private String location;
    SearchView searchView;
    // Creating JSON Parser object
    JSONParser jParser = new JSONParser();
    JSONObject json;
    ArrayList<HashMap<String, String>> nodos;
    private String latitud, longitud;
    // objetos JSONArray
    JSONArray nodes = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        searchView = findViewById(R.id.searchGo);
        // Hashmap para los nodos
        //nodos = new ArrayList<HashMap<String, String>>();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        //llamado a cargar los nodos registrados
        new LoadAllNodes().execute();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                location=searchView.getQuery().toString();
                List<Address> addressList = null;
                if (!location.equals("") || !location.isEmpty()) {
                    Geocoder geocoder = new Geocoder(MapsActivity.this);
                    try{
                        addressList = geocoder.getFromLocationName(location,1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(!addressList.isEmpty()){
                    Address address=addressList.get(0);
                    LatLng latLng = new LatLng(address.getLatitude(), address.getLongitude());
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
                    }else{
                        Toast.makeText(MapsActivity.this, "Por favor ingresar el nombre correcto " +
                                "del lugar a ubicar", Toast.LENGTH_LONG).show();
                    }
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        mapFragment.getMapAsync(MapsActivity.this);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
       /** int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        if (status == ConnectionResult.SUCCESS) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map); //referencia a el fragment del mapa de cali
            mapFragment.getMapAsync(this);
            new LoadAllNodes().execute();
        } else {
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, (Activity) getApplicationContext(), 10);
            dialog.show();
        }**/
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
        /**mMap.addMarker(new MarkerOptions().position(cali).title("Esta en Cali").icon(
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));**/ //titulo del marcador, tambien se le cambia el color al marcador
        //cargar los objetos tipo nodo en background thread
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

    /**public void geoLocate(View view) throws IOException {
        List<Address> list = null;
        EditText et = (EditText) findViewById(R.id.editText);
        location = et.getText().toString();
        // se ingresa el nombre de la zona a buscar, sino es vacia o null, entonces entra a buscar y ubica en el mapa
        if (!location.equals("") || !location.isEmpty()) {
            Geocoder gc = new Geocoder(this);
            //tengo problema aqui ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            list = gc.getFromLocationName(location, 1);
            Address address = list.get(0);
            String locality = address.getLocality();
            Toast.makeText(this, locality, Toast.LENGTH_LONG).show();

            double lat = address.getLatitude();
            double lng = address.getLongitude();
            goToLocationZoom(lat, lng, 15);
           /// setMarker(locality, lat, lng);

        } else {
            Toast.makeText(MapsActivity.this, "Por favor ingresar el nombre del lugar a ubicar", Toast.LENGTH_LONG).show();
        }

    }**/

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



    /**
     * clase encargada de obtener todos los nodos de la base de datos en segundo plano y se pintan en el mapa
     * */

    class LoadAllNodes extends AsyncTask<String, Void, String> {

        //Antes de empezar el background thread Show Progress Dialog
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /**pDialog = new ProgressDialog(MapsActivity.this);
            pDialog.setMessage("Cargando, Por favor espere...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();**/
        }

        @Override
        /**
         * obtengo todos los nodos
         * */
        protected String doInBackground(String... args) {

            // Building Parameters
            List params = new ArrayList();
            // getting JSON string from URL
            json = jParser.makeHttpRequest(URL_ALL_NODES, "GET", params);
            // Check your log cat for JSON reponse
            Log.d("All Nodes: ", json.toString());
            String result=json.toString();
                        // creating new HashMap
                       /** HashMap map = new HashMap();
                        // adding each child node to HashMap key => value
                        map.put(TAG_ID, id);
                        map.put(TAG_DIRECCION, direccion);
                        map.put(TAG_LATITUD, latitud);
                        map.put(TAG_LONGITUD, longitud);
                        nodos.add(map);**/
                       // Address prueba = nodos.
                       // setMarker(coordenadas)
            return result;
        }

        @Override
        protected void onPostExecute(String result)
        {
            try {
                int success = json.getInt(TAG_SUCCESS);
                if (success == 1) {
                    // Getting Array of nodes
                    nodes = json.getJSONArray(TAG_GPS);
                    // looping through All Products
                    Log.i("Lorena", "produtos.length " + nodes.length());
                    for (int i = 0; i < nodes.length(); i++) {
                        JSONObject c = nodes.getJSONObject(i);
                        // Storing each json item in variable
                        String id = c.getString(TAG_ID);
                        String direccion = c.getString(TAG_DIRECCION);
                        latitud = c.getString(TAG_LATITUD);
                        longitud = c.getString(TAG_LONGITUD);
                        String estado= c.getString(TAG_ESTADO);

                        if(estado.equals("activo")) {
                            mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(Double.parseDouble(latitud), Double.parseDouble(longitud))).title(direccion)
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                        }else{
                            mMap.addMarker(new MarkerOptions()
                                    .position(new LatLng(Double.parseDouble(latitud), Double.parseDouble(longitud))).title(direccion)
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
                        }
                    }
                }

                //eventos de desactivar alarma, listener con información del nodo y cuando se toque cambie de color indicando que
                // fue desactivado
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


      /**  private void drawMarker(String lati, String longi){
            mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(Double.parseDouble(lati), Double.parseDouble(longi))).title("Titulo")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
        }**/


    }

}
