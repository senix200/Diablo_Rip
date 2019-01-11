package com.example.senix.diablorip.adapters;

import android.app.Application;
import android.os.AsyncTask;
import androidx.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.senix.diablorip.data.DataBaseRoom;
import com.example.senix.diablorip.model.Item;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class ItemViewModel extends AndroidViewModel {

    private LiveData<List<Item>> itemList;
    private static DataBaseRoom db;

    public ItemViewModel(@NonNull Application application) {
        super(application);
        db = DataBaseRoom.getINSTANCE(application);
        itemList = db.itemdao().getItems();
    }

    public LiveData<List<Item>> getItemList(){
        return itemList;
    }

    public void addItem (Item item){
        new AsyncAddItemDB().execute(item);
    }

    public void updateItem(Item item){
        new AsyncEditItemDB().execute(item);
    }

    public void deleteItem(Item item){
        new AsynDeleteItemDB().execute(item);
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
                id = db.itemdao().insertItem(items[0]);
                item.setId(id);
            }

            return id;
        }

        @Override
        protected void onPostExecute(Long id) {
            if (id == -1){
                Toast.makeText(getApplication(), "Error adding item", Toast.LENGTH_LONG)
                        .show();
            } else {
                Toast.makeText(getApplication(), "Item added", Toast.LENGTH_LONG)
                        .show();
                //itemList.add(0,item);
                //adapter.notifyItemInserted(0);
            }
        }
    }

    private class AsyncEditItemDB extends AsyncTask<Item, Void, Integer> {



        public AsyncEditItemDB() {

        }

        @Override
        protected Integer doInBackground(Item... items) {
            int updatedrows=0;
            if (items.length!=0) {

                updatedrows=db.itemdao().updateItem(items[0]);

            }

            return updatedrows;
        }

        @Override
        protected void onPostExecute(Integer updatedRows) {
            if (updatedRows == 0){
                Toast.makeText(getApplication(),  "Error updating item", Toast.LENGTH_LONG)
                        .show();
            } else {
                Toast.makeText(getApplication(),  "Item updated", Toast.LENGTH_LONG)
                        .show();
                //adapter.notifyItemChanged(position);
            }
        }
    }


    private class AsynDeleteItemDB extends AsyncTask <Item, Void, Integer> {

        public AsynDeleteItemDB() {

        }

        @Override
        protected Integer doInBackground(Item... items) {

            int deletedrows=0;

            if (items.length!=0) {

                deletedrows=db.itemdao().deleteItem(items[0]);

            }

            return deletedrows;

        }

        @Override
        protected void onPostExecute(Integer deletedRows) {
            if (deletedRows == 0){
                Toast.makeText(getApplication(), "Error deleting item", Toast.LENGTH_LONG)
                        .show();
            } else {
                Toast.makeText(getApplication(), "Item deleted", Toast.LENGTH_LONG)
                        .show();
                //itemList.remove(position);
                //adapter.notifyItemRemoved(position);
                //adapter.notifyItemRangeRemoved(position, adapter.getItemCount());
            }
        }
    }


}
