package ca.chesm.it.smartcity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startActivity(new Intent(SplashActivity.this,LoginActivity.class));

        //Wait at splash screen
        //Bad Method. Figure out next time
        Thread bg = new Thread();
        try {
            bg.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // close splash activity
        finish();
    }

}