package com.example.lab04;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter extends BaseAdapter implements ListAdapter {


    Context context;
    String items[];
    LayoutInflater inflater;



    //constructor
    public MyAdapter (Context ctx, String [] toDoList) {
        this.context=ctx;
        this.items = toDoList;
        inflater = LayoutInflater.from(ctx);


    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

     @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = inflater.inflate(R.layout.activity_custom_list_view, null);
        TextView txtView = (TextView) convertView.findViewById(R.id.txtView);
        txtView.setText(items[position]);
        return convertView;
    }


    @Override
    public long getItemId(int i) {
//This is the database id of the item at position. For now, we aren’t using SQL, so just return the number: position
        return 0;
    }

}
