package johannt.carpool_2.Profile_Features;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import johannt.carpool_2.Login_Phase.ProfileSettingActivity;
import johannt.carpool_2.Login_Phase.SignInActivity;
import johannt.carpool_2.R;
import johannt.carpool_2.Rides_And_Validator.MyDrives;
import johannt.carpool_2.Users.User;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    //firebase object
    private FirebaseAuth firebaseAuth;
    private DatabaseReference firebaseDatabaseRides;
    private DatabaseReference firebaseDatabaseUsers;
    private FirebaseDatabase databaseCarPool;
    private FirebaseUser firebaseUser;

    //view objects
    private TextView textViewUserEmail;
    private Button buttonLogout;
    public Button FindRideBtn ,PostRideBtn;
    private ImageButton settingProfileBtn;
    private Button MyRideHistoyButton;
    private ImageView imageProfile;

    private User secondUser;
    private FirebaseUser user;
    public static boolean profilPicIsSet ;
    public static String  firstName, lastName, city, university, phoneNumber, email, picture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        //if the user is not logged in
        //that means current user will return null
        //getting current user
          user = firebaseAuth.getCurrentUser();

        //initializing views
        textViewUserEmail = findViewById(R.id.textViewUserEmail);
        imageProfile = findViewById(R.id.imageProfile);
        buttonLogout = findViewById(R.id.buttonLogout);
        FindRideBtn = findViewById(R.id.FindRideBtn);
        PostRideBtn = findViewById(R.id.PostRideBtn);
        settingProfileBtn = findViewById(R.id.settingProfileButton);
        MyRideHistoyButton = findViewById(R.id.MyRideHistoyButton);


        //adding listener to button
        buttonLogout.setOnClickListener(this);
        FindRideBtn.setOnClickListener(this);
        PostRideBtn.setOnClickListener(this);
        settingProfileBtn.setOnClickListener(this);
        MyRideHistoyButton.setOnClickListener(this);

    }


    @Override
    protected void onStart() {
        super.onStart();
        if (user == null) {
            //closing this activity
            finish();
            //starting login activity
            startActivity(new Intent(this, SignInActivity.class));

        } else {
            // /getting firebase auth object
            firebaseAuth = FirebaseAuth.getInstance();
            firebaseUser = firebaseAuth.getCurrentUser();
            databaseCarPool = FirebaseDatabase.getInstance();
            firebaseDatabaseUsers = databaseCarPool.getReference("Users");

            secondUser = new User();
            firebaseDatabaseUsers.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                        secondUser = userSnapshot.getValue(User.class);
                        if (firebaseUser.getUid().equals(secondUser.getUID())) {
                            //displaying logged in user name
                            firstName = secondUser.getFirstName();
                            lastName = secondUser.getLastName();
                            city = secondUser.getCity();
                            university = secondUser.getUniversity();
                            phoneNumber = secondUser.getPhoneNumber();
                            email = secondUser.getEmail();
                            picture = secondUser.getImgProfile();

                            textViewUserEmail.setText("Welcome " + firstName);
                            // load the picture profile
                            if(picture != null && picture.length() > 10)
                                Glide.with(getApplicationContext()).load(picture).into(imageProfile);
                            else imageProfile.setImageResource(R.drawable.user_icon);
                            break;
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Failed to read value
                    Log.w("Failed to read value.", databaseError.toException());

                }
            });


        }
    }

    @Override
    public void onClick(View view) {
        //if logout is pressed
        if (view == settingProfileBtn ){
            startActivity(new Intent(this, ProfileSettingActivity.class));
        }

        if(view == buttonLogout){
            //logging out the user
            firebaseAuth.signOut();

            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this, SignInActivity.class));
        }

        if(view == FindRideBtn){
            startActivity(new Intent(this, FindRideActivity.class));
        }

        if(view == PostRideBtn){
            // check if the second user
            if (secondUser.getPhoneNumber().equals("")){
                Toast.makeText(ProfileActivity.this, "Please set your phone number in profile setting to post a drive !", Toast.LENGTH_LONG).show();
            }
            else
            startActivity(new Intent(this, PublishActivity.class));
        }
        if(view == MyRideHistoyButton){
            startActivity(new Intent(this, MyDrives.class));
        }
}
}
