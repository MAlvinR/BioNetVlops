package id.co.horveno.volleysport;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Kategori extends AppCompatActivity {

    private RecyclerView recycler;

    private RequestQueue requestQueue;
    private StringRequest stringRequest;
    HashMap<String, String> map;
    ArrayList<HashMap<String, String>> list_data;
    /*private RelativeLayout loading;
    private RelativeLayout errElement;*/
    KategoriAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kategori);
        String url = URL.BASE_URL+"kategorifreelancer.php";
        recycler = (RecyclerView) findViewById(R.id.recyclerkategori);

        /*loading = (RelativeLayout)findViewById(R.id.loadingElement);
        errElement = (RelativeLayout)findViewById(R.id.errorElement);*/

        LinearLayoutManager llm = new LinearLayoutManager(this);

        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recycler.setLayoutManager(llm);


        requestQueue = Volley.newRequestQueue(Kategori.this);

        list_data = new ArrayList<HashMap<String, String>>();

        stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("response ", response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("kategori_freelancer");
                    for (int a = 0; a < jsonArray.length(); a++) {
                        JSONObject json = jsonArray.getJSONObject(a);
                        map = new HashMap<String, String>();
                        map.put("id_kategori", json.getString("id_kategori"));
                        map.put("nama_kategori", json.getString("nama_kategori"));
                        map.put("gambar_kategori", json.getString("gambar_kategori"));
                        map.put("deskripsi_kategori", json.getString("deskripsi_kategori"));


                        list_data.add(map);
                        adapter = new KategoriAdapter(Kategori.this, list_data);
                        recycler.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                /*loading.setVisibility(View.GONE);
                errElement.setVisibility(View.GONE);*/


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                /*errElement.setVisibility(View.VISIBLE);
                loading.setVisibility(View.GONE);*/

            }
        });

        requestQueue.add(stringRequest);
    }
}
