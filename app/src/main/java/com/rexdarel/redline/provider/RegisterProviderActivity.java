package com.rexdarel.redline.provider;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rexdarel.redline.R;
import com.rexdarel.redline.recycler.Provider;

public class RegisterProviderActivity extends AppCompatActivity {

    private Button btnRegister;
    private TextInputEditText inputName, inputMobileNumber, inputTelephoneNumber, inputAddress, inputEmail, inputPassword;
    private String name, mobileNumber, telephoneNumber, address, email, password;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_provider);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        btnRegister = (Button) findViewById(R.id.buttonRegisterProviderFinal);
        inputAddress = (TextInputEditText) findViewById(R.id.inputAddress);
        inputEmail = (TextInputEditText) findViewById(R.id.inputProviderEmail);
        inputMobileNumber = (TextInputEditText)findViewById(R.id.inputMobileNumber);
        inputTelephoneNumber = (TextInputEditText) findViewById(R.id.inputTelephoneNumber);
        inputName = (TextInputEditText) findViewById(R.id.inputName);
        inputPassword = (TextInputEditText) findViewById(R.id.inputProviderPassword);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerProvider();
            }
        });
    }

    public void registerProvider(){
        TextInputLayout firstInputLayout = (TextInputLayout) findViewById(R.id.inputLayoutName);
        firstInputLayout.setError(null);
        name = inputName.getText().toString();
        mobileNumber = inputMobileNumber.getText().toString();
        telephoneNumber = inputTelephoneNumber.getText().toString();
        address = inputAddress.getText().toString();
        email = inputEmail.getText().toString();
        password = inputPassword.getText().toString();

        if(name.isEmpty()) {
            firstInputLayout.setError("This field is required");
        }else{
            mAuth.createUserWithEmailAndPassword(inputEmail.getText().toString(), inputPassword.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                //Log.d(TAG, "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();
                                //updateUI(user);
                                String userUid = user.getUid();
                                Provider provider = new Provider(name, Long.valueOf(inputMobileNumber.getText().toString()), Long.valueOf(inputTelephoneNumber.getText().toString()),
                                        address, email);
                                mDatabase.child("providers").child(userUid).setValue(provider);
                                openDashboardActivity();
                            } else {
                                // If sign in fails, display a message to the user.
                                //Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                Toast.makeText(RegisterProviderActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                //updateUI(null);
                            }
                            // ...
                        }
                    });
        }
    }

    public void openDashboardActivity(){
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }

}
