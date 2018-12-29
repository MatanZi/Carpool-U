package johannt.carpool_2.Profile_Features;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;

import johannt.carpool_2.R;
import johannt.carpool_2.Rides_And_Validator.Carpool;
import johannt.carpool_2.Rides_And_Validator.ResultActivity;
import johannt.carpool_2.Rides_And_Validator.Validator;

public class FindRideActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextDate;
    private EditText editTextStartTime;
    private EditText editTextEndTime;
    private EditText editTextPrice;
    private Spinner spinnerSrc;
    private Spinner spinnerDest;
    private Button searchBtn;
    private ImageButton swapSrcDstBtn, calendarBtn;
    private ListView listView;

    private String date, endTime, startTime, price, src, dst, calendarDate;
    private int Day,Month,Year;
    private Carpool ride;
    private boolean checker, checkDates, swap;
    private Calendar calendar;


    private Validator validator;
    public ArrayList<Carpool> carpoolList = new ArrayList<>();
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference firebaseDatabaseRides;
    private FirebaseDatabase databaseCarPool;
    private FirebaseUser firebaseUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_ride);
        swap = true;

        calendar = Calendar.getInstance();
        int Day = calendar.get(Calendar.DAY_OF_MONTH);
        int Month = calendar.get(Calendar.MONTH);
        int Year = calendar.get(Calendar.YEAR);

        editTextDate = findViewById(R.id.dateField);
        editTextEndTime = findViewById(R.id.arrivalTimeField);
        editTextStartTime= findViewById(R.id.pickupTimeField);
        editTextPrice= findViewById(R.id.priceField);
        spinnerSrc = findViewById(R.id.spinnerCity);
        spinnerDest = findViewById(R.id.spinnerUniversity);
        swapSrcDstBtn = findViewById(R.id.swapSrcDstBtn);
        calendarBtn = findViewById(R.id.calendarButton);
        //attaching click listener
        searchBtn = findViewById(R.id.SearchBtn);
        searchBtn.setOnClickListener(this);
        swapSrcDstBtn.setOnClickListener(this);
        calendarBtn.setOnClickListener(this);

        validator = new Validator();
        //ListView listView = (ListView) findViewById(R.id.ResultList);
        //carpoolAdapter = new ArrayAdapter<Carpool>(this,android.R.layout.simple_list_item_1, carpoolList);


    }

    @Override
    protected void onStart() {
        super.onStart();
        setCityToUniversity();
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

    public void setCityToUniversity(){
        spinnerSrc.setSelection(getIndex(spinnerSrc, ProfileActivity.city));
        spinnerDest.setSelection(getIndex(spinnerDest, ProfileActivity.university));
    }
    public void setUniversityToCity(){
        spinnerSrc.setSelection(getIndex(spinnerSrc, ProfileActivity.university));
        spinnerDest.setSelection(getIndex(spinnerDest, ProfileActivity.city));
    }


    //checking if date ,endTime , startTime , src and dst are empty
    public void onClick(View view) {

        if( view == calendarBtn ) {
            com.wdullaer.materialdatetimepicker.date.DatePickerDialog dialog =
                    com.wdullaer.materialdatetimepicker.date.DatePickerDialog.newInstance(new com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener() {
                        @Override

                        public void onDateSet(com.wdullaer.materialdatetimepicker.date.DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
                            calendarDate = dayOfMonth+"/"+(monthOfYear+1)+"/"+year;
                            editTextDate.setText(calendarDate);
                        }
                    }, Year, Month, Day);

            Calendar minDate = calendar;
            minDate.setTime(calendar.getTime());
            dialog.setMinDate(minDate);
            dialog.show(getFragmentManager(), "DatePickerDialog");
        }

        if (view == swapSrcDstBtn) {

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
                setUniversityToCity();
                swap = false;
            } else {
                spinnerSrc.setAdapter(cityAdapter);
                spinnerDest.setAdapter(universityAdapter);
                setCityToUniversity();
                swap = true;
            }
        }

        if(view == searchBtn) {
            date = editTextDate.getText().toString();
            endTime = editTextEndTime.getText().toString();
            startTime = editTextStartTime.getText().toString();
            price = editTextPrice.getText().toString();
            src = spinnerSrc.getSelectedItem().toString();
            dst = spinnerDest.getSelectedItem().toString();

            validator = new Validator();

            try {
                checker = validator.checkDate(date, this) &&
                        validator.checkdst(dst, this) &&
                        validator.checkSrc(src, this) &&
                        validator.checkPrice(price, this) &&
                        validator.checkTime(endTime, this) &&
                        validator.checkTime(startTime, this);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            if (checker) {
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                intent.putExtra("date", date);
                intent.putExtra("endTime", endTime);
                intent.putExtra("startTime", startTime);
                intent.putExtra("price", price);
                intent.putExtra("src", src);
                intent.putExtra("dst", dst);

                startActivity(intent);
                finish();
            }
        }
    }

/**    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }**/
}



