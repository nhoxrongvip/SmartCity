package ca.chesm.it.smartcity.GarbageBinControl;

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

import ca.chesm.it.smartcity.R;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder>
{

    private List<City> mListCity;
    private FragmentActivity mFragment;

    public CityAdapter(FragmentActivity fragment,List<City> listCity)
    {
        this.mFragment = fragment;
        this.mListCity = listCity;
    }

    @NonNull
    @Override
    public CityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.glistview, parent,false);
        return new CityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityViewHolder holder, int position)
    {
        final City city = mListCity.get(position);
        if(city == null)
        {
            return;
        }
        holder.imgv.setImageDrawable(mFragment.getResources().getDrawable(R.drawable.house));
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
        fragmentTransaction.replace(R.id.container,fragment);
        fragmentTransaction.addToBackStack(null);
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

    //Cityviewholder
    public class CityViewHolder extends RecyclerView.ViewHolder
    {

        private TextView tvAddress;
        private RelativeLayout layoutitem;
        private ImageView imgv;

        public CityViewHolder(@NonNull View itemView)
        {
            super(itemView);
            layoutitem = itemView.findViewById(R.id.glayoutitem);
            tvAddress = itemView.findViewById(R.id.txtviewcityholderadd);
            imgv = itemView.findViewById(R.id.gimageView);
        }
    }
}
