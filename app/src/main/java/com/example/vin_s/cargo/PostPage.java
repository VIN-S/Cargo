package com.example.vin_s.cargo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.vin_s.cargo.model.Person;
import com.example.vin_s.cargo.model.Post;

/**
 * Created by VIN-S on 16/3/7.
 */
public class PostPage extends AppCompatActivity{

    private Post postCreated;
    private Post postSelected;
    private TextView title;
    private TextView slogan;
    private TextView createrName;
    private Boolean createPostOrNot = false;
    private Person creater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_page);

        createPostOrNot = (Boolean) getIntent().getSerializableExtra("createPost");

        if(createPostOrNot) {
//          find related display in postpage layout
            title = (TextView) findViewById(R.id.post_page_title);
            slogan = (TextView) findViewById(R.id.post_page_slogan);
            createrName = (TextView) findViewById(R.id.post_page_createrName);

//          get post content and creater info
            postCreated = (Post) getIntent().getSerializableExtra("postCreated");
            creater = setCreaterInfo(postCreated.getOwnerID());

//          set content for layout
            title.setText(postCreated.getTitle().toString());
            slogan.setText(postCreated.getSlogan().toString());
            createrName.setText(creater.getName());
        }
    }

    public Person setCreaterInfo(String personID){
        DatabaseHelper dbHandler = new DatabaseHelper(this);

        creater = dbHandler.getUserByID(personID);

        return creater;
    }
}
