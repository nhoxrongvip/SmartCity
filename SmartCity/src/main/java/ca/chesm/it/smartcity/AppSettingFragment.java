package ca.chesm.it.smartcity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;


public class AppSettingFragment extends Fragment {


    private int STORAGE_PERMISSION_CODE = 1;
    private View v;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        v = inflater.inflate(R.layout.fragment_settings, container, false);
        Button btn = v.findViewById(R.id.bntpremission);
        btn.setOnClickListener(view ->
        {
            if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                Snackbar.make(getContext(),getView(),"Permission Granted", Snackbar.LENGTH_SHORT).show();
            }

            else
            {
                if(ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION))
                {
                    Snackbar.make(getContext(),getView(),"Permission Denied", Snackbar.LENGTH_SHORT).show();
                }
                else {
                    ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.ACCESS_FINE_LOCATION},STORAGE_PERMISSION_CODE);
                }
            }
        });
        return v;
    }
}