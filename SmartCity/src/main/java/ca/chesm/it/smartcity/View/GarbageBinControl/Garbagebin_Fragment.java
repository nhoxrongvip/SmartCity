package ca.chesm.it.smartcity.View.GarbageBinControl;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
                pool.shutdown();
                pool = Executors.newFixedThreadPool(1);
                txtbinname.setText(orgabin_btn.getText().toString());
                pool.execute(new progressbaraniation(city.getBin1()+0.01));
            });

            recylebin_btn.setOnClickListener(view ->
            {
                pool.shutdown();
                pool = Executors.newFixedThreadPool(1);
                txtbinname.setText(recylebin_btn.getText().toString());
                pool.execute(new progressbaraniation(city.getBin2()));
            });

            garbagebin_btn.setOnClickListener(view ->
            {
                pool.shutdown();
                pool = Executors.newFixedThreadPool(1);
                txtbinname.setText(garbagebin_btn.getText().toString());
                pool.execute(new progressbaraniation(city.getBin3()));
            });
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            txtbinname.setText(orgabin_btn.getText().toString());
            pool = Executors.newFixedThreadPool(1);
            pool.execute(new progressbaraniation(city.getBin1()+0.01));
        }

    }

    class progressbaraniation implements Runnable
    {

        double number;

        public progressbaraniation(double number)
        {
            this.number = number;
        }

        @Override
        public void run()
        {
            for (double i = 0; i < number; i += 0.01)
            {
                    progressBar.setProgress((int) i);
                    txtbattery.setText(String.format("%.2f", i) + "%");
            }
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