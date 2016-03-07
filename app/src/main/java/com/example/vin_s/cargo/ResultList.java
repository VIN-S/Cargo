package com.example.vin_s.cargo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.vin_s.cargo.model.Post;
import java.util.*;


public class ResultList extends AppCompatActivity {

    private TextView org_des;
    private TextView content;
    private List<Post> posts = new ArrayList<Post>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_list);
        String testing = getIntent().getStringExtra("test");
        org_des = (TextView) findViewById(R.id.org_des);
        content = (TextView) findViewById(R.id.postContent);

        posts = (List<Post>) getIntent().getSerializableExtra("resultList");
        if(posts.isEmpty()) {
            org_des.setText("Sorry! There s no trip available!");
            content.setText("Please search again!");
            System.out.printf("is empty");
        }
        else {
            org_des.setText((String)posts.get(0).getOrigin());
            content.setText((String)posts.get(0).getDuration());
            System.out.printf("not empty");
        }
    }

    /** Called when the user clicks the block */
    public void showPostPage(View view) {
        Intent intent = new Intent(this, PostPage.class);
        startActivity(intent);
    }
}
