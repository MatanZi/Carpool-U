package johannt.carpool_2.Rides_And_Validator;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import johannt.carpool_2.Profile_Features.FindRideActivity;
import johannt.carpool_2.R;
import johannt.carpool_2.Users.User;

public class ResultActivity extends AppCompatActivity {


    private ListView carpoolListView;

    private String date, endTime, startTime, price, src, dst;
    private Carpool ride;
    private User driver;
    private boolean checker, checkDates;
    private Validator validator;
    private List<Carpool> carpoolList;
    private ProgressDialog progressDialog;
    private RideInfoAdapter carpoolAdapter;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference firebaseDatabaseRides,firebaseUsersRef,userRef;
    private FirebaseDatabase databaseCarPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Searching please wait...");
        progressDialog.show();

        // /getting firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();
        //firebaseUser = firebaseAuth.getCurrentUser();
        databaseCarPool = FirebaseDatabase.getInstance();
        firebaseDatabaseRides = databaseCarPool.getReference("Rides");
        firebaseUsersRef = databaseCarPool.getReference("Users");

        carpoolListView = findViewById(R.id.list_view_carpool);


        carpoolList = new ArrayList<>();


        Intent intent = getIntent();
        date = intent.getStringExtra("date");
        endTime = intent.getStringExtra("endTime");
        startTime = intent.getStringExtra("startTime");
        price = intent.getStringExtra("price");
        src = intent.getStringExtra("src");
        dst = intent.getStringExtra("dst");

        ride = new Carpool();
        driver = new User();
        validator = new Validator();


        carpoolListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int i, long l) {
                Carpool carpool = carpoolList.get(i);
                User driver = getDriver(carpool.getUID());
                String name = carpool.getSrc()+" -> "+carpool.getDst()+"  "+carpool.getStartTime();
                showContactDialog(carpool,driver,name);
            }
        });

    }
    /**
     * @param uid
     * @return driver corresponding to the carpool
     */
    public User getDriver(String uid) {
        userRef = firebaseUsersRef.child(uid);
        ValueEventListener valueEventListener = userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                driver = dataSnapshot.getValue(User.class);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return  driver ;
    }

    @Override
    protected void onStart() {
        super.onStart();
        carpoolList.clear();
        firebaseDatabaseRides.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot rideSnapshot : dataSnapshot.getChildren()) {
                    ride = rideSnapshot.getValue(Carpool.class);
                    checkDates = date.equals(ride.getDate()) &&
                            validator.checkBetweenTime(ride.getStartTime(), startTime) &&
                            validator.checkBetweenTime(ride.getEndTime(), endTime) &&
                            validator.checkPrice(price, ride.getPrice()) &&
                            ride.getSrc().equals(src) &&
                            ride.getDst().equals(dst);
                    if (checkDates) {
                        carpoolList.add(ride);
                    }
                }

                progressDialog.dismiss();
                if (carpoolList.isEmpty()) {
                    Toast.makeText(ResultActivity.this, "No rides were found", Toast.LENGTH_SHORT).show();
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

    private void showContactDialog(final Carpool carpool,final User actualDriver,String Name) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.carpool_contact_dialog, null);
        dialogBuilder.setView(dialogView);

        final TextView fullName = (TextView) dialogView.findViewById(R.id.fullName);
        final TextView numOfFreeplace = (TextView) dialogView.findViewById(R.id.numOfFreePlace);
      //  final ImageView imageProfile = (ImageView) dialogView.findViewById(R.id.profilePict);
        final ImageButton buttonSms = (ImageButton) dialogView.findViewById(R.id.smsButton);
        final ImageButton buttonCall = (ImageButton) dialogView.findViewById(R.id.callButton);
        final ImageButton buttonWhatsapp = (ImageButton) dialogView.findViewById(R.id.whatsappButton);

        // setting the current values
        fullName.setText(actualDriver.getFirstName()+" "+actualDriver.getLastName());
        numOfFreeplace.setText((carpool.getFreeSits()));

        dialogBuilder.setTitle(Name);
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Call", Toast.LENGTH_SHORT).show();
                b.dismiss();

            }
        });


        buttonSms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "SMS", Toast.LENGTH_SHORT).show();

                b.dismiss();

            }
        });

        buttonWhatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "WhatsApp", Toast.LENGTH_SHORT).show();
                b.dismiss();

            }
        });
    }


}