package com.example.micheal.assingment2;

import android.content.Context;

/**
 * Created by Micheal on 28/04/16.
 */
public class DBUtils {

    // Pass the application context via getApplicationContext(); to delete the DB
    public static void deleteAll(Context con){
        con.deleteDatabase("sugar_example.db");
        con.deleteDatabase("sugar_example.db-journal");
    }
}
