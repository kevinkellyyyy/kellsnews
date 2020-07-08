package id.ac.umn.kevinkellyisyanta;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TampilBeritaDariSumbers extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AdapterBerita adapterBerita;
    private ArrayList<Berita> beritaArrayList;
    private RequestQueue requestQueue;
    View view;
    TextView topHead;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tampil_berita_dari_sumbers);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent idSumberIntent = getIntent();
        String idSumber = idSumberIntent.getStringExtra("extraIdSumber");
        String namaSumber = idSumberIntent.getStringExtra("extraNamaSumber");

        view = findViewById(android.R.id.content);

        topHead = findViewById(R.id.sumberberita);
        topHead.setText("Top Headlines From "+namaSumber);
        recyclerView = findViewById(R.id.recViewTampilInter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        beritaArrayList = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(this);
        parseJSON(idSumber, namaSumber);
    }

    private void parseJSON(String idSumber,String namaSumber) {
        String url = "https://newsapi.org/v2/top-headlines?sources="+idSumber+"&pageSize=40&apiKey=2e5d07768e1249b0836a8695b0d52867";
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching "+namaSumber+"\'s Top Headlines....");
        progressDialog.show();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("articles");

                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject hit = jsonArray.getJSONObject(i);

                                String judul = hit.getString("title");
                                String penulis = hit.getString("author");
                                String url = hit.getString("url");
                                String urlGambar = hit.getString("urlToImage");
                                String rilis = hit.getString("publishedAt");
                                String content = hit.getString("content");

                                beritaArrayList.add(new Berita(judul, penulis, url, urlGambar, rilis, content));
                            }

                            adapterBerita = new AdapterBerita(TampilBeritaDariSumbers.this, beritaArrayList);
                            recyclerView.setAdapter(adapterBerita);
                        }
                        catch (JSONException e){
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                progressDialog.dismiss();
            }
        });

        requestQueue.add(request);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home){
            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

}
