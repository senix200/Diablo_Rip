package com.example.senix.diablorip.adapters;


import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.example.senix.diablorip.R;
import com.example.senix.diablorip.model.Runes;

import java.util.List;



public class RunesAdapter extends RecyclerView.Adapter<RunesAdapter.ViewHolder>{

    private List<Runes> runes;
    private Context context;


    public RunesAdapter(Context context, List<Runes> runes) {
        this.context = context;
        this.runes = runes;
    }

    @NonNull
    @Override
    public RunesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.details_skills_layout,viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RunesAdapter.ViewHolder viewHolder, final int i) {
        final Runes rune = runes.get(i);

        viewHolder.nombreSkill.setText(rune.getName());
        viewHolder.descripcionSkill.setText(rune.getDescripcion());
        viewHolder.levelSkill.setText(rune.getNivel()+"");



    }

    @Override
    public int getItemCount() {
        return runes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nombreSkill;
        public TextView descripcionSkill;
        public TextView levelSkill;

        public ViewHolder(View itemView) {
            super(itemView);
            nombreSkill = itemView.findViewById(R.id.nombreSkill);
            descripcionSkill = itemView.findViewById(R.id.descripcionSkill);
            levelSkill = itemView.findViewById(R.id.levelSkill);
        }
    }

}
