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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;

import ca.chesm.it.smartcity.R;


public class GarbageFragment extends Fragment
{
    View v;
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference dbc = ref.child("Garbage").child("City");
    private DatabaseReference dbb = ref.child("Garbage").child("City").child("Toronto");
    ArrayList<City> citylist = new ArrayList<City>();
    City city = new City();
    private TextView txttt;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_garbage, container, false);
        v = root;
        regid();
        return root;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        dbc.addListenerForSingleValueEvent(new ValueEventListener()
        {
            String ahaha = "";
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                int i = 1;
                for (DataSnapshot child : snapshot.getChildren())
                {
                    String cityname = child.getKey().toString();
                    if(cityname.equals("Etobicoke"))
                        ahaha = ahaha + " " + child.child(String.valueOf(i)).child("address").getValue(String.class);
                        i++;
                        continue;

                }
                Toast.makeText(getActivity(), ahaha, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });


    }
    public void regid()
    {
        txttt = (TextView) v.findViewById(R.id.txtviewtest);

    }
}