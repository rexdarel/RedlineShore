package com.rexdarel.redline;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.rexdarel.redline.provider.RegisterProviderActivity;

public class RegisterActivity extends AppCompatActivity {

    private Button btnRegisterUser, btnRegisterProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Register New Account");
        setSupportActionBar(toolbar);

        btnRegisterUser = (Button) findViewById(R.id.buttonRegisterUser);
        btnRegisterProvider = (Button) findViewById(R.id.buttonRegisterProvider);

        btnRegisterUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btnRegisterProvider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCheckCodeActivity();
            }
        });
    }

    public void openCheckCodeActivity(){
        Intent intent = new Intent(this, CheckCodeActivity.class);
        startActivity(intent);
    }

}
