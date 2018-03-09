package com.rexdarel.redline.provider;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rexdarel.redline.R;
import com.rexdarel.redline.provider.fragments.UpdateServiceFragment;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity {

    private TextView tv_name, tv_price, tv_schedule, tv_description, tv_location, tv_requirements;
    private CollapsingToolbarLayout collapsingToolbarLayout;
    ArrayList<String> itemRequirement = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = this.getIntent();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Detail");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final ArrayAdapter<String> adapter;

        tv_name = (TextView) findViewById(R.id.textViewName) ;
        tv_price = (TextView) findViewById(R.id.textViewPrice);
        tv_schedule = (TextView) findViewById(R.id.textViewSchedule);
        tv_requirements = (TextView) findViewById(R.id.textViewRequirements);
        tv_location = (TextView) findViewById(R.id.textViewLocation);
        tv_description = (TextView) findViewById(R.id.textViewDescription);


        final String NAME = intent.getExtras().getString("NAME");
        final String PRICE = String.valueOf(intent.getExtras().getFloat("PRICE"));
        final String SCHEDULE = intent.getExtras().getString("SCHEDULE");
        final String LOCATION = intent.getExtras().getString("LOCATION");
        final String DESC = intent.getExtras().getString("DESCRIPTION");
        final String KEY = intent.getExtras().getString("KEY");

        DatabaseReference mDatabase;
        FirebaseAuth mAuth;
        FirebaseUser user;
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                itemRequirement);
        final ListView listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(adapter);

        mDatabase.child("services/" + KEY + "/requirements").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot postSnapShot:dataSnapshot.getChildren()) {
                        itemRequirement.add(postSnapShot.getValue().toString());
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        tv_name.setText(NAME);
        tv_price.setText("Php " + PRICE);
        tv_schedule.setText(SCHEDULE);
        tv_requirements.setText(KEY);
        tv_location.setText(LOCATION);
        tv_description.setText(DESC);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                UpdateServiceFragment newFragment = new UpdateServiceFragment();
                Bundle bundle = new Bundle();
                bundle.putString("NAME", NAME);
                bundle.putString("SCHEDULE", SCHEDULE);
                bundle.putString("PRICE", PRICE);
                bundle.putString("LOCATION", LOCATION);
                bundle.putString("DESCRIPTION", DESC);
                bundle.putString("KEY", KEY);
                bundle.putStringArrayList("REQUIREMENTS", itemRequirement);
                newFragment.setArguments(bundle);
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.add(android.R.id.content, newFragment).addToBackStack(null).commit();
            }
        });
    }

}
