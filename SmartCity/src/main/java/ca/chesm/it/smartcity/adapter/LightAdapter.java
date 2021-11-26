package ca.chesm.it.smartcity.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ca.chesm.it.smartcity.R;
import ca.chesm.it.smartcity.models.Lights;

public class LightAdapter extends RecyclerView.Adapter<LightAdapter.ViewHodler> {

    private List<Lights> arr;
    private Context context;

    public LightAdapter(List<Lights> arr, Context context) {
        this.arr = arr;
        this.context = context;
    }

    @NonNull
    @Override
    public LightAdapter.ViewHodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(context).inflate(R.layout.donglight,parent,false);
        return  new ViewHodler(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LightAdapter.ViewHodler holder, int position) {

        Lights lights = arr.get(position);

        holder.lightVIew.setImageResource(lights.isState() ? R.drawable.light_on : R.drawable.light_off);
        int k = position + 1;
        holder.txtlight.setText("Light "+k);

    }

    @Override
    public int getItemCount() {
        return arr.size();
    }

    public class ViewHodler extends RecyclerView.ViewHolder {
        ImageView lightVIew;
        TextView txtlight;
        public ViewHodler(@NonNull View itemView) {
            super(itemView);
            lightVIew = itemView.findViewById(R.id.lightNumber);
            txtlight = itemView.findViewById(R.id.txtlight);
        }
    }
}
