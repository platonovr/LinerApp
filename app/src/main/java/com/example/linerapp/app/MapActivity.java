package com.example.linerapp.app;

import android.app.Activity;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.linerapp.app.model.Company;
import com.example.linerapp.app.util.GeoHelper;
import com.example.linerapp.app.util.JSONLoader;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.ui.IconGenerator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MapActivity extends Activity implements LocationListener {

    //здесь хранится наша карта
    private GoogleMap googleMap;
    private double latitude;
    private double longitude;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        try {
            // грузим карту
            initilizeMap();

        } catch (Exception e) {
            e.printStackTrace();
        }

        googleMap.setMyLocationEnabled(true);

        // Getting LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Creating a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Getting the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Getting Current Location
        Location location = locationManager.getLastKnownLocation(provider);

        if(location!=null){
            onLocationChanged(location);
        }
        locationManager.requestLocationUpdates(provider, 20000, 0, this);


    }

    /**
     * метод для загрузки карты
     * */
    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();

            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();

        // Getting longitude of the current location
        longitude = location.getLongitude();

        // Creating a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);

        // Showing the current location in Google Map
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom in the Google Map
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        IconGenerator gen = new IconGenerator(this);
        gen.setStyle(IconGenerator.STYLE_PURPLE);
        Bitmap ourB = gen.makeIcon("LinerApp");

        googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(ourB)).
        position(new LatLng(latitude+0.003, longitude+0.003)));

        gen = new IconGenerator(this);
        gen.setStyle(IconGenerator.STYLE_PURPLE);
        ourB = gen.makeIcon("LinerApp");

        googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(ourB)).
                position(GeoHelper.geoLatLng(getApplicationContext(), "Казань Курская 28")));


        CompanyJSONLoader jsonLoader = new CompanyJSONLoader();
        jsonLoader.execute();
    }

    // ============================ Stas ===============================
    public void initCompanies(List<Company> companies) {
        //TODO
        // do something then companies loaded
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    class CompanyJSONLoader extends AsyncTask<Void, Void, List<Company>> {

        @Override
        protected List<Company> doInBackground(Void... voids) {
            String address = JSONLoader.BASE_URL.concat("companies");

            JSONArray jsonArray = null;
            try {
                jsonArray = JSONLoader.getJSONFromURL(address);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

            List<Company> companies = new ArrayList<>();
            try {
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    companies.add(new Company(jsonObject.getInt("id"), jsonObject.getString("name"),
                            jsonObject.getString("address")));
                }
            } catch (JSONException e) {
                Log.e("Error", "Error parsing JSON array");
                e.printStackTrace();
            }
            return companies;
        }

        @Override
        protected void onPostExecute(List<Company> companies) {
            initCompanies(companies);
        }
    }
}