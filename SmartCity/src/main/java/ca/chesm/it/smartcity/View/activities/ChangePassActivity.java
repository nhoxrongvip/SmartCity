package ca.chesm.it.smartcity.View.activities;

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
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import ca.chesm.it.smartcity.R;
import ca.chesm.it.smartcity.View.accounts.LoginActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class ChangePassActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private Toolbar toolbar;
    private EditText editPass,editPassnew;

    private  TextView txtemail;
    private CircleImageView cImage;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private  String base64 ="";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        toolbar = findViewById(R.id.toolbar);
        editPass = findViewById(R.id.idpassnew);
        editPassnew = findViewById(R.id.idpassnew1);



        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


       findViewById(R.id.btnchangepass).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String pass= editPass.getText().toString().trim();
               String pass1= editPassnew.getText().toString().trim();
               if(pass.length()>=6){
                   if(pass.equalsIgnoreCase(pass1)){

                       firebaseUser.updatePassword(pass).addOnSuccessListener(new OnSuccessListener<Void>() {
                           @Override
                           public void onSuccess(Void unused) {
                               startActivity(new Intent( ChangePassActivity.this, LoginActivity.class));
                           }
                       });

                   }else{
                       Toast.makeText(ChangePassActivity.this, "Pass no same", Toast.LENGTH_SHORT).show();
                   }
               }else{
                   Toast.makeText(ChangePassActivity.this, "pass > 6", Toast.LENGTH_SHORT).show();
               }
           }
       });



    }






}
