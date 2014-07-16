package com.example.linerapp.app.map;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.util.Log;

import com.example.linerapp.app.model.ClusterMarker;
import com.example.linerapp.app.model.Company;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterManager;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Stas on 23.06.2014.
 */
public class GeoHelper {

    //private static final double VISIBILITY_RADIUS = 100000;
    //public static HashMap<Marker, Company> markerCompanyHashMap;


    public static LatLng geoLatLng(Context context, String address) {
        Geocoder geoCoder = new Geocoder(context, Locale.getDefault());
        LatLng user = new LatLng(0, 0);
        try {
            List<Address> addresses = geoCoder.getFromLocationName(address, 5);
            if (addresses.size() > 0) {

                Double lat = (double) (addresses.get(0).getLatitude());
                Double lon = (double) (addresses.get(0).getLongitude());

                Log.d("lat-long", "" + lat + "......." + lon);
                user = new LatLng(lat, lon);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return user;

    }
/*
    public static boolean addMarkers(Context context, List<Company> companies, GoogleMap googleMap, double lat, double lon) {
        LatLng person = new LatLng(lat, lon);
        if (companies != null) {
            IconGenerator gen = new IconGenerator(context);
            gen.setStyle(IconGenerator.STYLE_BLUE);
            markerCompanyHashMap = new HashMap<>();
            for (Company company : companies) {
                Bitmap ourB = gen.makeIcon(company.getName());

                if (company.getAddress() != null) {
                    LatLng place = geoLatLng(context, company.getAddress());
                 //   if (getDistance(person, place) <= VISIBILITY_RADIUS) {

                        Marker marker = googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(ourB)).
                                position(place));
                        markerCompanyHashMap.put(marker, company);
                 // }
                }
            }
            return true;
        } else {
            return false;
        }

    }
    */
    public static boolean addClusterMarkers(Context context, List<Company> companies, ClusterManager<ClusterMarker> clusterManager) {
        if (companies != null) {
            for (Company company : companies) {
                if (company.getAddress() != null) {
                    LatLng place = geoLatLng(context, company.getAddress());

                    ClusterMarker clusterMarker = new ClusterMarker(place,company);

                    clusterManager.addItem(clusterMarker);
                }
            }
            return true;
        } else {
            return false;
        }

    }


    public static double getDistance(LatLng person, LatLng place) {
        double distance = 0;
        Location locationA = new Location("A");
        locationA.setLatitude(person.latitude);
        locationA.setLongitude(person.longitude);
        Location locationB = new Location("B");
        locationB.setLatitude(place.latitude);
        locationB.setLongitude(place.longitude);
        distance = locationA.distanceTo(locationB);
        return distance;

    }
}
