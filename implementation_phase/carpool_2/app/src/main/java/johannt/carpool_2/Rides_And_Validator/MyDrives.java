package johannt.carpool_2.Rides_And_Validator;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
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

import johannt.carpool_2.R;
import johannt.carpool_2.Users.User;

public class MyDrives extends AppCompatActivity {

    public ListView carpoolListView;
    private String  currentUserUid , carpoolId;
    public List<Carpool> carpools;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference firebaseDatabaseRides,databaseUsersRef;
    private FirebaseDatabase databaseCarPool;
    private FirebaseUser firebaseUser;
    private User currentUser;
    private CarpoolList carpoolAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_drives);
        // /getting firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseCarPool = FirebaseDatabase.getInstance();
        firebaseDatabaseRides = databaseCarPool.getReference("Rides");
        currentUserUid = firebaseUser.getUid();

        carpoolListView = (ListView)findViewById(R.id.list);
        carpools = new ArrayList<>();



        carpoolListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Carpool carpool = carpools.get(i);
                String name = carpool.getSrc()+" -> "+carpool.getDst()+"  "+carpool.getStartTime();
                showUpdateDeleteDialog(carpool.getId(), name);
                return true;
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();



        //attaching value event listener
        firebaseDatabaseRides.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //clearing the previous carpool list
                carpools.clear();
                //iterating through all the nodes
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    //getting artist
                    Carpool carpool = postSnapshot.getValue(Carpool.class);
                    String uid = carpool.getUID();
                    if(uid.equals(currentUserUid)) {
                        carpools.add(carpool);
                    }
                }

                if (!carpools.isEmpty()) {
                    //creating adapter
                    carpoolAdapter = new CarpoolList(MyDrives.this, carpools);
                    //attaching adapter to the listview

                    carpoolListView.setAdapter(carpoolAdapter);
                }
            }


            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private boolean updateCarpool(String hour, String freeplaces,String id) {

        DatabaseReference carpoolRef = firebaseDatabaseRides.child(id);
        //getting the specified user reference
        try {
            carpoolRef.child("startTime").setValue(hour);
            carpoolRef.child("freeSits").setValue(freeplaces);
            Toast.makeText(getApplicationContext(), "updated successfully", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
    private boolean deleteCarpool(String id) {
        //getting the specified artist reference
        DatabaseReference carpoolRef = firebaseDatabaseRides.child(id);

        //removing carpool
        carpoolRef.removeValue();

        Toast.makeText(getApplicationContext(), "Carpool Deleted", Toast.LENGTH_LONG).show();

        return true;
    }

    private void showUpdateDeleteDialog(final String carpoolId, String Name) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.update_dialog_drive, null);
        dialogBuilder.setView(dialogView);

        final EditText editHour = (EditText) dialogView.findViewById(R.id.editHour);
        final Spinner spinnerFreePlaces = (Spinner) dialogView.findViewById(R.id.spinnerFreePlace);
        final Button buttonUpdate = (Button) dialogView.findViewById(R.id.buttonUpdateCarpool);
        final Button buttonDelete = (Button) dialogView.findViewById(R.id.buttonDeleteCarpool);

        dialogBuilder.setTitle(Name);
        final AlertDialog b = dialogBuilder.create();
        b.show();


        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hour = editHour.getText().toString().trim();
                String freeplaces = spinnerFreePlaces.getSelectedItem().toString();
                if (!TextUtils.isEmpty(hour)) {
                    updateCarpool(hour,freeplaces,carpoolId);
                    b.dismiss();
                }
            }
        });


        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteCarpool(carpoolId);
                b.dismiss();

            }
        });
    }

}
