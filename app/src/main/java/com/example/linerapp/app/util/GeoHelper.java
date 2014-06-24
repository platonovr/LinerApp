package com.example.linerapp.app.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by Stas on 23.06.2014.
 */
public class GeoHelper {

    public static LatLng geoLatLng(Context context,String address){
        Geocoder geoCoder = new Geocoder(context,Locale.getDefault());
        LatLng user = null;
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
}
