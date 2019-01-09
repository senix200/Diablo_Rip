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
import android.widget.Toast;

import com.example.senix.diablorip.adapters.ItemAdapter;
import com.example.senix.diablorip.adapters.ItemViewModel;
import com.example.senix.diablorip.data.DataBaseRoom;
import com.example.senix.diablorip.R;
import com.example.senix.diablorip.model.Item;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
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
        new GetAsyncItems().execute();
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

        builder = new AlertDialog.Builder(getActivity());
        View view= LayoutInflater.from(this.getActivity()).inflate(R.layout.popup, null);

        builder.setView(view);

        dialog= builder.create();
        dialog.show();

        final EditText itemName= view.findViewById(R.id.popupItemName);
        final EditText itemDescription= view.findViewById(R.id.popupItemDescription);
        Button saveButton= view.findViewById(R.id.popupSaveItemButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(itemName.getText()) && !TextUtils.isEmpty(itemDescription.getText())){

                    saveItemDb(itemName.getText().toString(), itemDescription.getText().toString());

                }

            }
        });
    }

    private void saveItemDb(String name, String description) {

        Item item= new Item (name, description);
        model.addItem(item);
        dialog.dismiss();
    }

    private void editItem(final int position) {

        final Item p= itemList.get(position);

        builder = new AlertDialog.Builder(getActivity());
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.popup, null);

        builder.setView(view);

        dialog= builder.create();
        dialog.show();

        final EditText itemName= view.findViewById(R.id.popupItemName);
        final EditText itemDescription= view.findViewById(R.id.popupItemDescription);
        Button saveButton= view.findViewById(R.id.popupSaveItemButton);

        itemName.setText(p.getName());
        itemDescription.setText(p.getDescription());

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(itemName.getText()) && !TextUtils.isEmpty(itemDescription.getText())){

                    p.setName(itemName.getText().toString());
                    p.setDescription(itemDescription.getText().toString());


                    model.updateItem(p);

                }

                dialog.dismiss();

            }
        });


    }


    private class GetAsyncItems extends AsyncTask<Void, Void, List<Item>> {


        @Override
        protected List<Item> doInBackground(Void... voids) {

            LiveData<List<Item>> products= dbRoom.itemdao().getItems();
            return (List<Item>) products;
        }

        @Override
        protected void onPostExecute(List<Item> products) {
            itemList.addAll(products);
            adapter.notifyDataSetChanged();
        }
    }
    private void deleteItem(int position) {
        Item p= itemList.get(position);


        model.deleteItem(p);
    }

    @Override
    public void onButtonCliked(View v, Item item) {

        if (v.getId()== R.id.editITemIcon) {
            Toast.makeText(getActivity(), "Edit", Toast.LENGTH_SHORT).show();

        } else if (v.getId()==R.id.deleteItemIcon) {


            Toast.makeText(getActivity(), "position: ", Toast.LENGTH_SHORT).show();
        }
    }
}
