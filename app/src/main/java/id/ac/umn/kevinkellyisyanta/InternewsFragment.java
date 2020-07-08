package id.ac.umn.kevinkellyisyanta;

import android.app.ProgressDialog;
import android.content.Intent;
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

public class InternewsFragment extends Fragment {

    private RecyclerView recyclerView;
    private AdapterSumber adapterSumber;
    private ArrayList<Sumber> sourceArrayList;
    private RequestQueue requestQueue;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_internews, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = getView().findViewById(R.id.recViewInterTampil);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        sourceArrayList = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(getActivity());
        parseJSON();
    }

    private void parseJSON() {
        final String url = "https://newsapi.org/v2/sources?apiKey=2e5d07768e1249b0836a8695b0d52867";
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching International Sources ....");
        progressDialog.show();

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("sources");

                            for(int i = 0; i < jsonArray.length(); i++){
                                JSONObject hit = jsonArray.getJSONObject(i);

                                String idSource = hit.getString("id");
                                String nameSource = hit.getString("name");
                                String descSource = hit.getString("description");
                                String urlSource = hit.getString("url");

                                sourceArrayList.add(new Sumber(idSource, nameSource, descSource, "https://besticon-demo.herokuapp.com/icon?url="+urlSource+"&size=80..120..200"));
                            }

                            adapterSumber = new AdapterSumber(getActivity(), sourceArrayList);
                            recyclerView.setAdapter(adapterSumber);
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
