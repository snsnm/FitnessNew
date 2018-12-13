package com.example.steve.fitness;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Confirmation extends AppCompatActivity {

    private EditText mEmailCode, mPhoneCode;

    private Button mNext, mBack;

    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.confirmation);

        mNext = findViewById(R.id.next);
        mBack = findViewById(R.id.back);

        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Confirmation.this, Register.class);
                startActivity(intent);
                finish();
                return;
            }
        });

        mNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mEmailCode = findViewById(R.id.emailCode);

                String eCode = mEmailCode.getText().toString();

                if(eCode.equals("1234")){
                    Intent intent = new Intent(Confirmation.this, Password_creation.class );
                    startActivity(intent);
                }else{
                    Toast.makeText(Confirmation.this, "Code do not match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
