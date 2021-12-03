/*
    Name:Dinh Hoa Tran N01354661
    Course: CENG322-RND
    Purpose: City Light
*/
package ca.chesm.it.smartcity.View.activities;

import android.os.Bundle;

import androidx.annotation.ArrayRes;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.airbnb.lottie.L;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import ca.chesm.it.smartcity.PreSenter.CityLightPreSenter;
import ca.chesm.it.smartcity.PreSenter.CityView;
import ca.chesm.it.smartcity.PreSenter.LightPreSenter;
import ca.chesm.it.smartcity.PreSenter.LightView;
import ca.chesm.it.smartcity.R;
import ca.chesm.it.smartcity.adapter.LightAdapter;
import ca.chesm.it.smartcity.models.Lights;


public class CityLightFragMent extends Fragment  implements LightView, CityView {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private AppCompatSpinner spinerCity,spinerStreet;
    private CityLightPreSenter cityLightPreSenter ;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    String name_city= "";
    String name_key= "";
    String name_street= "";
    List<String> liststreet ;
    List<String> listLightCity ;
    List<String> listLightNumber ;
    Button btnOnof;
    int k = 0;
    String name_light="";
   LightPreSenter lightPreSenter;
    ImageView imageLight;
    private ArrayAdapter arrayAdapterStreetname;
    private List<Lights> listLights;
    private RecyclerView rcvLights;
    private LightAdapter lightAdapter;
    private  Button btncheckperson;
    private Handler handler;
     private  Runnable runnable;
     private Spinner spinner;
     private int check =0;
    public CityLightFragMent() {
        // Required empty public constructor
        Init();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment3.
     */
    // TODO: Rename and change types and number of parameters
    public static CityLightFragMent newInstance(String param1, String param2) {
        CityLightFragMent fragment = new CityLightFragMent();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    private  void Init() {

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
        view =inflater.inflate(R.layout.fragment_city_light, container, false);
        spinerCity = view.findViewById(R.id.SpinerCity);
        spinerStreet = view.findViewById(R.id.SpinerStreet);
        btnOnof=view.findViewById(R.id.btnOnof);
        imageLight=view.findViewById(R.id.light);
        rcvLights = view.findViewById(R.id.rcvLights);
        btncheckperson=view.findViewById(R.id.btnPerson);
        spinner = view.findViewById(R.id.spinerMode);

        cityLightPreSenter = new CityLightPreSenter(this);
        listLightCity = new ArrayList<>();
        liststreet = new ArrayList<>();
        cityLightPreSenter.getDataCityLight();
        listLights = new ArrayList<>();
        lightPreSenter = new LightPreSenter(this);
        // Created the user can choice the buttom
        String[] s= {"Select Mode","Manual","Auto"};
        btncheckperson.setVisibility(View.GONE);
        btnOnof.setVisibility(View.GONE);
        ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,s);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(i>0){
                    switch (i){
                        case  1 : btnOnof.setVisibility(View.VISIBLE);
                        btncheckperson.setVisibility(View.GONE);break;
                        case 2:
                            Calendar calendar = Calendar.getInstance();
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

                            try {

                                Date timer_now = simpleDateFormat.parse(simpleDateFormat.format(calendar.getTime()));
                                if(timer_now.getHours()>=17 && timer_now.getHours()<=24 ||
                                timer_now.getHours()>=0 && timer_now.getHours()<=5 ){
                                    btncheckperson.setVisibility(View.VISIBLE);
                                    btnOnof.setVisibility(View.GONE);

                                }else{
                                    Toast.makeText(getActivity(), "The auto function will work from 5 pm - 5 am", Toast.LENGTH_SHORT).show();
                                }

                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });






       spinerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               if(listLightCity.size()>0){
                   name_city = listLightCity.get(i);

                   if(liststreet.size()> 0 ){
                       liststreet.clear();

                   }

                   arrayAdapterStreetname = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,liststreet);
                   spinerStreet.setAdapter(arrayAdapterStreetname);
                   cityLightPreSenter.getDataListCityStreet(name_city);
               }

           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });
       spinerStreet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               if(liststreet.size()>0){
                   name_street = liststreet.get(i);
                   listLights.clear();
                   lightPreSenter.getDataListLight(name_city,liststreet.get(i));
                   lightAdapter = new LightAdapter(listLights,getActivity());
                   rcvLights.setLayoutManager(new GridLayoutManager(getActivity(),5));
                   rcvLights.setAdapter(lightAdapter);
               }

           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });



        btnOnof.setText("OFF");
         handler = new Handler();
         runnable= new Runnable() {
             @Override
             public void run() {

             }
         };
        btnOnof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check=0;
                handler.removeCallbacks(runnable);
                handler.removeCallbacks(runnable);
                if(liststreet.size()>0 ){
                    if(listLights.size()>0){
                        switch (k){
                            case  0 :
                                btnOnof.setText("ON");
                                for(Lights l : listLights){
                                    lightPreSenter.HandleUpdate(l.getId(),1,name_city,name_street);

                                }

                                k=1;break;
                            case 1:
                                btnOnof.setText("OFF");
                                for(Lights l : listLights){
                                    lightPreSenter.HandleUpdate(l.getId(),0,name_city,name_street);

                                }
                                k=0;
                                break;
                        }
                    }else{
                        Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });
        // Set up default distance radius
        btncheckperson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check = 1;
                if(check == 1){
                    Random random = new Random();
                    runnable= new Runnable() {
                        @Override
                        public void run() {
                            int k = random.nextInt(200) +10;
                            Log.d("CHECKED",k+" ");
                            if(listLights.size()>0){
                                for(Lights l : listLights){
                                    if(k > l.getDistance()){
                                        if (k - l.getDistance() <= 20) {
                                            lightPreSenter.HandleUpdate(l.getId(),1,name_city,name_street);
                                        }else{
                                            lightPreSenter.HandleUpdate(l.getId(),0,name_city,name_street);
                                        }

                                    }
                                }
                            }

                            handler.postDelayed(this,3000);

                        }

                    };
                    runnable.run();
                }


            }
        });



        return  view;
    }



    @Override
    public void getDataCityname(String cityname) {
       listLightCity.add(cityname);

                if(listLightCity.size()>0){
                    try {
                        ArrayAdapter arrayAdapter  =new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,listLightCity);
                        spinerCity.setAdapter(arrayAdapter);
                    }
                    catch (Exception e){

                    }


                }
    }

    @Override
    public void getDataStreetname(String streetname) {
        liststreet.add(streetname);
        if(liststreet.size()>0){
            try{
                arrayAdapterStreetname= new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,liststreet);
                spinerStreet.setAdapter(arrayAdapterStreetname);

            }catch ( Exception e){

            }
        }


    }

    @Override
    public void getDataLight(Long distance, Boolean state, String id) {
        listLights.add(new Lights(distance,state,id));
        lightAdapter = new LightAdapter(listLights,getActivity());
        rcvLights.setLayoutManager(new GridLayoutManager(getActivity(),5));
        rcvLights.setAdapter(lightAdapter);
    }

    @Override
    public void EventChange() {
        listLights.clear();
        lightAdapter = new LightAdapter(listLights,getActivity());
        rcvLights.setLayoutManager(new GridLayoutManager(getActivity(),5));
        rcvLights.setAdapter(lightAdapter);

    }
}