package ca.chesm.it.smartcity.View.GarbageBinControl;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.common.base.MoreObjects;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

import ca.chesm.it.smartcity.R;
import ca.chesm.it.smartcity.View.accounts.RegisterActivity;
import ca.chesm.it.smartcity.models.City;

public class Garbagebin_Fragment extends Fragment
{

    private View v;
    private TextView txtaddress, txtbattery, txtbinname;
    private Button orgabin_btn, recylebin_btn, garbagebin_btn, randombtn, collectbtn;
    private SharedPreferences sharedPreferences;
    private String binname;
    private ProgressBar progressBar;
    private City city;

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
        sharedPreferences = this.getActivity().getSharedPreferences("SmartCity", Context.MODE_PRIVATE);
        v = inflater.inflate(R.layout.garbagebin_fragment, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel("Smart City", "Smart City", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getContext().getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        Bundle bundle = this.getArguments();
        setupid();
        if (bundle != null)
        {

        }
        city = (City) bundle.get("city");
        ProgressbarBin progressbarBin = new ProgressbarBin();
        progressbarBin.execute();

        txtaddress.setText(city.getAddress());
        updatevalue();
        randombtn.setOnClickListener(view ->
        {
            randomvalueupdate();
        });
        collectbtn.setOnClickListener(view ->
        {
            collect();
        });
        return v;
    }

    public void updatevalue()
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Garbage").child("City").child(city.getName()).child(String.valueOf(city.getId()));
        ref.addValueEventListener(new ValueEventListener()
        {
            double temp1 = city.getBin1();
            double temp2 = city.getBin2();
            double temp3 = city.getBin3();
            double bin1, bin2, bin3;

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                try
                {
                    String cbin1 = "Organic Bin Collect";
                    String cbin2 = "Garbage Bin Collect";
                    String cbin3 = "Recycle Bin Collect";
                    ProgressBarAnimation anim = null;
                    bin1 = city.getBin1();
                    bin2 = city.getBin2();
                    bin3 = city.getBin3();
                    boolean collect1 = snapshot.child(cbin1).getValue(Boolean.class);
                    boolean collect2 = snapshot.child(cbin2).getValue(Boolean.class);
                    boolean collect3 = snapshot.child(cbin3).getValue(Boolean.class);
                    if (bin1 >= 80)
                    {
                        if (!collect1 != false)
                        {
                            notibuild("Your Organic Bin is almost full ", 1);
                        }
                    } else if (bin1 == 100)
                    {
                        if (collect2 != false)
                        {
                            notibuild("Your Organic Bin is full ", 1);
                        }
                    } else
                    {
                        ref.child(cbin1).setValue(false);
                    }

                    if (bin2 >= 80)
                    {
                        if (!collect2 != false)
                        {
                            notibuild("Your Recycle Bin is almost full ", 2);
                        }
                    }
                    else if (bin2 == 100)
                    {
                        if (!collect2 != false)
                        {
                            notibuild("Your Recycle Bin is full ", 2);
                        }
                    } else
                    {
                        ref.child(cbin2).setValue(false);
                    }

                    if (bin3 >= 80)
                    {
                        if (!collect3 != false)
                        {
                            notibuild("Your Garbage Bin is almost full ", 3);
                        }
                    } else if (bin3 == 100)
                    {
                        if (!collect3 != false)
                        {
                            notibuild("Your Garbage Bin is full ", 3);
                        }
                    } else
                    {
                        ref.child(cbin3).setValue(false);
                    }

                    if (txtbinname.getText().equals("Organic Bin"))
                    {
                        if (bin1 != temp1)
                        {
                            anim = new ProgressBarAnimation(progressBar, temp1, bin1);
                            city.setBin1(bin1);
                        } else
                        {
                            anim = new ProgressBarAnimation(progressBar, bin1, temp1);
                            city.setBin1(bin1);
                        }
                    } else if (txtbinname.getText().equals("Recycle Bin"))
                    {
                        if (bin2 != temp2)
                        {
                            anim = new ProgressBarAnimation(progressBar, temp2, bin2);
                            city.setBin2(bin2);

                        } else
                        {
                            anim = new ProgressBarAnimation(progressBar, bin2, temp2);
                            city.setBin2(bin2);
                        }
                    } else if (txtbinname.getText().equals("Garbage Bin"))
                    {
                        if (bin3 != temp3)
                        {
                            anim = new ProgressBarAnimation(progressBar, temp3, bin3);
                            city.setBin3(bin3);

                        } else
                        {
                            anim = new ProgressBarAnimation(progressBar, bin3, temp3);
                            city.setBin3(bin3);
                        }
                    }

                    anim.setDuration(1000);
                    progressBar.startAnimation(anim);
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

    private void notibuild(String mess, int id)
    {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(), "Smart City");
        builder.setContentTitle("Garbage Bin Controller");
        builder.setContentText(mess + getString(R.string.collect_mess));
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setAutoCancel(true);

        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getContext());
        managerCompat.notify(id, builder.build());

    }

    private void collect()
    {
        double bin1 = city.getBin1();
        double bin2 = city.getBin2();
        double bin3 = city.getBin3();
        String cbin1 = "Organic Bin Collect";
        String cbin2 = "Garbage Bin Collect";
        String cbin3 = "Recycle Bin Collect";
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Garbage").child("City").child(city.getName()).child(String.valueOf(city.getId()));
        if (txtbinname.getText().toString().equals("Organic Bin"))
        {
            if (bin1 >= 80)
            {
                ref.child(cbin1).setValue(true);
                Toast.makeText(getContext(), "Collect submmited !", Toast.LENGTH_SHORT).show();
            } else
            {
                Toast.makeText(getContext(), cbin1 + " is not full !", Toast.LENGTH_SHORT).show();
            }
        }
        if (txtbinname.getText().toString().equals("Garbage Bin"))
        {
            if (bin2 >= 80)
            {
                ref.child(cbin2).setValue(true);
                Toast.makeText(getContext(), "Collect submmited !", Toast.LENGTH_SHORT).show();
            } else
            {
                Toast.makeText(getContext(), cbin2 + " is not full !", Toast.LENGTH_SHORT).show();
            }
        }
        if (txtbinname.getText().toString().equals("Recycle Bin"))
        {
            if (bin3 >= 80)
            {
                ref.child(cbin3).setValue(true);
                Toast.makeText(getContext(), "Collect submmited !", Toast.LENGTH_SHORT).show();
            } else
            {
                Toast.makeText(getContext(), cbin3 + " is not full !", Toast.LENGTH_SHORT).show();
            }
        }


    }

    private double rndDataforBin()
    {
        int max = 100;
        int min = 0;
        Random rad = new Random();
        double radNum = min + (max - min) * rad.nextDouble();
        return radNum;
    }


    //Use to generate random value
    private void randomvalueupdate()
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Garbage").child("City").child(city.getName()).child(String.valueOf(city.getId()));
        String rad = String.format("%.2f", rndDataforBin());
        String rad2 = String.format("%.2f", rndDataforBin());
        String rad3 = String.format("%.2f", rndDataforBin());
        double number = Double.parseDouble(rad);
        double number1 = Double.parseDouble(rad2);
        double number2 = Double.parseDouble(rad3);
        city.setBin1(number);
        city.setBin2(number1);
        city.setBin3(number2);
        ref.child(orgabin_btn.getText().toString()).setValue(number);
        ref.child(recylebin_btn.getText().toString()).setValue(number1);
        ref.child(garbagebin_btn.getText().toString()).setValue(number2);
    }

    private void randomvalueupdate2()
    {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Garbage").child("City").child(city.getName()).child(String.valueOf(city.getId()));
        boolean rad = false;
        ref.child("Organic Bin Collect").setValue(rad);
        ref.child("Garbage Bin Collect").setValue(rad);
        ref.child("Recycle Bin Collect").setValue(rad);
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
                binname = orgabin_btn.getText().toString();
                txtbinname.setText(binname);
                ProgressBarAnimation anim = new ProgressBarAnimation(progressBar, 0, city.getBin1());
                anim.setDuration(1000);
                progressBar.startAnimation(anim);
            });

            recylebin_btn.setOnClickListener(view ->
            {
                binname = recylebin_btn.getText().toString();
                txtbinname.setText(binname);
                ProgressBarAnimation anim = new ProgressBarAnimation(progressBar, 0, city.getBin2());
                anim.setDuration(1000);
                progressBar.startAnimation(anim);

            });

            garbagebin_btn.setOnClickListener(view ->
            {
                binname = garbagebin_btn.getText().toString();
                txtbinname.setText(binname);
                ProgressBarAnimation anim = new ProgressBarAnimation(progressBar, 0, city.getBin3());
                anim.setDuration(1000);
                progressBar.startAnimation(anim);
            });
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
            binname = orgabin_btn.getText().toString();
            txtbinname.setText(binname);
            ProgressBarAnimation anim = new ProgressBarAnimation(progressBar, 0, city.getBin1());
            anim.setDuration(1000);
            progressBar.startAnimation(anim);
        }

    }

    private class ProgressBarAnimation extends Animation
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

    @Override
    public void onResume()
    {
        super.onResume();
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
        collectbtn = v.findViewById(R.id.collectbtn);
        randombtn = v.findViewById(R.id.randombtn);
    }
}