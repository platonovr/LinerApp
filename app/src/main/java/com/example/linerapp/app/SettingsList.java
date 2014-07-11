package com.example.linerapp.app;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.linerapp.app.database.SqlCommand;
import com.example.linerapp.app.model.Row;

import java.util.ArrayList;

/**
 * Created by Stas on 27.06.2014.
 */
public class SettingsList extends ListFragment {

    String[] settings = new String[]{
            "Избранное",
            "Профиль",
            "Выход"
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, settings);

        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String clickedDetail = (String)l.getItemAtPosition(position);
        if(clickedDetail.equals("Избранное")) {
            Intent intent = new Intent(getActivity().getApplicationContext(), FavoritesActivity.class);
            startActivity(intent);
        } else   if(clickedDetail.equals("Выход")) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else   if(clickedDetail.equals("Профиль")) {
            Toast.makeText(getActivity().getApplicationContext(),"Доступно в следующей версии",Toast.LENGTH_SHORT).show();
        }


    }
}
