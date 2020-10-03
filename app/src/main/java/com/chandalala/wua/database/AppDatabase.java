package com.chandalala.wua.database;

/*
*   *   Database class
 *       Contains the database holder and serves as the main access point for the underlying connection to your app's persisted relational data
 *       It must be an Abstract class that extends RoodDatabase
 *       It must have an annotation called Database with two annotation properties(Entities and version)
 *       Contain an abstract method that has 0 arguments and returns an object of data access object class(Class with annotation @AppDao)
 *       At runtime you can acquire an instance of Database by calling Room.databaseBuilder() or Room.inMemoryDatabaseBuilder()
* */

import android.app.Activity;

import androidx.fragment.app.Fragment;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// This class creates sqlite database using ROOMDATABASE

@Database(entities={StudentTable.class, PaymentHistoryTable.class, Courses_table.class, ResultsTable.class, Timetable_table.class},version = 6)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance; // To avoid creation of multiple instances of the same database we turn this class into a singleton

    public abstract AppDao app_dao();

    //Synchronised means only one thread can access this method at any given time
    public static synchronized AppDatabase getInstance(Fragment fragment){
        if (instance == null){
            instance = Room.databaseBuilder(fragment.getActivity(),AppDatabase.class,"app_database")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public static synchronized AppDatabase getInstance(Activity activity){
        if (instance == null){
            instance = Room.databaseBuilder(activity.getApplicationContext(),AppDatabase.class,"app_database")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }




}



