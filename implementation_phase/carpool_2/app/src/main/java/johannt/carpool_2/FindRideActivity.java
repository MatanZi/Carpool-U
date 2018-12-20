package johannt.carpool_2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FindRideActivity extends AppCompatActivity implements View.OnClickListener {




    private EditText editTextDate;
    private EditText editTextStartTime;
    private EditText editTextEndTime;
    private EditText editTextPrice;
    private Spinner spinnerCity;
    private Spinner spinnerUniversity;
    private Button searchBtn;

    public String date, endTime, startTime, price, src, dst;


    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_ride);


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

        editTextDate = findViewById(R.id.dateField);
        editTextEndTime = findViewById(R.id.arrivalTimeField);
        editTextStartTime= findViewById(R.id.pickupTimeField);
        editTextPrice= findViewById(R.id.priceField);
        spinnerCity = findViewById(R.id.spinnerCity);
        spinnerUniversity = findViewById(R.id.spinnerUniversity);
        //attaching click listener
        searchBtn = findViewById(R.id.SearchBtn);
        searchBtn.setOnClickListener(this);
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

        if(view == searchBtn){
            if(checker){
                //send qurey to firebase
            }
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}



