/*
    Name:Dinh Hoa Tran N01354661
    Course: CENG322-RND
    Purpose: City Light
*/
package ca.chesm.it.smartcity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import ca.chesm.it.smartcity.models.Lights;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CityLight#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CityLight extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private AppCompatSpinner spinerCity,spinerStreet;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;

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
        Lights lights = new Lights();
        List<String> listcity  = lights.getDataListCity();
        ArrayAdapter arrayAdapter  =new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,lights.getDataListCity());

        spinerCity.setAdapter(arrayAdapter);

        spinerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ArrayAdapter arrayAdapter1  =new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1,lights.getDataListStreet(listcity.get(i)));
                spinerStreet.setAdapter(arrayAdapter1);
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
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                arrayAdapter.notifyDataSetChanged();
            }
        },1000);

        return  view;
    }
}