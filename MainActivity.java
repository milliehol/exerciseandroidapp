package com.example.kalala.exerciseapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    //declares the textview
    private TextView activityLabel;
    private TextView Goals;
    private TextView cGoals;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initializes widgets
        activityLabel = findViewById(R.id.activityLabel);
        Goals = findViewById(R.id.Goals);
        cGoals = findViewById(R.id.cGoals);

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

    public void EnterActivity (View view){
        startActivity(new Intent(MainActivity.this, SelectName.class));
    }

    public void EnterGoals (View view){
        startActivity(new Intent(MainActivity.this, EnterGoals.class));
    }

    public void CheckGoals (View view){
        startActivity(new Intent(MainActivity.this, SelectGoals.class));
    }



    //code for label navigation

    @Override
    public void onClick(View v) {




    }
}

