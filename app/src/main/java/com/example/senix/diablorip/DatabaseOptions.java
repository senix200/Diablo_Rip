package com.example.senix.diablorip;

/**
 * Created by Zoom on 10/1/2018.
 */

public class DatabaseOptions {

    public static  String DB_NAME = "diablorip.db";
    public static  int DB_VERSION = 1;

    public static String USERS_TABLE = "users";

    public static  String ID = "id";
    public static  String EMAIL = "email";
    public static  String PASSWORD = "password";

    public static String CREATE_USERS_TABLE_ =
            "CREATE TABLE  " + USERS_TABLE + "(" +
                    ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    EMAIL + " TEXT NOT NULL," +
                    PASSWORD + " TEXT );";

}
