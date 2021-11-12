//Dung Ly N01327929
//Thanh Phat Lam N01335598 CENG322-RND
//Hieu Chu N01371619 CENG322-RND

package ca.chesm.it.smartcity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import ca.chesm.it.smartcity.GarbageBinControl.GarbageFragment;

public class MainActivity extends AppCompatActivity
{

    BottomNavigationView botnavigation;
    SharedPreferences sharedPreferences;
    boolean sw;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreferences = this.getSharedPreferences("Switch", Context.MODE_PRIVATE);
        sw = sharedPreferences.getBoolean("Switch",false);
        if (sw == false)
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        } else
        {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        botnavigation = (BottomNavigationView) findViewById(R.id.botnavigation);
        botnavigation.setOnNavigationItemSelectedListener(bottomNavMethod);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new AirQualityFragment()).commit();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_overflow, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        Fragment frag;

        switch (item.getItemId())
        {
            case R.id.menu_info:
                frag = new AppInfoFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, frag).commit();
                return true;

            case R.id.search1:
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.overflow1:
                Toast.makeText(this, " App Settings", Toast.LENGTH_SHORT).show();
                frag = new AppSettingFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.container, frag).commit();
                return true;
            case R.id.overflow2:
                Toast.makeText(this, "Wifi", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.overflow3:
                Toast.makeText(this, "Bluetooh", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
                break;
            case R.id.Fragment2:
                frag = new GarbageFragment();
                break;
            case R.id.CityLight:
                frag = new CityLight();
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