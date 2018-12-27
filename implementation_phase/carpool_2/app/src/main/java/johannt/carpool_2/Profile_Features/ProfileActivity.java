package johannt.carpool_2.Profile_Features;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import johannt.carpool_2.Login_Phase.SignInActivity;
import johannt.carpool_2.R;
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
    public Button FindRideBtn;
    private Button PostRideBtn;

    private User secondUser;
    private String  firstName, lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();

        //if the user is not logged in
        //that means current user will return null

        //getting current user
        FirebaseUser user = firebaseAuth.getCurrentUser();

        //initializing views
        textViewUserEmail = findViewById(R.id.textViewUserEmail);
        buttonLogout = findViewById(R.id.buttonLogout);
        FindRideBtn = findViewById(R.id.FindRideBtn);
        PostRideBtn = findViewById(R.id.PostRideBtn);

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

            secondUser= new User();
            firebaseDatabaseUsers.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for(DataSnapshot userSnapshot : dataSnapshot.getChildren()){
                        secondUser = userSnapshot.getValue(User.class);
                        if(firebaseUser.getUid().equals(secondUser.getUID())){
                            //displaying logged in user name
                            textViewUserEmail.setText("Welcome " + secondUser.getFirstName() +" "+ secondUser.getLastName());
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


            //adding listener to button
            buttonLogout.setOnClickListener(this);
            FindRideBtn.setOnClickListener(this);
            PostRideBtn.setOnClickListener(this);
        }
    }


    @Override
    public void onClick(View view) {
        //if logout is pressed
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
            startActivity(new Intent(this, PublishActivity.class));
        }

}
}
