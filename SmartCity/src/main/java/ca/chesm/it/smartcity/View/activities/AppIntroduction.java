package ca.chesm.it.smartcity.View.activities;

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
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;

import com.github.appintro.AppIntro;
import com.github.appintro.AppIntroFragment;
import com.github.appintro.AppIntroPageTransformerType;

import ca.chesm.it.smartcity.R;
import ca.chesm.it.smartcity.View.accounts.LoginActivity;

public class AppIntroduction extends AppIntro {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Add slides
        addSlide(AppIntroFragment.newInstance(
                "Welcome to Smart City",
                "Thanks for choosing Smart City\nLet's see what we have",
                R.drawable.applogo,
                getResources().getColor(R.color.teal_200),
                getColor(R.color.IntroTitleBlue)

        ));

        addSlide(AppIntroFragment.newInstance(
                "Contributor",
                "This is a product of 4 dudes above, detail information will be inside app",
                R.drawable.intro_image,
                getResources().getColor(R.color.teal_200),
                getColor(R.color.IntroTitleBlue)

        ));
        addSlide(AppIntroFragment.newInstance(
                "Request Permission",
                "This app requires a location permission for functionalities. Click this to provide permission"
        ));
        askForPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},3,true);

        //Set transformer type
        setTransformer(AppIntroPageTransformerType.Fade.INSTANCE);
        showStatusBar(true);
        setSkipButtonEnabled(false);
        isIndicatorEnabled();
        setProgressIndicator();





        sharedPreferences = getApplicationContext().getSharedPreferences("Firsttime", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        if(sharedPreferences != null){
            boolean firstTime = sharedPreferences.getBoolean("checkFirstTime",false);
            if(firstTime) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        }



    }

    @Override
    protected void onSkipPressed(@Nullable Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        editor.putBoolean("checkFirstTime",false).commit();
        finish();
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