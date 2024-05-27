package com.example.witcheroldworldhelper.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface StanZetonowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void updateStanZetnow(StanZetonow stanZetonow);

    @Query("Select * from stanzetonow")
    StanZetonow getStanZetonow();

}
