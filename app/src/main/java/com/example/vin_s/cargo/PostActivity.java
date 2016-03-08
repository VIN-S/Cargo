package com.example.vin_s.cargo;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.vin_s.cargo.model.Post;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class PostActivity extends AppCompatActivity implements View.OnClickListener {

    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    private EditText fromDateEtxt;
    private EditText title;
    private EditText slogan;
    private EditText car;
    private Spinner origin;
    private Spinner destination;
    private EditText departure_date;
    private EditText numberOfSeats;
    private EditText seatsLeft;
    private EditText details;
    private EditText duration;
    private EditText requirements;
    private Post postCreated;

    private String userID;
    private String userName;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        findViewsById();
        setDateTimeField();

        prefs = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        userName = prefs.getString("nameKey", null);
        userID = prefs.getString("loginIDKey", null);

        if(userName == null){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private void findViewsById() {
        fromDateEtxt = (EditText) findViewById(R.id.post_departure_date_edit);
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();
        origin = (Spinner) findViewById(R.id.originPicker);
        destination = (Spinner) findViewById(R.id.destinationPicker);
        departure_date = (EditText) findViewById(R.id.post_departure_date_edit);
        title = (EditText) findViewById((R.id.post_title_edit));
        slogan = (EditText) findViewById(R.id.post_slogan_edit);
        car = (EditText) findViewById(R.id.post_car_edit);
        numberOfSeats = (EditText) findViewById(R.id.post_number_of_seats_edit);
        seatsLeft = (EditText) findViewById((R.id.post_seats_left_edit));
        details = (EditText) findViewById(R.id.post_route_details_edit);
        duration = (EditText) findViewById(R.id.post_duration_edit);
        requirements = (EditText) findViewById(R.id.post_requirements_edit);
    }

    private void setDateTimeField() {
        fromDateEtxt.setOnClickListener(this);

        Calendar newCalendar = Calendar.getInstance();
        fromDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                fromDateEtxt.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

    }

    @Override
    public void onClick(View view) {
        if(view == fromDateEtxt) {
            fromDatePickerDialog.show();
        }
    }

    public void submitPost(View view){
        DatabaseHelper dbHandler = new DatabaseHelper(this);

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");

        Date departureDate = new Date();

        try {
            departureDate = format.parse(this.departure_date.getText().toString());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



        String ownerID = userID;

        int numOfSeats = Integer.parseInt(numberOfSeats.getText().toString());

        int seatLeft = Integer.parseInt(seatsLeft.getText().toString());

        Post post =
                new Post(ownerID,title.getText().toString(),slogan.getText().toString(),car.getText().toString(),seatLeft,numOfSeats, details.getText().toString(),duration.getText().toString(),requirements.getText().toString(), origin.getSelectedItem().toString(), destination.getSelectedItem().toString(),departureDate);

        postCreated =
                new Post(ownerID,title.getText().toString(),slogan.getText().toString(),car.getText().toString(),seatLeft,numOfSeats, details.getText().toString(),duration.getText().toString(),requirements.getText().toString(), origin.getSelectedItem().toString(), destination.getSelectedItem().toString(),departureDate);

        dbHandler.createPost(post);

        gotoPostPage(view);

    }

    /** Called after the post is written into the database */
    private void gotoPostPage(View view) {
        Intent intent = new Intent(this, PostPage.class);
        intent.putExtra("createPost", true);
        intent.putExtra("postCreated", postCreated);
        startActivity(intent);
    }

    public void redirectToSearch(View view){
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }

    public void redirectToProfile(View view){
        Intent intent = new Intent(this, MyProfile.class);
        startActivity(intent);
    }

    public void redirectToSettings(View view){
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    public  void redirectToHome(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
