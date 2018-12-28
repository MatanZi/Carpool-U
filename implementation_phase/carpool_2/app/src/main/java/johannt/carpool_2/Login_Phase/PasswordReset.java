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

import johannt.carpool_2.R;

public class PasswordReset extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail;
    private Button PasswordResetBtn;
    private String email;

    //firebase auth object
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        editTextEmail = findViewById(R.id.editTextEmail);
        email = editTextEmail.getText().toString().trim();
        PasswordResetBtn = findViewById(R.id.PasswordResetBtn);
        PasswordResetBtn.setOnClickListener(this);
    }


    private void passwordReset(){
        if(firebaseAuth.getCurrentUser() != null){
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
        }
        else{
            FirebaseAuth.getInstance().sendPasswordResetEmail(email)
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
        }

    }

    @Override
    public void onClick(View view) {
        if (view == PasswordResetBtn) {
            passwordReset();
        }
    }
}
