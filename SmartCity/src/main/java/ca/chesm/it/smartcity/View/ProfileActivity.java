package ca.chesm.it.smartcity.View;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import ca.chesm.it.smartcity.R;
import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private Toolbar toolbar;
    private TextView txtusername;
    private TextView txtphonenumber;
    private  TextView txtemail;
    private CircleImageView cImage;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private  String base64 ="";

// 1
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        toolbar = findViewById(R.id.toolbar);
        txtphonenumber = findViewById(R.id.txtphonumber);
        txtemail = findViewById(R.id.txtemail);
        txtusername = findViewById(R.id.txtusername);
        cImage = findViewById(R.id.img_profile);


        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

         sharedPreferences = getSharedPreferences("INFO",MODE_PRIVATE);
         editor = sharedPreferences.edit();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser =  firebaseAuth.getCurrentUser();
        txtemail.setText(firebaseUser.getEmail());
        if(firebaseUser.getPhoneNumber()!=null){
            txtphonenumber.setText(firebaseUser.getPhoneNumber());
        }else{

        }
        String phone = sharedPreferences.getString(firebaseUser.getUid()+"phone","");
        txtphonenumber.setText(phone);
        if(firebaseUser.getDisplayName() !=null){
            txtusername.setText(firebaseUser.getDisplayName());
        }
        String basecode = sharedPreferences.getString(firebaseUser.getUid(),"");
        if(basecode.length() >20){
           byte[] decodeImge = Base64.decode(basecode,Base64.NO_WRAP);
           Bitmap bitmap = BitmapFactory.decodeByteArray(decodeImge,0,decodeImge.length);
           cImage.setImageBitmap(bitmap);
        }else{
            if(firebaseUser.getPhotoUrl().toString().length() >20){
                basecode = firebaseUser.getPhotoUrl().toString();
                byte[] decodeImge = Base64.decode(basecode,Base64.NO_WRAP);
                Bitmap bitmap = BitmapFactory.decodeByteArray(decodeImge,0,decodeImge.length);
                cImage.setImageBitmap(bitmap);
            }
        }
        cImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},123);
                }else{
                    PickGallary();
                }
            }
        });
        txtusername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDiaLogUpdate(1);
            }
        });
        txtphonenumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowDiaLogUpdate(2);
            }
        });





    }

    private void ShowDiaLogUpdate(int i) {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_update);
        EditText editUpdate = dialog.findViewById(R.id.editUpdate);
        dialog.show();
        switch (i){
            case 1:editUpdate.setText(txtusername.getText().toString().trim()+"");break;
            case 2:editUpdate.setText(txtphonenumber.getText().toString().trim()+"");break;


        }
        dialog.findViewById(R.id.btnupdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(i==1){
                    String name = editUpdate.getText().toString().trim();
                    UserProfileChangeRequest userProfileChangeRequest = new UserProfileChangeRequest.Builder()
                            .setDisplayName(name)
                            .setPhotoUri(Uri.parse(base64))
                            .build();
                    firebaseUser.updateProfile(userProfileChangeRequest);
                    dialog.cancel();
                    txtusername.setText(name);

                }else{
                    editUpdate.setInputType(InputType.TYPE_NUMBER_FLAG_DECIMAL);
                    String phone = editUpdate.getText().toString().trim();
                   editor.putString(firebaseUser.getUid()+"phone","+1"+phone);
                    editor.commit();
                    txtphonenumber.setText(phone);
                   dialog.cancel();
                }
            }
        });
    }

    private void PickGallary() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,123);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

         if(requestCode == 123 && resultCode == RESULT_OK ){
             Uri uri = data.getData();

             try {
                 InputStream inputStream = getContentResolver().openInputStream(uri);
                 Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                  cImage.setImageBitmap(bitmap);
                 ByteArrayOutputStream  baos = new ByteArrayOutputStream();
                  if(bitmap.getHeight()>1400){
                      bitmap = Bitmap.createScaledBitmap(bitmap,bitmap.getWidth()/4,bitmap.getHeight()/4,true);
                  }else{
                      bitmap = Bitmap.createScaledBitmap(bitmap,bitmap.getWidth()/2,bitmap.getHeight()/2,true);
                  }
                 bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
                 byte[] byteArray =  baos.toByteArray();
                 String encdoe  = Base64.encodeToString(byteArray,Base64.DEFAULT);
                 editor.putString(firebaseUser.getUid(),encdoe);
                 editor.commit();
             } catch (FileNotFoundException e) {
                 e.printStackTrace();
             }

         }

        super.onActivityResult(requestCode, resultCode, data);
    }
}
