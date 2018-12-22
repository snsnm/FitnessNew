package com.example.steve.fitness;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class HomePage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawer;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.setNavigationItemSelectedListener(this);
        TextView txtProfileName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.email);
        Log.d("the email here is ", UserState.email);
        txtProfileName.setText(UserState.email);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new Home()).commit();
            navigationView.setCheckedItem(R.id.home);
        }

    }

    @Override
    public boolean onNavigationItemSelected(@Nullable MenuItem item){
        switch (item.getItemId()){
            case R.id.home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Home()).commit();
                break;
            case R.id.saved:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new Saved()).commit();
                break;

            case R.id.log_out:
                Toast.makeText(this, "logg outttt foool", Toast.LENGTH_SHORT).show();
                mAuth = FirebaseAuth.getInstance();
                mAuth.signOut();
                UserState.email = "";
                Toast.makeText(HomePage.this, "log sucessfull", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(HomePage.this, LoginActivity.class);
                startActivity(intent);
                finish();

                /*mAuth.signOut().addOnCompleteListener(HomePage.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    Log.d("yo", "okk");
                    if(!task.isSuccessful()){

                    }else{
                        Toast.makeText(HomePage.this, "log sucessfull", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(HomePage.this, LoginActivity.class);
                        startActivity(intent);

                    }
                }
            });*/
                break;
        }

        drawer.closeDrawer(GravityCompat.START );

        return true;
    }


    @Override
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
}
