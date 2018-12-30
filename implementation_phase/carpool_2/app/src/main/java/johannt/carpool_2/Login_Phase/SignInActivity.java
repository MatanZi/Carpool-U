package johannt.carpool_2.Login_Phase;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import johannt.carpool_2.Profile_Features.ProfileActivity;
import johannt.carpool_2.R;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {


    //defining views
    private Button buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignup;
    private TextView textViewPasswordReset;

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    //progress dialog
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);


        // /getting firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();

        //if the objects getcurrentuser method is not null
        //means user is already logged in
        if(firebaseAuth.getCurrentUser() != null){
            //close this activity
            finish();
            //opening profile activity
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        //initializing views
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        buttonSignIn = findViewById(R.id.buttonSignin);
        textViewSignup  = findViewById(R.id.textViewSignin);
        textViewPasswordReset  = findViewById(R.id.textViewPasswordReset);
        progressDialog = new ProgressDialog(this);
        //attaching click listener
        buttonSignIn.setOnClickListener(this);
        textViewSignup.setOnClickListener(this);
        textViewPasswordReset.setOnClickListener(this);

    }




    //method for user login
    private void userLogin(){
        String email = editTextEmail.getText().toString().trim();
        String password  = editTextPassword.getText().toString().trim();



        //checking if email and passwords are empty
        if(TextUtils.isEmpty(email)){
            Toast.makeText(this,"Please enter email",Toast.LENGTH_LONG).show();
            return;
        }

        if(TextUtils.isEmpty(password)){
            Toast.makeText(this,"Please enter password",Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Connection... ");
        progressDialog.show();

        //logging in the user
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        //if the task is successfull
                        if(task.isSuccessful()){
                            //start the profile activity
                            //finish();
                            startActivity(new Intent(SignInActivity.this, ProfileActivity.class));
                        }
                        else {
                          //  Toast.makeText(SignInActivity.this,"Authentification failed",Toast.LENGTH_LONG).show();
//                            Toast toast = new Toast(SignInActivity.this);
//                            toast.setGravity(Gravity.TOP|Gravity.LEFT,0,0);
//                            toast.setText("Authentication failed.");
//                            toast.setDuration(Toast.LENGTH_LONG);
//                            toast.show();
                            Toast toast = Toast.makeText(SignInActivity.this,"Authentification failed", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.TOP, 0, 0);
                            toast.show();
                        }
                    }
                });

    }


    @Override
    public void onClick(View view) {
        if(view == buttonSignIn){
            userLogin();

        }

        if(view == textViewSignup){
            //finish();
            startActivity(new Intent(this, SignUpActivity.class));
        }

        if(view == textViewPasswordReset){
            //finish();
            startActivity(new Intent(this, PasswordReset.class));
        }
    }

}