package com.rexdarel.redline.provider;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hootsuite.nachos.ChipConfiguration;
import com.hootsuite.nachos.NachoTextView;
import com.hootsuite.nachos.chip.Chip;
import com.hootsuite.nachos.chip.ChipSpan;
import com.hootsuite.nachos.chip.ChipSpanChipCreator;
import com.hootsuite.nachos.terminator.ChipTerminatorHandler;
import com.hootsuite.nachos.tokenizer.SpanChipTokenizer;
import com.hootsuite.nachos.validator.ChipifyingNachoValidator;
import com.rexdarel.redline.R;
import com.rexdarel.redline.recycler.Service;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewServiceActivity extends AppCompatActivity {

    private static String TAG = "Nachos";
    private static String[] SUGGESTIONS = new String[]{"Birth Certificate", "Medical Certificate", "Identification Card"};
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @BindView(R.id.nacho_text_view)
    NachoTextView mNachoTextView;
    @BindView(R.id.service_name)
    TextInputEditText inputName;
    @BindView(R.id.service_description)
    TextInputEditText inputDescription;
    @BindView(R.id.service_location)
    TextInputEditText inputLocation;
    @BindView(R.id.service_price)
    TextInputEditText inputPrice;
    @BindView(R.id.service_schedule)
    TextInputEditText inputSchedule;

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_service);
        ButterKnife.bind(this);

        setupChipTextView(mNachoTextView);

        List<String> testList = new ArrayList<>();
        testList.add("Birth Certificate");
        testList.add("Identification Card");
        mNachoTextView.setText(testList);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

    }

    private void setupChipTextView(NachoTextView nachoTextView) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line, SUGGESTIONS);
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

    @SuppressWarnings("unused")
    @OnClick(R.id.bt_submit)
    public void listChipValues(View view) {
        final List<String> requirements = mNachoTextView.getChipValues();

        String name = inputName.getText().toString();
        String description = inputDescription.getText().toString();
        String location = inputLocation.getText().toString();
        String schedule = inputSchedule.getText().toString();
        float price = Float.parseFloat(inputPrice.getText().toString());

        DatabaseReference ref = mDatabase.child("services");
        DatabaseReference newRef = ref.push();
        final String key = newRef.getKey();

        newRef.setValue(new Service(user.getUid(), name, schedule, location, description, price, key))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        DatabaseReference revRef = mDatabase.child("providers/" + user.getUid() + "/services");
                        DatabaseReference pRev = revRef.push();
                        pRev.setValue(key).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                for (int i = 0; i < requirements.size(); i++){
                                    DatabaseReference ref = mDatabase.child("services/" + key + "/requirements");
                                    DatabaseReference newRef = ref.push();
                                    final String key = newRef.getKey();

                                    newRef.setValue(requirements.get(i)).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(NewServiceActivity.this, "Success adding service", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(NewServiceActivity.this, "Trouble adding requirements", Toast.LENGTH_SHORT).show();
                                        }
                                    });

                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(NewServiceActivity.this, "Trouble adding service", Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(NewServiceActivity.this, "Error adding service", Toast.LENGTH_SHORT).show();
                    }
                });
    }

/*    @SuppressWarnings("unused")
    @OnClick(R.id.list_chip_values)
    public void listChipValues(View view) {
        List<String> chipValues = mNachoTextView.getChipValues();
        alertStringList("Chip Values", chipValues);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.list_chip_and_token_values)
    public void listChipAndTokenValues(View view) {
        List<String> chipAndTokenValues = mNachoTextView.getChipAndTokenValues();
        alertStringList("Chip and Token Values", chipAndTokenValues);
    }

    @SuppressWarnings("unused")
    @OnClick(R.id.to_string)
    public void toastToString(View view) {
        List<String> strings = new ArrayList<>();
        strings.add(mNachoTextView.toString());
        alertStringList("toString()", strings);
    }*/

    private void alertStringList(String title, List<String> list) {
        String alertBody;
        if (!list.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            for (String chipValue : list) {
                builder.append(chipValue);
                builder.append("\n");
            }
            builder.deleteCharAt(builder.length() - 1);
            alertBody = builder.toString();
        } else {
            alertBody = "No strings";
        }

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(alertBody)
                .setCancelable(true)
                .setNegativeButton("Close", null)
                .create();

        dialog.show();
    }
}
