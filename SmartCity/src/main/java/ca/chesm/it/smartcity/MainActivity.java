//Dung Ly N01327929
//Thanh Phat Lam N01335598 CENG322-RND
//Hieu Chu N01371619 CENG322-RND
package ca.chesm.it.smartcity;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView botnavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        botnavigation = (BottomNavigationView) findViewById(R.id.botnavigation);
        botnavigation.setOnNavigationItemSelectedListener(bottomNavMethod);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new AirQualityFragment()).commit();


    }

    //Back button pressed
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle(R.string.app_name)
                .setCancelable(false)
                .setMessage("Do you really want to exit?")
                .setPositiveButton("Yes", (dialog, which) -> finish())
                .setNegativeButton("No", (dialog, which) -> dialog.cancel())
                .create()
                .show();
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = item -> {
        Fragment frag = null;
        //Set the Fragment that is need to show
        switch (item.getItemId()) {
            case R.id.AirQualityFragment:
                frag = new AirQualityFragment();
                break;
            case R.id.Fragment2:
                frag = new GarbageFragment();
                break;
            case R.id.Fragment3:
                frag = new CityLight();
                break;
            case R.id.Fragment4:
                frag = new SnowLevelFragment();
                break;

        }
        item.setChecked(true);
        //Change the fragment to the selected fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.container, frag).commit();
        return true;
    };
}