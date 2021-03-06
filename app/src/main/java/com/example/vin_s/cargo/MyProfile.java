package com.example.vin_s.cargo;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MyProfile extends AppCompatActivity{

    private TextView userNicknameView;
    private TextView userIntroView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        userNicknameView = (TextView) findViewById(R.id.user_nickname);
        userIntroView = (TextView) findViewById(R.id.user_intro);

        SharedPreferences prefs = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String userName = prefs.getString("nameKey", null);
        String userIntro = prefs.getString("introKey", null);

        userNicknameView.setText(userName);
        userIntroView.setText(userIntro);
    }

    public void redirectToSearch(View view){
        Intent intent = new Intent(this, Search.class);
        startActivity(intent);
    }

    public void redirectToPost(View view){
        Intent intent = new Intent(this, PostActivity.class);
        startActivity(intent);
    }

    public  void redirectToHome(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void logout(View view){
        showDialog(MyProfile.this, "Logout Warning", "Are you going to log out?");
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
    }
