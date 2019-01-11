package com.example.senix.diablorip.fragments;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.senix.diablorip.adapters.ItemAdapter;
import com.example.senix.diablorip.adapters.ItemViewModel;
import com.example.senix.diablorip.data.DataBaseRoom;
import com.example.senix.diablorip.R;
import com.example.senix.diablorip.model.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class Fragment_personalizado extends Fragment implements ItemAdapter.OnButtonClickedListener{
    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private List<Item> itemList = new ArrayList<>();
    private DataBaseRoom dbRoom;
    private android.app.AlertDialog.Builder builder;
    private android.app.AlertDialog dialog;
    private ItemViewModel model;
    public Fragment_personalizado() {

    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personalizado, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        dbRoom= DataBaseRoom.getINSTANCE(getContext());

        adapter= new ItemAdapter(getContext(), itemList, this);

        recyclerView.setAdapter(adapter);

        model = ViewModelProviders.of(this.getActivity()).get(ItemViewModel.class);
        model.getItemList().observe(this.getActivity(), new Observer<List<Item>>() {
            @Override
            public void onChanged(@Nullable List<Item> items) {
                adapter.addItems(items);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopUp();
            }
        });

        return view;
    }

    private void createPopUp() {

        builder = new android.app.AlertDialog.Builder(getActivity());
        View view= LayoutInflater.from(this.getActivity()).inflate(R.layout.popup, null);

        builder.setView(view);

        dialog= builder.create();
        dialog.show();

        final EditText itemName= view.findViewById(R.id.popupItemName);
        final EditText itemDescription= view.findViewById(R.id.popupItemDescription);
        final EditText urlimagen= view.findViewById(R.id.urlimagen);
        Button saveButton= view.findViewById(R.id.popupSaveItemButton);
        final ImageView preview = view.findViewById(R.id.iconPreview);

        final String url = "https://blzmedia-a.akamaihd.net/d3/icons/items/large/sword_2h_206_demonhunter_male.png";
        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(urlimagen.getText().toString().equals("")){
                    Picasso.get().load(url).fit().into(preview);
                }else{
                    Picasso.get().load(urlimagen.getText().toString()).fit().into(preview);
                }
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(itemName.getText()) && !TextUtils.isEmpty(itemDescription.getText())){

                    saveItemDb(itemName.getText().toString(), itemDescription.getText().toString(), urlimagen.getText().toString());

                }

            }
        });
    }

    private void saveItemDb(String name, String description, String imagenurl) {

        Item item= new Item (name, description, imagenurl);
        model.addItem(item);
        dialog.dismiss();
    }

    private void editItem(final Item item) {



        builder = new android.app.AlertDialog.Builder(getActivity());
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.popup, null);

        builder.setView(view);

        dialog= builder.create();
        dialog.show();

        final EditText itemName= view.findViewById(R.id.popupItemName);
        final EditText itemDescription= view.findViewById(R.id.popupItemDescription);
        final EditText urlimagen= view.findViewById(R.id.urlimagen);
        Button saveButton= view.findViewById(R.id.popupSaveItemButton);
        final ImageView preview = view.findViewById(R.id.iconPreview);

        final String url = "https://blzmedia-a.akamaihd.net/d3/icons/items/large/sword_2h_206_demonhunter_male.png";
        preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(urlimagen.getText().toString().equals("")){
                    Picasso.get().load(url).fit().into(preview);
                }else{
                    Picasso.get().load(urlimagen.getText().toString()).fit().into(preview);
                }
            }
        });

        itemName.setText(item.getName());
        itemDescription.setText(item.getDescription());
        urlimagen.setText(item.getImagenUrl());

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(itemName.getText()) && !TextUtils.isEmpty(itemDescription.getText())){

                    item.setName(itemName.getText().toString());
                    item.setDescription(itemDescription.getText().toString());
                    item.setImagenUrl(urlimagen.getText().toString());

                    model.updateItem(item);

                }

                dialog.dismiss();

            }
        });


    }



    private void deleteItem(Item item) {
        model.deleteItem(item);
    }

    @Override
    public void onButtonCliked(View v, Item item) {

        if (v.getId()== R.id.editITemIcon) {
            Toast.makeText(getActivity(), "Editado", Toast.LENGTH_SHORT).show();
            editItem(item);
        } else if (v.getId()==R.id.deleteItemIcon) {
            deleteItem(item);

            Toast.makeText(getActivity(), "Eliminado ", Toast.LENGTH_SHORT).show();
        }
    }
}
