package com.example.linerapp.app;

import android.app.ActivityGroup;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.example.linerapp.app.map.MapActivity;


public class TabBar extends ActivityGroup implements TabHost.OnTabChangeListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup(getLocalActivityManager());


        TabHost.TabSpec tabSpec;
        Intent intent;

      //  Drawable myIcon = getResources().getDrawable(android.R.drawable.ic_menu_mapmode);
        //ColorFilter filter = new LightingColorFilter( Color.WHITE, Color.WHITE);
       // myIcon.setColorFilter(filter);

        intent = new Intent(this, MapActivity.class);
        tabSpec = tabHost.newTabSpec("First").setIndicator("",getResources().getDrawable(R.drawable.ic_action_location_map)).setContent(intent);
        tabHost.addTab(tabSpec);

        intent = new Intent(this, CompanyActivity.class);


        tabSpec = tabHost.newTabSpec("Second").setIndicator("", getResources().getDrawable(R.drawable.ic_action_collections_view_as_list)).setContent(intent);
        tabHost.addTab(tabSpec);

        intent = new Intent(this, SettingsActivity.class);
        tabSpec = tabHost.newTabSpec("Third").setIndicator("", getResources().getDrawable(R.drawable.ic_action_very_basic_settings_icon)).setContent(intent);
        tabHost.addTab(tabSpec);

        TabWidget widget = tabHost.getTabWidget();
        for(int i = 0; i < widget.getChildCount(); i++) {
            View v = widget.getChildAt(i);

            // Look for the title view to ensure this is an indicator and not a divider.
            TextView tv = (TextView)v.findViewById(android.R.id.title);
            if(tv == null) {
                continue;
            }
            v.setBackgroundResource(R.drawable.tab_indicator_ab_example);
        }

        tabHost.getTabWidget().setCurrentTab(0);

    }

    @Override
    public void onTabChanged(String s) {

    }
}