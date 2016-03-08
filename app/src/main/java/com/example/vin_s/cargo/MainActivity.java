package com.example.vin_s.cargo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Boolean loginOrNot = false;
    private TextView loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.onCreate(dbHelper.getWritableDatabase());
//        dbHelper.onUpgrade(dbHelper.getWritableDatabase(), 1, 2);

        loginButton = (TextView) findViewById(R.id.login_button);

        //get log in session if exists
        SharedPreferences prefs = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String loginName = prefs.getString("nameKey", null);

        //if login session exists, let loginOrNot be true
        if (loginName != null && loginName.length() > 0) loginOrNot = true;
        else loginOrNot = false;

        //if loginOrNot is true, then display welcome message in main page.
        //Otherwise, display log in info
        if (loginOrNot) loginButton.setText("Hi, " + loginName + "!");
        else loginButton.setText("Log in");

    }

    /** Called when the user clicks the Search button */
    public void startSearch(View view) {
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }

    /** Called when the user clicks the post button */
    public void startPost(View view) {
        Intent intent = new Intent(this, PostActivity.class);
        startActivity(intent);
    }

    /** Called when the user clicks the log in button */
    public void logIn(View view) {
        //if user is already logged in, then go to my profile page, otherwise, go to log in page
        if(!loginOrNot)
            {
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
            }
        else{
            Intent intent = new Intent(this, MyProfile.class);
            startActivity(intent);
        }
    }
}
