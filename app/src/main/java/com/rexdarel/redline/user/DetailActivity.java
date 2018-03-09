package com.rexdarel.redline.user;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
/*import com.rexdarel.redline.recycler.CommentsAdapter;
import com.rexdarel.redline.recycler.ItemComment;
import com.rexdarel.redline.recycler.ItemPreparation;
import com.rexdarel.redline.recycler.ItemReview;
import com.rexdarel.redline.recycler.PreparationAdapter;
import com.rexdarel.redline.recycler.Review;
import com.rexdarel.redline.recycler.ReviewAdapter;*/
import com.rexdarel.redline.utils.CircleGlide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.rexdarel.redline.R;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DetailActivity extends AppCompatActivity {

    private CollapsingToolbarLayout collapsingToolbarLayout;
    private RecyclerView recyclerViewPreparation;
    //private PreparationAdapter mAdapterPreparation;
    private RecyclerView recyclerViewComments;
    //private CommentsAdapter mAdapterComments;
    private CoordinatorLayout rootView;

    private RecyclerView recyclerViewReview;
   // private ReviewAdapter mAdapterReview;

    private TextView tv_name, tv_desc, tv_location;
    private RatingBar ratingBar;
    private Toolbar toolbar;

    private FirebaseAuth mAuth;
    FirebaseUser user;
    private String username, email, uid;
    private Uri photoUrl;

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_hospital);
        rootView = (CoordinatorLayout) findViewById(R.id.rootview);
        setupToolbar(R.id.toolbar, "DESSERTS", android.R.color.white, android.R.color.transparent, R.drawable.ic_arrow_back_black_24dp);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        if (user != null) {
            // Name, email address, and profile photo Url
            username = user.getDisplayName();
            email = user.getEmail();
            photoUrl = user.getPhotoUrl();

            // Check if user's email is verified
            boolean emailVerified = user.isEmailVerified();

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getToken() instead.
            uid = user.getUid();
        }

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitleEnabled(false);

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("hospitals").orderByChild("name").equalTo(getIntent().getExtras().getString("NAME"));

        /*FirebaseRecyclerOptions<ItemReview> options =
                new FirebaseRecyclerOptions.Builder<ItemReview>()
                        .setQuery(query, ItemReview.class)
                        .build();*/

        tv_name = (TextView) findViewById(R.id.tv_recipe_name);
        tv_desc = (TextView) findViewById(R.id.tv_desc);
        tv_location = (TextView) findViewById(R.id.tv_location);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        Intent i = this.getIntent();
        final String name=i.getExtras().getString("NAME");
        String desc = i.getExtras().getString("DESC");
        String location = i.getExtras().getString("LOCATION");
        String photo = "https://images.pexels.com/photos/53468/dessert-orange-food-chocolate-53468.jpeg?h=350&auto=compress&cs=tinysrgb";
        final String id = i.getExtras().getString("ID");
        ratingBar.setRating(3);
        tv_name.setText(name);
        tv_desc.setText(desc);
        tv_location.setText(location);

        /*/*FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(DetailActivity.this);
                dialog.requestWindowFeature(1);
                dialog.setContentView(R.layout.dialog_add_review);
                TextView tv_username = (TextView) dialog.findViewById(R.id.username);
                tv_username.setText(username);
                dialog.setCancelable(true);
                WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
                layoutParams.copyFrom(dialog.getWindow().getAttributes());
                layoutParams.width = -2;
                layoutParams.height = -2;
                ImageView imageView = (ImageView) dialog.findViewById(R.id.user_image);
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
                });*/
                /*((AppCompatButton) dialog.findViewById(R.id.bt_submit)).setOnClickListener(new View.OnClickListener() {

                    public void onClick(View view) {
                        String trim = editText.getText().toString();
                        if(trim.isEmpty()){
                            Toast.makeText(getApplicationContext(), "Review message is empty", Toast.LENGTH_SHORT).show();
                        } else {
                            DatabaseReference ref = databaseReference.child("reviews");
                            DatabaseReference newRef = ref.push();
                            final String key = newRef.getKey();
                            newRef.setValue(new Review(key, username, trim, (long) appCompatRatingBar.getRating()))
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            DatabaseReference revRef = databaseReference.child("hospitals/" + id + "/reviews");
                                            DatabaseReference pRev = revRef.push();
                                            pRev.setValue(key);
                                            dialog.dismiss();
                                            Toast.makeText(getApplicationContext(), "Success adding review", Toast.LENGTH_SHORT).show();
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getApplicationContext(), "Error adding review", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    }
                });
                dialog.show();
                dialog.getWindow().setAttributes(layoutParams);
            }
        });*/

        /*recyclerViewPreparation = (RecyclerView) findViewById(R.id.recyclerPreparation);

        mAdapterPreparation = new PreparationAdapter(getBaseContext(), generatePreparation(),this);
        LinearLayoutManager mLayoutManagerPreparation = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewPreparation.setLayoutManager(mLayoutManagerPreparation);
        recyclerViewPreparation.setItemAnimator(new DefaultItemAnimator());
        recyclerViewPreparation.setAdapter(mAdapterPreparation);*/

        //recyclerViewComments = (RecyclerView) findViewById(R.id.recyclerComment);

        //recyclerViewReview = (RecyclerView) findViewById(R.id.recyclerComment);

       /* mAdapterComments = new CommentsAdapter(generateComments(), this);
        LinearLayoutManager mLayoutManagerComment = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewComments.setLayoutManager(mLayoutManagerComment);
        recyclerViewComments.setItemAnimator(new DefaultItemAnimator());
        recyclerViewComments.setAdapter(mAdapterComments);*/

        /*mAdapterReview = new ReviewAdapter(generateReview(), this);
        LinearLayoutManager mLayoutManagerReview = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerViewReview.setLayoutManager(mLayoutManagerReview);
        recyclerViewReview.setItemAnimator(new DefaultItemAnimator());
        recyclerViewReview.setAdapter(mAdapterReview);*/


        final ImageView image = (ImageView) findViewById(R.id.image);
        Glide.with(this).load(photo).into(image);

    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_home, menu);
        Drawable drawable = menu.findItem(R.id.action_search).getIcon();

        drawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(drawable, ContextCompat.getColor(this,android.R.color.white));
        menu.findItem(R.id.action_search).setIcon(drawable);
        return true;
    }*/


    /*public List<ItemReview> generateReview(){
        final List<ItemReview> itemList = new ArrayList<>();
        final List<String> hospitalKeys = new ArrayList<>();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference.child("hospitals").orderByChild("name").equalTo(getIntent().getExtras().getString("NAME"));

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    itemList.clear();
                    for(DataSnapshot uniqueKeySnapshot : dataSnapshot.getChildren()){
                        //Loop 1 to go through all the child nodes of users
                        for(DataSnapshot booksSnapshot : uniqueKeySnapshot.child("reviews").getChildren()){
                            String key = booksSnapshot.getValue().toString();
                            DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference();

                            Query query1 = reference1.child("reviews").orderByChild("id").equalTo(key).limitToFirst(5);

                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("reviews");
                            //ref.orderByChild("id").equalTo(key);
                            ref.orderByChild("id").equalTo(key).limitToLast(10).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if(dataSnapshot.exists())
                                    {
                                        for(DataSnapshot postSnapShot:dataSnapshot.getChildren())
                                        {
                                            ItemReview review = postSnapShot.getValue(ItemReview.class);
                                            itemList.add(review);
                                            mAdapterReview.notifyDataSetChanged();
                                        }
                                    }
                                    //p
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                            mAdapterReview.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });



        return itemList;
    }*/


    /*public List<ItemPreparation> generatePreparation(){
        List<ItemPreparation> itemList = new ArrayList<>();
        String step[] = {"In a medium saucepan, whisk together egg yolks and sugar until well blended. Whisk in milk and cook over medium heat, stirring constantly, until mixture boils. Boil gently for 1 minute, remove from heat and allow to cool slightly. Cover tightly and chill in refrigerator 1 hour.",
                "In a medium bowl, beat cream with vanilla until stiff peaks form. Whisk mascarpone into yolk mixture until smooth.",
                "In a small bowl, combine coffee and rum. Split ladyfingers in half lengthwise and drizzle with coffee mixture.",
                "Arrange half of soaked ladyfingers in bottom of a 7x11 inch dish. Spread half of mascarpone mixture over ladyfingers, then half of whipped cream over that. Repeat layers and sprinkle with cocoa. Cover and refrigerate 4 to 6 hours, until set."};

        for (int i = 0; i<step.length; i++){
            ItemPreparation item = new ItemPreparation();
            item.setStep(step[i]);
            item.setNumber(String.valueOf(i+1));
            itemList.add(item);
        }
        return itemList;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }*/

    public void setupToolbar(int toolbarId, String title, @ColorRes int titleColor, @ColorRes int colorBg, @DrawableRes int burger){
        toolbar = (Toolbar) findViewById(toolbarId);
        toolbar.setBackgroundColor(getResources().getColor(colorBg));
        setSupportActionBar(toolbar);
        TextView pageTitle = (TextView) toolbar.findViewById(R.id.tv_title);
        pageTitle.setText("Details");
        pageTitle.setTextColor(getResources().getColor(titleColor));
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(burger);
        changeStatusBarColor();
    }
    public void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

}
