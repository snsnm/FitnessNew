package com.example.steve.fitness;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {

    private EditText mFirstName, mLastName, mEmail, mPhoneNumber;
    private Button mNext, mBack;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        mFirstName = findViewById(R.id.fName);
        mLastName = findViewById(R.id.lName);
        mEmail = findViewById(R.id.eEmail);
        mPhoneNumber = findViewById(R.id.phonenumber);


        mNext = findViewById(R.id.next);
        mBack = findViewById(R.id.back);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Register.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return;
            }
        });


        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = mEmail.getText().toString().trim();
                String fName = mFirstName.getText().toString().trim();
                String lName = mLastName.getText().toString().trim();
                String phone = mPhoneNumber.getText().toString().trim();
                //String dob = mDob.getText().toString().trim();

                if (email.equals("") || fName.isEmpty() || lName.isEmpty()){
                    Toast.makeText(Register.this, "Please fill in all the required fields", Toast.LENGTH_SHORT).show();

                }else{
                    UserState.email = email;

                    sendEmail();

                    UserState.name = fName + lName;
                    UserState.fullName = fName + " " + lName;
                    UserState.phone = phone;
                    //UserState.dob = dob;

                    Intent intent = new Intent(Register.this, Confirmation.class);
                    startActivity(intent);
                }

            }
        });

    }


    private void sendEmail(){
        mEmail = findViewById(R.id.eEmail);
        String email = mEmail.getText().toString().trim();

        mFirstName = findViewById(R.id.fName);
        String name = mFirstName.getText().toString().trim();

        String subject = "Confirmation Code";
        String message = "Hi " +name + ",\nWelcome to Connectic Fitness, \nHere is your confirmation code: 1234. \n\nThe Support Team, \n" +
             "\n"+
             "Connectic Fitness\n";


        SendMail sm = new SendMail(this, email, subject, message);
        sm.execute();


    }
}
