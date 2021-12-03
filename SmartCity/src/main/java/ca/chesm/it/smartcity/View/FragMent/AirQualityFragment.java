/*
    Name:Thanh Phat Lam N01335598
    Course: CENG322-RND
    Purpose: Report Air Quality of the selected area
    Last updated: Nov 14 2021
*/


package ca.chesm.it.smartcity.View.FragMent;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

import ca.chesm.it.smartcity.R;
import ca.chesm.it.smartcity.View.activities.MainActivity;
import de.hdodenhof.circleimageview.CircleImageView;


public class AirQualityFragment extends Fragment {

    View v;
    SharedPreferences sharedPreferences;

    //AQI report components
    LinearLayout aqi_layout;
    TextView location, aqi_quality, aqi_condition;
    CircleImageView aqi_conditionemotion;

    //Pollutant components
    TextView pollutants_pm25, pollutants_co, pollutants_o3;

    //Daily graph components
    RadioGroup dailyGroup;
    RadioButton rb_o3, rb_pm10, rb_pm25;
    BarChart dailygraph;

    //Daily Graph data
    List<Float> o3daily, pm10daily, pm25daily;
    List<String> o3DateSupport, pm10DateSupport, pm25DateSupport;

    //API value
    public String aqivalue, pm25value, covalue, o3value;
    final String TOKEN = "335c6bfed754d30c7a80d76cd33f15a76c0f15c1";
    private String city_name ="";
    private String url;

    //Location data
    double longitude,latitude;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_air_quality, container, false);
        getID();


        sharedPreferences = this.getActivity().getSharedPreferences("Firsttime", Context.MODE_PRIVATE);
        longitude = Double.parseDouble(sharedPreferences.getString("longitude", "0.0"));
        latitude = Double.parseDouble(sharedPreferences.getString("latitude", "0.0"));

        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getContext(), Locale.getDefault());





        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 1);
            city_name = addresses.get(0).getLocality();

        } catch (Exception e) {
            e.printStackTrace();
        }

        location.setText(city_name);

        url = "https://api.waqi.info/feed/" + city_name + "/?token=" + TOKEN;

        ReadJSONFeed feed = new ReadJSONFeed();
        try {
            feed.execute(url);
        }catch (Exception e){
            v = inflater.inflate(R.layout.fragment_not_supported, container, false);
        }


        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }





    private void barChartEntry(BarChart graph, List<Float> dailyValue, String labelsName, List<String> dateSupport) {
        //Get data from List and Create a Bar
        List<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < dailyValue.size(); i++) {
            entries.add(new BarEntry(i, dailyValue.get(i)));
        }

        //Create Bar Dataset with entries and labels
        BarDataSet set = new BarDataSet(entries, labelsName);

        //Set Bar attributes
        BarData data = new BarData(set);
        data.setBarWidth(0.5f);
        data.setValueTextSize(20f);

        XAxis xAxis = graph.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);



        String[] date = dateSupport.toArray(new String[0]);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(date));

        if(date.length < 4) {
            xAxis.setTextSize(15f);
        }
        else if(date.length < 10){
            xAxis.setTextSize(8f);
        }

        //Set data for graph
        graph.setData(data);
        graph.setFitBars(true);
        graph.invalidate();
    }


    public void getID() {
        location = v.findViewById(R.id.AQ_currentlocation);
        aqi_quality = v.findViewById(R.id.AQ_quality);
        aqi_condition = v.findViewById(R.id.AQ_condition);
        aqi_conditionemotion = v.findViewById(R.id.AQ_conditionemotion);
        aqi_layout = v.findViewById(R.id.aqilayout);

        pollutants_pm25 = v.findViewById(R.id.AQ_pm25);
        pollutants_co = v.findViewById(R.id.AQ_co);
        pollutants_o3 = v.findViewById(R.id.AQ_o3);


        rb_o3 = v.findViewById(R.id.rb_o3daily);
        rb_pm25 = v.findViewById(R.id.rb_pm25daily);
        rb_pm10 = v.findViewById(R.id.rb_pm10daily);
        dailygraph = v.findViewById(R.id.AQ_dailygraph);
        dailyGroup = v.findViewById(R.id.dailyRG);

    }


    private void setPollutantsTextView() {

        aqi_quality.setText(aqivalue);
        pollutants_pm25.setText(pm25value);
        pollutants_co.setText(covalue);
        pollutants_o3.setText(o3value);
    }

    public String aqiConditionCheck() {
        try {
            int value = Integer.parseInt(aqivalue);
            if (value <= 50) {
                return "Good";
            } else if (value >= 51 && value <= 100) {
                return "Moderate";
            } else if (value > 100) {
                return "Unhealthy";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return "Not in range";

    }


    private class ReadJSONFeed extends AsyncTask<String, Void, String> {
        private ProgressDialog pDialog;
        private long startTime;

        public ReadJSONFeed() {


        }



        private String readJSON(String address) {
            URL url = null;
            StringBuilder sb = new StringBuilder();
            HttpsURLConnection urlConnection = null;
            try {
                //Get URL
                url = new URL(address);
                //Open URL
                urlConnection = (HttpsURLConnection) url.openConnection();
                InputStream content = new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
            }
            return sb.toString();

        }
        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(getContext());

            startTime = System.currentTimeMillis();
            pDialog.setCancelable(true);
            pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            pDialog.setMax(100);
            pDialog.setMessage("Loading data");
            pDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            pDialog.show();

        }

        @Override
        protected String doInBackground(String... urls) {
            return readJSON(urls[0]);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            long endTime = 1000 - (System.currentTimeMillis() - startTime);
            if(endTime > 0 ){
                SystemClock.sleep(endTime);
            }
            pDialog.dismiss();

            try {
                JSONObject dataObject = new JSONObject(result).getJSONObject("data");

                getAQIvalue(dataObject);
                getPollutantsvalue(dataObject);
                getDailyGraphvalue(dataObject);
                setPollutantsTextView();
                setAQITextView();
                barChartEntry(dailygraph, o3daily, "03", o3DateSupport);
                dailyGroup.setOnCheckedChangeListener((radioGroup, i) -> {
                    switch (i) {
                        case R.id.rb_o3daily:
                            barChartEntry(dailygraph, o3daily, "03", o3DateSupport);
                            break;
                        case R.id.rb_pm10daily:
                            barChartEntry(dailygraph, pm10daily, "PM1.0", pm10DateSupport);
                            break;
                        case R.id.rb_pm25daily:
                            barChartEntry(dailygraph, pm25daily, "PM2.5", pm25DateSupport);
                            break;

                    }
                });



            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.container, new NotSupportedFragment());
                ft.commit();
            }


        }

        private void getAQIvalue(JSONObject dataObject) {
            try {
                aqivalue = dataObject.getString("aqi");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        private void getPollutantsvalue(JSONObject dataObject) {
            try {
                dataObject = dataObject.getJSONObject("iaqi");
                pm25value = dataObject.getJSONObject("pm25").getString("v");
                covalue = dataObject.getJSONObject("co").getString("v");
                o3value = dataObject.getJSONObject("o3").getString("v");
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
        }

        //Messy code
        private void getDailyGraphvalue(JSONObject dataObject) {
            try {

                o3daily = new ArrayList<>();
                pm10daily = new ArrayList<>();
                pm25daily = new ArrayList<>();

                o3DateSupport = new ArrayList<>();
                pm10DateSupport = new ArrayList<>();
                pm25DateSupport = new ArrayList<>();


                JSONArray o3Array = dataObject.getJSONObject("forecast").getJSONObject("daily").getJSONArray("o3");
                JSONArray pm10Array = dataObject.getJSONObject("forecast").getJSONObject("daily").getJSONArray("pm10");
                JSONArray pm25Array = dataObject.getJSONObject("forecast").getJSONObject("daily").getJSONArray("pm25");

                for (int i = 0; i < o3Array.length(); i++) {
                    JSONObject o3temp = o3Array.getJSONObject(i);
                    String date = o3temp.getString("day");
                    String o3dailyValue = o3temp.getString("avg");
                    o3DateSupport.add(date);
                    o3daily.add(Float.parseFloat(o3dailyValue));
                }

                for (int i = 0; i < pm10Array.length(); i++) {
                    JSONObject pm10temp = pm10Array.getJSONObject(i);
                    String pm10dailyValue = pm10temp.getString("avg");
                    String date = pm10temp.getString("day");

                    pm10DateSupport.add(date);
                    pm10daily.add(Float.parseFloat(pm10dailyValue));
                }

                for (int i = 0; i < pm25Array.length(); i++) {
                    JSONObject pm25temp = pm25Array.getJSONObject(i);
                    String pm25dailyValue = pm25temp.getString("avg");
                    String date = pm25temp.getString("day");

                    pm25DateSupport.add(date);
                    pm25daily.add(Float.parseFloat(pm25dailyValue));
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        private void setAQITextView() {
            String aqi_result = aqiConditionCheck();
            aqi_condition.setText(aqi_result);
            try{
                switch (aqi_result) {
                    case "Good":
                        aqi_conditionemotion.setImageDrawable(getResources().getDrawable(R.drawable.aq_happy));
                        aqi_layout.setBackground(getResources().getDrawable(R.drawable.aq_section_layout_good));
                        break;
                    case "Moderate":
                        aqi_conditionemotion.setImageDrawable(getResources().getDrawable(R.drawable.aq_worry));
                        aqi_layout.setBackground(getResources().getDrawable(R.drawable.aq_section_layout_moderate));
                        break;
                    case "Unhealthy":
                        aqi_conditionemotion.setImageDrawable(getResources().getDrawable(R.drawable.aq_angry));
                        aqi_layout.setBackground(getResources().getDrawable(R.drawable.aq_section_layout_bad));
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + aqi_result);
                }
            }
            catch (Exception e)
            {

            }
        }


    }
}






