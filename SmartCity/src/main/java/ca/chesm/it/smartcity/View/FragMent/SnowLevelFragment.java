
package ca.chesm.it.smartcity.View.FragMent;
//        Name: Hieu Chu N01371619
//        Course: CENG322-RND
//        Purpose: Control Snow level on street
//        Last updated: Sep 27 2021

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

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
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;

import ca.chesm.it.smartcity.R;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SnowLevelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SnowLevelFragment extends Fragment
{

    View v;

    TextView snow_alert, snow_time;
    Button snow_alertBtn;

    Spinner snow_location;
    TextView snow_lv, temperature, humidity;

    CircleImageView snow_weather_image;
    //Location data
    double longitude, latitude;

    //API
    SharedPreferences sharedPreferences;
    private String city_name = "";
    private String url;
    public String humid, snow, temp, snowDay;

    List<Float> snowValueList;
    List<String> snowDailyList, locationArray;
    BarChart dailygraph;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private final String APIkey = "fb3bee783ff014198af717516379bfe1";

    public SnowLevelFragment()
    {
        // Required empty public constructor
    }


    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SnowLevelFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SnowLevelFragment newInstance(String param1, String param2)
    {
        SnowLevelFragment fragment = new SnowLevelFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_snow_level, container, false);

        //Blinking effect for Alert textview
        snow_alert = v.findViewById(R.id.Snow_Alert);

        //Bar chart
        dailygraph = v.findViewById(R.id.Snow_hoursgraph);

        //Call support button
        snow_alertBtn = v.findViewById(R.id.Snow_AlertBtn);
        snow_alertBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                callSupport();
            }
        });

        //Change weather image based on weather
        snow_weather_image = v.findViewById(R.id.snowLevelImage);

        //Text View temp and humid
        temperature = v.findViewById(R.id.Snow_temperature);
        humidity = v.findViewById(R.id.Snow_humidity);
        snow_lv = v.findViewById(R.id.snow_level);

        //Show current time
        snow_time = v.findViewById(R.id.snow_currentTime);



        //Spinner location
        snow_location = v.findViewById(R.id.Snow_currentlocation);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.snow_locationarray, android.R.layout.simple_spinner_item);

        locationArray = Arrays.asList(getResources().getStringArray(R.array.snow_locationarray));
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        snow_location.setAdapter(adapter);
        snow_location.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id)
            {
                url = "https://api.openweathermap.org/data/2.5/forecast?q=" + locationArray.get(position) + "&appid=" + APIkey;
                ReadJSONFeed feed = new ReadJSONFeed();
                feed.execute(url);
                currentTime();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent)
            {

            }


        });

        //Get location
        sharedPreferences = this.getActivity().getSharedPreferences("SmartCity", Context.MODE_PRIVATE);
        longitude = Double.parseDouble(sharedPreferences.getString("longitude", "0.0"));
        latitude = Double.parseDouble(sharedPreferences.getString("latitude", "0.0"));

        //Get default location
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getContext(), Locale.getDefault());


        try
        {
            addresses = geocoder.getFromLocation(latitude, longitude, 3);
            city_name = addresses.get(0).getLocality();
            int spinnerPosition = adapter.getPosition(city_name); //set default location for the spinner
            snow_location.setSelection(spinnerPosition);

        } catch (Exception e)
        {
            e.printStackTrace();
        }

        url = "https://api.openweathermap.org/data/2.5/forecast?q=" + city_name + "&appid=" + APIkey;
        ReadJSONFeed feed = new ReadJSONFeed();
        feed.execute(url);

        //Returns
        return v;
    }


    private void barChartEntry(BarChart graph, List<Float> dailyValue, String labelsName, List<String> dateSupport)
    {
        //Get data from List and Create a Bar
        List<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < dailyValue.size(); i++)
        {
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
        xAxis.setTextSize(10f);

        String[] date = dateSupport.toArray(new String[0]);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(date));


        //Set data for graph
        graph.setData(data);
        graph.setFitBars(true);
        graph.invalidate();
    }


    @SuppressLint("WrongConstant")
    public void BlinkEffect()
    {
        ObjectAnimator anim = ObjectAnimator.ofInt(snow_alert, "backgroundColor", Color.WHITE, Color.RED, Color.WHITE);
        anim.setDuration(800);
        anim.setEvaluator(new ArgbEvaluator());
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        anim.start();
    }

    public void callSupport()
    {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel: 6476771222"));
        startActivity(intent);
    }

    public void currentTime()
    {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm z");
        String currentTime = sdf.format(new Date());
        snow_time.setText(currentTime);

    }


    public void setSnowTHView()
    {
        temperature.setText(temp);
        humidity.setText(humid);
        snow_lv.setText(snow);
    }

    private class ReadJSONFeed extends AsyncTask<String, Void, String>
    {
        private ProgressDialog proDia;
        private long startTime;
        public ReadJSONFeed()
        {

        }

        @Override
        protected String doInBackground(String... urls)
        {
            return readJSON(urls[0]);
        }

        private String readJSON(String address)
        {
            URL url = null;
            StringBuilder sb = new StringBuilder();
            HttpsURLConnection urlConnection = null;
            try
            {
                //Get URL
                url = new URL(address);
                //Open URL
                urlConnection = (HttpsURLConnection) url.openConnection();
                InputStream content = new BufferedInputStream(urlConnection.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line);
                }
            } catch (MalformedURLException e)
            {
                e.printStackTrace();
            } catch (IOException e)
            {
                e.printStackTrace();
            } finally
            {
                urlConnection.disconnect();
            }
            return sb.toString();
        }

        @Override
        protected void onPreExecute() {
            proDia = new ProgressDialog(getContext());

            startTime = System.currentTimeMillis();

            proDia.setCancelable(true);
            proDia.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            proDia.setMax(100);
            proDia.setMessage("Loading data");
            proDia.show();

        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);


        }
        @Override
        protected void onPostExecute(String result)
        {
            long endTime = 1000 - (System.currentTimeMillis() - startTime);
            if(endTime > 0 ){
                SystemClock.sleep(endTime);
            }
            proDia.dismiss();

            try
            {

                JSONObject dataObject = new JSONObject(result);
                JSONArray dataList = dataObject.getJSONArray("list");
                JSONObject dataMain = dataList.getJSONObject(0).getJSONObject("main");

                getDailyGraphvalue(dataObject);
                getTemperature(dataMain);
                getHumidity(dataMain);


                barChartEntry(dailygraph, snowValueList, barDate(dataObject), snowDailyList);
                snowCheck(dataList.getJSONObject(0));
                setSnowTHView();

            } catch (Exception jsonException)
            {
                jsonException.printStackTrace();
            }


        }


        private void getDailyGraphvalue(JSONObject dataObject)
        {
            try
            {

                snowValueList = new ArrayList<>();
                snowDailyList = new ArrayList<>();
                JSONArray snowArray = dataObject.getJSONArray("list");

                for (int i = 0; i < 6; i++)
                {
                    JSONObject snowValue = snowArray.getJSONObject(i);
                    String date = snowValue.getString("dt_txt").split(" ")[1].split(":")[0] + ":" + snowValue.getString("dt_txt").split(" ")[1].split(":")[1];
                    String snowHourlyValue;
                    if (snowValue.has("snow"))
                    {
                        snowHourlyValue = snowValue.getJSONObject("snow").getString("3h");
                    } else
                    {
                        snowHourlyValue = "00.00";
                    }

                    snowDailyList.add(date);
                    snowValueList.add(Float.parseFloat(snowHourlyValue));
                }
            } catch (JSONException e)
            {
                e.printStackTrace();
            }

        }
    }

    public void getTemperature(JSONObject dataObject)
    {
        try
        {
            DecimalFormat df = new DecimalFormat("0.00");
            temp = df.format(Double.parseDouble(dataObject.getString("temp")) - 273.15) + " °C";

        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public void getHumidity(JSONObject dataObject)
    {
        try
        {
            humid = dataObject.getString("humidity") + " %";
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public void snowCheck(JSONObject dataObject) throws JSONException
    {
        if (dataObject.has("snow"))
        {
            JSONObject dataSnow = dataObject.getJSONObject("snow");
            getSnow(dataSnow);

        } else
        {
            snow = "0";
            snowLVCheck(Double.parseDouble(snow));
        }
    }

    public void getSnow(JSONObject dataObject)
    {
        try
        {
            snow = dataObject.getString("3h");
            if (Double.parseDouble(snow) >= 60)
            {
                BlinkEffect();
            }
            snowLVCheck(Double.parseDouble(snow));
        } catch (JSONException e)
        {
            e.printStackTrace();
        }
    }

    public void snowLVCheck(double value)
    {
        if (value <= 0)
        {
            snow_weather_image.setImageDrawable(getResources().getDrawable(R.drawable.nosnow));
        } else if (value >= 0 && value < 1)
        {
            snow_weather_image.setImageDrawable(getResources().getDrawable(R.drawable.snow));
        } else if (value > 1 && value <= 4)
        {
            snow_weather_image.setImageDrawable(getResources().getDrawable(R.drawable.snowmedium));
        } else
        {
            snow_weather_image.setImageDrawable(getResources().getDrawable(R.drawable.snowheavy));
        }
    }

    public String barDate(JSONObject dataObject) throws JSONException
    {
        JSONArray dateArray = dataObject.getJSONArray("list");
        for (int i = 0; i < 5; i++)
        {
            JSONObject snowDate = dateArray.getJSONObject(i);
            snowDay = snowDate.getString("dt_txt").split(" ")[0];
        }
        return snowDay;
    }
}


