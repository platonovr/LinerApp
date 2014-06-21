package com.example.linerapp.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity implements OnClickListener {

    private  Button mCompany_btn ;
    private Button mAbout_btn ;
    private Button mMap_btn ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCompany_btn =(Button) findViewById(R.id.company_btn);
        mAbout_btn = (Button) findViewById(R.id.about_btn);
        mMap_btn = (Button) findViewById(R.id.map_btn);
        mCompany_btn.setOnClickListener(this);
        mAbout_btn.setOnClickListener(this);
        mMap_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.about_btn : {

                break;
            }
            case R.id.company_btn : {
                Intent i = new Intent(this,CompanyActivity.class);
                startActivity(i);
                break;
            }
            case R.id.map_btn : {
                Intent i = new Intent(this,MapActivity.class);
                startActivity(i);
                break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        Log.e("Exit", "Exit");
        System.exit(0);
    }
}
