package id.co.horveno.volleysport;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerhome;
    String id_kategori,gambar_kategori;
    ArrayList<HashMap<String,String>> list_home;
    private ImageView headerimage;
    private TextView headertext,desktext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        headerimage = (ImageView)findViewById(R.id.headerhome_image);
        headertext = (TextView) findViewById(R.id.headerhome_text);
        desktext = (TextView)findViewById(R.id.desk_kategori);

        id_kategori = getIntent().getStringExtra("id_kategori");
        headertext.setText(getIntent().getStringExtra("nama_kategori"));
        gambar_kategori = getIntent().getStringExtra("gambar_kategori");
        desktext.setText(getIntent().getStringExtra("deskripsi_kategori"));

        Glide.with(getApplicationContext())
                .load(URL.BASE_IMG + gambar_kategori)
                .crossFade()
                .into(headerimage);

        initCollapsingToolbar();
        recyclerhome = (RecyclerView)findViewById(R.id.recyclerhome);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerhome.setLayoutManager(mLayoutManager);
        final SwipeRefreshLayout refreshLayout = (SwipeRefreshLayout)findViewById(R.id.refreshhome);
        refreshLayout.setColorSchemeResources(
                R.color.colorPrimary,
                R.color.colorPrimaryDark,
                R.color.colorAccent);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshLayout.setRefreshing(false);
                loadHome();
            }
        });
        loadHome();

    }

    private void loadHome(){
        RequestQueue req = Volley.newRequestQueue(MainActivity.this);
        list_home = new ArrayList<HashMap<String, String>>();

        StringRequest stringReq = new StringRequest(Request.Method.POST, URL.BASE_URL+"getAllFreelancer.php",
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response", response);
                try{
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("freelancer");
                    for (int a = 0; a < jsonArray.length();a++){
                        JSONObject json = jsonArray.getJSONObject(a);
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("kategori", json.getString("kategori"));
                        hashMap.put("nama", json.getString("nama"));
                        hashMap.put("TTL", json.getString("TTL"));
                        hashMap.put("gambar", json.getString("gambar"));
                        hashMap.put("alamat", json.getString("alamat"));
                        hashMap.put("blog", json.getString("blog"));
                        hashMap.put("facebook", json.getString("facebook"));
                        hashMap.put("email", json.getString("email"));
                        list_home.add(hashMap);
                        HomeAdapter homeAdapter = new HomeAdapter(MainActivity.this, list_home);
                        recyclerhome.setAdapter(homeAdapter);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> mapPostHome = new HashMap<String, String>();
                mapPostHome.put("id_kategori", id_kategori);
                return mapPostHome;
            }
        };
        req.add(stringReq);

    }




    /**
     * Initializing collapsing toolbar
     * Will show and hide the toolbar title on scroll
     */
    private void initCollapsingToolbar() {
        final CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(" ");
        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
        appBarLayout.setExpanded(true);

        // hiding & showing the title when toolbar expanded & collapsed
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar.setTitle(getString(R.string.app_name));
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbar.setTitle(" ");
                    isShow = false;
                }
            }
        });
    }

}
