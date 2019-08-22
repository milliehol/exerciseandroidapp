package com.example.kalala.exerciseapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;


public class EnterActivity extends AppCompatActivity {

    //declare widgets
    private Spinner typeSpinner;
    private Spinner timeSpinner;
    EditText DateEditText;
    EditText time;
    Button btnSave;
    EditText nEditText;
    Database mDatabase;
    String athlete;
    int id = 0;
    int type = 0;
    int length = 0;
    private SharedPreferences sharedPreferences;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the view for the activity using XML
        setContentView(R.layout.enteractivity);

        //set references to widgets
        typeSpinner = (Spinner) findViewById(R.id.typeSpinner);
        timeSpinner = (Spinner) findViewById(R.id.timeSpinner);


        //set adapter for type of activity
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.type_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);

        //set adapter for time of activity
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(
                this, R.array.time_array, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        timeSpinner.setAdapter(adapter2);

        // initiate the widgets
        DateEditText = (EditText) findViewById(R.id.DateEditText);
        nEditText = (EditText) findViewById(R.id.NameEditText);
        mDatabase = new Database(this);
        sharedPreferences = this.getSharedPreferences("athletes", Context.MODE_PRIVATE);
        btnSave = (Button) findViewById(R.id.SaveButton);

        //load preferences
        loadPreferences();

        //calculate name based on ID
        calculateName();


        //updates exercise length and type per user name
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                type = typeSpinner.getSelectedItemPosition();
                length = timeSpinner.getSelectedItemPosition();
                mDatabase.updateType(id, type);
                mDatabase.updateLength(id, length);
                toastMessage("Data Successfully Entered");

            }
        });
    }


    //code that displays the menu
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected (MenuItem item){
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
            athlete = sharedPreferences.getString("athlete2", "");
            id = sharedPreferences.getInt("athlete2ID", 0);
            //DateEditText.setText(Integer.toString(id));
            DateEditText.setText(athlete);


        } else {

            Toast.makeText(this,"Athlete Not Selected",Toast.LENGTH_LONG).show();
        }
    }

    private void calculateName() {


        Cursor data = mDatabase.getName(id); //get the name associated with that id
        String name = null;
        while (data.moveToNext()) {
            name = data.getString(0);
            nEditText.setText(name);
        }

    }


    //code for toast

    private void toastMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }


}
