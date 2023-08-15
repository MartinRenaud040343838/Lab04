package com.example.lab04;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DATABASE_NAME = "ToDoList.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "ToDoListTable";
    private static final String COL_ID = "_id";
    private static final String TODO_DESC = "todo_item_desc";



    public MyDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String query =
                "CREATE TABLE " + TABLE_NAME +
                        " (" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + TODO_DESC + " TEXT);";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    void addToDoItem(String todo_item) {

        SQLiteDatabase db = this.getWritableDatabase();

        //store all data from application to pass to table
        ContentValues cv = new ContentValues();

        cv.put(TODO_DESC, todo_item);
        long result = db.insert(TABLE_NAME, null, cv);

        //debug check for error with db
        if(result == -1) {
            Toast.makeText(context, "Error: failed to insert to database.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully written to database.", Toast.LENGTH_SHORT).show();

        }

    }




   //public int deleteQuery (int listItem) {
   public void deleteQuery (String toDoItem) {

       SQLiteDatabase db = this.getWritableDatabase();

       Toast.makeText(context, "Item that was deleted : " + toDoItem , Toast.LENGTH_SHORT).show();

       //tested in logcat and it works
       db.execSQL("DELETE FROM " + TABLE_NAME+ " WHERE " + TODO_DESC + "='"+toDoItem+"'");
       db.close();


   }





    public void openDB() {

        SQLiteDatabase myDB = this.getReadableDatabase();
    }




    public <Todo> void printCursor() {



        String[] columns = new String[]{COL_ID, TODO_DESC};
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor c = db.query(TABLE_NAME, columns, null, null, null, null, null);
        String result = "";
        int indexCount = 0;


        int iRow = c.getColumnIndex(COL_ID);
        int iToDo = c.getColumnIndex(TODO_DESC);
        String displayRows = "";

        String queryColNames = "SELECT * FROM " + TABLE_NAME;
        String[] columnNames = new String[0];
        int rowCount = 0;
        String rowValues = null;

        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {

            indexCount = c.getColumnCount();
            columnNames = c.getColumnNames();
            rowCount = c.getCount();
            rowValues = rowValues + c.getString(iRow) + " " + c.getString(iToDo) + "\n";

        }
        c.close();


        Log.d("cursor_debug", "test");
        Log.d("cursor_debug", "Database Version: " + DATABASE_VERSION);
        Log.d("cursor_debug", "The number of columns in the cursor: " + indexCount);
        Log.d("cursor_debug", "The names of the columns in the cursor: " + Arrays.toString(columnNames));
        Log.d("cursor_debug", "Row count: " + String.valueOf(rowCount));
        Log.d("cursor_debug", "Row values: " + rowValues);


    }



}
