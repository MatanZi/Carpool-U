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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import johannt.carpool_2.Profile_Features.ProfileActivity;
import johannt.carpool_2.R;
import johannt.carpool_2.Users.User;
import johannt.carpool_2.Rides_And_Validator.validator;

/**
 * A login screen that offers login via email/password.
 */
public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String repeatPassword;
    private String phoneNumber;
    private String city;
    private String university;
    private String id;
    private Boolean checker;

    //defining view objects
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextPhoneNumber;
    private EditText editTextRepeatPassword;
    private Spinner spinnerCity;
    private Spinner spinnerUniversity;
    private Button buttonSignup;
    private ProgressDialog progressDialog;


    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseUsers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();
        databaseUsers = FirebaseDatabase.getInstance().getReference("Users");

        //initializing views
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextRepeatPassword = findViewById(R.id.editTextRepeatPassword);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        spinnerCity = findViewById(R.id.spinnerCity);
        spinnerUniversity = findViewById(R.id.spinnerUniversity);

        buttonSignup = findViewById(R.id.buttonSignup);

        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        buttonSignup.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        //updateUI(currentUser);
    }
    private void registerUser(){
        //getting email and password from edit texts
         firstname = editTextFirstName.getText().toString().trim();
         lastname  = editTextLastName.getText().toString().trim();
         email = editTextEmail.getText().toString().trim();
         password = editTextPassword.getText().toString().trim();
         repeatPassword = editTextRepeatPassword.getText().toString().trim();
         phoneNumber = editTextPhoneNumber.getText().toString().trim();
         city = spinnerCity.getSelectedItem().toString().trim();
         university = spinnerUniversity.getSelectedItem().toString().trim();


         validator validator = new validator();


        // check attributes
        checker = validator.checkEmail(email , this) &&
        validator.checkPassword(password ,this) &&
        validator.checkRepeatPassword(repeatPassword, password,this) &&
        validator.checkFirstName(firstname,this) &&
        validator.checkLastName(lastname,this) &&
        validator.checkPhonenumber(phoneNumber, this) &&
        validator.checkSrc(city,this) &&
        validator.checkdst(city,this);




        if(checker) {


            //Todo: insert the new user object to the firebase database
            id = databaseUsers.push().getKey();
            User newUser = new User(firstname, lastname, email, password, phoneNumber , city , university, id);

            //adding the new user to the database
            databaseUsers.child(id).setValue(newUser);

            //if the email and password are not empty
            //displaying a progress dialog

            progressDialog.setMessage("Registering Please Wait...");
            progressDialog.show();

            //creating a new user

            firebaseAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            //checking if success
                            if (task.isSuccessful()) {
                                //display some message here
                                Toast.makeText(SignUpActivity.this, "Successfully registered", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

                            } else {
                                //display some message here
                                Toast.makeText(SignUpActivity.this, "Registration Error", Toast.LENGTH_LONG).show();
                            }
                            progressDialog.dismiss();
                        }
                    });
        }

    }

    @Override
    public void onClick(View view) {
        //calling register method on click
        registerUser();
    }
}

