package ca.chesm.it.smartcity.View.activities;

import androidx.annotation.ColorInt;
import androidx.annotation.DrawableRes;
import androidx.annotation.FontRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.github.appintro.AppIntro;
import com.github.appintro.AppIntro2;
import com.github.appintro.AppIntroFragment;
import com.github.appintro.AppIntroPageTransformerType;

import ca.chesm.it.smartcity.R;
import ca.chesm.it.smartcity.View.accounts.LoginActivity;

public class AppIntroduction extends AppIntro2 {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;



    private void addIntroSlide(CharSequence title, CharSequence description, @DrawableRes int image){
        addSlide(AppIntroFragment.newInstance(
                title,
                description,
                image,
                getResources().getColor(R.color.white),
                getColor(R.color.IntroTitleBlue),
                getColor(R.color.black),
                0,
                0,
                R.drawable.intro_bg
        ));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setTransformer(AppIntroPageTransformerType.Fade.INSTANCE);
        setSkipButtonEnabled(false);
        addIntroSlide("Welcome to Smart City","Thanks for choosing this app\nThere is more fun inside",R.drawable.applogo);
        addIntroSlide("Contributors","This is a product of 4 dudes above, detail information will be inside app ",R.drawable.intro_image);
        addIntroSlide("Request Permission","This app requires a location permission for functionalities. Click this to provide permission",R.drawable.location_icon);

        askForPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},3,true);
        addIntroSlide("Get Started","Great!! Now go and explore the app\n",R.drawable.letsgo);

        //Set transformer type




        setProgressIndicator();


        sharedPreferences = getApplicationContext().getSharedPreferences("Firsttime", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        if(sharedPreferences != null){
            boolean firstTime = sharedPreferences.getBoolean("checkFirstTime",false);
            if(firstTime) {
                //startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        }



    }





    @Override
    protected void onDonePressed(@Nullable Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        editor.putBoolean("checkFirstTime",true).commit();

        finish();
    }


    @Override
    protected void onUserDeniedPermission(@NonNull String permissionName) {
        super.onUserDeniedPermission(permissionName);
        new AlertDialog.Builder(this)
                .setTitle("Permission is denied")
                .setCancelable(false)
                .setMessage("Uh oh! I need permission to run. Please allow me")
                .setPositiveButton("Click to allow Permission", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        askForPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},3,true);

                    }
                }).create().show();
    }

    //If user disable the permission, it goes to Settings screen
    @Override
    protected void onUserDisabledPermission(@NonNull String permissionName) {
        super.onUserDisabledPermission(permissionName);
        new AlertDialog.Builder(this)
                .setTitle("Permission is disabled")
                .setCancelable(false)
                .setMessage("Sorry, you disable the location permission. Press the button below to grant us permission")
                .setNegativeButton("Click to allow permission", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       Intent i = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.fromParts("package",getPackageName(),null));
                       i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                       startActivity(i);
                    }
                }).create().show();
    }
}