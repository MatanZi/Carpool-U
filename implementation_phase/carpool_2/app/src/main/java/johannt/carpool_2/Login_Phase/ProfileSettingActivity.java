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

import static johannt.carpool_2.Profile_Features.ProfileActivity.email;
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


        // FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        buttonSave = findViewById(R.id.buttonSave);

        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        buttonSave.setOnClickListener(this);
    }

    /**
     * @param spinner
     * @param myString
     * @return the index of the item corresponding to myString
     */
    private int getIndex(Spinner spinner, String myString){
        for (int i=0;i<spinner.getCount();i++){
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                return i;
            }
        }
        return 0;
    }

    public void setCurrentsValues(){
        editTextFirstName.setText(firstName);
        editTextLastName.setText(ProfileActivity.lastName);
        editTextPhoneNumber.setText(ProfileActivity.phoneNumber);
        spinnerCity.setSelection(getIndex(spinnerCity, ProfileActivity.city));
        spinnerUniversity.setSelection(getIndex(spinnerUniversity, ProfileActivity.university));
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        databaseUsersRef = FirebaseDatabase.getInstance().getReference("Users");
        firebaseUser = firebaseAuth.getCurrentUser();
        id = firebaseUser.getUid();
        //updateUI(currentUser);
        setCurrentsValues();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCurrentsValues();
    }


    /**
     *
     * @param firstname
     * @param lastname
     * @param phoneNumber
     * @return
     */
    private boolean updateUser(String firstname, String lastname, String phoneNumber, String city, String university) {
        //getting the specified user reference
        //DatabaseReference dR = FirebaseDatabase.getInstance().getReference("user").child(id);

        try {
            databaseUsersRef.child(id).child("firstName").setValue(firstname);
            databaseUsersRef.child(id).child("lastName").setValue(lastname);
            databaseUsersRef.child(id).child("phoneNumber").setValue(phoneNumber);
            databaseUsersRef.child(id).child("city").setValue(city);
            databaseUsersRef.child(id).child("university").setValue(university);

        } catch (Exception e) {
            e.printStackTrace();
        }

        progressDialog.dismiss();
        Toast.makeText(this, "Profile Updated", Toast.LENGTH_LONG).show();
        return true;
    }


    @Override
    public void onClick(View v) {

        if (v == buttonSave) {

            String firstname = editTextFirstName.getText().toString();
            String lastname = editTextLastName.getText().toString();
            String phoneNumber = editTextPhoneNumber.getText().toString();
            String city = spinnerCity.getSelectedItem().toString();
            String university = spinnerUniversity.getSelectedItem().toString();

            progressDialog.setMessage("Updating...");
            progressDialog.show();
            updateUser(firstname,lastname,phoneNumber,city,university);

           startActivity(new Intent(this, ProfileActivity.class));

            finish();
        }
    }
}
