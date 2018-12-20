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
    private boolean checkFields() {
         date = editTextDate.getText().toString();
         endTime = editTextEndTime.getText().toString();
         startTime = editTextStartTime.getText().toString();
         price = editTextPrice.getText().toString();
         src = spinnerCity.getSelectedItem().toString();
         dst = spinnerUniversity.getSelectedItem().toString();

        if (TextUtils.isEmpty(date)) {
            Toast.makeText(this, "Please enter date", Toast.LENGTH_LONG).show();
            return false;
        }

        else if (TextUtils.isEmpty(endTime)) {
            Toast.makeText(this, "Please enter arrival time", Toast.LENGTH_LONG).show();
            return false;
        }

        else if (TextUtils.isEmpty(startTime)) {
            Toast.makeText(this, "Please enter pickup time", Toast.LENGTH_LONG).show();
            return false;
        }

        else if (TextUtils.isEmpty(price)) {
            Toast.makeText(this, "Please enter a price", Toast.LENGTH_LONG).show();
            return false;
        }

        else if (TextUtils.isEmpty(src)) {
            Toast.makeText(this, "Please enter source place", Toast.LENGTH_LONG).show();
            return false;
        }

        else if (TextUtils.isEmpty(dst)) {
            Toast.makeText(this, "Please enter destination place", Toast.LENGTH_LONG).show();
            return false;
        }
        return  true;
    }



    public void onClick(View view) {
        if(view == searchBtn){
            if(checkFields()){
               //send qurey to firebase
            }
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}



