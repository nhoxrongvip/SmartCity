package ca.chesm.it.smartcity.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ca.chesm.it.smartcity.View.GarbageBinControl.Garbagebin_Fragment;
import ca.chesm.it.smartcity.R;
import ca.chesm.it.smartcity.models.City;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder>
{

    private List<City> mListCity;
    private FragmentActivity mFragment;

    public CityAdapter(FragmentActivity fragment, List<City> listCity)
    {
        this.mFragment = fragment;
        this.mListCity = listCity;
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.glistview, parent, false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position)
    {
        City city = mListCity.get(position);
        if (city == null)
        {
            return;
        }
        holder.tvAddress.setText(city.getAddress());
        holder.layoutitem.setOnClickListener(view ->
        {
            onClickgotodetail(city);
        });
    }

    private void onClickgotodetail(City city)
    {
        Fragment fragment = new Garbagebin_Fragment();
        FragmentManager fragmentManager = mFragment.getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.container, fragment);
        fragmentTransaction.addToBackStack(null);
        Bundle bundle = new Bundle();
        bundle.putSerializable("city", city);
        fragment.setArguments(bundle);
        fragmentTransaction.commit();

    }

    @Override
    public int getItemCount()
    {
        if (mListCity != null)
        {
            return mListCity.size();
        }
        return 0;
    }

    public void release()
    {
        mFragment = null;
    }

    //Cityviewholder
    public class CityViewHolder extends RecyclerView.ViewHolder
    {

        private TextView tvAddress;
        private RelativeLayout layoutitem;

        public CityViewHolder(@NonNull View itemView)
        {
            super(itemView);
            layoutitem = itemView.findViewById(R.id.glayoutitem);
            tvAddress = itemView.findViewById(R.id.txtviewcityholderadd);
        }
    }
}
