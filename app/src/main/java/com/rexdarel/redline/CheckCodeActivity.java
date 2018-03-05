package com.rexdarel.redline;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.rexdarel.redline.provider.RegisterProviderActivity;

public class CheckCodeActivity extends AppCompatActivity {

    private Button btnCheckCode;
    private TextInputEditText inputCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_code);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Verification");
        setSupportActionBar(toolbar);

        btnCheckCode = (Button) findViewById(R.id.buttonCheckCode);
        inputCode = (TextInputEditText) findViewById(R.id.inputCode);

        btnCheckCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterActivity();
            }
        });
    }

    public void openRegisterActivity(){

        if(inputCode.getText().toString().isEmpty()){
            Toast.makeText(this, "Empty Fields",
                    Toast.LENGTH_SHORT).show();
        }else{
            Intent intent = new Intent(this, RegisterProviderActivity.class);
            startActivity(intent);
        }
    }

}
