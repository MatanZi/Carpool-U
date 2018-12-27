package johannt.carpool_2.Rides_And_Validator;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import johannt.carpool_2.R;

public class ResultActivity extends AppCompatActivity {

    private ArrayList<Carpool> carpoolList;
    private ArrayAdapter<Carpool> carpoolAdapter;
    private ListView carpoolListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        carpoolList = (ArrayList<Carpool>)intent.getSerializableExtra("carpoolList");

        carpoolListView = findViewById(R.id.carpoolListView);

        RideInfoAdapter rideInfoAdapter = new RideInfoAdapter(ResultActivity.this ,carpoolList);
        carpoolListView.setAdapter(carpoolAdapter);
    }
}
