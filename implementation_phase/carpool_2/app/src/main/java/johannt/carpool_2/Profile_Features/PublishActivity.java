package johannt.carpool_2.Profile_Features;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import johannt.carpool_2.Login_Phase.SignInActivity;
import johannt.carpool_2.R;
import johannt.carpool_2.Rides_And_Validator.carpool;
import johannt.carpool_2.Rides_And_Validator.validator;

import static johannt.carpool_2.R.id.activity_publish;
import static johannt.carpool_2.R.id.swapSrcDstBtn;

public class PublishActivity extends AppCompatActivity  implements View.OnClickListener {

    private EditText editTextDate;
    private EditText editTextStartTime;
    private EditText editTextEndTime;
    private EditText editTextPrice;
    private EditText editTextFreeSits;
    private Spinner spinnerSrc, spinnerDest;
    private Button addRideBtn ;
    private ImageButton swapSrcDstBtn;
    private String id,firstName , lastName , date, endTime, startTime, price, freeSits, src, dst;
    private carpool carpool;
    private boolean swap;


    private FirebaseAuth firebaseAuth;

    private DatabaseReference firebaseDatabaseCarpool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        swap = true;

        //check for empty fields
        // /getting firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //if the objects getcurrentuser method is not null
        //means user is already logged in
        if(firebaseAuth.getCurrentUser() == null){
            //close this activity
            finish();
            //opening SignIn activity
            startActivity(new Intent(getApplicationContext(), SignInActivity.class));
        }

        editTextDate = findViewById(R.id.dateFieldAddRide);
        editTextEndTime = findViewById(R.id.arrivalTimeFieldAddRide);
        editTextStartTime= findViewById(R.id.pickupTimeFieldAddRide);
        editTextPrice= findViewById(R.id.priceFieldAddRide);
        spinnerSrc = findViewById(R.id.spinnerSrcAddRide);
        spinnerDest = findViewById(R.id.spinnerDestAddRide);


        //attaching click listener
        addRideBtn = findViewById(R.id.addRideBtn);
        swapSrcDstBtn = findViewById(R.id.swapSrcDstBtn);
        addRideBtn.setOnClickListener(this);
        swapSrcDstBtn.setOnClickListener(this);

    }

   // class addArideOnClickListener implements View.OnClickListener {
        // @Override
        public void onClick(View v) {

                if(v == addRideBtn){
                    date = editTextDate.getText().toString();
                    endTime = editTextEndTime.getText().toString();
                    startTime = editTextStartTime.getText().toString();
                    price = editTextPrice.getText().toString();
                    src = spinnerSrc.getSelectedItem().toString();
                    dst = spinnerDest.getSelectedItem().toString();

                    validator validator = new validator();
                    boolean checker = validator.checkDate(date, this) &&
                            validator.checkdst(dst, this) &&
                            validator.checkSrc(src, this) &&
                            validator.checkPrice(price, this) &&
                            validator.checkTime(endTime, this) &&
                            validator.checkTime(startTime, this);

                    if(checker){
                        firebaseDatabaseCarpool = FirebaseDatabase.getInstance().getReference("Users");
                        id = firebaseDatabaseCarpool.push().getKey();
                        //todo: get the user name and last name from the firebase users database
                        carpool = new carpool(id , date , startTime , endTime , price , freeSits , src , dst);
                        firebaseDatabaseCarpool.child(id).setValue(carpool);
                        //todo:insert data firebase

                    }
                }
                if( v == swapSrcDstBtn){

                    // Create an ArrayAdapter using the string array and a default spinner layout
                    ArrayAdapter<CharSequence> cityAdapter = ArrayAdapter.createFromResource(this,
                            R.array.city_array, android.R.layout.simple_spinner_item);
                    ArrayAdapter<CharSequence> universityAdapter = ArrayAdapter.createFromResource(this,
                            R.array.university_array, android.R.layout.simple_spinner_item);

// Specify the layout to use when the list of choices appears
                    cityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    universityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                    if(swap == true) {
                        spinnerSrc.setAdapter(universityAdapter);
                        spinnerDest.setAdapter(cityAdapter);
                        swap = false;
                    }
                    else{
                        spinnerSrc.setAdapter(cityAdapter);
                        spinnerDest.setAdapter(universityAdapter);
                        swap = true ;
                    }
                }
            }











}
