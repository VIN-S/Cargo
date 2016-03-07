package com.example.vin_s.cargo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class resultList extends AppCompatActivity {

    private TextView org_des;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_list);
        String testing = getIntent().getStringExtra("test");
        org_des = (TextView) findViewById(R.id.org_des);
        org_des.setText(testing);
    }

    /** Called when the user clicks the block */
    public void showPostPage(View view) {
        Intent intent = new Intent(this, postPage.class);
        startActivity(intent);
    }
}
