package com.rexdarel.redline.provider.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rexdarel.redline.R;
import com.rexdarel.redline.recycler.ItemService;
import com.rexdarel.redline.recycler.Service;
import com.rexdarel.redline.recycler.ServiceAdapter;

import java.util.ArrayList;
import java.util.List;

public class ServiceFragment extends Fragment implements View.OnClickListener {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private List<ItemService> itemService = new ArrayList<>();
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ServiceAdapter mAdapter;
    private View view;
    private TextView textView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        view = inflater.inflate(R.layout.fragment_service, container, false);
        getActivity().setTitle("Services");
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        textView = (TextView) view.findViewById(R.id.textViewStatus);
        mAdapter = new ServiceAdapter(setupService(), getActivity());
        progressBar = (ProgressBar) view.findViewById(R.id.progressBarHome);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mLayoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        FloatingActionButton floatingActionButton = (FloatingActionButton) view.findViewById(R.id.fab);
        floatingActionButton.setOnClickListener(this);

        return view;
    }

    private List<ItemService> setupService(){
        itemService = new ArrayList<>();
        mDatabase.child("providers/" + user.getUid() + "/services").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    textView.setVisibility(View.INVISIBLE);
                    itemService.clear();
                    for(DataSnapshot postSnapShot:dataSnapshot.getChildren())
                    {
                        Object key = postSnapShot.getValue();
                        mDatabase.child("services/" + key).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()){
                                        ItemService service = dataSnapshot.getValue(ItemService.class);
                                        itemService.add(service);
                                        mAdapter.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                }else{
                    textView.setVisibility(View.VISIBLE);
                }
                progressBar.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                //hideProgressDialog();
            }
        });

        return itemService;
    }

    @Override
    public void onClick(View v) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.dialog_add_service);
        final TextInputEditText inputName = (TextInputEditText) dialog.findViewById(R.id.service_name);
        final TextInputEditText inputRequirements = (TextInputEditText) dialog.findViewById(R.id.service_requirements);
        final TextInputEditText inputSchedule = (TextInputEditText) dialog.findViewById(R.id.service_schedule);
        final TextInputEditText inputPrice = (TextInputEditText) dialog.findViewById(R.id.service_price);
        final TextInputEditText inputLocation = (TextInputEditText) dialog.findViewById(R.id.service_location);
        final TextInputEditText inputDescription = (TextInputEditText) dialog.findViewById(R.id.service_description);
        //dialog.setCancelable(true);
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        layoutParams.width = -2;
        layoutParams.height = -2;
        /*ImageView imageView = (ImageView) dialog.findViewById(R.id.user_image);
        if(photoUrl != null) {
            Glide.with(DetailActivity.this)
                    .load(photoUrl)
                    .transform(new CircleGlide(DetailActivity.this))
                    .into(imageView);
        }else{
            Glide.with(DetailActivity.this)
                    .load(Uri.parse("https://firebasestorage.googleapis.com/v0/b/diagonline-a0560.appspot.com/o/users%2Fuser.png?alt=media&token=8c6af845-a0e4-4113-839d-64a242427403"))
                    .transform(new CircleGlide(DetailActivity.this))
                    .into(imageView);
        }
        final EditText editText = (EditText) dialog.findViewById(R.id.et_post);
        final AppCompatRatingBar appCompatRatingBar = (AppCompatRatingBar) dialog.findViewById(R.id.rating_bar_dialog);
        ((AppCompatButton) dialog.findViewById(R.id.bt_cancel)).setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        */
        ((AppCompatButton) dialog.findViewById(R.id.bt_submit)).setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String trimName = inputName.getText().toString();
                String trimRequirements = inputRequirements.getText().toString();
                String trimSchedule = inputSchedule.getText().toString();
                float trimPrice = Float.parseFloat(inputPrice.getText().toString());
                String trimLocation = inputLocation.getText().toString();
                String trimDescription = inputDescription.getText().toString();

                if(trimName.isEmpty()){
                    Toast.makeText(getContext(), "Name is empty", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseReference ref = mDatabase.child("services");
                    DatabaseReference newRef = ref.push();
                    final String key = newRef.getKey();
                    newRef.setValue(new Service(user.getUid(), trimName, trimRequirements, trimSchedule, trimLocation, trimDescription, trimPrice))
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    DatabaseReference revRef = mDatabase.child("providers/" + user.getUid() + "/services");
                                    DatabaseReference pRev = revRef.push();
                                    pRev.setValue(key);
                                    dialog.dismiss();
                                    Toast.makeText(getContext(), "Success adding service", Toast.LENGTH_SHORT).show();
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getContext(), "Error adding service", Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });
        dialog.show();
        dialog.getWindow().setAttributes(layoutParams);
    }
}