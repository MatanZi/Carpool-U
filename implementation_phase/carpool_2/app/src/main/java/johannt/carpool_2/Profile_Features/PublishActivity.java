package johannt.carpool_2.Profile_Features;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import johannt.carpool_2.Login_Phase.SignInActivity;
import johannt.carpool_2.R;
import johannt.carpool_2.Rides_And_Validator.Carpool;
import johannt.carpool_2.Rides_And_Validator.Validator;
import johannt.carpool_2.Users.User;

public class PublishActivity extends AppCompatActivity  implements View.OnClickListener {

    private EditText editTextDate;
    private EditText editTextStartTime;
    private EditText editTextEndTime;
    private EditText editTextPrice;
    private Spinner spinnerSrc, spinnerDest , spinnerFreePlace;
    private Button addRideBtn;
    private ImageButton swapSrcDstBtn;
    private String id, firstName, lastName, date, endTime, startTime, price, freeSits, src, dst, currentUID , databaseUserUID;
    private Carpool carpool;
    private boolean swap;
    private User secondUser;


    private FirebaseAuth firebaseAuth;
    private DatabaseReference firebaseDatabaseRides;
    private DatabaseReference firebaseDatabaseUsers;
    private FirebaseDatabase databaseCarPool;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        swap = true;


        // /getting firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseCarPool = FirebaseDatabase.getInstance();
        firebaseDatabaseRides = databaseCarPool.getReference("Rides");
        firebaseDatabaseUsers = databaseCarPool.getReference("Users");

        //if the objects getcurrentuser method is not null
        //means user is already logged in
        if (firebaseUser == null) {
            //close this activity
            finish();
            //opening SignIn activity
            startActivity(new Intent(getApplicationContext(), SignInActivity.class));
        }

        editTextDate = findViewById(R.id.dateFieldAddRide);
        editTextEndTime = findViewById(R.id.arrivalTimeFieldAddRide);
        editTextStartTime = findViewById(R.id.pickupTimeFieldAddRide);
        editTextPrice = findViewById(R.id.priceFieldAddRide);
        spinnerSrc = findViewById(R.id.spinnerSrcAddRide);
        spinnerDest = findViewById(R.id.spinnerDestAddRide);
        spinnerFreePlace =findViewById(R.id.spinnerFreePlace);



        //attaching click listener
        addRideBtn = findViewById(R.id.addRideBtn);
        swapSrcDstBtn = findViewById(R.id.swapSrcDstBtn);
        addRideBtn.setOnClickListener(this);
        swapSrcDstBtn.setOnClickListener(this);

    }

    // class addArideOnClickListener implements View.OnClickListener {
    // @Override
    public void onClick(View v) {


        if (v == swapSrcDstBtn) {

            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> cityAdapter = ArrayAdapter.createFromResource(this,
                    R.array.city_array, android.R.layout.simple_spinner_item);
            ArrayAdapter<CharSequence> universityAdapter = ArrayAdapter.createFromResource(this,
                    R.array.university_array, android.R.layout.simple_spinner_item);

            // Specify the layout to use when the list of choices appears
            cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            universityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            if (swap == true) {
                spinnerSrc.setAdapter(universityAdapter);
                spinnerDest.setAdapter(cityAdapter);
                swap = false;
            } else {
                spinnerSrc.setAdapter(cityAdapter);
                spinnerDest.setAdapter(universityAdapter);
                swap = true;
            }
        }

        if (v == addRideBtn) {
            date = editTextDate.getText().toString();
            endTime = editTextEndTime.getText().toString();
            startTime = editTextStartTime.getText().toString();
            price = editTextPrice.getText().toString();
            src = spinnerSrc.getSelectedItem().toString();
            dst = spinnerDest.getSelectedItem().toString();
            freeSits = spinnerFreePlace.getSelectedItem().toString();

            Validator validator = new Validator();
            boolean checker = validator.checkDate(date, this) &&
                    validator.checkdst(dst, this) &&
                    validator.checkSrc(src, this) &&
                    validator.checkPrice(price, this) &&
                    validator.checkTime(endTime, this) &&
                    validator.checkTime(startTime, this);

            if (checker) {
                id = firebaseDatabaseRides.push().getKey();
                secondUser= new User();

                firebaseDatabaseUsers.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot userSnapshot : dataSnapshot.getChildren()){
                            secondUser = userSnapshot.getValue(User.class);
                            if(firebaseUser.getUid().equals(secondUser.getUID())){
                                firstName = secondUser.getFirstName();
                                lastName = secondUser.getLastName();
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

                carpool = new Carpool(id,firstName , lastName, date, startTime, endTime, price, freeSits, src, dst);
                firebaseDatabaseRides.child(firstName+" "+lastName).setValue(carpool);

            }
        }
    }
}