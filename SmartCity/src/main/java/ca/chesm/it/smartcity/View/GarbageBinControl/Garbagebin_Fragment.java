package ca.chesm.it.smartcity.View.GarbageBinControl;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ca.chesm.it.smartcity.R;

public class Garbagebin_Fragment extends Fragment
{
    private View v;

    public Garbagebin_Fragment()
    {
        // Required empty public constructor
    }


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

        v = inflater.inflate(R.layout.garbagebin_fragment, container, false);
        return v;
    }
}