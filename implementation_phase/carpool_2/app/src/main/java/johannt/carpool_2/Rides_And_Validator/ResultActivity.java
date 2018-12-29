package johannt.carpool_2.Rides_And_Validator;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import johannt.carpool_2.Profile_Features.FindRideActivity;
import johannt.carpool_2.R;

public class ResultActivity extends AppCompatActivity {


    private ListView carpoolListView;

    private String date, endTime, startTime, price, src, dst;
    private Carpool ride;
    private boolean checker , checkDates;
    private Validator validator;
    private List<Carpool> carpoolList;
    private ProgressDialog progressDialog;
    private RideInfoAdapter carpoolAdapter;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference firebaseDatabaseRides;
    private FirebaseDatabase databaseCarPool;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // /getting firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseCarPool = FirebaseDatabase.getInstance();
        firebaseDatabaseRides = databaseCarPool.getReference("Rides");

        carpoolListView = findViewById(R.id.list_view_carpool);

        progressDialog = new ProgressDialog(this);

        validator = new Validator();

        carpoolList = new ArrayList<>();

    }

    protected void onStart(){
        super.onStart();

        Intent intent = getIntent();
        date = intent.getStringExtra("date");
        endTime = intent.getStringExtra("endTime");
        startTime = intent.getStringExtra("startTime");
        price = intent.getStringExtra("price");
        src = intent.getStringExtra("src");
        dst = intent.getStringExtra("dst");

        checker = validator.checkDate(date, this) &&
                validator.checkdst(dst, this) &&
                validator.checkSrc(src, this) &&
                validator.checkPrice(price, this) &&
                validator.checkTime(endTime, this) &&
                validator.checkTime(startTime, this);

        progressDialog.setMessage("Searching please wait...");
        progressDialog.show();
        if (checker) {


            firebaseDatabaseRides.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot rideSnapshot : dataSnapshot.getChildren()) {
                        ride = rideSnapshot.getValue(Carpool.class);
                        checkDates = validator.matchDates(ride.getDate(), date) &&
                                validator.checkBetweenTime(ride.getStartTime(), date) &&
                                validator.checkBetweenTime(date, endTime) &&
                                validator.checkPrice(price, ride.getPrice()) &&
                                ride.getSrc().equals(src) &&
                                ride.getDst().equals(dst);
                        if (checkDates) {
                            carpoolList.add(ride);
                        }
                    }
                    progressDialog.dismiss();
                    if (carpoolList.isEmpty()) {
                        Toast.makeText(ResultActivity.this, "No rides were found", Toast.LENGTH_LONG).show();
                    } else {
                        carpoolAdapter = new RideInfoAdapter(ResultActivity.this, carpoolList);
                        carpoolListView.setAdapter(carpoolAdapter);
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
}
