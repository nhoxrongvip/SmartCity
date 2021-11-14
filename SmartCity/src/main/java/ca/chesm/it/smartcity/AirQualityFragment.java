/*
    Name:Thanh Phat Lam N01335598
    Course: CENG322-RND
    Purpose: Report Air Quality of the selected area
    Last updated: Sep 27 2021
*/


package ca.chesm.it.smartcity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

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

    //API value
    String aqivalue,pm25value,covalue,o3value;
    final String TOKEN = "335c6bfed754d30c7a80d76cd33f15a76c0f15c1";
    private String city_name = "toronto";
    private String url = "https://api.waqi.info/feed/"+city_name+"/?token="+TOKEN;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_air_quality, container, false);
        getID();
        getAQIdata();


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




    private String readJSON(String address){
        URL url = null;
        StringBuilder sb = new StringBuilder();
        HttpsURLConnection urlConnection = null;
        try {
            //Get URL
            url = new URL(address);
        }catch (MalformedURLException e) {
            e.printStackTrace();
        }

        try {
            //Open URL
            urlConnection = (HttpsURLConnection) url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            InputStream content = new BufferedInputStream(urlConnection.getInputStream());

            BufferedReader reader = new BufferedReader(new InputStreamReader(content));
            String line;
            while((line = reader.readLine()) != null){
                sb.append(line);
            }
        } catch (IOException e) {
            //Catch invalid zip code
            e.printStackTrace();
        }finally {
            urlConnection.disconnect();
        }
        return sb.toString();

    }
    private void getAQIdata(){
        new ReadJSONFeed().execute(url);
    }

    private class ReadJSONFeed extends AsyncTask<String,Void,String> {


        @Override
        protected String doInBackground(String... urls) {
            return readJSON(urls[0]);
        }


        @Override
        protected void onPostExecute(String result){
           getData(result);


            aqi_quality.setText(aqivalue);
            pollutants_pm25.setText(pm25value);
            pollutants_co2.setText(covalue);
            pollutants_o3.setText(o3value);

            String aqi_result = aqiConditionCheck();
            switch (aqi_result) {
                case "Good":
                    aqi_conditionemotion.setImageDrawable(getResources().getDrawable(R.drawable.aq_happy));
                    aqi_condition.setText(aqi_result);
                    aqi_layout.setBackgroundColor(getResources().getColor(R.color.aqi_good));
                    break;
                case "Moderate":
                    aqi_conditionemotion.setImageDrawable(getResources().getDrawable(R.drawable.aq_worry));
                    aqi_condition.setText(aqi_result);
                    aqi_layout.setBackgroundColor(getResources().getColor(R.color.aqi_normal));
                    break;
                case "Unhealthy":
                    aqi_condition.setText(aqi_result);
                    aqi_conditionemotion.setImageDrawable(getResources().getDrawable(R.drawable.aq_angry));
                    aqi_layout.setBackgroundColor(getResources().getColor(R.color.aqi_bad));
                    break;
            }


        }

        public String aqiConditionCheck(){
            try {
                int value = Integer.parseInt(aqivalue);
                if(value <= 50){
                    return "Good";
                }
                else if(value >=51 && value <=100){
                    return "Moderate";
                }
                else if (value > 100) {
                    return "Unhealthy";
                }

            }catch (Exception e) {
                e.printStackTrace();
            }
            return "Not in range";

        }

        private void getData(String result){
            try {
                JSONObject qualityObject = new JSONObject(result);
                //Get AQI
                JSONObject dataObject = qualityObject.getJSONObject("data");
                aqivalue = dataObject.getString("aqi");
                pm25value = dataObject.getJSONObject("iaqi").getJSONObject("pm25").getString("v");
                covalue = dataObject.getJSONObject("iaqi").getJSONObject("co").getString("v");
                o3value = dataObject.getJSONObject("iaqi").getJSONObject("o3").getString("v");




            } catch (JSONException e) {
                e.printStackTrace();
            }

        }







    }



}