package com.example.linerapp.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.linerapp.app.util.DataStorage;
import com.example.linerapp.app.util.JSONLoader;


public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Thread() {
            @Override
            public void run() {
                try {
                    DataStorage.setCategories(JSONLoader.loadCategories());
                    Thread.sleep(300);
                    startActivity(new Intent("com.example.linerapp.app.MAINSCREEN"));

                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                }
            }
        }.start();
    }
}
