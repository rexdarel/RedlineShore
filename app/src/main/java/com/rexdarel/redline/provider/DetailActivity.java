package com.rexdarel.redline.provider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.rexdarel.redline.R;

public class DetailActivity extends AppCompatActivity {

    private String name, price, schedule, description, location, requirements;
    private TextView tv_name, tv_price, tv_schedule, tv_description, tv_location, tv_requirements;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = this.getIntent();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(name);
        toolbar.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitleEnabled(false);

        tv_name = (TextView) findViewById(R.id.textViewName) ;
        tv_price = (TextView) findViewById(R.id.textViewPrice);
        tv_schedule = (TextView) findViewById(R.id.textViewSchedule);
        tv_requirements = (TextView) findViewById(R.id.textViewRequirements);
        tv_location = (TextView) findViewById(R.id.textViewLocation);
        tv_description = (TextView) findViewById(R.id.textViewDescription);


        name = intent.getExtras().getString("NAME");
        price = String.valueOf(intent.getExtras().getFloat("PRICE"));
        schedule = intent.getExtras().getString("SCHEDULE");
        requirements = intent.getExtras().getString("REQUIREMENTS");
        location = intent.getExtras().getString("LOCATION");
        description = intent.getExtras().getString("DESCRIPTION");

        tv_name.setText(name);
        tv_price.setText("Php " + price);
        tv_schedule.setText(schedule);
        tv_requirements.setText(requirements);
        tv_location.setText(location);
        tv_description.setText(description);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }
    public void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

}
