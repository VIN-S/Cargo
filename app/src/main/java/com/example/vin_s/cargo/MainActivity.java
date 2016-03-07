package com.example.vin_s.cargo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DatabaseHelper dbHelper = new DatabaseHelper(this);
//        dbHelper.onCreate(dbHelper.getWritableDatabase());
//        dbHelper.onUpgrade(dbHelper.getWritableDatabase(), 1, 2);

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
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
}
