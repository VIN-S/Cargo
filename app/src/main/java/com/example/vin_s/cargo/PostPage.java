package com.example.vin_s.cargo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.FrameLayout.LayoutParams;
import com.example.vin_s.cargo.model.Comment;
import com.example.vin_s.cargo.model.Person;
import com.example.vin_s.cargo.model.Post;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * Created by VIN-S on 16/3/7.
 */
public class PostPage extends AppCompatActivity{

    private Post postCreated;
    private Post postSelected;
    private TextView title;
    private TextView slogan;
    private TextView creatorName;
    private TextView details;
    private TextView car;
    private TextView origin;
    private TextView destination;
    private TextView departureDate;
    private TextView duration;
    private TextView numOfSeats;
    private TextView seatsLeft;
    private TextView requirements;
    private Boolean createPostOrNot = false;
    private Boolean selectPostOrNot = false;
    private Person creator;

    private String userID;
    private SharedPreferences prefs;
    private LinearLayout commentLayout;
    private List<Comment> comments = new ArrayList<Comment>();
    private String postID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_page);

        selectPostOrNot = (Boolean) getIntent().getSerializableExtra("selectPostOrNot");
        createPostOrNot = (Boolean) getIntent().getSerializableExtra("createPost");

        prefs = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userID = prefs.getString("loginIDKey", null);

        if(selectPostOrNot!=null&&selectPostOrNot){
            postSelected = (Post) getIntent().getSerializableExtra("selectedPost");
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            postID = postSelected.getId();

            creator = setCreaterInfo(postSelected.getOwnerID());

//          find related display in postpage layout
            title = (TextView) findViewById(R.id.post_page_title);
            slogan = (TextView) findViewById(R.id.post_page_slogan);
            creatorName = (TextView) findViewById(R.id.post_page_creatorName);
            details = (TextView) findViewById(R.id.post_page_detail);
            car = (TextView) findViewById(R.id.post_page_car);
            origin = (TextView) findViewById(R.id.post_page_origin);
            destination = (TextView) findViewById(R.id.post_page_destination);
            departureDate = (TextView) findViewById(R.id.post_page_departure_date);
            duration = (TextView) findViewById(R.id.post_page_duration);
            numOfSeats = (TextView) findViewById(R.id.post_page_number_seats);
            seatsLeft = (TextView) findViewById(R.id.post_page_seats_left);
            requirements = (TextView) findViewById(R.id.post_page_requirements);

//          set content for layout
            title.setText(postSelected.getTitle().toString());
            slogan.setText(postSelected.getSlogan().toString());
            creatorName.setText(creator.getName());
            details.setText(postSelected.getDetails().toString());
            car.setText("Car: " + postSelected.getCarType().toString());
            origin.setText("Origin: " + postSelected.getOrigin().toString());
            destination.setText("Destination: " + postSelected.getDest().toString());
            try {
                departureDate.setText("Departure Date: " + df.format(postSelected.getDate()).toString());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            duration.setText("Duration: " + postSelected.getDuration().toString());
            numOfSeats.setText("Number of Seats: " + postSelected.getNumberOfSeats());
            seatsLeft.setText("Seats Left: " + postSelected.getSeatsLeft());
            requirements.setText("Special Requirements: " + postSelected.getRequirements());
        }

        else if(createPostOrNot!=null&&createPostOrNot) {
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

//          find related display in postpage layout
            title = (TextView) findViewById(R.id.post_page_title);
            slogan = (TextView) findViewById(R.id.post_page_slogan);
            creatorName = (TextView) findViewById(R.id.post_page_creatorName);
            details = (TextView) findViewById(R.id.post_page_detail);
            car = (TextView) findViewById(R.id.post_page_car);
            origin = (TextView) findViewById(R.id.post_page_origin);
            destination = (TextView) findViewById(R.id.post_page_destination);
            departureDate = (TextView) findViewById(R.id.post_page_departure_date);
            duration = (TextView) findViewById(R.id.post_page_duration);
            numOfSeats = (TextView) findViewById(R.id.post_page_number_seats);
            seatsLeft = (TextView) findViewById(R.id.post_page_seats_left);
            requirements = (TextView) findViewById(R.id.post_page_requirements);

//          get post content and creater info
            postCreated = (Post) getIntent().getSerializableExtra("postCreated");
            creator = setCreaterInfo(postCreated.getOwnerID());
            postID = postCreated.getId();

//          set content for layout
            title.setText(postCreated.getTitle().toString());
            slogan.setText(postCreated.getSlogan().toString());
            creatorName.setText(creator.getName());
            details.setText(postCreated.getDetails().toString());
            car.setText("Car: " + postCreated.getCarType().toString());
            origin.setText("Origin: " + postCreated.getOrigin().toString());
            destination.setText("Destination: " + postCreated.getDest().toString());
            try {
                departureDate.setText("Departure Date: " + df.format(postCreated.getDate()).toString());
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            duration.setText("Duration: " + postCreated.getDuration().toString());
            numOfSeats.setText("Number of Seats: " + postCreated.getNumberOfSeats());
            seatsLeft.setText("Seats Left: " + postCreated.getSeatsLeft());
            requirements.setText("Special Requirements: " + postCreated.getRequirements());
        }


        //comments Layout
        commentLayout = (LinearLayout) findViewById(R.id.commentLayout);

        LinearLayout commentTitleLayout = new LinearLayout(this);
        commentTitleLayout.setOrientation(LinearLayout.VERTICAL);
        commentTitleLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT));

        TextView commentsNumber = new TextView(this);
        LinearLayout.LayoutParams lpT = new LinearLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT);
        lpT.setMargins(10,10,0,10);
        commentsNumber.setLayoutParams(lpT);
        commentsNumber.setTextSize(14);
        commentsNumber.setTextColor(0XFF000000);


        View view = new View(this);
        LinearLayout.LayoutParams lpV = new LinearLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,1);
        view.setLayoutParams(lpV);
        view.setBackgroundColor(0XFF000000);

    }

    public Person setCreaterInfo(String personID){
        DatabaseHelper dbHandler = new DatabaseHelper(this);

        creator = dbHandler.getUserByID(personID);

        return creator;
    }

    public void submitComment(View view){
        DatabaseHelper dbHandler = new DatabaseHelper(this);
        EditText commentTextView = (EditText) findViewById(R.id.newComment_edit);
        String content = commentTextView.getText().toString();

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setOwnerID(userID);
        comment.setDateOfComment(new Date());

        if(postCreated != null){
            comment.setPostID(postCreated.getId());
        }else{
            comment.setPostID(postSelected.getId());
        }

        dbHandler.createComment(comment);
    }
}
