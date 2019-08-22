package com.example.kalala.exerciseapp;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class CheckGoals extends AppCompatActivity {


    //declarewidgets
    EditText DateEditText;
    EditText nEditText;
    Database mDatabase;
    String athlete;
    TextView mGoalTextView;
    int id = 0;
    int mt1 = -1;
    int mt2 = -2;
    int ml1 = -1;
    int ml2 = -2;
    private SharedPreferences sharedPreferences;
    Button btnSave;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the view for the activity using XML
        setContentView(R.layout.checkgoals);

        //initialize widgets
        nEditText = (EditText) findViewById(R.id.NameEditText);
        DateEditText = (EditText) findViewById(R.id.DateEditText);
        mGoalTextView = (TextView) findViewById(R.id.mGoalTextView);
        mDatabase = new Database(this);
        sharedPreferences = this.getSharedPreferences("athletes", Context.MODE_PRIVATE);
        btnSave = (Button) findViewById(R.id.SaveButton);

        //load preferences
        loadPreferences();

        //loads name based on ID stored in preferences file
        calculateName();


       //loads length and type of exercises for goals and main activities
        setmt1();
        setmt2();
        setml1();
        setml2();



        //checks if user has met their type of exercise and length of exercise goals
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mt1 == mt2 && ml1 == ml2)
                { mGoalTextView.setText("yes"); }
                else
                { mGoalTextView.setText("no");}

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
                Intent intent1 = new Intent(this, SelectName.class);
                this.startActivity(intent1);
                return true;
            case R.id.cGoals:
                Intent intent2 = new Intent(this, SelectGoals.class);
                this.startActivity(intent2);
                return true;
            case R.id.eGoals:
                Intent intent3 = new Intent(this, EnterGoals.class);
                this.startActivity(intent3);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void loadPreferences() {

        if(sharedPreferences!= null) {
            athlete = sharedPreferences.getString("athlete", "");
            id = sharedPreferences.getInt("athleteID", 0);
            DateEditText.setText(athlete);


        } else {

            Toast.makeText(this,"Athlete Not Selected",Toast.LENGTH_LONG).show();
        }
    }


    private void calculateName() {


        Cursor data = mDatabase.getName(id); //gets the name associated with that id
        String name = null;
        while (data.moveToNext()) {
                name = data.getString(0);
              nEditText.setText(name);
              }
    }

    private void setmt1() {


        Cursor data = mDatabase.getType(id); //gets the exercise type associated with that id
        while (data.moveToNext()) {
            mt1 = data.getInt(0);

        }
    }

    private void setmt2() {


        Cursor data = mDatabase.getType2(id); //gets the exercise goal type associated with that id
        while (data.moveToNext()) {
            mt2 = data.getInt(0);

        }
    }

    private void setml1() {


        Cursor data = mDatabase.getLength(id); //gets the exercise length associated with that id
        while (data.moveToNext()) {
            ml1 = data.getInt(0);

        }
    }

    private void setml2() {


        Cursor data = mDatabase.getLength2(id); //gets the exercise length goal associated with that id
        while (data.moveToNext()) {
            ml2 = data.getInt(0);

        }
    }



    //code for toast

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}
