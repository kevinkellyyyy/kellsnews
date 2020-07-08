package id.ac.umn.kevinkellyisyanta;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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

public class SearchnewsFragment extends Fragment {

    EditText searchQuery;
    Button btnSearch;
    TextView testtext;

    private RecyclerView recyclerView;
    private AdapterBerita adapterBerita;
    private ArrayList<Berita> beritaArrayList;
    private RequestQueue requestQueue;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_searchnews, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchQuery = getView().findViewById(R.id.editseachnews);
        btnSearch = getView().findViewById(R.id.btnSearchnews);
        testtext = getView().findViewById(R.id.texttest);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                testtext.setText("Search result for \""+searchQuery.getText().toString()+"\"");

                recyclerView = getView().findViewById(R.id.recViewTampilDariSearch);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                beritaArrayList = new ArrayList<>();

                requestQueue = Volley.newRequestQueue(getActivity());
                parseJSON(searchQuery.getText().toString());

            }
        });
    }

    private void parseJSON(String query) {
        String url = "https://newsapi.org/v2/everything?q="+query+"&pageSize=100&apiKey=2e5d07768e1249b0836a8695b0d52867";
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Searching for \""+query+"\"");
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

                            adapterBerita = new AdapterBerita(getActivity(), beritaArrayList);
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
}
