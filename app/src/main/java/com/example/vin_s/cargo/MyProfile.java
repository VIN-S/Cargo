package com.example.vin_s.cargo;

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

        user = (Person) getIntent().getSerializableExtra("NEW_USER");

        userNicknameView.setText(user.getName());
        userIntroView.setText(user.getIntro());

    }

}
