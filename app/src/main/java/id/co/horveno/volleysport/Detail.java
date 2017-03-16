package id.co.horveno.volleysport;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

public class Detail extends AppCompatActivity {
    TextView nama;
    TextView ttl;
    TextView blog;
    TextView gmail;
    TextView fb;
    TextView alamat;
    String gambar;
    ImageView image_detail;
    String nampungblog;
    private Uri imageUri;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fabdetail = (FloatingActionButton)findViewById(R.id.fabdetail);
        fabdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                imageUri = Uri.parse(URL.BASE_IMG+gambar);

                intent = new Intent(Intent.ACTION_SEND);
//text
                String sharedata = "Nama : "+nama.getText().toString()+"\n"+"Alamat : "+alamat.getText().toString()+"\n"+"Tanggal Lahir : "+ttl.getText().toString()+"\n"+"Blog : "+blog.getText().toString()+"\n"+"Email : "+gmail.getText().toString()+"\n"+"Facebook : "+fb.getText().toString();
                intent.putExtra(Intent.EXTRA_SUBJECT, "Biodata "+nama.getText().toString());
                intent.putExtra(Intent.EXTRA_TEXT, sharedata);
//image
                intent.putExtra(Intent.EXTRA_STREAM, imageUri);
//type of things
                intent.setType("*/*");
//sending
                startActivity(intent);
            }
        });
        Button visit = (Button)findViewById(R.id.visit_blog_detail);
        visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://"+nampungblog;
                Intent toBrowser = new Intent(Intent.ACTION_VIEW);
                toBrowser.setData(Uri.parse(url));
                startActivity(toBrowser);
            }
        });

        nama = (TextView)findViewById(R.id.text_nama_detail);
        ttl = (TextView)findViewById(R.id.text_ttl_detail);
        blog = (TextView)findViewById(R.id.text_blog_detail);
        gmail = (TextView)findViewById(R.id.text_gmail_detail);
        fb = (TextView)findViewById(R.id.text_fb_detail);
        alamat = (TextView)findViewById(R.id.text_alamat_detail);
        image_detail = (ImageView)findViewById(R.id.image_detail);

        nama.setText(getIntent().getStringExtra("nama"));
        ttl.setText(getIntent().getStringExtra("TTL"));
        nampungblog = getIntent().getStringExtra("blog");
        blog.setText(nampungblog);
        gmail.setText(getIntent().getStringExtra("email"));
        fb.setText(getIntent().getStringExtra("facebook"));
        alamat.setText(getIntent().getStringExtra("alamat"));
        gambar = getIntent().getStringExtra("gambar");

        Glide.with(getApplicationContext())
                .load(URL.BASE_URL + gambar)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(image_detail);


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
