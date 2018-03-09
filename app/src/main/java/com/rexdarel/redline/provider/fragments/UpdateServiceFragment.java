package com.rexdarel.redline.provider.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hootsuite.nachos.NachoTextView;
import com.hootsuite.nachos.chip.Chip;
import com.hootsuite.nachos.terminator.ChipTerminatorHandler;
import com.hootsuite.nachos.validator.ChipifyingNachoValidator;
import com.rexdarel.redline.R;
import com.rexdarel.redline.provider.NewServiceActivity;
import com.rexdarel.redline.recycler.Service;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.content.ContentValues.TAG;

/**
 * Created by Admin on 3/6/2018.
 */

public class UpdateServiceFragment extends DialogFragment {
    TextView textViewName, textViewSchedule, textViewPrice, textViewLocation, textViewDescription;
    String name, key, description, schedule, location;
    float price;
    DatabaseReference mDatabase;
    DatabaseReference ref;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    ArrayList<String> requirements = new ArrayList<>();
    ProgressBar progressBar;

    NachoTextView mNachoTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.dialog_update_service, container, false);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("Edit Service");

        mNachoTextView = (NachoTextView) rootView.findViewById(R.id.nacho_text_view);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBarHome);
        progressBar.setVisibility(View.INVISIBLE);


        setupChipTextView(mNachoTextView);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        name = this.getArguments().getString("NAME");
        schedule = this.getArguments().getString("SCHEDULE");
        price = Float.parseFloat(this.getArguments().getString("PRICE"));
        location = this.getArguments().getString("LOCATION");
        description = this.getArguments().getString("DESCRIPTION");
        key = this.getArguments().getString("KEY");
        requirements = this.getArguments().getStringArrayList("REQUIREMENTS");

        textViewName = (TextView) rootView.findViewById(R.id.service_name);
        textViewSchedule = (TextView) rootView.findViewById(R.id.service_schedule);
        textViewPrice = (TextView) rootView.findViewById(R.id.service_price);
        textViewLocation = (TextView) rootView.findViewById(R.id.service_location);
        textViewDescription = (TextView) rootView.findViewById(R.id.service_description);

        textViewName.setText(name);
        textViewSchedule.setText(schedule);
        textViewPrice.setText(String.valueOf(price));
        textViewLocation.setText(location);
        textViewDescription.setText(description);
        mNachoTextView.setText(requirements);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        }
        setHasOptionsMenu(true);
        return rootView;
    }

    private void setupChipTextView(NachoTextView nachoTextView) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, requirements);
        nachoTextView.setAdapter(adapter);
        nachoTextView.setIllegalCharacters('\"');
        nachoTextView.addChipTerminator('\n', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_ALL);
        nachoTextView.addChipTerminator(' ', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_TO_TERMINATOR);
        nachoTextView.addChipTerminator(';', ChipTerminatorHandler.BEHAVIOR_CHIPIFY_CURRENT_TOKEN);
        nachoTextView.setNachoValidator(new ChipifyingNachoValidator());
        nachoTextView.enableEditChipOnTouch(true, true);
        nachoTextView.setOnChipClickListener(new NachoTextView.OnChipClickListener() {
            @Override
            public void onChipClick(Chip chip, MotionEvent motionEvent) {
                Log.d(TAG, "onChipClick: " + chip.getText());
            }
        });
        /*nachoTextView.setOnChipRemoveListener(new NachoTextView.OnChipRemoveListener() {
            @Override
            public void onChipRemove(Chip chip) {
                Log.d(TAG, "onChipRemoved: " + chip.getText());
            }
        });*/
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.menu_update_service, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        final List<String> requirement = mNachoTextView.getChipValues();

        if (id == R.id.action_save) {
            progressBar.setVisibility(View.VISIBLE);
            String newName = textViewName.getText().toString();
            String newSchedule = textViewSchedule.getText().toString();
            String newLocation = textViewLocation.getText().toString();
            float newPrice = Float.parseFloat(textViewPrice.getText().toString());
            String newDescription = textViewDescription.getText().toString();

            ref = mDatabase.child("services/" + key);
            ref.setValue(new Service(user.getUid(), newName, newSchedule, newLocation, newDescription, newPrice, key)).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    for (int i = 0; i < requirement.size(); i++){
                        DatabaseReference ref = mDatabase.child("services/" + key + "/requirements");
                        DatabaseReference newRef = ref.push();
                        final String key = newRef.getKey();

                        newRef.setValue(requirement.get(i)).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(getContext(), "Success updating service", Toast.LENGTH_SHORT).show();
                                dismiss();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getContext(), "Trouble adding requirements", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Error updating service", Toast.LENGTH_SHORT).show();
                }
            });
            return true;
        } else if (id == android.R.id.home) {
            // handle close button click here
            dismiss();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
