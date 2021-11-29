package ca.chesm.it.smartcity.View.GarbageBinControl;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import ca.chesm.it.smartcity.R;
import ca.chesm.it.smartcity.models.City;

public class Garbagebin_Fragment extends Fragment
{

    private View v;
    private Runnable runnable;
    private TextView txtaddress, txtbattery, txtbinname;
    private Button orgabin_btn, recylebin_btn, garbagebin_btn;
    private ProgressBar progressBar;
    private City city;
    private Thread thread;
    private ExecutorService pool;

    public Garbagebin_Fragment()
    {

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
        Bundle bundle = this.getArguments();
        setupid();
        if (bundle != null)
        {

        }
        city = (City) bundle.get("city");
        ProgressbarBin progressbarBin = new ProgressbarBin();
        progressbarBin.execute();
        txtaddress.setText(city.getAddress());


        return v;
    }

    private class ProgressbarBin extends AsyncTask<Void, Void, Void>
    {


        @Override
        protected Void doInBackground(Void... voids)
        {

            return null;
        }

        @Override
        protected void onPostExecute(Void unused)
        {
            orgabin_btn.setOnClickListener(view ->
            {
                txtbinname.setText(orgabin_btn.getText().toString());
                ProgressBarAnimation anim = new ProgressBarAnimation(progressBar, 0, city.getBin1());
                anim.setDuration(1000);
                progressBar.startAnimation(anim);
            });

            recylebin_btn.setOnClickListener(view ->
            {

                txtbinname.setText(recylebin_btn.getText().toString());
                ProgressBarAnimation anim = new ProgressBarAnimation(progressBar, 0, city.getBin2());
                anim.setDuration(1000);
                progressBar.startAnimation(anim);

            });

            garbagebin_btn.setOnClickListener(view ->
            {
                txtbinname.setText(garbagebin_btn.getText().toString());
                ProgressBarAnimation anim = new ProgressBarAnimation(progressBar, 0, city.getBin3());
                anim.setDuration(1000);
                progressBar.startAnimation(anim);
            });
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            txtbinname.setText(orgabin_btn.getText().toString());
            ProgressBarAnimation anim = new ProgressBarAnimation(progressBar, 0, city.getBin1());
            anim.setDuration(1000);
            progressBar.startAnimation(anim);
        }

    }

    public class ProgressBarAnimation extends Animation
    {

        private ProgressBar progressBar;
        private double from;
        private double to;

        public ProgressBarAnimation(ProgressBar progressBar, double from, double to)
        {
            super();
            this.progressBar = progressBar;
            this.from = from;
            this.to = to;
        }

        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t)
        {
            super.applyTransformation(interpolatedTime, t);
            double value = from + (to - from) * interpolatedTime;
            progressBar.setProgress((int) value);
            txtbattery.setText(String.format("%.2f", value) + "%");
        }

    }

    private void setupid()
    {
        txtaddress = (TextView) v.findViewById(R.id.txtview_houseaddress);
        txtbattery = (TextView) v.findViewById(R.id.txtview_percentbin);
        txtbinname = (TextView) v.findViewById(R.id.txtview_nameofbin);
        progressBar = (ProgressBar) v.findViewById(R.id.bin_progressbar);
        orgabin_btn = (Button) v.findViewById(R.id.greenbinbtn);
        recylebin_btn = (Button) v.findViewById(R.id.bluebinbtn);
        garbagebin_btn = (Button) v.findViewById(R.id.blackbinbtn);
    }
}