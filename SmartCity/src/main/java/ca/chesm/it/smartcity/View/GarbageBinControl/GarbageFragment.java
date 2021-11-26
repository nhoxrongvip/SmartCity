package ca.chesm.it.smartcity.View.GarbageBinControl;/*
    Name:Dung Ly N01327929
    Course: CENG322-RND
    Purpose: Control Garbage bins in city
    Last updated: Sep 27 2021
*/



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
        View root = inflater.inflate(R.layout.fragment_garbage, container, false);
        v = root;
        regid();
        LoadAlldata();
        return root;
    }

    private void LoadDatatoView(String name)
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Garbage").child("City").child(name);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recycview.setLayoutManager(linearLayoutManager);
        ref.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                cityList.clear();
                for (DataSnapshot cityload : snapshot.getChildren())
                {
                    int id = Integer.parseInt(cityload.getKey());
                    String address = cityload.child("address").getValue(String.class);
                    double bin1 = cityload.child("bin 1").getValue(Double.class);
                    double bin2 = cityload.child("bin 2").getValue(Double.class);
                    double bin3 = cityload.child("bin 3").getValue(Double.class);
                    cityList.add(new City(name,id,address,bin1,bin2,bin3));
                }
                cityAdapter = new CityAdapter(getActivity(), cityList);
                recycview.setAdapter(cityAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });

    }

    private void LoadAlldata()
    {
        DatabaseReference refspin = FirebaseDatabase.getInstance().getReference("Garbage").child("City");
        ArrayList<String> spinerlist = new ArrayList<>();
        refspin.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for(DataSnapshot spinnerlist : snapshot.getChildren())
                {
                    String name = spinnerlist.getKey();
                    spinerlist.add(name);
                }
                ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(),R.layout.support_simple_spinner_dropdown_item,spinerlist);
                citySpinner.setAdapter(arrayAdapter);
                citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
                {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
                    {
                        LoadDatatoView(spinerlist.get(position));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView)
                    {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });
    }

    private void regid()
    {
        recycview = v.findViewById(R.id.rcv_data);
        citySpinner = v.findViewById(R.id.gcityspinner);
    }
}