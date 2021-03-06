package com.example.steve.fitness;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private Button mSignin, mRegister;
    private EditText mEmail, mPassword;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    //String user;
    String pass;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        // first check if any user is lready logged in
        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                //get the state of the user. check if any user is lready logged in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if(user!=null){
                    Intent intent = new Intent(LoginActivity.this, HomePage.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

        mSignin =  findViewById(R.id.signin);
        mRegister =  findViewById(R.id.register);

        mEmail = findViewById(R.id.email);
        mPassword = findViewById(R.id.password);

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, Register.class);
                startActivity(intent);
                finish();
                return;



            }
        });

        //user = (String) mUsername.getText();
        mSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email = mEmail.getText().toString().trim();
                final String password = mPassword.getText().toString();
                if(email.isEmpty() || password.isEmpty()){
                    Toast.makeText(LoginActivity.this, "Sign in Error", Toast.LENGTH_SHORT).show();
                }else{
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(LoginActivity.this, "Sign in Error", Toast.LENGTH_SHORT).show();
                            }else{
                                UserState.email=email;
                                Intent intent = new Intent(LoginActivity.this, HomePage.class);
                                startActivity(intent);
                                finish();
                                return;
                            }
                        }
                    });
                }

            }
        });

    }

    @Override
    protected void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);

    }
    @Override
    protected void onStop(){
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }
}
