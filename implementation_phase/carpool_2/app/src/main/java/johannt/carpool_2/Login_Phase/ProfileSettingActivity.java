package johannt.carpool_2.Login_Phase;


import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import johannt.carpool_2.Profile_Features.ProfileActivity;
import johannt.carpool_2.R;
import johannt.carpool_2.Rides_And_Validator.Validator;
import johannt.carpool_2.Users.User;

import static android.graphics.BitmapFactory.decodeFile;
import static johannt.carpool_2.Profile_Features.ProfileActivity.firstName;

public class ProfileSettingActivity extends AppCompatActivity implements View.OnClickListener{



    private String id;
    final private int PICK_IMAGE = 2;
    final private int CAPTURE_IMAGE = 1;
    private Boolean checker;
    //defining view objects
    private EditText editTextFirstName;
    private EditText editTextLastName;
    private EditText editTextPhoneNumber;
    private Spinner spinnerCity;
    private Spinner spinnerUniversity;
    private Button buttonSave;
    private ImageButton uploadPicProfile;
    private ProgressDialog progressDialog;
    private ImageView profilePict ;


    //defining firebaseauth object
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseUsersRef;
    private FirebaseDatabase databaseCarPool;
    private FirebaseUser firebaseUser;
    private User user;
    private Validator validator;
    private File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_setting);

        //initializing firebase auth object
        firebaseAuth = FirebaseAuth.getInstance();
        //Carpool = new Firebase("https://carpool-u.firebaseio.com/");


        //initializing views
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        spinnerCity = findViewById(R.id.spinnerCity);
        profilePict = (ImageView) findViewById(R.id.imageViewProfile);
        spinnerUniversity = findViewById(R.id.spinnerUniversity);
        uploadPicProfile = findViewById(R.id.uploadPicProfileButton);
        buttonSave = findViewById(R.id.buttonSave);


        //attaching listener to button

        progressDialog = new ProgressDialog(this);
        buttonSave.setOnClickListener(this);
        uploadPicProfile.setOnClickListener(this);

    }

    private void showPictureSettingDialog() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.dialog_set_profile_picture, null);
        dialogBuilder.setView(dialogView);

        final ImageButton btnTakePicure = dialogView.findViewById(R.id.takePictureButton);
        final ImageButton btnImportPicture = dialogView.findViewById(R.id.importPictureButton);


        dialogBuilder.setTitle("Profile picture Setting");
        final AlertDialog b = dialogBuilder.create();
        b.show();

        btnTakePicure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               takePicture();
                b.dismiss();

            }
        });

        btnImportPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               importPicture();
                b.dismiss();
            }
        });
    }

//    /**
//     * This method upload the image from our database.
//     */
//    private void updateImageProfil() {
//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        StorageReference storageReference = storage.getReference();
//        StorageReference ref = storageReference.child("images/" + email.getMail() + "/profile");
//        profilePict.setDrawingCacheEnabled(true);
//        profilePict.buildDrawingCache();
//        Bitmap bitmap = profilePict.getDrawingCache();
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        byte[] data = baos.toByteArray();
//        ref.putBytes(data);
//        Log.e("trying to upload image", "success");
//        FireBaseQuery.updateUserPictureUri();
//
//    }


    private void takePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent,CAPTURE_IMAGE );
        }
    }

    private void importPicture(){

        // invoke the image gallery using an implict intent.
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);

        // where do we want to find the data?
        File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String pictureDirectoryPath = pictureDirectory.getPath();
        // finally, get a URI representation
        Uri data = Uri.parse(pictureDirectoryPath);

        // set the data and type.  Get all image types.
        photoPickerIntent.setDataAndType(data, "image/*");

        // we will invoke this activity, and get something back from it.
        startActivityForResult(photoPickerIntent, PICK_IMAGE);
    }

@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
    if(resultCode == RESULT_OK) {
             switch (requestCode) {
                 case PICK_IMAGE:

                     Uri imageUri = imageReturnedIntent.getData();

                     InputStream inputStream;

                     try {
                         inputStream = getContentResolver().openInputStream(imageUri);

                         Bitmap image = BitmapFactory.decodeStream(inputStream);
                         // show the image to the user
                         profilePict.setImageBitmap(image);

                     } catch (FileNotFoundException e) {
                         e.printStackTrace();
                         // show a message to the user indictating that the image is unavailable.
                         Toast.makeText(this, "Unable to open image", Toast.LENGTH_LONG).show();
                     }
                //    uploadImage();
                break;
                 case CAPTURE_IMAGE:
                Bitmap selectedImage = (Bitmap) imageReturnedIntent.getExtras().get("data");
                profilePict.setImageBitmap(selectedImage);
                //  uploadImage();
                break;
            }
        }
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

    public void setCurrentsValues(){
        editTextFirstName.setText(firstName);
        editTextLastName.setText(ProfileActivity.lastName);
        editTextPhoneNumber.setText(ProfileActivity.phoneNumber);
        spinnerCity.setSelection(getIndex(spinnerCity, ProfileActivity.city));
        spinnerUniversity.setSelection(getIndex(spinnerUniversity, ProfileActivity.university));
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        databaseUsersRef = FirebaseDatabase.getInstance().getReference("Users");
        firebaseUser = firebaseAuth.getCurrentUser();
        id = firebaseUser.getUid();
        //updateUI(currentUser);
        setCurrentsValues();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setCurrentsValues();
    }


    /**
     *
     * @param firstname
     * @param lastname
     * @param phoneNumber
     * @return
     */
    private boolean updateUser(String firstname, String lastname, String phoneNumber, String city, String university) {
        //getting the specified user reference
        //DatabaseReference dR = FirebaseDatabase.getInstance().getReference("user").child(id);

        try {
            databaseUsersRef.child(id).child("firstName").setValue(firstname);
            databaseUsersRef.child(id).child("lastName").setValue(lastname);
            databaseUsersRef.child(id).child("phoneNumber").setValue(phoneNumber);
            databaseUsersRef.child(id).child("city").setValue(city);
            databaseUsersRef.child(id).child("university").setValue(university);

        } catch (Exception e) {
            e.printStackTrace();
        }

        progressDialog.dismiss();
        Toast.makeText(this, "Profile Updated", Toast.LENGTH_LONG).show();
        return true;
    }


    @Override
    public void onClick(View v) {

        if (v == uploadPicProfile){
            showPictureSettingDialog();
        }

        if (v == buttonSave) {



            String firstname = editTextFirstName.getText().toString();
            String lastname = editTextLastName.getText().toString();
            String phoneNumber = editTextPhoneNumber.getText().toString();
            String city = spinnerCity.getSelectedItem().toString();
            String university = spinnerUniversity.getSelectedItem().toString();

            validator = new Validator();

            checker = validator.checkFirstName(firstname, this) &&
                    validator.checkLastName(lastname, this) &&
                    validator.checkPhonenumber(phoneNumber, this) &&
                    validator.checkSrc(city, this) &&
                    validator.checkdst(university, this);

            if(checker){
                progressDialog.setMessage("Updating...");
                progressDialog.show();
                updateUser(firstname,lastname,phoneNumber,city,university);

                startActivity(new Intent(this, ProfileActivity.class));

                finish();
            }

        }
    }
}
