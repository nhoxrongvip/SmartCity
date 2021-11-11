/*
    Name:Thanh Phat Lam N01335598
    Course: CENG322-RND
    Purpose: Report Air Quality of the selected area
    Last updated: Sep 27 2021
*/


package ca.chesm.it.smartcity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.google.android.material.snackbar.Snackbar;

import de.hdodenhof.circleimageview.CircleImageView;


public class AirQualityFragment extends Fragment {

    View v;

    //AQI report components
    LinearLayout aqi_layout;
    TextView location, aqi_quality, aqi_condition;
    CircleImageView aqi_conditionemotion;

    //Pollutant components
    TextView pollutants_pm25, pollutants_co2, pollutants_o3;

    //Daily graph components
    RadioButton rb_aqi, rb_co, rb_co2, rb_o3, rb_pm25, rb_so2, rb_no2;
    BarChart dailygraph;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_air_quality, container, false);
        getID();

        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public void getID(){
        location = v.findViewById(R.id.AQ_currentlocation);
        aqi_quality = v.findViewById(R.id.AQ_quality);
        aqi_condition = v.findViewById(R.id.AQ_condition);
        aqi_conditionemotion = v.findViewById(R.id.AQ_conditionemotion);
        aqi_layout = v.findViewById(R.id.aqilayout);

        pollutants_pm25 = v.findViewById(R.id.AQ_pm25);
        pollutants_co2 = v.findViewById(R.id.AQ_co2);
        pollutants_o3 = v.findViewById(R.id.AQ_o3);

        rb_aqi = v.findViewById(R.id.rb_aqi);
        rb_co = v.findViewById(R.id.rb_co);
        rb_co2 = v.findViewById(R.id.rb_co2);
        rb_o3 = v.findViewById(R.id.rb_o3);
        rb_pm25 = v.findViewById(R.id.rb_pm25);
        rb_so2 = v.findViewById(R.id.rb_so2);
        rb_no2 = v.findViewById(R.id.rb_no2);
        dailygraph = v.findViewById(R.id.AQ_dailygraph);


    }

}