package com.rexdarel.redline;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.rexdarel.redline.provider.RegisterProviderActivity;
import com.rexdarel.redline.recycler.Service;
import com.rexdarel.redline.user.UserActivity;

public class ChooserActivity extends AppCompatActivity {

    private Button btnSignIn, btnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chooser);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Choose");
        setSupportActionBar(toolbar);

        btnSignIn = (Button) findViewById(R.id.buttonSignInChooser);
        btnRegister = (Button) findViewById(R.id.buttonRegisterChooser);

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignInActivity();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterActivity();
            }
        });
    }

    public void openRegisterActivity(){

        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.content_register);
        //dialog.setCancelable(true);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = -2;
        layoutParams.height = -2;

        ((AppCompatButton) dialog.findViewById(R.id.buttonRegisterProvider)).setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(ChooserActivity.this, RegisterProviderActivity.class);
                startActivity(intent);
            }
        });

        ((AppCompatButton) dialog.findViewById(R.id.buttonRegisterUser)).setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(ChooserActivity.this, UserActivity.class);
                startActivity(intent);
            }
        });

        dialog.show();
        dialog.getWindow().setAttributes(layoutParams);
    }

    public void openSignInActivity(){
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }

}
