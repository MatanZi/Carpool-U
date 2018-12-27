package johannt.carpool_2.Login_Phase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import johannt.carpool_2.Profile_Features.ProfileActivity;
import johannt.carpool_2.R;
import johannt.carpool_2.Users.User;

import static johannt.carpool_2.Profile_Features.ProfileActivity.firstName;

public class ProfileSettingActivity extends AppCompatActivity implements View.OnClickListener{



    private String id;
    private Boolean checker;
    //defining view objects
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextPhoneNumber;
    private Spinner spinnerCity;
    private Spinner spinnerUniversity;
    private Button buttonSave;
    private ProgressDialog progressDialog;


    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseUsersRef;
    private FirebaseDatabase databaseCarPool;
    private FirebaseUser firebaseUser;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();
        //Carpool = new Firebase("https://carpool-u.firebaseio.com/");


        //initializing views
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        spinnerCity = findViewById(R.id.spinnerCity);
        spinnerUniversity = findViewById(R.id.spinnerUniversity);

        editTextFirstName.setText(firstName);
        editTextLastName.setText(ProfileActivity.lastName);
        editTextPhoneNumber.setText(ProfileActivity.phoneNumber);
        spinnerCity.setSelection(getIndex(spinnerCity, ProfileActivity.city));
        spinnerUniversity.setSelection(getIndex(spinnerUniversity, ProfileActivity.university));

        // FirebaseUser currentUser = firebaseAuth.getCurrentUser();


        buttonSave = findViewById(R.id.buttonSave);

        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        buttonSave.setOnClickListener(this);
    }

    //private method of your class
    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        databaseUsersRef = FirebaseDatabase.getInstance().getReference("Users");
        firebaseUser = firebaseAuth.getCurrentUser();
        id = firebaseUser.getUid();
        //updateUI(currentUser);
    }

    private boolean updateUser(String firstname, String lastname, String phoneNumber) {
        //getting the specified user reference
        //DatabaseReference dR = FirebaseDatabase.getInstance().getReference("user").child(id);

        try {
            databaseUsersRef.child(firstName+" "+ lastname).child("firstName").setValue(firstname);
            databaseUsersRef.child(firstName+" "+ lastname).child("lastName").setValue(lastname);
            databaseUsersRef.child(firstName+" "+ lastname).child("phoneNumber").setValue(phoneNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }

      //  Toast.makeText(this, "Artist Updated", Toast.LENGTH_LONG).show();
        return true;
    }


    @Override
    public void onClick(View v) {

        if (v == buttonSave) {

            String firstname = editTextFirstName.getText().toString();
            String lastname = editTextLastName.getText().toString();
            String phoneNumber = editTextPhoneNumber.getText().toString();

            updateUser(firstname,lastname,phoneNumber);

            startActivity(new Intent(this, ProfileActivity.class));
        }
    }
}
