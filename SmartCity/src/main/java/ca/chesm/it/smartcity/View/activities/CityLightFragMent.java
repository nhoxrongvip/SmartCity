/*
    Name:Dinh Hoa Tran N01354661
    Course: CENG322-RND
    Purpose: City Light
*/
package ca.chesm.it.smartcity.View.activities;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import com.airbnb.lottie.L;

import java.util.ArrayList;
import java.util.List;

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




//        FirebaseDatabase database = FirebaseDatabase.getInstance("https://smartcity-69701-default-rtdb.firebaseio.com/");
//        DatabaseReference databaseReference = database.getReference();
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        })

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

        cityLightPreSenter = new CityLightPreSenter(this);
        listLightCity = new ArrayList<>();
        liststreet = new ArrayList<>();
        cityLightPreSenter.getDataCityLight();
        listLights = new ArrayList<>();
        lightPreSenter = new LightPreSenter(this);





       spinerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               name_city = listLightCity.get(i);

               if(liststreet.size()> 0 ){
                   liststreet.clear();

               }
               arrayAdapterStreetname = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,liststreet);
               spinerStreet.setAdapter(arrayAdapterStreetname);
               cityLightPreSenter.getDataListCityStreet(name_city);
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });
       spinerStreet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               listLights.clear();
               lightPreSenter.getDataListLight(name_city,liststreet.get(i));
               lightAdapter = new LightAdapter(listLights,getActivity());
               rcvLights.setLayoutManager(new GridLayoutManager(getActivity(),5));
               rcvLights.setAdapter(lightAdapter);
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });



        btnOnof.setText("OFF");

        btnOnof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        return  view;
    }



    @Override
    public void getDataCityname(String cityname) {
       listLightCity.add(cityname);
       ArrayAdapter arrayAdapter  =new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,listLightCity);
        spinerCity.setAdapter(arrayAdapter);

    }

    @Override
    public void getDataStreetname(String streetname) {
        liststreet.add(streetname);
        arrayAdapterStreetname= new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,liststreet);
        spinerStreet.setAdapter(arrayAdapterStreetname);


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