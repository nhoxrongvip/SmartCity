
package ca.chesm.it.smartcity.View.FragMent;
//        Name: Hieu Chu N01371619
//        Course: CENG322-RND
//        Purpose: Control Snow level on street
//        Last updated: Sep 27 2021
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
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
public class SnowLevelFragment extends Fragment {
    View v;

    TextView snow_alert;
    Button snow_alertBtn;

    Spinner snow_location;
    TextView snow_lv, temperature, humidity;

    CircleImageView snow_weather_image;
    //Location data
    double longitude, latitude;

    SharedPreferences sharedPreferences;
    private String city_name = "";
    private String url;
    public String humid, snow, temp;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private final String APIkey = "fb3bee783ff014198af717516379bfe1";

    public SnowLevelFragment() {
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
    public static SnowLevelFragment newInstance(String param1, String param2) {
        SnowLevelFragment fragment = new SnowLevelFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_snow_level, container, false);

        //Blinking effect for Alert button
        snow_alert = v.findViewById(R.id.Snow_Alert);
        snow_alertBtn = v.findViewById(R.id.Snow_AlertBtn);
        snow_alertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BlinkEffect();
            }
        });

        //Change weather image based on weather
        snow_weather_image = v.findViewById(R.id.snowLevelImage);

        //Text View temp and humid
        temperature = v.findViewById(R.id.Snow_temperature);
        humidity = v.findViewById(R.id.Snow_humidity);
        snow_lv = v.findViewById(R.id.snow_level);



        //Spinner location
        snow_location = v.findViewById(R.id.Snow_currentlocation);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
                R.array.snow_locationarray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        snow_location.setAdapter(adapter);


        sharedPreferences = this.getActivity().getSharedPreferences("SmartCity", Context.MODE_PRIVATE);
        longitude = Double.parseDouble(sharedPreferences.getString("longitude", "0.0"));
        latitude = Double.parseDouble(sharedPreferences.getString("latitude", "0.0"));

        longitude = Double.parseDouble(sharedPreferences.getString("longitude", "0.0"));
        latitude = Double.parseDouble(sharedPreferences.getString("latitude", "0.0"));

        //Get default location
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(getContext(), Locale.getDefault());


        try {
            addresses = geocoder.getFromLocation(latitude, longitude, 3);
            city_name = addresses.get(0).getLocality();
            int spinnerPosition = adapter.getPosition(city_name);
            snow_location.setSelection(spinnerPosition);

        } catch (Exception e) {
            e.printStackTrace();
        }

        url = "https://api.openweathermap.org/data/2.5/weather?q=" + city_name + "&appid=" + APIkey;
        ReadJSONFeed feed = new ReadJSONFeed();
        feed.execute(url);

        //Returns
        return v;
    }


    @SuppressLint("WrongConstant")
    public void BlinkEffect() {
        ObjectAnimator anim = ObjectAnimator.ofInt(snow_alert, "backgroundColor", Color.WHITE, Color.RED, Color.WHITE);
        anim.setDuration(800);
        anim.setEvaluator(new ArgbEvaluator());
        anim.setRepeatMode(Animation.REVERSE);
        anim.setRepeatCount(Animation.INFINITE);
        anim.start();
    }

    public void setSnowTHView() {
        temperature.setText(temp);
        humidity.setText(humid);
        snow_lv.setText(snow);
    }

    private class ReadJSONFeed extends AsyncTask<String, Void, String> {

        public ReadJSONFeed() {

        }

        @Override
        protected String doInBackground(String... urls) {
            return readJSON(urls[0]);
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
        protected void onPostExecute(String result) {

            try {
                JSONObject dataObject = new JSONObject(result);
                JSONObject dataMain = dataObject.getJSONObject("main");

                if (dataObject.has("snow")) {
                    JSONObject dataSnow = dataObject.getJSONObject("snow");
                    getSnow(dataSnow);
                } else {
                    snow = "0";
                }

                getTemperature(dataMain);
                getHumidity(dataMain);
                setSnowTHView();


            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }


        }

        public void getTemperature(JSONObject dataObject) {
            try {
                DecimalFormat df = new DecimalFormat("0.00");
                temp = df.format(Double.parseDouble(dataObject.getString("temp")) - 273.15);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public void getHumidity(JSONObject dataObject) {
            try {
                humid = dataObject.getString("humidity");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        public void getSnow(JSONObject dataObject) {
            try {
                snow = dataObject.getString("1h");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}