package id.ac.umn.kevinkellyisyanta;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterSumber extends RecyclerView.Adapter<AdapterSumber.SumberViewHolder> {

    private Context context;
    private ArrayList<Sumber> listSumber;
    private OnitemClickListener listener;

    public interface OnitemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnitemClickListener listener){
        this.listener = listener;
    }

    public AdapterSumber(Context context, ArrayList<Sumber> listSumber) {
        this.context = context;
        this.listSumber = listSumber;
    }

    @NonNull
    @Override
    public SumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.cardviewsumber, parent, false);
        return new SumberViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SumberViewHolder holder, final int position) {
        Sumber currSumber = listSumber.get(position);

        String id = currSumber.getIdSum();
        String name = currSumber.getNameSum();
        String desc = currSumber.getDescSum();
        String url = currSumber.getUrlSum();

        holder.namaSumber.setText(name);
        holder.descSumber.setText(desc);
        Picasso.get().load(url).fit().centerCrop().into(holder.imgSumber);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent isiBeritadariSumber = new Intent(context, TampilBeritaDariSumbers.class);
                Sumber clickSumber = listSumber.get(position);

                isiBeritadariSumber.putExtra("extraIdSumber", clickSumber.getIdSum());
                isiBeritadariSumber.putExtra("extraNamaSumber", clickSumber.getNameSum());

                context.startActivity(isiBeritadariSumber);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listSumber.size();
    }

    public class SumberViewHolder extends RecyclerView.ViewHolder{

        public ImageView imgSumber;
        public TextView namaSumber, descSumber;

        public SumberViewHolder(View itemView) {
            super(itemView);
            imgSumber = itemView.findViewById(R.id.imgsumber);
            namaSumber = itemView.findViewById(R.id.namasumber);
            descSumber = itemView.findViewById(R.id.descsumber);
        }
    }
}
