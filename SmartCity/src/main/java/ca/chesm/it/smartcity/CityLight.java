/*
    Name:Dinh Hoa Tran N01354661
    Course: CENG322-RND
    Purpose: City Light
*/
package ca.chesm.it.smartcity;

import android.os.Bundle;

import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;

import java.util.List;

import ca.chesm.it.smartcity.CityLightControl.LightView;
import ca.chesm.it.smartcity.CityLightControl.Lights;


public class CityLight extends Fragment  implements LightView {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private AppCompatSpinner spinerCity,spinerStreet;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    String name_city= "";
    String name_key= "";
    String name_street= "";
    List<String> liststreet ;
    List<String> listLight ;
    List<String> listLightNumber ;
    Button btnOnof;
    int k = 0;
    String name_light="";
    Lights lights;
    ImageView imageLight;
    public CityLight() {
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
    public static CityLight newInstance(String param1, String param2) {
        CityLight fragment = new CityLight();
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
         lights = new Lights(this);
        List<String> listcity  = lights.getDataListCity();

        ArrayAdapter arrayAdapter  =new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,lights.getDataListCity());


        spinerCity.setAdapter(arrayAdapter);

        spinerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                liststreet  = lights.getDataListStreet(listcity.get(i));
                ArrayAdapter arrayAdapter1  =new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,liststreet);
                spinerStreet.setAdapter(arrayAdapter1);
                name_city = listcity.get(i);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        arrayAdapter1.notifyDataSetChanged();
                    }
                },1000);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinerStreet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    listLight = lights.getDataListLight(name_city,liststreet.get(i));
                    name_street = liststreet.get(i);



            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                arrayAdapter.notifyDataSetChanged();
            }
        },1000);
        btnOnof.setText("OFF");

        btnOnof.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (k){
                    case  0:
                        for(String s : listLight){
                            listLightNumber =lights.getDataListLight(name_city,name_street,s);

                        }
                        imageLight.setImageResource(R.drawable.light_on);






                             k=1;btnOnof.setText("ON");btnOnof.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.aqi_good));break;

                    case  1:
                        for(String s : listLight){
                            name_light = s;
                            listLightNumber =lights.getDataListLight(name_city,name_street,s);

                        }
                        imageLight.setImageResource(R.drawable.light_off);
                        k=0;btnOnof.setText("OFF");btnOnof.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.grey));break;



                }
            }
        });

        return  view;
    }

    @Override
    public void getDataLightNumber(String toString) {
        listLightNumber.add(toString);
        if(k==0){
            for(String s : listLight){

                lights.setValuesLightOn(name_city,name_street,s,toString,0);
            }

        }else{
            for(String s : listLight){

                lights.setValuesLightOn(name_city,name_street,s,toString,1);
            }
        }

    }
}