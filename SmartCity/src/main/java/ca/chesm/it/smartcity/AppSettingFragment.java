package ca.chesm.it.smartcity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;

import com.google.android.material.snackbar.Snackbar;

import ca.chesm.it.smartcity.ReviewFragControl.ReviewFragment;

public class AppSettingFragment extends Fragment
{

    SharedPreferences sharedPreferences;
    SwitchCompat swpotrait;
    Button btnhelp;
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


    public void setupid()
    {
        swpotrait = (SwitchCompat) v.findViewById(R.id.portraitlocker);
    }
}