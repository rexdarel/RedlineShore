package com.rexdarel.redline;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rexdarel.redline.provider.DashboardActivity;

public class WelcomeActivity extends AppCompatActivity {

    private Button btnGetStarted;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Welcome");
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();

        btnGetStarted = (Button) findViewById(R.id.buttonGetStarted);
        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChooserActivity();
            }
        });
    }

    public void openChooserActivity(){
        Intent intent = new Intent(this, ChooserActivity.class);
        startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);
        }
    }
}

