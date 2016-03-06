package com.example.vin_s.cargo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.vin_s.cargo.model.Person;

public class UserRegistration extends AppCompatActivity {

    //UI References
    private EditText registrationEmailView;
    private EditText registrationPassword1View;
    private EditText registrationPassword2View;
    private View signUpProgressView;
    private View signUpFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registration);

        registrationEmailView = (EditText) findViewById(R.id.registration_email);
        registrationPassword1View = (EditText) findViewById(R.id.registration_password);
        registrationPassword2View = (EditText) findViewById(R.id.registration_repeat_password);

        signUpProgressView = (View) findViewById(R.id.signUp_progress);
        signUpFormView = (View) findViewById(R.id.signUp_form);

    }

    public void register(View view){
        registrationEmailView.setError(null);
        registrationPassword1View.setError(null);
        registrationPassword2View.setError(null);

        boolean cancel = false;
        View focusView = null;

        String registrationEmail = registrationEmailView.getText().toString();
        String registrationPassword1 = registrationPassword1View.getText().toString();
        String registrationPassword2 = registrationPassword2View.getText().toString();

        if(registrationEmail.equals("foo@example.com") || registrationEmail.equals("bar@example.com")){
            registrationEmailView.setError("This email already exists in the system");
            focusView = registrationEmailView;
            cancel = true;
        }

        if(!isEmailValid(registrationEmail)) {
            registrationEmailView.setError("Invalid email format");
            focusView = registrationEmailView;
            cancel = true;
        }

        if(!isPasswordValid(registrationPassword1)) {
            registrationPassword1View.setError("Password is too short");
            focusView = registrationPassword1View;
            cancel = true;
        }

        if(!registrationPassword1.equals(registrationPassword2)){
            registrationPassword2View.setError(getString(R.string.repeat_password_wrong));
            focusView = registrationPassword2View;
            cancel = true;
        }

        if(cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            Person newUser = new Person(registrationEmail, null);
            Intent intent = new Intent(this, UserRegistrationFinalStep.class);
            intent.putExtra("NEW_USER", newUser);
            startActivity(intent);
        }

    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }


    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            signUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            signUpFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    signUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            signUpProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            signUpProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    signUpProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            signUpProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            signUpFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
