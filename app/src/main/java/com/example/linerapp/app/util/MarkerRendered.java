package com.example.linerapp.app.util;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.linerapp.app.model.ClusterMarker;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

/**
 * Created by Stas on 30.06.2014.
 */
public class MarkerRendered extends DefaultClusterRenderer<ClusterMarker> {

    private IconGenerator gen;
    private Bitmap ourB ;



    public MarkerRendered(Context context, GoogleMap map,
                           ClusterManager<ClusterMarker> clusterManager) {
        super(context, map, clusterManager);
        gen = new IconGenerator(context);
        gen.setStyle(IconGenerator.STYLE_BLUE);
    }

    @Override
    protected void onBeforeClusterItemRendered(ClusterMarker item, MarkerOptions markerOptions) {
       // super.onBeforeClusterItemRendered(item, markerOptions);
        ourB = gen.makeIcon(item.getCompany().getName());
        markerOptions.icon(BitmapDescriptorFactory.fromBitmap(ourB));
    }
}
