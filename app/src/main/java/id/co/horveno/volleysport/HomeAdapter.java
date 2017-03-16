package id.co.horveno.volleysport;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ASUS on 01/03/2017.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder>{

    Context context;
    ArrayList<HashMap<String, String>> list_home;

    public HomeAdapter(MainActivity kategori, ArrayList<HashMap<String, String>> list_data) {
        this.context = kategori;
        this.list_home = list_data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_home, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(context)
                .load(URL.BASE_IMG+ list_home.get(position).get("gambar"))
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.imagehome);
        holder.txt_home.setText(list_home.get(position).get("nama"));
        holder.card_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Detail.class);
                intent.putExtra("nama",list_home.get(position).get("nama"));
                intent.putExtra("TTL",list_home.get(position).get("TTL"));
                intent.putExtra("gambar",list_home.get(position).get("gambar"));
                intent.putExtra("alamat",list_home.get(position).get("alamat"));
                intent.putExtra("blog",list_home.get(position).get("blog"));
                intent.putExtra("facebook",list_home.get(position).get("facebook"));
                intent.putExtra("email",list_home.get(position).get("email"));
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_home.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_home;
        ImageView imagehome;
        CardView card_home;

        public ViewHolder(View itemView) {
            super(itemView);
            card_home = (CardView)itemView.findViewById(R.id.card);
            txt_home = (TextView)itemView.findViewById(R.id.txtnama_home);
            imagehome = (ImageView)itemView.findViewById(R.id.img_home);

        }
    }
}
