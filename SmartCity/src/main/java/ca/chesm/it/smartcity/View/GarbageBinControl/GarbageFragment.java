package ca.chesm.it.smartcity.View.GarbageBinControl;/*
    Name:Dung Ly N01327929
    Course: CENG322-RND
    Purpose: Control Garbage bins in city
    Last updated: Sep 27 2021
*/


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;


import ca.chesm.it.smartcity.R;
import ca.chesm.it.smartcity.adapter.CityAdapter;
import ca.chesm.it.smartcity.models.City;


public class GarbageFragment extends Fragment
{

    View v;
    private RecyclerView recycview;
    private Spinner citySpinner;
    private CityAdapter cityAdapter;
    private final List<City> cityList = new ArrayList<>();
    private SharedPreferences sharedPreferences;
    private static String CITY_NAME = "city_name";
    private static String POSITION = "position";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        sharedPreferences = getActivity().getSharedPreferences("SmartCity", Context.MODE_PRIVATE);
        View root = inflater.inflate(R.layout.fragment_garbage, container, false);
        v = root;
        regid();
        LoadAlldata();
        return root;
    }


    private void LoadDatatoView(String name)
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Garbage").child("City").child(name);
        ref.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                try
                {
                    cityList.clear();
                    for (DataSnapshot cityload : snapshot.getChildren())
                    {
                        int id = Integer.parseInt(cityload.getKey());
                        String address = cityload.child("address").getValue(String.class);
                        double bin1 = cityload.child("Organic Bin").getValue(Double.class);
                        double bin2 = cityload.child("Recycle Bin").getValue(Double.class);
                        double bin3 = cityload.child("Garbage Bin").getValue(Double.class);
                        cityList.add(new City(name, id, address, bin1, bin2, bin3));
                    }
                    cityAdapter = new CityAdapter(getActivity(), cityList);
                    recycview.setAdapter(cityAdapter);
                } catch (Exception e)
                {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });

    }

    private void LoadAlldata()
    {
        int value = sharedPreferences.getInt(POSITION,0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        DatabaseReference refspin = FirebaseDatabase.getInstance().getReference("Garbage").child("City");
        ArrayList<String> spinerlist = new ArrayList<>();
        refspin.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                try
                {
                    for (DataSnapshot spinnerlist : snapshot.getChildren())
                    {
                        String name = spinnerlist.getKey();
                        spinerlist.add(name);
                    }
                    ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), R.layout.support_simple_spinner_dropdown_item, spinerlist);
                    citySpinner.setAdapter(arrayAdapter);
                    if(value > 0)
                    {
                        citySpinner.setSelection(value);
                    }
                    else {
                        citySpinner.setSelection(0);
                    }
                    citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                    {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
                        {
                            editor.putInt(POSITION,position);
                            editor.apply();
                            LoadDatatoView(spinerlist.get(position));
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView)
                        {

                        }
                    });
                } catch (Exception e)
                {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if (cityAdapter != null)
        {
            cityAdapter.release();
        }
    }

    private void regid()
    {
        recycview = (RecyclerView) v.findViewById(R.id.rcv_data);
        recycview.setLayoutManager(new LinearLayoutManager(getActivity()));
        cityAdapter = new CityAdapter(getActivity(), new ArrayList<>());
        recycview.setAdapter(cityAdapter);
        citySpinner = (Spinner) v.findViewById(R.id.gcityspinner);
    }
}