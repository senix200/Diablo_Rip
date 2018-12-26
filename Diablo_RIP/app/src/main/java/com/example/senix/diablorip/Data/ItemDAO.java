package com.example.senix.diablorip.Data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.senix.diablorip.model.Item;

import java.util.List;

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
    public List<Item> getItems();
}
