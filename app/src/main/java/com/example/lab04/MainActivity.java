package com.example.lab04;

import static android.graphics.BlendMode.COLOR;
import static android.graphics.Color.RED;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("UseSwitchCompatOrMaterialCode")
public class MainActivity extends AppCompatActivity {

    ArrayList<String> items;
    ArrayAdapter<String> itemsAdapter;
    ListView listView;
    Button button;
    Button buttonDebug;
    Switch myUrgentSwitch;

    MyAdapter myAdapter;
    MainActivity mainActivity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listView = findViewById(R.id.listView);
        button = findViewById(R.id.button);
        buttonDebug = findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText input = findViewById(R.id.editText);
                //String itemText = input.getText().toString();

                MyDatabaseHelper myDB = new MyDatabaseHelper(MainActivity.this);

                myDB.addToDoItem(input.getText().toString().trim());
                AddItem(view);

            }

        });


        buttonDebug.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                MyDatabaseHelper db = new MyDatabaseHelper(MainActivity.this);

                db.printCursor();
                db.close();


            }

        });



        items = new ArrayList<>();
        itemsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items);
        listView.setAdapter(itemsAdapter);


        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //Step 8 in Assignment
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {


            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int listitem, long l) {


                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("Do you want to remove " + items.get(listitem) + " from the list?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                String toDoDeletedItems = items.get(listitem);
                                //this line is crashing my app, spent 2 days
                                //resolved by changing int listitem above to final int listitem
                                MyDatabaseHelper myDB = new MyDatabaseHelper(MainActivity.this);
                                myDB.deleteQuery(toDoDeletedItems);
                                items.remove(listitem);

                                //Adapter is up to date, refreshed
                                itemsAdapter.notifyDataSetChanged();

                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        }).create().show();
                return false;


            }
        });
    }


    private void AddItem(View view) {


        EditText input = findViewById(R.id.editText);
        String itemText = input.getText().toString();
        Switch urgentSwitch = (Switch) findViewById(R.id.switch1);


        itemsAdapter.add(itemText);
        input.setText("");




    }


}