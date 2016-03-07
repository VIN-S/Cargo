package com.example.vin_s.cargo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by VIN-S on 16/3/7.
 */
public class ResultList extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_list);
    }

    /** Called when the user clicks the block */
    public void showPostPage(View view) {
        Intent intent = new Intent(this, PostPage.class);
        startActivity(intent);
    }
}
