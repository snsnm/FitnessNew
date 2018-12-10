package com.example.steve.fitness;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

class Password_creation extends AppCompatActivity {
    private Button mNext, mBack;
    private EditText mPassword, mConfirmPassword;

    private FirebaseAuth mAuth;

    //private Firebase test;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.password_creation);

        mPassword = (EditText) findViewById(R.id.pass);
        mConfirmPassword = (EditText) findViewById(R.id.confirmPass);

        mNext = (Button) findViewById(R.id.next);
        //mBack = (Button) findViewById(R.id.back);


        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pass = mPassword.getText().toString();
                String confirmPass = mConfirmPassword.getText().toString();

                if(pass == "" || confirmPass == "" || !(pass.equals(confirmPass)) ){
                    Toast.makeText(Password_creation.this, "Password do not match", Toast.LENGTH_SHORT).show();
                }else{
                    UserState.password = pass;

                    mAuth = FirebaseAuth.getInstance();
                    //now we create a user with authentication
                    mAuth.createUserWithEmailAndPassword(UserState.email, UserState.password).addOnCompleteListener(Password_creation.this, new OnCompleteListener<AuthResult>() {
                        //Toast.makeText(Password_creation.this, "User creation Successful", Toast.LENGTH_SHORT).show();
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(Password_creation.this, "User creation Unsuccessful", Toast.LENGTH_SHORT).show();
                            }else{
                                //create the user  in the database here.

                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference myRef = database.getReference("Users").getRef().child(UserState.name);
                                myRef.setValue(true);

                                myRef = database.getReference("Users").getRef().child(UserState.name).child("Email");
                                myRef.setValue(UserState.email);

                                myRef = database.getReference("Users").getRef().child(UserState.name).child("DOB");
                                myRef.setValue(UserState.dob);

                                myRef = database.getReference("Users").getRef().child(UserState.name).child("Phone");
                                myRef.setValue(UserState.phone);

                                //we get the UID of the user that was created.
                                String user_id = mAuth.getCurrentUser().getUid();

                                //we add the user email in the "all users UID database"
                                String emai1 = UserState.email;
                                String emai = emai1.replace('.', '_');
                                myRef = database.getReference("Users").getRef().child("AllUsersEmail").child(emai);
                                myRef.setValue(UserState.email);


                                Toast.makeText(Password_creation.this, "User creation Successful", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Password_creation.this, HomePage.class);
                                startActivity(intent);
                            }
                        }
                    });

                    //Toast.makeText(PasswordCreation.this, "PLeaseee go", Toast.LENGTH_SHORT).show();


                }

            }
        });




    }
}
