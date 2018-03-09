package com.rexdarel.redline.user.fragments;

/**
 * Created by Admin on 3/9/2018.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.rexdarel.redline.R;
import com.rexdarel.redline.recycler.HospitalAdapter;
import com.rexdarel.redline.recycler.ItemClickListener;
import com.rexdarel.redline.recycler.ItemHospital;
import com.rexdarel.redline.utils.CircleGlide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rexdarel.redline.user.UserActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Dytstudio.
 */

public class FragmentHome extends Fragment{
    private List<ItemHospital> hospitals = new ArrayList<>();
    private RecyclerView recyclerView;
    private HospitalAdapter mAdapter;
    private AppCompatActivity appCompatActivity;
    private ProgressBar progressBar;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    public FragmentHome(){
        setHasOptionsMenu(true);
    }
    public void onCreate(Bundle a){
        super.onCreate(a);
        setHasOptionsMenu(true);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_home, null, false);

        //((UserActivity)getActivity()).setupToolbar(R.id.toolbar, "DESSERT", R.color.colorPink, R.color.colorWhiteTrans, R.drawable.ic_burger);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBarHome);


        mAdapter = new HospitalAdapter(setupRecipe(), getActivity());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getActivity(), 2);
        mLayoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        appCompatActivity = (AppCompatActivity) getActivity();

        return view;
    }


    private List<ItemHospital> setupRecipe(){
        hospitals = new ArrayList<>();

        databaseReference.child("providers").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    hospitals.clear();
                    for(DataSnapshot postSnapShot:dataSnapshot.getChildren())
                    {
                        ItemHospital hospital = postSnapShot.getValue(ItemHospital.class);
                        hospitals.add(hospital);
                        mAdapter.notifyDataSetChanged();
                    }
                }
                //progressBar.setVisibility(View.INVISIBLE);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                //hideProgressDialog();
            }
        });

        String recipe[] = {"BLOOD ORANGE CAKE", "SEMIFREDDO TIRAMISU", "MARBLE CAKE", "RICE PUDDING", "RAINBOW CAKE", "ICE CREAM", "STROWBERRY CAKE", "CUPCAKE FRUIT"};
        String img[] = {"https://images.pexels.com/photos/53468/dessert-orange-food-chocolate-53468.jpeg?h=350&auto=compress&cs=tinysrgb",
                "https://images.pexels.com/photos/159887/pexels-photo-159887.jpeg?h=350&auto=compress",
                "https://images.pexels.com/photos/136745/pexels-photo-136745.jpeg?w=1260&h=750&auto=compress&cs=tinysrgb",
                "https://images.pexels.com/photos/39355/dessert-raspberry-leaf-almond-39355.jpeg?h=350&auto=compress&cs=tinysrgb",
                "https://images.pexels.com/photos/239578/pexels-photo-239578.jpeg?w=1260&h=750&auto=compress&cs=tinysrgb",
                "https://images.pexels.com/photos/8382/pexels-photo.jpg?w=1260&h=750&auto=compress&cs=tinysrgb",
                "https://images.pexels.com/photos/51186/pexels-photo-51186.jpeg?w=1260&h=750&auto=compress&cs=tinysrgb",
                "https://images.pexels.com/photos/55809/dessert-strawberry-tart-berry-55809.jpeg?w=1260&h=750&auto=compress&cs=tinysrgb"};
        String time[] = {"1h 5'", "30m", "1h 10'", "50m", "20m", "1h 20'", "20m", "1h 20'"};
        float rating[] = {3, 4, 4, 3, 5, 4, 4, 3};

        return hospitals;
    }


    /*public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.menu_home, menu);
    }*/

    /*private void openDetailActivity(String name, String location, String contact, String description, String photo, String id)
    {
        Intent i=new Intent(getContext(), DetailActivity.class);

        i.putExtra("NAME",name);
        i.putExtra("LOCATION",location);
        i.putExtra("CONTACT",contact);
        i.putExtra("DESC",description);
        i.putExtra("PHOTO", photo);
        i.putExtra("ID", id);

        getContext().startActivity(i);
    }*/
}
