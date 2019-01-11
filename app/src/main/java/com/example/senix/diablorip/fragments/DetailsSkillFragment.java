package com.example.senix.diablorip.fragments;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.senix.diablorip.R;
import com.example.senix.diablorip.adapters.RunesAdapter;
import com.example.senix.diablorip.model.Runes;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;

public class DetailsSkillFragment extends Fragment {
    private RecyclerView rList;
    private List<Runes> runesList;
    private RequestQueue mRequestQueue;
    private RecyclerView.Adapter adapter;




    private static final String api = "https://us.api.blizzard.com/d3/data/hero/barbarian?locale=en_US&access_token=USVlM3J83VCmiS9aM42qk64i1Pmh23yIIY";
    private String single = "https://us.api.blizzard.com/d3/data/hero/barbarian/skill/bash?locale=en_US&access_token=USVlM3J83VCmiS9aM42qk64i1Pmh23yIIY";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_runes, container, false);

        rList = view.findViewById(R.id.lista);

        rList.setHasFixedSize(true);
        rList.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        runesList = new ArrayList<>();

        getData();
        return view;
    }


    private void getData() {
        runesList.clear();
        mRequestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        single = "https://us.api.blizzard.com/d3/data/hero/barbarian/skill/bash?locale=en_US&access_token=US9AA2271eyPZ2DG9FnATCZKHB1gYCYMeF";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, single, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("runes");


                            for(int i = 0; i < jsonArray.length() ; i++){
                                JSONObject skill = jsonArray.getJSONObject(i);
                                String nombreSkill = skill.getString("name");
                                String descriptionSkill = skill.getString("description");
                                int nivel = skill.getInt("level");

                                Runes rune1 = new Runes(nombreSkill, descriptionSkill,nivel);
                                runesList.add(rune1);


                            }
                            adapter = new RunesAdapter(getActivity().getApplicationContext(), runesList);
                            rList.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Volley", error.toString());

            }
        });
        mRequestQueue.add(request);
    }
}
