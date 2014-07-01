package com.example.linerapp.app.model;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by Stas on 30.06.2014.
 */
public class ClusterMarker implements ClusterItem {
    private final LatLng mPosition;
    private Company company ;


    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public ClusterMarker(LatLng latLng,Company company) {
        mPosition = latLng;
        this.company = company;
    }

    @Override
    public LatLng getPosition() {
        return mPosition;
    }
}
