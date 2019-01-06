package com.example.senix.diablorip.fragments;

import android.support.v4.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.example.senix.diablorip.model.Skills;
import com.example.senix.diablorip.adapters.SkillsAdapter;
import com.example.senix.diablorip.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;




public class WitchDoctorFragment extends Fragment {

    private RecyclerView rList;
    private List<Skills> skillsList;
    private RequestQueue mRequestQueue;
    private RecyclerView.Adapter adapter;
    private OnSkillSelected callback;



    private static final String api = "https://us.api.blizzard.com/d3/data/hero/witch-doctor?locale=en_US&access_token=USVlM3J83VCmiS9aM42qk64i1Pmh23yIIY";
    private static final String single = "https://us.api.blizzard.com/d3/data/hero/barbarian/skill/bash?locale=en_US&access_token=USwSAx9NcNLmQAdw7THK90ESjoJYhXV2Fc";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_skills, container, false);

        rList = view.findViewById(R.id.lista);

        rList.setHasFixedSize(true);
        rList.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        skillsList = new ArrayList<>();

        getData();
        return view;
    }



    private void getData() {
        skillsList.clear();
        mRequestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, api, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject skills = response.getJSONObject("skills");
                            JSONArray jsonArray = skills.getJSONArray("active");

                            for(int i = 0; i < jsonArray.length() ; i++){
                                JSONObject skill = jsonArray.getJSONObject(i);
                                String nombreSkill = skill.getString("name");
                                String descriptionSkill = skill.getString("description");
                                int nivel = skill.getInt("level");
                                String imagen = skill.getString("icon");
                                String imagenUrl = "http://media.blizzard.com/d3/icons/skills/42/"+imagen+".png";

                                Skills Skill1 = new Skills(nombreSkill, descriptionSkill,nivel, imagenUrl);
                                skillsList.add(Skill1);


                            }
                            adapter = new SkillsAdapter(skillsList, getActivity().getApplicationContext(), new SkillsAdapter.OnItemClickListener() {
                                @Override
                                public void onItemClick(Skills skills, int position) {
                                    callback.onChange(skills);
                                }
                            });
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




    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (OnSkillSelected) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString());
        }
    }

    public interface OnSkillSelected {
        public void onChange(Skills skills);
    }
}
