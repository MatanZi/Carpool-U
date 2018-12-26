package johannt.carpool_2.Profile_Features;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import johannt.carpool_2.Login_Phase.SignInActivity;
import johannt.carpool_2.R;
import johannt.carpool_2.Rides_And_Validator.validator;

public class PublishActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextDate;
    private EditText editTextStartTime;
    private EditText editTextEndTime;
    private EditText editTextPrice;
    private EditText editTextFreeSits;
    private Spinner spinnerCity;
    private Spinner spinnerUniversity;
    private Button addRideBtn;

    private String id,firstName , lastName , date, endTime, startTime, price, freeSits, src, dst;
    private johannt.carpool_2.Rides_And_Validator.carpool carpool;


    private FirebaseAuth firebaseAuth;
    private DatabaseReference firebaseDatabaseCarpool;
    private FirebaseDatabase firebaseDatabase;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //check for empty fields
        // /getting firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        //if the objects getcurrentuser method is not null
        //means user is already logged in
        if(firebaseUser == null){
            //close this activity
            finish();
            //opening SignIn activity
            startActivity(new Intent(getApplicationContext(), SignInActivity.class));
        }

        editTextDate = findViewById(R.id.dateFieldAddRide);
        editTextEndTime = findViewById(R.id.arrivalTimeFieldAddRide);
        editTextStartTime= findViewById(R.id.pickupTimeFieldAddRide);
        editTextPrice= findViewById(R.id.priceFieldAddRide);
        spinnerCity = findViewById(R.id.spinnerCityAddRide);
        spinnerUniversity = findViewById(R.id.spinnerUniversityAddRide);
        //attaching click listener
        addRideBtn = findViewById(R.id.addRideBtn);
        addRideBtn.setOnClickListener(this);

       /**FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });**/
    }



    //checking if date ,endTime , startTime , src and dst are empty
    public void onClick(View view) {
        date = editTextDate.getText().toString();
        endTime = editTextEndTime.getText().toString();
        startTime = editTextStartTime.getText().toString();
        price = editTextPrice.getText().toString();
        src = spinnerCity.getSelectedItem().toString();
        dst = spinnerUniversity.getSelectedItem().toString();

        validator validator = new validator();
        boolean checker = validator.checkDate(date, this) &&
                validator.checkdst(dst, this) &&
                validator.checkSrc(src, this) &&
                validator.checkPrice(price, this) &&
                validator.checkTime(endTime, this) &&
                validator.checkTime(startTime, this);

        if(view == addRideBtn){
            if(checker){
                firebaseDatabaseCarpool = FirebaseDatabase.getInstance().getReference("Rides");
                id = firebaseDatabaseCarpool.push().getKey();
                //todo: get the user name and last name from the firebase users database
                //carpool = new carpool(id , firebaseUser.getClass().get, date , startTime , endTime , price , freeSits , src , dst);
                //firebaseDatabaseCarpool.child(id).setValue(carpool);
                //todo:insert data firebase

            }
        }
    }

}
