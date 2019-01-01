package johannt.carpool_2.Login_Phase;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import johannt.carpool_2.R;
import johannt.carpool_2.Rides_And_Validator.Validator;
import johannt.carpool_2.Users.User;

public class PasswordReset extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail ;
    private Button PasswordResetButton;
    private String email;
    private Validator validator;
    private User secondUser;
    private ProgressDialog progressDialog;

    //firebase object
    private FirebaseAuth firebaseAuth;
    private DatabaseReference firebaseDatabaseRides;
    private DatabaseReference firebaseDatabaseUsers;
    private FirebaseDatabase databaseCarPool;
    private FirebaseUser firebaseUser;
    private Boolean flag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        editTextEmail = findViewById(R.id.editTextEmail);
        validator = new Validator();

        PasswordResetButton = findViewById(R.id.PasswordResetBtn);
        PasswordResetButton.setOnClickListener(this);
        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseCarPool = FirebaseDatabase.getInstance();
        firebaseDatabaseUsers = databaseCarPool.getReference("Users");



    }

    @Override
    public void onClick(View v) {
        email = editTextEmail.getText().toString().trim();
        if(validator.checkEmail(email, this)){
            progressDialog.setMessage("Loading Please Wait...");
            progressDialog.show();
            PasswordRecovery(email);
        }
    }


    private void PasswordRecovery(final String email){
        firebaseDatabaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot userSnapshot : dataSnapshot.getChildren()){
                    secondUser = userSnapshot.getValue(User.class);
                    if(email.equals(secondUser.getEmail())){
                        progressDialog.dismiss();
                        flag = true;
                        Toast.makeText(PasswordReset.this, "Your password is: "+ secondUser.getPassword() , Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                if(!flag){
                    progressDialog.dismiss();
                    Toast.makeText(PasswordReset.this, "Email doesnt exist" , Toast.LENGTH_SHORT).show();
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