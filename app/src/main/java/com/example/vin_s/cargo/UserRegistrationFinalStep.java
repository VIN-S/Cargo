package com.example.vin_s.cargo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.vin_s.cargo.model.Person;

public class UserRegistrationFinalStep extends AppCompatActivity {

    //UI References
    private EditText nickNameView;
    private EditText selfIntroView;

    private Person user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration_final_step);

        nickNameView = (EditText) findViewById(R.id.nick_name);
        selfIntroView = (EditText) findViewById(R.id.self_introduction);

        user = (Person) getIntent().getSerializableExtra("NEW_USER");
    }

    public void submitRegistration(View view){
        user.setName(nickNameView.getText().toString());
        user.setIntro(selfIntroView.getText().toString());
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.createPerson(user);
    }
}
