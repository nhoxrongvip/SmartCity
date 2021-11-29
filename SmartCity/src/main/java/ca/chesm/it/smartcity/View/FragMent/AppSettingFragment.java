package ca.chesm.it.smartcity.View.FragMent;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import ca.chesm.it.smartcity.R;
import ca.chesm.it.smartcity.View.ReviewFragControl.ReviewFragment;
import ca.chesm.it.smartcity.View.activities.MainActivity;

public class AppSettingFragment extends Fragment
{

    private static final int REQUEST_LOCATION = 1;
    SharedPreferences sharedPreferences;
    SwitchCompat swpotrait;
    Button btnhelp,btnPermission;

    View v;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        v = inflater.inflate(R.layout.fragment_settings, container, false);
        setupid();
        sharedPreferences = this.getActivity().getSharedPreferences("SmartCity", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        boolean orisw = sharedPreferences.getBoolean("Switch", false);
        btnhelp = (Button) v.findViewById(R.id.helpandfeedbackBtn);
        if (!orisw)
        {
            swpotrait.setChecked(false);
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        } else
        {
            swpotrait.setChecked(true);
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        swpotrait.setOnCheckedChangeListener((buttonView, isChecked) ->
                {
                    if (isChecked)
                    {
                        editor.putBoolean("Switch",true);
                        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        editor.apply();

                        Snackbar portraitsb = Snackbar.make(v,"Portrait lock: ON",Snackbar.LENGTH_SHORT);
                        portraitsb.show();



                    }
                    else
                    {
                        editor.putBoolean("Switch",false);
                        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                        editor.apply();
                        Snackbar portraitsb = Snackbar.make(v,"Portrait lock: OFF",Snackbar.LENGTH_SHORT);
                        portraitsb.show();
                    }
                }
        );
        btnhelp.setOnClickListener(view ->
        {
            Fragment fragment = new ReviewFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container,fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        });





        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void setupid()
    {
        swpotrait = (SwitchCompat) v.findViewById(R.id.portraitlocker);
        btnPermission = (Button) v.findViewById(R.id.btnPermission);
    }




}