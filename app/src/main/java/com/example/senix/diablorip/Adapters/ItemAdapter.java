package com.example.senix.diablorip.adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import com.example.senix.diablorip.R;
import com.example.senix.diablorip.model.Item;
import com.squareup.picasso.Picasso;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private Context context;
    private List<Item> items;
    private OnButtonClickedListener listener;
    private String url = "https://blzmedia-a.akamaihd.net/d3/icons/items/large/sword_2h_206_demonhunter_male.png";

    public ItemAdapter(Context context, List<Item> list, OnButtonClickedListener listener) {
        this.context = context;
        this.items = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(context).inflate(R.layout.item_row, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAdapter.ViewHolder viewHolder, final int i) {

        final Item p = items.get(i);

        viewHolder.itemName.setText(p.getName());
        viewHolder.itemDescription.setText(p.getDescription());
        if(p.getImagenUrl().equals("") || p.getImagenUrl().equals("\n")){
            Picasso.get().load(url).fit().into(viewHolder.imagenUrl);
        }else{
            Picasso.get().load(p.getImagenUrl()).fit().into(viewHolder.imagenUrl);
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItems(List<Item> list){
        items=list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView itemName;
        public TextView itemDescription;
        public ImageView editItem;
        public ImageView deleteItem;
        public ImageView imagenUrl;

        public ImageView preview;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.itemNameTextView);
            itemDescription = itemView.findViewById(R.id.itemDescriptionTextView);

            editItem = itemView.findViewById(R.id.editITemIcon);
            deleteItem = itemView.findViewById(R.id.deleteItemIcon);

            imagenUrl = itemView.findViewById(R.id.iconPreview);
            preview = itemView.findViewById(R.id.urlimagen);

            editItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onButtonCliked(view, items.get(getAdapterPosition()));
                }
            });

            deleteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onButtonCliked(view, items.get(getAdapterPosition()));
                }
            });

        }
    }

    public interface OnButtonClickedListener {
        void onButtonCliked(View v, Item item);
    }
}
