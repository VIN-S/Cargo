package com.example.vin_s.cargo;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.vin_s.cargo.model.Person;

public class MyProfile extends AppCompatActivity {

    private Person user;
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
        String userIntro = prefs.getString("nameIntro", null);

        userNicknameView.setText(userName);
        userIntroView.setText(userIntro);
    }

}
