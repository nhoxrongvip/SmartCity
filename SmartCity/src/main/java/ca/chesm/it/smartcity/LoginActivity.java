package ca.chesm.it.smartcity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity
{
    Button btn;
    EditText txtedituser,txteditpass;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        resg();

        btn.setOnClickListener(view ->
        {
            String username,password;
            username = txtedituser.getText().toString();
            password = txteditpass.getText().toString();
            if(username.equals("admin") && password.equals("admin"))
            {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }
            else
            {

            }
        });
    }

    public void resg()
    {
        txtedituser = (EditText) findViewById(R.id.txteditusername);
        txteditpass = (EditText) findViewById(R.id.txteditpassword);
        btn = (Button) findViewById(R.id.bntlogin);
    }
}