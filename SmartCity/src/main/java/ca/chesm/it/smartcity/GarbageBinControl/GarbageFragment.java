/*
    Name:Dung Ly N01327929
    Course: CENG322-RND
    Purpose: Control Garbage bins in city
    Last updated: Sep 27 2021
*/

package ca.chesm.it.smartcity.GarbageBinControl;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import ca.chesm.it.smartcity.CityLight;
import ca.chesm.it.smartcity.R;


public class GarbageFragment extends Fragment
{

    View v;
    private RecyclerView recycview;
    private Spinner citySpinner;
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Garbage").child("City");
    private CityAdapter cityAdapter;
    private final List<City> cityList = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_garbage, container, false);
        v = root;
        regid();
        LoadDataInfo();
        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        System.out.println(cityList.size());
    }

    private void LoadDataInfo()
    {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recycview.setLayoutManager(linearLayoutManager);
        ref.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for (DataSnapshot cityload : snapshot.getChildren())
                {
                    String name = cityload.getKey();
                    DataSnapshot dataload = cityload.child(name);
                    for (DataSnapshot datacity : cityload.getChildren())
                    {
                        int id = Integer.parseInt(datacity.getKey());
                        String address = datacity.child("address").getValue(String.class);
                        double bin1 = datacity.child("bin 1").getValue(Double.class);
                        double bin2 = datacity.child("bin 2").getValue(Double.class);
                        double bin3 = datacity.child("bin 3").getValue(Double.class);
                        cityList.add(new City(name,id,address,bin1,bin2,bin3));
                    }
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

    private void addCity(City city)
    {
        cityList.add(city);
    }

    private void Spinnercreate()
    {

    }

    private void regid()
    {
        recycview = v.findViewById(R.id.rcv_data);
        citySpinner = v.findViewById(R.id.gcityspinner);
    }
}