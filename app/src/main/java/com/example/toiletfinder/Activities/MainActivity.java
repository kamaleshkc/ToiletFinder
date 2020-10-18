package com.example.toiletfinder.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.toiletfinder.R;
import com.example.toiletfinder.storage.SharedPref;

public class MainActivity extends AppCompatActivity {
    private Button signup;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        signup=findViewById(R.id.Signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Signup.class));
            }
        });


        login=findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Login.class));
            }
        });




    }
    @Override
    protected void onStart() {
        super.onStart();
        if (SharedPref.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, MapProfile.class);

            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

}
