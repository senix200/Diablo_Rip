package com.example.senix.diablorip.data;


import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;

import com.example.senix.diablorip.model.Item;

@Database(entities = {Item.class}, version=2, exportSchema = false)
public abstract class DataBaseRoom extends RoomDatabase {

    public abstract ItemDAO itemdao();
    private static DataBaseRoom INSTANCE=null;

    public static DataBaseRoom getINSTANCE(final Context context){
        if (INSTANCE==null){
            INSTANCE= Room.databaseBuilder(context.getApplicationContext(), DataBaseRoom.class, "Items.db").fallbackToDestructiveMigration().build();
        }

        return INSTANCE;
    }
    public static void destroyInstance(){
        INSTANCE=null;
    }
}
