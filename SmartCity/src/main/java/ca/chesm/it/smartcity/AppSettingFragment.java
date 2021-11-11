package ca.chesm.it.smartcity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

public class AppSettingFragment extends Fragment
{

    SharedPreferences sharedPreferences;
    SwitchCompat swpotrait;
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
                    }
                    else
                    {
                        editor.putBoolean("Switch",false);
                        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                        editor.apply();
                    }
                }
        );

        return v;
    }


    public void setupid()
    {
        swpotrait = (SwitchCompat) v.findViewById(R.id.portraitlocker);
    }
}