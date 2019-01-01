package johannt.carpool_2.Login_Phase;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import johannt.carpool_2.R;

public class PasswordReset extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail;
    private Button PasswordResetBtn;
    private String email;

    //firebase auth object
    private FirebaseAuth firebaseAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        editTextEmail = findViewById(R.id.editTextEmail);
        user = firebaseAuth.getCurrentUser();

        email = editTextEmail.getText().toString().trim();
        PasswordResetBtn = findViewById(R.id.PasswordResetBtn);
        PasswordResetBtn.setOnClickListener(this);
    }


    private void passwordReset(){
            FirebaseAuth.getInstance().sendPasswordResetEmail(firebaseAuth.getCurrentUser().getEmail())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast toast = Toast.makeText(PasswordReset.this,"Mail sent , please check your email inbox", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.TOP, 0, 0);
                                toast.show();
                                return;
                            }
                        }
                    });

/**            user.sendEmailVerification()
                    .addOnCompleteListener(this, new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            // Re-enable button
                            findViewById(R.id.verify_email_button).setEnabled(true);

                            if (task.isSuccessful()) {
                                Toast.makeText(EmailPasswordActivity.this,
                                        "Verification email sent to " + user.getEmail(),
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e("STATE", "sendEmailVerification", task.getException());
                                Toast.makeText(EmailPasswordActivity.this,
                                        "Failed to send verification email.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    });**/
        }
           // Toast.makeText(PasswordReset.this,"Email not found", Toast.LENGTH_SHORT).show();



    @Override
    public void onClick(View view) {
        if (view == PasswordResetBtn) {
            passwordReset();
        }
    }
}
