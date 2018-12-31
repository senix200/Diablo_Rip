package com.example.senix.diablorip.Data;

import android.provider.BaseColumns;

public class ItemContract {

    private ItemContract(){

    }

    public static class ItemEntry implements BaseColumns{
        public static final String TABLE_NAME = "items";
        public static final String COLUMN_NAME_ITEM_NAME = "name";
        public static final String COLUMN_NAME_ITEM_DESCRIPTION = "description";
    }
}
