package com.example.senix.diablorip.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.example.senix.diablorip.R;
import com.example.senix.diablorip.model.Item;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private Context context;
    private List<Item> items;
    private OnButtonClickedListener listener;

    public ItemAdapter(Context context, List<Item> list, OnButtonClickedListener listener) {
        this.context = context;
        this.items = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_row, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        final Item p = items.get(i);

        viewHolder.itemName.setText(p.getName());
        viewHolder.itemDescription.setText(p.getDescription());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView itemName;
        public TextView itemDescription;
        public ImageView editItem;
        public ImageView deleteItem;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.itemNameTextView);
            itemDescription = itemView.findViewById(R.id.itemDescriptionTextView);

            editItem = itemView.findViewById(R.id.editITemIcon);
            deleteItem = itemView.findViewById(R.id.deleteItemIcon);

            editItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onButtonCliked(view, getAdapterPosition());
                }
            });

            deleteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onButtonCliked(view, getAdapterPosition());
                }
            });

        }
    }

    public interface OnButtonClickedListener {
        void onButtonCliked(View v, int position);
    }
}
