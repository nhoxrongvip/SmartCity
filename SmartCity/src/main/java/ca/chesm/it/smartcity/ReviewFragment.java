package ca.chesm.it.smartcity;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import ca.chesm.it.smartcity.Userscontrol.User;

public class ReviewFragment extends Fragment
{

    private DatabaseReference reff;
    private FirebaseAuth mAuth;
    private Button bntsubmit,bntreview1,bntreview2,bntreview3,bntreview4,bntreview5;
    private ImageButton bntface1, bntface2, bntface3;
    private EditText editextarea;
    private String face, share, recommend;
    User user;
    View v;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_review, container, false);

        reff = FirebaseDatabase.getInstance().getReference().child("Review");
        mAuth = FirebaseAuth.getInstance();
        id();

        bntface1.setOnClickListener(view ->
        {
            face = "very happy";
        });

        bntface2.setOnClickListener(view ->
        {
            face = "quite happy";
        });

        bntface3.setOnClickListener(view ->
        {
            face = "sad";
        });

        bntreview1.setOnClickListener(view ->
        {
            recommend = bntreview1.getText().toString();
        });

        bntreview2.setOnClickListener(view ->
        {
            recommend = bntreview2.getText().toString();
        });

        bntreview3.setOnClickListener(view ->
        {
            recommend = bntreview3.getText().toString();
        });

        bntreview4.setOnClickListener(view ->
        {
            recommend = bntreview4.getText().toString();
        });
        bntreview5.setOnClickListener(view ->
        {
            recommend = bntreview5.getText().toString();
        });

        bntsubmit.setOnClickListener(view ->
        {
            if(face.equals(""))
            {
                Toast.makeText(getActivity(), "Please choose what you think !", Toast.LENGTH_SHORT).show();
                return;
            }
            share = editextarea.getText().toString();
            if(share.equals(""))
            {
                Toast.makeText(getActivity(), "Please enter your through !", Toast.LENGTH_SHORT).show();
                return;
            }
            if(recommend.equals(""))
            {
                Toast.makeText(getActivity(), "Please choose what would you like below !", Toast.LENGTH_SHORT).show();
                return;
            }
            user = new User(mAuth.getCurrentUser().getUid().toString(),face,share,recommend);
            reff.setValue(user);
        });
        return v;
    }


    private void id()
    {
        bntsubmit = (Button) v.findViewById(R.id.bntreviewsubmit);
        bntface1 = (ImageButton) v.findViewById(R.id.bntfac1);
        bntface2 = (ImageButton) v.findViewById(R.id.bntfac2);
        bntface3 = (ImageButton) v.findViewById(R.id.bntfac3);
        bntreview1 = (Button) v.findViewById(R.id.bntreview1);
        bntreview2 = (Button) v.findViewById(R.id.bntreview2);
        bntreview3 = (Button) v.findViewById(R.id.bntreview3);
        bntreview4 = (Button) v.findViewById(R.id.bntreview4);
        bntreview5 = (Button) v.findViewById(R.id.bntreview5);
        editextarea = (EditText) v.findViewById(R.id.txteditreview);
    }
}