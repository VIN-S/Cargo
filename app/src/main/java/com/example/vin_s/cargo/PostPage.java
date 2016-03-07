package com.example.vin_s.cargo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.vin_s.cargo.model.Post;

/**
 * Created by VIN-S on 16/3/7.
 */
public class PostPage extends AppCompatActivity{

    private Post postCreated;
    private Post postSelected;
    private TextView title;
    private Boolean createPostOrNot = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_page);

        createPostOrNot = (Boolean) getIntent().getSerializableExtra("createPost");

        if(createPostOrNot) {

            title = (TextView) findViewById(R.id.post_page_title);

            postCreated = (Post) getIntent().getSerializableExtra("postCreated");
            title.setText(postCreated.getTitle().toString());
        }
    }
}
