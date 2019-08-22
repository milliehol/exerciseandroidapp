package com.example.kalala.exerciseapp;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;



import java.util.Calendar;


public class EnterGoals extends AppCompatActivity {

    //declare widgets
    private Spinner typeSpinner;
    private Spinner timeSpinner;
    DatePickerDialog datePickerDialog;
    EditText DateEditText;
    EditText time;
    String name1;
    String name2;
    EditText nEditText;
    EditText dEditText;
    Button btnSave;
    Database mDatabase;
    String newEntry;
    String newEntry3;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the view for the activity using XML
        setContentView(R.layout.entergoals);

        //set references to widgets
        typeSpinner = (Spinner) findViewById(R.id.typeSpinner);
        timeSpinner = (Spinner) findViewById(R.id.timeSpinner);

        //initialize database
        mDatabase = new Database(this);


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

        // initiate the date picker and a button
        DateEditText = (EditText) findViewById(R.id.DateEditText);
        // perform click event on edit text
        DateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // calender class's instance and get current date , month and year from calender
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR); // current year
                int mMonth = c.get(Calendar.MONTH); // current month
                int mDay = c.get(Calendar.DAY_OF_MONTH); // current day
                // date picker dialog
                datePickerDialog = new DatePickerDialog(EnterGoals.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // set day of month , month and year value in the edit text
                                DateEditText.setText((monthOfYear + 1) + "/"
                                        + (dayOfMonth) + "/" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        //initializes widgets
        nEditText = (EditText) findViewById(R.id.NameEditText);
        dEditText = (EditText) findViewById(R.id.DateEditText);
        btnSave = (Button) findViewById(R.id.SaveButton);
        mDatabase = new Database(this);


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //retrieves data the user has entered
                String newEntry = nEditText.getText().toString();
                int newEntry2 = typeSpinner.getSelectedItemPosition();
                String newEntry3 = dEditText.getText().toString();
                int newEntry4 = timeSpinner.getSelectedItemPosition();
                //used to check to see if name and date combo exist
                name1 = compare(newEntry3);
                name2 = newEntry;


                //checks to ensure user has entered name and date
                if (nEditText.length() != 0 && newEntry3.compareTo("Select Date") != 0) {

                    //checks to ensure name and date combo to not exit
                    if (name1.compareTo(name2) == 0) {
                        toastMessage("You must select a new date for that name!");
                    }
                    else   {
                        AddData(newEntry, newEntry2, newEntry3, newEntry4);
                        nEditText.setText("");
                        dEditText.setText("Select Date");
                    }


                } else {
                    toastMessage("You must put something in the text field!");
                }



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


    //adds activity to the database
    public void AddData(String newEntry, int newEntry2, String newEntry3, int newEntry4) {
        boolean insertData = mDatabase.addData(newEntry, newEntry2, newEntry3, newEntry4);

        if (insertData) {
            toastMessage("Data Successfully Inserted!");
        } else {
            toastMessage("Something went wrong");
        }
    }

    //Checks to see if name and date combo exist

    public String compare(String date){

        Cursor data = mDatabase.checkDoubles(date);
        String name = "";
        while (data.moveToNext()) {

            name = data.getString(0);
        }

        return name;

    }



    /**
     * code for toast
     */
    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }




}




