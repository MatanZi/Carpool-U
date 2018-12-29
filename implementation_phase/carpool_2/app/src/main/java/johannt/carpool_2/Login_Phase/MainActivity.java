package johannt.carpool_2.Login_Phase;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import johannt.carpool_2.Profile_Features.ProfileActivity;
import johannt.carpool_2.R;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MenuItem Useritem = findViewById(R.id.NameAccount);
        //initializing firebase authentication object
        firebaseAuth = FirebaseAuth.getInstance();
        //getting current user
        final FirebaseUser user = firebaseAuth.getCurrentUser();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (user == null) {
                    //closing this activity and opening signin activity.
                    startActivity(new Intent(MainActivity.this, SignInActivity.class));
                } else {
                    startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                }
                MainActivity.this.finish();
            }
        }, 1500);
    }
}



//    @SuppressWarnings("StatementWithEmptyBody")
//    @Override
//    public boolean onNavigationItemSelected(MenuItem item) {
//        // Handle navigation view item clicks here.
//        int id = item.getItemId();
//
//        if (id == R.id.find_carpool) {
//            startActivity(new Intent(this, FindRideActivity.class));
//
//        } else if (id == R.id.post_carpool) {
//            startActivity(new Intent(this, PublishActivity.class));
//
//        } else if (id == R.id.results) {
//        //    startActivity(new Intent(this, SignInActivity.class));
//
//        } else if (id == R.id.posts) {
//           startActivity(new Intent(this, PublishActivity.class));
//
//        } else if (id == R.id.profile) {
//            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
//
//        } else if (id == R.id.signOut) {
//            //logging out the user
//            firebaseAuth.signOut();
//        Intent signin = new Intent(MainActivity.this, SignInActivity.class);
//        startActivity(signin);
//    }
//
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }
