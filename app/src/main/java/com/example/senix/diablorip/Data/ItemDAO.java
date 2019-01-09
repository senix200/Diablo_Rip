package com.example.senix.diablorip.data;


import com.example.senix.diablorip.model.Item;


import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface ItemDAO {

    @Insert
    public long insertItem(Item item);

    @Update
    public int updateItem(Item item);

    @Delete
    public int deleteItem(Item item);

    @Query("SELECT * FROM ITEMS WHERE id=:id")
    public Item getItem(long id);

    @Query("SELECT * FROM ITEMS")
    LiveData<List<Item>> getItems();
}
