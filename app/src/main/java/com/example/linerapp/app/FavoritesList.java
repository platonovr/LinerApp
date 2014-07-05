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

import com.example.linerapp.app.database.SqlCommand;
import com.example.linerapp.app.model.Row;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Stas on 04.07.2014.
 */
public class FavoritesList extends ListFragment {

    private ArrayList<Row> rowArrayList;
    private ArrayAdapter<String> adapter;
    private String name;
    private List<String> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rowArrayList = SqlCommand.get(getActivity().getApplicationContext()).getRows();
        list = new ArrayList<>();
        int i=0;
        for(Row row : rowArrayList){
            list.add(row.getName());
            i++;
        }

        adapter = new ArrayAdapter<String>(inflater.getContext(), android.R.layout.simple_list_item_1, list);

        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String clickedDetail = (String)l.getItemAtPosition(position);
        for(Row row : rowArrayList){
            if (row.getName().equals(clickedDetail)) {
                Intent intent = new Intent(getActivity().getApplicationContext(), CompanyInfoActivity.class);
                intent.putExtra(CompanyInfoActivity.EXTRA_CompanyInfoActivity,row.getId());
                startActivityForResult(intent, 1);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (name!=null) {
            list.remove(name);
            adapter.notifyDataSetChanged();
            name = null;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) {return;}

        name = data.getStringExtra("name");
        Log.d("My",name);
    }
}
