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
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import johannt.carpool_2.Login_Phase.SignInActivity;
import johannt.carpool_2.R;
import johannt.carpool_2.Rides_And_Validator.Carpool;
import johannt.carpool_2.Rides_And_Validator.Validator;

public class FindRideActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextDate;
    private EditText editTextStartTime;
    private EditText editTextEndTime;
    private EditText editTextPrice;
    private Spinner spinnerCity;
    private Spinner spinnerUniversity;
    private Button searchBtn;
    private ListView listView;

    private String date, endTime, startTime, price, src, dst;
    private Carpool ride;
    private boolean checker, checkDates;
    private Validator validator;
    private ArrayList<Carpool> carpoolList = new ArrayList<>();
    private ArrayAdapter<Carpool> carpoolAdapter;


    private FirebaseAuth firebaseAuth;
    private DatabaseReference firebaseDatabaseRides;
    private FirebaseDatabase databaseCarPool;
    private FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_ride);

        //send qurey to firebase
        // /getting firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseCarPool = FirebaseDatabase.getInstance();
        firebaseDatabaseRides = databaseCarPool.getReference("Rides");

        //if the objects getcurrentuser method is not null
        //means user is already logged in
        if(firebaseAuth.getCurrentUser() == null){
            //close this activity
            finish();
            //opening SignIn activity
            startActivity(new Intent(getApplicationContext(), SignInActivity.class));
        }

        editTextDate = findViewById(R.id.dateField);
        editTextEndTime = findViewById(R.id.arrivalTimeField);
        editTextStartTime= findViewById(R.id.pickupTimeField);
        editTextPrice= findViewById(R.id.priceField);
        spinnerCity = findViewById(R.id.spinnerCity);
        spinnerUniversity = findViewById(R.id.spinnerUniversity);
        //attaching click listener
        searchBtn = findViewById(R.id.SearchBtn);
        searchBtn.setOnClickListener(this);

        validator = new Validator();
        ListView listView = (ListView) findViewById(R.id.ResultList);

        //todo might not work! need to check
        carpoolAdapter = new ArrayAdapter<Carpool>(this,android.R.layout.simple_list_item_1, carpoolList);


    }


    //checking if date ,endTime , startTime , src and dst are empty
    public void onClick(View view) {
        date = editTextDate.getText().toString();
        endTime = editTextEndTime.getText().toString();
        startTime = editTextStartTime.getText().toString();
        price = editTextPrice.getText().toString();
        src = spinnerCity.getSelectedItem().toString();
        dst = spinnerUniversity.getSelectedItem().toString();


        checker = validator.checkDate(date, this) &&
                validator.checkdst(dst, this) &&
                validator.checkSrc(src, this) &&
                validator.checkPrice(price, this) &&
                validator.checkTime(endTime, this) &&
                validator.checkTime(startTime, this);

        if(view == searchBtn){
            if(checker){
                firebaseDatabaseRides.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot rideSnapshot : dataSnapshot.getChildren()){
                                ride = rideSnapshot.getValue(Carpool.class);
                                checkDates = validator.matchDates(ride.getDate(),date) &&
                                        validator.checkBetweenTime(ride.getStartTime(), date) &&
                                        validator.checkBetweenTime(date , endTime) &&
                                        validator.checkPrice(price , ride.getPrice());
                                if(checkDates){
                                    carpoolList.add(ride);
                                }
                            }
                            //todo line 129 bugs
                            listView.setAdapter(carpoolAdapter);
                            setContentView(R.layout.activity_result);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            // Failed to read value
                            Log.w("Failed to read value.", databaseError.toException());

                        }
                    });
            }
        }
    }

/**    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }**/
}



