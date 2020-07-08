package id.ac.umn.kevinkellyisyanta;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterBerita extends RecyclerView.Adapter<AdapterBerita.BeritaViewHolder> {

    private Context context;
    private ArrayList<Berita> listBerita;
    private OnitemClickListener listener;
    private Notif helper;
    final Handler handler = new Handler();

    public interface OnitemClickListener{
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnitemClickListener listener){
        this.listener = listener;
    }

    public AdapterBerita(Context context, ArrayList<Berita> listBerita) {
        this.context = context;
        this.listBerita = listBerita;
    }

    @NonNull
    @Override
    public BeritaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(context).inflate(R.layout.cardview, parent, false);
        return new BeritaViewHolder(v);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull final BeritaViewHolder holder, final int position) {
        Berita currBerita = listBerita.get(position);
        helper = new Notif(context);

        String imgUrl = currBerita.getUrlgambar();
        final String judul = currBerita.getJudul();
        final String url = currBerita.getUrl();
        String rilis = currBerita.getRilis();

        holder.judulBerita.setText(judul);
        holder.rilis.setText(rilis);
        Picasso.get().load(imgUrl).fit().centerCrop().into(holder.imgBerita, new Callback() {
            @Override
            public void onSuccess() {
                holder.progressBarImg.setVisibility(View.GONE);
            }

            @Override
            public void onError(Exception e) {

            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public boolean onLongClick(View v) {

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Notification.Builder builder = helper.getChannelNotification(judul,url);
                        helper.getManager().notify(1, builder.build());
                    }
                }, 5000);


                Toast.makeText(v.getContext(), "The data has been registered", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailIntent = new Intent(context, IsiBeritaDetil.class);
                Berita clickBerita = listBerita.get(position);

                detailIntent.putExtra("extrajudul", clickBerita.getJudul());
                detailIntent.putExtra("extraimg", clickBerita.getUrlgambar());
                detailIntent.putExtra("extrarilis", clickBerita.getRilis());
                detailIntent.putExtra("extrapenulis", clickBerita.getPenulis());
                detailIntent.putExtra("extracontent", clickBerita.getContent());
                detailIntent.putExtra("extralink", clickBerita.getUrl());

                context.startActivity(detailIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listBerita.size();
    }

    public class BeritaViewHolder extends RecyclerView.ViewHolder{
        public ImageView imgBerita;
        public TextView judulBerita, penulisBerita, rilis, url;
        public ProgressBar progressBarImg;

        public BeritaViewHolder(View itemView) {
            super(itemView);
            imgBerita = itemView.findViewById(R.id.imgNews);
            judulBerita = itemView.findViewById(R.id.title);
            rilis = itemView.findViewById(R.id.publishedAt);
            progressBarImg = itemView.findViewById(R.id.progress_load_img);
        }
    }
}
