package com.example.linerapp.app.map;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.linerapp.app.CompanyInfoActivity;
import com.example.linerapp.app.R;
import com.example.linerapp.app.customization.NonHierarchicalDistanceBasedAlgorithmMy;
import com.example.linerapp.app.model.ClusterMarker;
import com.example.linerapp.app.model.Company;
import com.example.linerapp.app.util.JSONLoader;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterManager;

import java.util.List;


public class MapActivity extends Activity implements LocationListener,ClusterManager.OnClusterItemClickListener<ClusterMarker>,View.OnClickListener {

    private GoogleMap googleMap;
    private ClusterManager<ClusterMarker> mClusterManager;
    private double latitude;
    private double longitude;
    private Button mapStyleBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        try {
            // грузим карту
            initializeMap();

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

       if (location != null) {
            onLocationChanged(location);
        }

        // Creating a LatLng object for the current location
        LatLng latLng = new LatLng(latitude, longitude);
        // Showing the current location in Google Map
       googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));

        // Zoom in the Google Map
       googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));


        locationManager.requestLocationUpdates(provider, 20000, 0, this);


       // googleMap.setOnMarkerClickListener(this);

        mClusterManager = new ClusterManager<>(this, googleMap);
        mClusterManager.setOnClusterItemClickListener(this);
        mClusterManager.setRenderer(new MarkerRendered(
                getApplicationContext(), googleMap, mClusterManager));
        mClusterManager.setAlgorithm(new NonHierarchicalDistanceBasedAlgorithmMy<ClusterMarker>());
        googleMap.setOnCameraChangeListener(mClusterManager);
        googleMap.setOnMarkerClickListener(mClusterManager);

        CompanyJSONLoader jsonLoader = new CompanyJSONLoader();
        jsonLoader.execute();

        mapStyleBtn = (Button) findViewById(R.id.map_style_button);
        mapStyleBtn.setOnClickListener(this);
       Drawable drawable = mapStyleBtn.getBackground();
        drawable.setAlpha(220);
    }



    /**
     * метод для загрузки карты
     */
    private void initializeMap() {
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
        initializeMap();
    }

    //изменяем широту и долготу в зивисимости от перемещения
    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();

        // Getting longitude of the current location
        longitude = location.getLongitude();

        // Creating a LatLng object for the current location
       // LatLng latLng = new LatLng(latitude, longitude);


    }

    // ============================ Stas ===============================
    public void initCompanies(List<Company> companies) {
        //GeoHelper.addMarkers(getApplicationContext(), companies, googleMap, latitude, longitude);
        GeoHelper.addClusterMarkers(getApplicationContext(),companies,mClusterManager);
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
/*
    @Override
    public boolean onMarkerClick(Marker marker) {
        Company company = GeoHelper.markerCompanyHashMap.get(marker);
        Intent intent = new Intent(this, CompanyInfoActivity.class);
        intent.putExtra(CompanyInfoActivity.EXTRA_CompanyInfoActivity, company.getId());
        startActivity(intent);
        return true;
    }
*/
    @Override
    public boolean onClusterItemClick(ClusterMarker clusterMarker) {
        Company company = clusterMarker.getCompany();
        Intent intent = new Intent(this, CompanyInfoActivity.class);
        intent.putExtra(CompanyInfoActivity.EXTRA_CompanyInfoActivity, company.getId());
        startActivity(intent);
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.map_style_button) {
            //Creating the instance of PopupMenu
            PopupMenu popup = new PopupMenu(MapActivity.this, mapStyleBtn);
            //Inflating the Popup using xml file
            popup.getMenuInflater().inflate(R.menu.map, popup.getMenu());

            //registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()){
                        case R.id.satellite : {
                            googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                            mapStyleBtn.setText(R.string.satellite);
                            break;
                        }
                        case R.id.normal : {
                            googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                            mapStyleBtn.setText(R.string.normal);
                            break;
                        }
                        case R.id.terrain : {
                            googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                            mapStyleBtn.setText(R.string.terrain);
                            break;
                        }
                        case R.id.hybrid : {
                            googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                            mapStyleBtn.setText(R.string.hybrid);
                            break;
                        }
                    }
                    return true;
                }
            });

            popup.show();//showing popup menu

        }
    }


    class CompanyJSONLoader extends AsyncTask<Void, Void, List<Company>> {

        @Override
        protected List<Company> doInBackground(Void... voids) {

            return JSONLoader.loadAllCompanies();
        }

        @Override
        protected void onPostExecute(List<Company> companies) {
            initCompanies(companies);
        }
    }
}