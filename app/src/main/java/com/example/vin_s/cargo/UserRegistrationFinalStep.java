package com.example.vin_s.cargo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    private SharedPreferences sharedpreferences;

    //Log in session
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "nameKey";
    public static final String Password = "passwordKey";
    public static final String Email = "emailKey";
    public static final String Intro = "introKey";
    public static final String loginID = "loginIDKey";

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

        //After log in successfully, create a log in session to store login user's information
        String nameSession  = user.getName().toString();
        String idSession  = user.getId().toString();
        String emailSession  = user.getEmail().toString();
        String passwordSession = user.getPassword().toString();
        String introSession = user.getIntro().toString();

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.putString(Name, nameSession);
        editor.putString(loginID, idSession);
        editor.putString(Email, emailSession);
        editor.putString(Intro, introSession);
        editor.putString(Password, passwordSession);
        editor.commit();

        Intent intent = new Intent(this, MyProfile.class);
        intent.putExtra("NEW_USER", user);
        startActivity(intent);
    }
}
