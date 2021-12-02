package ca.chesm.it.smartcity.View.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.github.appintro.AppIntro;
import com.github.appintro.AppIntroFragment;

import ca.chesm.it.smartcity.R;
import ca.chesm.it.smartcity.View.accounts.LoginActivity;

public class AppIntroduction extends AppIntro {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
                .setCancelable(true)
                .setMessage("Uh oh! I need permission to run. Please allow me")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        askForPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},3);
                    }
                }).create().show();
    }

    @Override
    protected void onUserDisabledPermission(@NonNull String permissionName) {
        super.onUserDisabledPermission(permissionName);
        new AlertDialog.Builder(this)
                .setTitle("Permission is disabled")
                .setCancelable(true)
                .setMessage("Uh oh! I need permission to run. Please allow me")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        askForPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},3);
                    }
                }).create().show();
    }
}