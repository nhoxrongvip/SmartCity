//If you want to change animation, modify in activity_splash.xml

package ca.chesm.it.smartcity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.View;

import com.airbnb.lottie.LottieAnimationView;

public class SplashActivity extends AppCompatActivity {

    LottieAnimationView logo_animation;
    final long DELAY = 2000;
    final long DURATION = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        //Splash Screen animation
        logo_animation = findViewById(R.id.splash);
        logo_animation.animate().translationY(-1600).setDuration(DURATION).setStartDelay(DELAY);

        //change Activity after animation
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run()
            {
                startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            }
        },DELAY);






    }


}