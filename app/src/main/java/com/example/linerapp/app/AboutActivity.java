package com.example.linerapp.app;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class AboutActivity extends Activity {

    private final String LINERAPPSITE = "http://www.linerapp.com";

    Button aboutSiteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        buttonListener();
    }

    public void buttonListener() {
        aboutSiteButton = (Button) findViewById(R.id.site_btn);
        aboutSiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(LINERAPPSITE));
                startActivity(intent);
            }
        });
    }
}
