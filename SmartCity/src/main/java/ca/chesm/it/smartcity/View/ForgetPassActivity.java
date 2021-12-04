package ca.chesm.it.smartcity.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

import ca.chesm.it.smartcity.R;
import ca.chesm.it.smartcity.View.accounts.LoginActivity;
import de.hdodenhof.circleimageview.CircleImageView;

public class ForgetPassActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;
    private Toolbar toolbar;
    private EditText editPass,editPassnew;

    private  EditText editEmail;
    private CircleImageView cImage;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private  String base64 ="";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpass);
        toolbar = findViewById(R.id.toolbar);
        editEmail = findViewById(R.id.editEmail);



        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Forget password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();


       findViewById(R.id.btnsearch).setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String email= editEmail.getText().toString().trim();

               if(email.length()>=6){
                 firebaseAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                     @Override
                     public void onSuccess(Void unused) {
                         Toast.makeText(ForgetPassActivity.this, "Please go to email !", Toast.LENGTH_SHORT).show();
                     }
                 });

               }else{
                   Toast.makeText(ForgetPassActivity.this, "Email > 6", Toast.LENGTH_SHORT).show();
               }
           }
       });



    }






}
