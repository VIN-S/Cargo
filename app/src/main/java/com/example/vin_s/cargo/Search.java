package com.example.vin_s.cargo;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vin_s.cargo.model.Post;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class Search extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {


    Spinner spinner;
    private DatePickerDialog fromDatePickerDialog;
    private SimpleDateFormat dateFormatter;
    private EditText fromDateEtxt;
    private Button btnSubmit;
    private Spinner spinner1, spinner2;
    private String origin;
    private String destination;
    private EditText depDate;
    private String departureDate;
    private List<Post> posts = new ArrayList<Post>();
    private DatabaseHelper dbHelper = new DatabaseHelper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Spinner spinner1 = (Spinner) findViewById(R.id.originPicker);
        Spinner spinner2 = (Spinner) findViewById(R.id.destinationPicker);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.search_origin, android.R.layout.simple_spinner_item);
        // dropdown list for origin
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.search_destination, android.R.layout.simple_spinner_item);
        // dropdown list for destination
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner1.setAdapter(adapter1);
        spinner2.setAdapter(adapter2);

        dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
        findViewsById();
        setDateTimeField();

        //dbHelper.onCreate(dbHelper.getWritableDatabase());
        //dbHelper.onUpgrade(dbHelper.getWritableDatabase(), 1, 2);
        addListenerOnButton();

        SharedPreferences prefs = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String userName = prefs.getString("nameKey", null);

        if(userName == null){
            LinearLayout tabBarLayout= (LinearLayout)this.findViewById(R.id.search_tab_bar);
            tabBarLayout.setVisibility(LinearLayout.GONE);
            ScrollView searchScroll = (ScrollView)this.findViewById(R.id.search_scrollView);
            searchScroll.setMinimumHeight(500);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l){
        TextView myText = (TextView) view;
        Toast.makeText(this, "You selected " + myText.getText(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void findViewsById() {
        fromDateEtxt = (EditText) findViewById(R.id.departureDate);
        fromDateEtxt.setInputType(InputType.TYPE_NULL);
        fromDateEtxt.requestFocus();
        depDate = (EditText) findViewById(R.id.departureDate);

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

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }


    /** Called when the user clicks the Search button */
    public void showSearchResult(View view) {
        Intent intent = new Intent(this, ResultList.class);
        intent.putExtra("resultList", (Serializable) posts);
        intent.putExtra("item", "Date");
        startActivity(intent);
    }

    public void redirectToProfile(View view){
        Intent intent = new Intent(this, MyProfile.class);
        startActivity(intent);
    }

    public void redirectToCreatePost(View view){
        Intent intent = new Intent(this, PostActivity.class);
        startActivity(intent);
    }

    public void redirectToHome(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void redirectToSearch(View view){
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }

    public void logout(View view){
        showDialog(Search.this, "Logout Warning", "Are you going to log out?");
    }

    public void showDialog(final Activity activity, String title, CharSequence message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        if (title != null) builder.setTitle(title);

        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                SharedPreferences preferences = getSharedPreferences("MyPrefs", 0);
                preferences.edit().clear().commit();

                Intent in=new Intent(activity, MainActivity.class);
                activity.startActivity(in);
            }
        });
        builder.setNegativeButton("Cancel", null);
        builder.show();
    }

    @Override
    public void onClick(View v) {
        if (v == fromDateEtxt) {
            fromDatePickerDialog.show();
        }
    }

    //get the selected dropdown list value
    public void addListenerOnButton() {

        final Spinner spinner1 = (Spinner) findViewById(R.id.originPicker);
        final Spinner spinner2 = (Spinner) findViewById(R.id.destinationPicker);

        btnSubmit = (Button) findViewById(R.id.search_button);

        btnSubmit.setOnClickListener(new OnClickListener() {

            public void onClick(View v) {

                if(v == fromDateEtxt) {
                    fromDatePickerDialog.show();
                }

                origin = String.valueOf(spinner1.getSelectedItem());
                destination = String.valueOf(spinner2.getSelectedItem());
                departureDate = depDate.getText().toString(); //dd-mm-yyyy

                if(TextUtils.isEmpty(departureDate)) {
                    depDate.setError("Cannot be Empty");
                }
                else {
                    try {
                        posts = dbHelper.getAllPostsByOD(origin,destination,departureDate);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    showSearchResult(v);
                }

            }

        });
    }

}
