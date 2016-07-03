package com.example.micheal.gattify;

import android.content.Context;

/**
 * Helper class to perform DB-related functions
 */
public class DBUtils {

    // Pass the application context via getApplicationContext(); to delete the DB
    public static void deleteAll(Context con){
        con.deleteDatabase("sugar_example.db");
        con.deleteDatabase("sugar_example.db-journal");
    }
}
