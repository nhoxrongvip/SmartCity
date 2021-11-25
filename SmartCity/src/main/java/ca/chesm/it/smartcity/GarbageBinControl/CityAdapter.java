package ca.chesm.it.smartcity.GarbageBinControl;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ca.chesm.it.smartcity.CityLight;
import ca.chesm.it.smartcity.R;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.CityViewHolder>
{

    private List<City> mListCity;

    public CityAdapter(List<City> listCity)
    {
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
        City city = mListCity.get(position);
        if(city == null)
        {
            return;
        }
        holder.tvAddress.setText(city.getAddress());
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

        public CityViewHolder(@NonNull View itemView)
        {
            super(itemView);
            tvAddress = itemView.findViewById(R.id.txtviewcityholderadd);
        }
    }
}
