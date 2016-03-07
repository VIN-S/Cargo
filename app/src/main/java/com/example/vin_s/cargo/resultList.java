package com.example.vin_s.cargo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.vin_s.cargo.model.Post;
import java.util.*;

public class resultList extends AppCompatActivity {

    private TextView org_des;
    private List<Post> posts = new ArrayList<Post>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_list);
        String testing = getIntent().getStringExtra("test");
        org_des = (TextView) findViewById(R.id.org_des);
        //org_des.setText(testing);
        posts = (List<Post>) getIntent().getSerializableExtra("resultList");
        if(!posts.isEmpty()){
            org_des.setText((String)posts.get(0).getOrigin());
        }
    }

    /** Called when the user clicks the block */
    public void showPostPage(View view) {
        Intent intent = new Intent(this, PostPage.class);
        startActivity(intent);
    }
}
