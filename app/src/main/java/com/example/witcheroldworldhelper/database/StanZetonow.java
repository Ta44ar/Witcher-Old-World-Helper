package com.example.witcheroldworldhelper.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

@Entity
public class StanZetonow {
    @PrimaryKey
    public int id;
    @ColumnInfo(name = "las")
    public ArrayList<Integer> las;
    @ColumnInfo(name = "woda")
    public ArrayList<Integer> woda;
    @ColumnInfo(name = "gory")
    public ArrayList<Integer> gory;
    @ColumnInfo(name = "zajety_las")
    public ArrayList<Integer> zajetyLas;
    @ColumnInfo(name = "zajeta_woda")
    public ArrayList<Integer> zajetaWoda;
    @ColumnInfo(name = "zajete_gory")
    public ArrayList<Integer> zajeteGory;

    public StanZetonow() {

    }

    public StanZetonow(ArrayList<Integer> las, ArrayList<Integer> woda, ArrayList<Integer> gory, ArrayList<Integer> zajetyLas, ArrayList<Integer> zajetaWoda, ArrayList<Integer> zajeteGory) {
        this.las = las;
        this.woda = woda;
        this.gory = gory;
        this.zajetyLas = zajetyLas;
        this.zajetaWoda = zajetaWoda;
        this.zajeteGory = zajeteGory;
    }
}
