package johannt.carpool_2.Profile_Features;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import johannt.carpool_2.Login_Phase.SignInActivity;
import johannt.carpool_2.R;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener {

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //view objects
    private TextView textViewUserEmail;
    private Button buttonLogout;
    public Button FindRideBtn;
    private Button PostRideBtn;



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
            //displaying logged in user name
            textViewUserEmail.setText("Welcome " + user.getEmail());

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
