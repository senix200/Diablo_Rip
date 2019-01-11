package com.example.senix.diablorip.adapters;


import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;

import com.example.senix.diablorip.R;
import com.example.senix.diablorip.model.Skills;
import com.squareup.picasso.Picasso;

import java.util.List;
public class SkillsAdapter extends RecyclerView.Adapter<SkillsAdapter.ViewHolder>{

    private List<Skills> skills;
    private Context context;
    private OnItemClickListener listener;


    public SkillsAdapter(List<Skills> skills, Context context, OnItemClickListener listener) {
        this.skills = skills;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SkillsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.skills,viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SkillsAdapter.ViewHolder viewHolder, final int i) {
        final Skills skill = skills.get(i);

        viewHolder.nombreSkill.setText(skill.getName());
        viewHolder.descripcionSkill.setText(skill.getDescripcion());
        viewHolder.levelSkill.setText(skill.getNivel()+"");

        String url = skill.getImagenURL();

        Picasso.get().load(url).fit().into(viewHolder.imagenUrl);


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(skill, i);
            }
        });



    }

    @Override
    public int getItemCount() {
        return skills.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView nombreSkill;
        public TextView descripcionSkill;
        public TextView levelSkill;
        public ImageView imagenUrl;

        public ViewHolder(View itemView) {
            super(itemView);



            nombreSkill = itemView.findViewById(R.id.nombreSkill);
            descripcionSkill = itemView.findViewById(R.id.descripcionSkill);
            levelSkill = itemView.findViewById(R.id.levelSkill);
            imagenUrl = itemView.findViewById(R.id.imagenSkill);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(Skills skill, int position);
    }

}
