package com.example.senix.diablorip.fragments;

import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.senix.diablorip.adapters.ItemAdapter;
import com.example.senix.diablorip.data.DataBaseRoom;
import com.example.senix.diablorip.R;
import com.example.senix.diablorip.model.Item;

import java.util.ArrayList;
import java.util.List;

public class Fragment_personalizado extends Fragment implements ItemAdapter.OnButtonClickedListener{
    private RecyclerView recyclerView;
    private ItemAdapter adapter;
    private List<Item> itemList = new ArrayList<>();
    private DataBaseRoom dbRoom;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    public Fragment_personalizado() {

    }

    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personalizado, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        dbRoom= DataBaseRoom.getINSTANCE(getContext());
        new GetAsyncItems().execute();

        adapter= new ItemAdapter(getContext(), itemList, this);

        recyclerView.setAdapter(adapter);

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
        View view= LayoutInflater.from(getActivity()).inflate(R.layout.popup, null);

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
        new AsyncAddItemDB().execute(item);
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


                    new AsyncEditItemDB(position).execute(p);

                }

                dialog.dismiss();

            }
        });


    }
    private void deleteItem(int position) {
        Item p= itemList.get(position);


        new AsynDeleteItemDB(position).execute(p);
    }

    private class GetAsyncItems extends AsyncTask<Void, Void, List<Item>> {


        @Override
        protected List<Item> doInBackground(Void... voids) {

            List<Item> products= dbRoom.itemdao().getItems();
            return products;
        }

        @Override
        protected void onPostExecute(List<Item> products) {
            itemList.addAll(products);
            adapter.notifyDataSetChanged();
        }
    }


    private class AsyncAddItemDB  extends AsyncTask< Item, Void, Long> {

        Item item;

        @Override
        protected Long doInBackground(Item... items) {

            long id=-1;

            if (items.length!=0) {
                String name = items[0].getName();
                Log.d("Product", name);
                item = items[0];
                id = dbRoom.itemdao().insertItem(items[0]);
                item.setId(id);
            }

            return id;
        }

        @Override
        protected void onPostExecute(Long id) {
            if (id == -1){
                Snackbar.make(recyclerView, "Error adding item", Snackbar.LENGTH_LONG)
                        .show();
            } else {
                Snackbar.make(recyclerView, "Item added", Snackbar.LENGTH_LONG)
                        .show();
                itemList.add(0,item);
                adapter.notifyItemInserted(0);
            }
        }
    }


    private class AsyncEditItemDB extends AsyncTask<Item, Void, Integer> {

        private int position;

        public AsyncEditItemDB(int position) {
            this.position = position;
        }

        @Override
        protected Integer doInBackground(Item... items) {
            int updatedrows=0;
            if (items.length!=0) {

                updatedrows=dbRoom.itemdao().updateItem(items[0]);

            }

            return updatedrows;
        }

        @Override
        protected void onPostExecute(Integer updatedRows) {
            if (updatedRows == 0){
                Snackbar.make(recyclerView, "Error updating item", Snackbar.LENGTH_LONG)
                        .show();
            } else {
                Snackbar.make(recyclerView, "Item updated", Snackbar.LENGTH_LONG)
                        .show();
                adapter.notifyItemChanged(position);
            }
        }
    }


    private class AsynDeleteItemDB extends AsyncTask <Item, Void, Integer> {
        private int position;
        public AsynDeleteItemDB(int position) {
            this.position=position;
        }

        @Override
        protected Integer doInBackground(Item... items) {

            int deletedrows=0;

            if (items.length!=0) {

                deletedrows=dbRoom.itemdao().deleteItem(items[0]);

            }

            return deletedrows;

        }

        @Override
        protected void onPostExecute(Integer deletedRows) {
            if (deletedRows == 0){
                Snackbar.make(recyclerView, "Error deleting item", Snackbar.LENGTH_LONG)
                        .show();
            } else {
                Snackbar.make(recyclerView, "Item deleted", Snackbar.LENGTH_LONG)
                        .show();
                itemList.remove(position);
                adapter.notifyItemRemoved(position);
                adapter.notifyItemRangeRemoved(position, adapter.getItemCount());
            }
        }
    }

    @Override
    public void onButtonCliked(View v, int position) {

        if (v.getId()== R.id.editITemIcon) {
            Toast.makeText(getActivity(), "Edit", Toast.LENGTH_SHORT).show();
            editItem(position);
        } else if (v.getId()==R.id.deleteItemIcon) {
            deleteItem(position);

            Toast.makeText(getActivity(), "position: "+ position, Toast.LENGTH_SHORT).show();
        }
    }

}
