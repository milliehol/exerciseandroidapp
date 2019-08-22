package com.example.kalala.exerciseapp;


import android.content.SharedPreferences;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.Toast;
import android.content.Context;
import android.widget.Button;



import java.util.ArrayList;

public class SelectName extends AppCompatActivity {

    private static final String TAG = "ListDataActivity";

    //initialize widgets
    Database mDatabase;
    private ListView mListView;
    private ListView mListView2;
    private String athlete;
    private SharedPreferences sharedPreferences;
    private TextView vAthlete;
    Button btnNext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectgoals);

       //initialize widgets
        mListView = (ListView) findViewById(R.id.listView);
        mListView2 = (ListView) findViewById(R.id.listView2);
        mDatabase = new Database(this);
        vAthlete = (TextView) findViewById(R.id.viewAthlete);
        btnNext = (Button) findViewById(R.id.Next);
        sharedPreferences = this.getSharedPreferences("athletes", Context.MODE_PRIVATE);

        //load preferences and populate listviews
        loadPreferences();
        populateListView();
        populateListView2();

        //takes the user to the next page to select activity length and type
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SelectName.this, EnterActivity.class));

            }
        });

    }


    //code that displays the menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.MainActivity:
                Intent intent4 = new Intent(this, MainActivity.class);
                this.startActivity(intent4);
                return true;
            case R.id.eActivity:
                Intent intent1 = new Intent (this, SelectName.class);
                this.startActivity(intent1);
                return true;
            case R.id.cGoals:
                Intent intent2 = new Intent (this, SelectGoals.class);
                this.startActivity(intent2);
                return true;
            case R.id.eGoals:
                Intent intent3 = new Intent (this, EnterGoals.class);
                this.startActivity(intent3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void loadPreferences() {

        if(sharedPreferences!= null) {
            athlete = sharedPreferences.getString("athlete2", "");
            vAthlete.setText(athlete);

        } else {

            Toast.makeText(this,"Player 1 Not Selected",Toast.LENGTH_LONG).show();
        }
    }

    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        //get the data and append to a list
        Cursor data = mDatabase.getData();
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            //get the value from the database in column 3
            //then add it to the ArrayList
            listData.add(data.getString(3));
        }
        //create the list adapter and set the adapter
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mListView.setAdapter(adapter);



        //set an onItemClickListener to the ListView
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String date = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemClick: You Clicked on " + date);

                Cursor data = mDatabase.getID(date); //get the id associated with that date
                int itemID = -1;
                while (data.moveToNext()) {
                    itemID = data.getInt(0);

                }
                //sets date & ID to corresponding shared preference file
                if (itemID > -1) {
                    Log.d(TAG, "onItemClick: The ID is: " + itemID);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("athlete2", date);
                    editor.putInt("athlete2ID", itemID);
                    editor.apply();
                    athlete = date;
                    vAthlete.setText(athlete);
                } else {
                    toastMessage("No ID associated with that name");
                }
            }
        });
    }

    private void populateListView2() {
        Log.d(TAG, "populateListView: Displaying data in the ListView.");

        //get the data and append to a list
        Cursor data = mDatabase.getData();
        ArrayList<String> listData = new ArrayList<>();
        while (data.moveToNext()) {
            //get the value from the database in column 1
            //then add it to the ArrayList
            listData.add(data.getString(1));
        }
        //create the list adapter and set the adapter
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
        mListView2.setAdapter(adapter);

    }




    //code for toast

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }



}