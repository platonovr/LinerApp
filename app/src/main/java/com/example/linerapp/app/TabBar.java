package com.example.linerapp.app;

import android.app.ActivityGroup;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

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


        intent = new Intent(this, MapActivity.class);
        tabSpec = tabHost.newTabSpec("First").setIndicator("", getResources().getDrawable(android.R.drawable.ic_menu_mapmode)).setContent(intent);
        tabHost.addTab(tabSpec);

        intent = new Intent(this, CompanyActivity.class);
        tabSpec = tabHost.newTabSpec("Second").setIndicator("", getResources().getDrawable(android.R.drawable.ic_input_get)).setContent(intent);
        tabHost.addTab(tabSpec);

        intent = new Intent(this, SettingsActivity.class);
        tabSpec = tabHost.newTabSpec("Third").setIndicator("", getResources().getDrawable(android.R.drawable.ic_menu_manage)).setContent(intent);
        tabHost.addTab(tabSpec);


        tabHost.getTabWidget().setCurrentTab(0);
    }

    @Override
    public void onTabChanged(String s) {

    }
}