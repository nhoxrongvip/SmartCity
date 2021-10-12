/*
    Name:Dung Ly N01327929
    Course: CENG322-RND
    Purpose: Control Login for user with firebase
    Last updated:  09 Oct 2021
*/
package ca.chesm.it.smartcity.Userscontrol;

import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import ca.chesm.it.smartcity.MainActivity;
import ca.chesm.it.smartcity.R;

public class LoginActivity extends AppCompatActivity
{

    private Button btnlog, btnreg;
    private EditText etxtedituser, etxteditpass;
    private FirebaseAuth mAuth;
    private SharedPreferences sharedPreferences;
    private boolean sw;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = this.getSharedPreferences("Switch", Context.MODE_PRIVATE);
        sw = sharedPreferences.getBoolean("Switch",false);
        if (sw == false)
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        } else
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        mAuth = FirebaseAuth.getInstance();
        res();
        btnlog.setOnClickListener(view ->
        {
            login();

        });
        btnreg.setOnClickListener(view ->
        {
            register();
        });


    }

    private void res()
    {
        etxtedituser = (EditText) findViewById(R.id.txteditusername);
        etxteditpass = (EditText) findViewById(R.id.txteditpassword);
        btnlog = (Button) findViewById(R.id.bntlogin);
        btnreg = (Button) findViewById(R.id.bntregister);
    }

    private void login()
    {
        String email, pass;
        email = etxtedituser.getText().toString();
        pass = etxteditpass.getText().toString();
        if (email.isEmpty() && pass.isEmpty())
        {
            etxtedituser.setError("This email can not be blank");
            etxteditpass.setError("This password can not be blank");
        }
        if (email.isEmpty())
        {
            etxtedituser.setError("This email can not be blank");
        }
        if (pass.isEmpty())
        {
            etxteditpass.setError("This password can not be blank");
        } else
        {
            if(!isEmailValid(email))
            {
                etxtedituser.setError("This look like an incorrect address");
                return;
            }
            mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>()
            {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task)
                {
                    if (task.isSuccessful())
                    {
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else
                    {
                        AlertDialog.Builder dialogb = Dialogb(task.getException().getMessage().toString());
                        dialogb.show();

                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                        {
                            NotificationChannel channel = new NotificationChannel("Login","Login",NotificationManager.IMPORTANCE_DEFAULT);
                            NotificationManager manager = getSystemService(NotificationManager.class);
                            manager.createNotificationChannel(channel);
                        }
                        NotificationCompat.Builder builder = Notibuild(task.getException().getMessage().toString());

                        NotificationManagerCompat managercompat = NotificationManagerCompat.from(LoginActivity.this);
                        managercompat.notify(1,builder.build());
                    }
                }
            });
        }
    }

    //Build Notication
    private NotificationCompat.Builder Notibuild(String mess)
    {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel("Login","Login",NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(LoginActivity.this,"Login");
        builder.setContentTitle("SmartCity");
        builder.setContentText(mess);
        builder.setSmallIcon(R.mipmap.ic_launcher_foreground);
        builder.setAutoCancel(true);

        return builder;
    }

    //Build Dialog
    private AlertDialog.Builder Dialogb (String mess)
    {
        AlertDialog.Builder builderd = new AlertDialog.Builder(LoginActivity.this);
        builderd.setCancelable(true);
        builderd.setMessage(mess);
        builderd.setNegativeButton("Cancel", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialogInterface, int i)
            {
                dialogInterface.cancel();
            }
        });
        return builderd;
    }

    //use to check email valid or not
    private static boolean isEmailValid(String email)
    {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    //Function to load intent register
    private void register()
    {
        Intent intent1 = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent1);
    }
}