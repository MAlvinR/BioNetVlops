package id.co.horveno.volleysport;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by ASUS on 01/03/2017.
 */

public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.ViewHolder>{

    Context context;
    ArrayList<HashMap<String, String>> list_data;

    public KategoriAdapter(Kategori kategori, ArrayList<HashMap<String, String>> list_data) {
        this.context = kategori;
        this.list_data = list_data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_kategori, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(context)
                .load(URL.BASE_IMG + list_data.get(position).get("gambar_kategori"))
                .crossFade()
                .into(holder.imagekategori);
        holder.txt_kategori.setText(list_data.get(position).get("nama_kategori"));
        holder.card_kategori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), MainActivity.class);
                intent.putExtra("id_kategori",list_data.get(position).get("id_kategori"));
                intent.putExtra("nama_kategori",list_data.get(position).get("nama_kategori"));
                intent.putExtra("gambar_kategori",list_data.get(position).get("gambar_kategori"));
                intent.putExtra("deskripsi_kategori",list_data.get(position).get("deskripsi_kategori"));

                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list_data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txt_kategori;
        ImageView imagekategori;
        CardView card_kategori;

        public ViewHolder(View itemView) {
            super(itemView);
            card_kategori = (CardView)itemView.findViewById(R.id.card_kategori);
            txt_kategori = (TextView)itemView.findViewById(R.id.textkategori);
            imagekategori = (ImageView)itemView.findViewById(R.id.imagekategori);

        }
    }
}
