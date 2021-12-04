//Dung Ly N01327929
//Thanh Phat Lam N01335598 CENG322-RND
//Hieu Chu N01371619 CENG322-RND

package ca.chesm.it.smartcity.View.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;

import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ca.chesm.it.smartcity.View.FragMent.AirQualityFragment;
import ca.chesm.it.smartcity.View.FragMent.AppInfoFragment;
import ca.chesm.it.smartcity.View.FragMent.AppSettingFragment;

import ca.chesm.it.smartcity.R;
import ca.chesm.it.smartcity.View.FragMent.SnowLevelFragment;
import ca.chesm.it.smartcity.View.GarbageBinControl.GarbageFragment;
import ca.chesm.it.smartcity.View.accounts.LoginActivity;

public class MainActivity extends AppCompatActivity
{

    BottomNavigationView botnavigation;
    SharedPreferences sharedPreferences;
    double longitude, latitude;
    FusedLocationProviderClient client;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    boolean sw;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = this.getSharedPreferences("SmartCity", Context.MODE_PRIVATE);
        sw = sharedPreferences.getBoolean("Switch", false);
        if (sw == false)
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        } else
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }


        botnavigation = (BottomNavigationView) findViewById(R.id.botnavigation);
        botnavigation.setOnNavigationItemSelectedListener(bottomNavMethod);
        toolbar = findViewById(R.id.toolbar);
        navigationView= findViewById(R.id.navigation_view);
        drawerLayout=findViewById(R.id.drawelayout);
        toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.Open,R.string.Close);
        toggle.syncState();

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
        View headerview = navigationView.getHeaderView(0);
        TextView txtuser= headerview.findViewById(R.id.txtusername);
        TextView txtfname = headerview.findViewById(R.id.txtefullname);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        txtuser.setText(firebaseUser.getEmail());
        txtfname.setText(firebaseUser.getDisplayName());
         navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
             @Override
             public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                 switch (item.getItemId()){
                     case  R.id.nav_signout:

                         AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                         builder.setMessage("Do you want sign out ?");
                         builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialogInterface, int i) {
                                 firebaseAuth.signOut();
                                 startActivity(new Intent(MainActivity.this, LoginActivity.class));

                             }
                         });
                         builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialogInterface, int i) {

                             }
                         });
                         builder.show();

                 }

                 return true;
             }
         });





        client = LocationServices.getFusedLocationProviderClient(MainActivity.this);

        AirQualityFragment airFrag = new AirQualityFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.container, airFrag, "Air Fragment").commit();


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_overflow, menu);
        return super.onCreateOptionsMenu(menu);
    }

//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item)
//    {
//        Fragment frag;
//
//        switch (item.getItemId())
//        {
//            case android.R.id.home:
//                moveTaskToBack(true);
//                return true;
//            case R.id.menu_info:
//                frag = new AppInfoFragment();
//                getSupportFragmentManager().beginTransaction().replace(R.id.container, frag).commit();
//                return true;
//
//            case R.id.search1:
////                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
//                //Check Conditions
//                if (ActivityCompat.checkSelfPermission(MainActivity.this,
//                        android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
//                        && ActivityCompat.checkSelfPermission(MainActivity.this,
//                        Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
//                {
//                    //When both permission are granted, call the method to get Long and Lat
//                    getCurrentLocation();
//
//
//                } else
//                {
//                    //When permission is not granted, request permission
//
//                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
//                }
//
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                editor.putString("longitude", String.valueOf(longitude));
//                editor.putString("latitude", String.valueOf(latitude));
//                editor.apply();
//                return true;
//            case R.id.overflow1:
//                Toast.makeText(this, " App Settings", Toast.LENGTH_SHORT).show();
//                frag = new AppSettingFragment();
//                getSupportFragmentManager().beginTransaction().replace(R.id.container, frag).commit();
//                return true;
//            case R.id.overflow2:
//                Toast.makeText(this, "Wifi", Toast.LENGTH_SHORT).show();
//                return true;
//            case R.id.overflow3:
//                Toast.makeText(this, "Bluetooh", Toast.LENGTH_SHORT).show();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        //Check condition
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100)
        {
            try
            {
                getCurrentLocation();
            } catch (Exception e)
            {
                e.printStackTrace();
            }

        }
    }

    @SuppressLint("MissingPermission")
    public void getCurrentLocation()
    {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //Check Condition: GPS_PROVIDER and NETWORK_PROVIDER
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER))
        {
            //When both permissions are granted, get last location
            client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>()
            {
                @Override
                public void onComplete(@NonNull Task<Location> task)
                {
                    try
                    {
                        Location l = task.getResult();
                        if (l != null)
                        {
                            //When location is not null, get Latitude and Longitude and display Toast
                            latitude = l.getLatitude();
                            longitude = l.getLongitude();

                        } else
                        {
                            //Initialize locaiton request
                            LocationRequest locationRequest = new LocationRequest().setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                                    .setInterval(10000)
                                    .setFastestInterval(1000)
                                    .setNumUpdates(1);
                            //Initialize location call back
                            LocationCallback locationCallback = new LocationCallback()
                            {
                                @Override
                                public void onLocationResult(@NonNull LocationResult locationResult)
                                {
                                    Location l1 = locationResult.getLastLocation();
                                    latitude = l1.getLatitude();
                                    longitude = l1.getLongitude();

                                }
                            };
                            //Request locaiton update
                            client.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                        }
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("longitude", String.valueOf(longitude));
                        editor.putString("latitude", String.valueOf(latitude));
                        editor.apply();
                    } catch (Exception e)
                    {

                    }
                }
            });

        } else
        {
            //When location service is not enable, go to location setting
            startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.app_name)
                    .setCancelable(false)
                    .setMessage("Do you really want to exit?")
                    .setPositiveButton("Yes", (dialog, which) -> finishAffinity())
                    .setNegativeButton("No", (dialog, which) -> dialog.cancel())
                    .create()
                    .show();
            return true;
        }
        return super.onKeyLongPress(keyCode, event);
    }

    //Back button pressed
    @Override
    public void onBackPressed()
    {
        new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setCancelable(false)
                .setMessage("Do you really want to exit?")
                .setPositiveButton("Yes", (dialog, which) -> finishAffinity())
                .setNegativeButton("No", (dialog, which) -> dialog.cancel())
                .create()
                .show();
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = item ->
    {
        Fragment frag = null;
        //Set the Fragment that is need to show
        switch (item.getItemId())
        {
            case R.id.AirQualityFragment:
                frag = new AirQualityFragment();
                try
                {
                    getCurrentLocation();
                } catch (Exception e)
                {
                    e.printStackTrace();
                }

                break;
            case R.id.Fragment2:
                frag = new GarbageFragment();
                break;
            case R.id.CityLight:
                frag = new CityLightFragMent();
                break;
            case R.id.SnowLevel:
                frag = new SnowLevelFragment();
                break;

        }
        item.setChecked(true);
        //Change the fragment to the selected fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.container, frag).commit();
        return true;
    };



}