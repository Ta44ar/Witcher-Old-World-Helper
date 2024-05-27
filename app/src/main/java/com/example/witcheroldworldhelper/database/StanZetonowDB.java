package com.example.witcheroldworldhelper.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {StanZetonow.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class StanZetonowDB extends RoomDatabase {
    public abstract StanZetonowDao stanZetonowDao();


}
